package com.cyborgJenn.cyborgUtils.module.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public abstract class Command extends CommandBase{

	public final String name;
	
	public Command(String name)
	{
		this.name = name;
	}
	@Override
	public String getCommandName()
	{
		return name;
	}
	@Override
	public abstract String getCommandUsage(ICommandSender var1);
	
	WrongUsageException showUsage(ICommandSender var1)
	{
		return new WrongUsageException(getCommandUsage(var1));
	}
	@SuppressWarnings("rawtypes")
	public List addTabCompletionOptions(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean canCommandSenderUseCommand(ICommandSender var1) {
		// TODO Auto-generated method stub
		return true;
	}
}
