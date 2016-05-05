package com.cyborgJenn.cyborgUtils.module.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.core.utils.Config;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CommandTps extends Command {
	
	@SuppressWarnings("rawtypes")
	private final List aliases;
	private static DecimalFormat floatfmt = new DecimalFormat("##0.00");
	private static final int MAX_TPS = 20;
	private static final int MIN_TICKMS = 1000 / MAX_TPS;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CommandTps() 
	{
		super("cu_tps");
		aliases = new ArrayList();
		aliases.add("CU_TPS");
		aliases.add("cu tps");
	}
	@Override
	public int compareTo(ICommand o) 
	{
		return 0;
	}

	@Override
	public String getCommandName() 
	{
		return "cu_tps";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		return "/"+sender+" [worldid|{o}]";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCommandAliases() 
	{
		return this.aliases;
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
		return Config.requireOpTps ? super.checkPermission(server, sender) : true;
    }
	
	private double getTickTimeSum(long[] times) {
        long timesum = 0L;
        if (times == null) return 0;
        for (int i = 0; i < times.length; i++) {
        	timesum += times[i];
        }

        return (double)(timesum / times.length);
    }
	
	private double getTickMs(World world) {
		return getTickTimeSum(world == null ? CyborgUtils.server.tickTimeArray : CyborgUtils.server.worldTickTimes.get(world.provider.getDimension())) * 1.0E-6D;
	}
	
	private double getTps(World world) {
		double tps = 1000 / getTickMs(world);
		return tps > MAX_TPS ? MAX_TPS : tps;
	}
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 1) {
			double tps = getTps(null);
			sender.addChatMessage(new TextComponentString("§9Overall: §a"+floatfmt.format(tps)+" §9TPS (§a"+(int)((tps / MAX_TPS) * 100)+"§a%§9)"));
			
			for (World world : CyborgUtils.server.worldServers) {
				tps = getTps(world);
				sender.addChatMessage(new TextComponentString("§9World §a"+world.provider.getDimension()+"§9: §a" + floatfmt.format(tps) + "§9 TPS (§a" + (int)((tps / MAX_TPS) * 100) + "§a%§9) " /*+ "[§a" + world.provider.getDimensionName() + "§9]"*/));
			}
		} else if (args[0].toLowerCase().charAt(0) == 'o') {
			double tickms = getTickMs(null);
			double tps = getTps(null);
			
			sender.addChatMessage(new TextComponentString("§9Overall server tick"));
			sender.addChatMessage(new TextComponentString("§9TPS: §a"+floatfmt.format(tps)+" §9TPS of §a"+floatfmt.format(MAX_TPS)+" §9TPS (§a"+(int)((tps / MAX_TPS) * 100)+"§a%§9)"));
			sender.addChatMessage(new TextComponentString("§9Tick time: §a"+floatfmt.format(tickms)+"§9 ms of §a"+floatfmt.format(MIN_TICKMS)+" §9ms"));
		} 
		
		else {
			int dim;
			try {
				dim = Integer.parseInt(args[0]);
			} catch (Throwable e) {
				throw showUsage(sender);
			}
			World world = CyborgUtils.server.worldServerForDimension(dim);
			if (world == null) throw new PlayerNotFoundException("World not found");
			double tickms = getTickMs(world);
			double tps = getTps(world);
			sender.addChatMessage(new TextComponentString("§9Dimension: §a"+ world.provider.getDimension() /*+ "§9: " + world.provider.getDimensionName()*/));
			sender.addChatMessage(new TextComponentString("§9TPS: §a"+ floatfmt.format(tps) + " §9TPS of §a" + floatfmt.format(MAX_TPS)+" §9TPS (§a" + (int)((tps / MAX_TPS) * 100) + "§a%§9)"));
			sender.addChatMessage(new TextComponentString("§9Tick time: §a"+ floatfmt.format(tickms) + " §9ms of §a" + floatfmt.format(MIN_TICKMS) + " §9ms"));
			//TODO sender.addChatMessage(new TextComponentString("§9Loaded chunks: §a"+ world.getChunkProvider().getLoadedChunkCount()));
			sender.addChatMessage(new TextComponentString("§9Entities: §a"+ world.loadedEntityList.size()));
			sender.addChatMessage(new TextComponentString("§9Tile entities: §a"+ world.loadedTileEntityList.size()));
		}
		
	}
}
