package com.cyborgJenn.cyborgUtils.module.motd;

import com.cyborgJenn.cyborgUtils.module.commands.Command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandMotd extends Command{

private final Motd motd;
	
	public CommandMotd(Motd motd)
	{
		super("motd");
		
		this.motd = motd;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/"+name;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		motd.serveMotd(sender);
	}
}
