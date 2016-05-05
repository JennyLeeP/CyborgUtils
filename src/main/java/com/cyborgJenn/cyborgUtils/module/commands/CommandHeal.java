package com.cyborgJenn.cyborgUtils.module.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.module.commands.CommandKill.EntityDummy;
import com.cyborgJenn.cyborgUtils.module.commands.CommandKill.ForcedDamageSource;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandHeal extends Command {
	@SuppressWarnings("rawtypes")
	private final List aliases;

	@SuppressWarnings("rawtypes")
	public CommandHeal() {
		super("heal");
		aliases = new ArrayList(); 
		//aliases.add("conjure"); 
		//aliases.add("conj"); 
	}
	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/" + getCommandName() + " <player>";
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCommandAliases()
	{
		return this.aliases;
	}
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		EntityPlayer player = null;
		 float amount = Integer.MIN_VALUE;
		 
		 if (args.length == 0) {
			 player = getCommandSenderAsPlayer(sender);
		 } else if (args.length == 1) {
			 try {
				 player = getCommandSenderAsPlayer(sender);
				 amount = Integer.parseInt(args[0]);
			 } catch (NumberFormatException e) {
				 player = CyborgUtils.server.getPlayerList().getPlayerByUsername(args[0]);
			 }
		 } else if (args.length > 1) {
			 player = CyborgUtils.server.getPlayerList().getPlayerByUsername(args[0]);
			 amount = parseInt(args[1]);
		 }
		 
		 if (player == null) throw new PlayerNotFoundException();
		 if (amount == Integer.MIN_VALUE) amount = player.getMaxHealth() - player.getHealth();
		 
		 player.heal(amount);
		 if (player.getHealth() < 1) {
			 if (sender == player) player.onDeath(ForcedDamageSource.forced);
			 else player.onDeath(ForcedDamageSource.causeEntityDamage(sender instanceof Entity ? (Entity)sender : new EntityDummy(sender.getName())));
		 }
		 
		 notifyOperators(sender, this, "Healed "+player.getName()+" by "+amount);
		

	}
	
	
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
	
	public static Optional<EntityPlayerMP> getPlayerForUsername(final String username) {
		Objects.requireNonNull(username, "username");
		return getAllPlayers().stream()
				.filter(player -> username.equalsIgnoreCase(player.getName()))
				.findFirst();
	}
	public static List<EntityPlayerMP> getAllPlayers() {
		return CyborgUtils.server.getPlayerList().getPlayerList();
	}
	
}
