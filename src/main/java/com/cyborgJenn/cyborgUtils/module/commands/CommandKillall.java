package com.cyborgJenn.cyborgUtils.module.commands;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.google.common.base.Joiner;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommandKillall extends Command
{
	public CommandKillall()
	{
		super("killall");
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/" + getCommandName() + " <entity>";
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		return args.length <= 1 ? getListOfStringsMatchingLastWord(args, EntityList.stringToClassMapping.keySet()) : null;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length < 1)
			throw new WrongUsageException(getCommandUsage(sender));

		Optional<String> targetName = EntityList.stringToClassMapping.keySet().stream()
				.filter(entityName -> entityName.equalsIgnoreCase(Joiner.on(' ').join(args)))
				.findAny();

		if (!targetName.isPresent()) {
			throw new CommandException("That entity type doesn't exist");
		}

		final String entityName = targetName.get();

		final AtomicInteger removed = new AtomicInteger(0); // Boo. Can't increment ints within a foreach
		for (World world : CyborgUtils.server.worldServers) {
			world.loadedEntityList.stream()
			.filter(entity -> !(entity instanceof EntityPlayerMP))
			.filter(entity -> EntityList.getEntityString(entity).equalsIgnoreCase(entityName))
			.forEach(entity -> {
				world.removeEntity(entity);
				removed.incrementAndGet();
			});
		}

		notifyOperators(sender, this, "Removed %s instances of %s", removed.intValue(), entityName);
	
		
	}

}
