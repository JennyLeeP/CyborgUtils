package com.cyborgJenn.cyborgUtils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cyborgJenn.cyborgUtils.core.handlers.CyborgEventHandler;
import com.cyborgJenn.cyborgUtils.core.handlers.EventHandlerEntity;
import com.cyborgJenn.cyborgUtils.core.item.ModItems;
import com.cyborgJenn.cyborgUtils.core.network.PacketHandler;
import com.cyborgJenn.cyborgUtils.core.proxy.CommonProxy;
import com.cyborgJenn.cyborgUtils.core.utils.Config;
import com.cyborgJenn.cyborgUtils.core.utils.CyborgUtilsTab;
import com.cyborgJenn.cyborgUtils.core.utils.ListEntities;
import com.cyborgJenn.cyborgUtils.core.utils.Reference;
import com.cyborgJenn.cyborgUtils.module.commands.CommandHeal;
import com.cyborgJenn.cyborgUtils.module.commands.CommandKill;
import com.cyborgJenn.cyborgUtils.module.commands.CommandKillall;
import com.cyborgJenn.cyborgUtils.module.commands.CommandTps;
import com.cyborgJenn.cyborgUtils.module.largeCaves.LargeCaveGen;
import com.cyborgJenn.cyborgUtils.module.motd.CommandMotd;
import com.cyborgJenn.cyborgUtils.module.motd.Motd;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(
		modid = Reference.MODID, 
		name = Reference.NAME, 
		version = Reference.VERSION,
		dependencies="required-after:Forge@[11.15.0,);")

public class CyborgUtils {

	@Instance(value = Reference.MODID)
	public static CyborgUtils instance;

	@SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.SERVERPROXY)
	public static CommonProxy proxy;

	public static CreativeTabs 		tabCyborgCore = new CyborgUtilsTab(CreativeTabs.getNextID(), "tabCyborgUtils");
	public static final Logger 		logger = LogManager.getLogger(Reference.NAME);
	public static int 				dimension;
	public static LargeCaveGen 		caveGen;
	public static MinecraftServer 	server;
	public static boolean 			firstStart = true;
	private static Path 			configDir;
	
	@SuppressWarnings("unused")
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)throws IOException
	{
		event.getModMetadata().version = Reference.VERSION;
		Config.init(event.getSuggestedConfigurationFile());

		File modConfigDir = event.getModConfigurationDirectory();
		configDir = modConfigDir.toPath().resolve("CyborgUtils");
		Files.createDirectories(configDir);

		PacketHandler.init();
		//EventHandlerEntity entityEventHandler = new EventHandlerEntity();
		//CyborgEventHandler cyborgEventHandler = new CyborgEventHandler(); 
		MinecraftForge.EVENT_BUS.register(new EventHandlerEntity());
		MinecraftForge.EVENT_BUS.register(new CyborgEventHandler());
		if (Config.enableModuleLargeCaves)
		{
			caveGen = new LargeCaveGen(); 
			MinecraftForge.TERRAIN_GEN_BUS.register(new CyborgEventHandler()); 

		}

		logger.info("Pre Init Complete..........");
	}

	@EventHandler
	public void Init(FMLInitializationEvent event)
	{

		if(Config.enableModuleAccessories)
		{
			ModItems.initAccessoryItems();
			ModItems.registerAccessoryItems();
			proxy.registerAccessoryItemRenderers();
		}
		if(Config.enableModuleCombat)
		{
			ModItems.initCombatItems();
			ModItems.registerCombatItems();
			proxy.registerCombatItemRenderers();
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		proxy.registerKeyBindings();
		proxy.registerRenderers();
		logger.info("Init Complete.............");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		new ListEntities();
		logger.info("Post Init Complete.............");
	}

	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event) throws IOException
	{
		server = event.getServer();
		if (Config.enableModuleCommands)
		{
			event.registerServerCommand(new CommandTps());
			event.registerServerCommand(new CommandHeal());
			event.registerServerCommand(new CommandKillall());
			event.registerServerCommand(new CommandKill());
			//event.registerServerCommand(new CommandInventory());
			// event.registerServerCommand(new CommandSpawn());
			//event.registerServerCommand(new CommandEntity());
			//event.registerServerCommand(new CommandHome());
		}
		if (Config.enableModuleMOTD) {
			final Motd motd = new Motd(configDir.resolve("motd.txt"));
			CommandMotd motdCommand = new CommandMotd(motd);
			event.registerServerCommand(motdCommand);
			MinecraftForge.EVENT_BUS.register(motd);
		}
	}
	
}
