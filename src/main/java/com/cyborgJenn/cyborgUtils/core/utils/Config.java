package com.cyborgJenn.cyborgUtils.core.utils;

import java.io.File;

import com.cyborgJenn.cyborgUtils.CyborgUtils;

import net.minecraftforge.common.config.Configuration;

public class Config {

	/* Modules */
	public static boolean 	enableModuleCommands;
	public static boolean 	enableModuleMOTD;
	public static boolean 	enableModuleAccessories;
	public static boolean 	enableModuleLargeCaves;
	public static boolean 	enableModuleMachine;
	public static boolean 	enableModuleMiniDungons;
	public static boolean	enableModuleVillage;
	public static boolean   enableModuleCombat;
	/* Commands */
	public static boolean 	requireOpTps;
	public static boolean 	requireOpKillSelf;
	public static boolean 	requireOpSpawn;
	/* Entity Options */
	public static double 	NMCreeperHealth;
	public static int 		NMCreeperBoostTime;
	public static int 		NMFuseTime;
	public static int 		NMBlastRadius;
	public static boolean 	NMPotionEffect;
	public static boolean 	LMFireEffect;
	/* Debug Options */
	public static boolean 	enableDebugging;
	public static boolean 	enableStructureDebug;
	/* Cave Options */
	public static int 		largeNodeMultiplier;
	public static int 		largeNodeFrequency;
	public static int 		nodeMultiplier;
	public static int 		nodeFrequency;
	public static double 	caveTunnelSizeVar1;
	public static double 	caveTunnelSizeVar2;
	public static double 	caveVar3;
	/* Village Options */
	public static boolean 	enableVillageStable;
	/* Recipe Options */
	public static boolean useThaumcraftRecipes;
	
	public static void init(File file)
	{
		Configuration config = new Configuration(file);
		try
		{
			config.load();
			configModules(config);
			configCommandsOptions(config);
			configEntityOptions(config);
			configlargeCaveOptions(config);
			configVillageOptions(config);
			configDebugOptions(config);
			configRecipeOptions(config);
		}
		catch (Exception e)
		{
			CyborgUtils.logger.fatal("CyborgUtils has had a problem loading its configuration file", new Object[0],e);
		}
		finally
		{
			if (config.hasChanged()) {
				config.save();
			}
		}
	}

	private static void configModules(Configuration config) 
	{
		String configModules = "Enable or Disable Modules";
		config.addCustomCategoryComment(configModules, "These settings allow you to disable sections of this mod, false means that module will be disaabled");
		enableModuleCommands 	= config.get(configModules, "Enable Commands Module", true).getBoolean();
		enableModuleMOTD 		= config.get(configModules, "Enable MOTD Module", true).getBoolean();
		enableModuleAccessories = config.get(configModules, "Enable Accessories Module", true).getBoolean();
		enableModuleLargeCaves	= config.get(configModules, "Enable Large Caves Module", true).getBoolean();
		enableModuleMachine		= config.get(configModules, "Enable Machine Module", true).getBoolean();
		enableModuleMiniDungons = config.get(configModules, "Enable MiniDungeons Module", true).getBoolean();
		enableModuleVillage 	= config.get(configModules, "Enable Village Module", true).getBoolean();
		enableModuleCombat		= config.get(configModules, "Enable Combat Module", true).getBoolean();
	}
	private static void configCommandsOptions(Configuration config)
	{
		String commandsOptions 		= "Command Options";
		config.addCustomCategoryComment(commandsOptions, "Permissions and related settings for commands, as part of the command module.");
		requireOpTps 		= config.get(commandsOptions, "require Op Tps", true).getBoolean(true);
		requireOpKillSelf 	= config.get(commandsOptions, "require Op Kill Self", true).getBoolean(true);
		requireOpSpawn 		= config.get(commandsOptions, "require Op Spawn", true).getBoolean(true);
	} 
	private static void configEntityOptions(Configuration config)
	{
		String entityOptions = "Entity Options";
		config.addCustomCategoryComment(entityOptions, "Setting for Mod Entities , Mob Health , effects , etc");
		NMFuseTime 			= config.get(entityOptions, "NightmareCreeper FuseTime", 20, "Fuse Time for the Nightmare Creeper").getInt();
		NMBlastRadius 		= config.get(entityOptions, "NightmareCreeper Blast Radius", 5,"Blast Radius for the Nightmare Creeper, this # is doubled if charged.").getInt();
		NMPotionEffect 		= config.getBoolean("NightmareCreeper Potion effect", entityOptions, true, "Whether or not the Nightmare Creeper causes potions effect to players caught in the blast radius.");
		NMCreeperBoostTime 	= config.get(entityOptions, "NightmareCreeper BoostTime", 30, "Time in ticks that this creeper gets a speedboost after being hit by indirect source (arrow, etc).").getInt();
		NMCreeperHealth 	= config.get(entityOptions,"NightmareCreeper Health", 60.0D , "This Creepers BaseHealth").getDouble();
		LMFireEffect = config.getBoolean("LavaMite Fire effect", entityOptions, true, "Whether or not the LavaMite sets players on fire.");
	}
	private static void configlargeCaveOptions(Configuration config)
	{
		String caveSettings = "Cave Settings";
		config.addCustomCategoryComment(caveSettings, "Setting for Controlling the size and frequemcy of Caves");
		largeNodeMultiplier = config.get(caveSettings, "Large Node Multiplier", 16, "MC is 5 by default. A larger number here gives largeNodes more girth").getInt();
		largeNodeFrequency 	= config.get(caveSettings, "Large Node Frequency", 2, "MC is 4 by default. A lower number here makes them more frequent.").getInt();
		nodeMultiplier 		= config.get(caveSettings, "Node Multiplier", 9, "MC is 3 by default. A larger number here gives Nodes more girth").getInt();
		nodeFrequency 		= config.get(caveSettings, "Node Frequency", 8, "MC is 10 by default. A lower number here makes them more frequent.").getInt();
		caveTunnelSizeVar1 	= config.getFloat("Tunnel_Var1", caveSettings, 4F, 0.5F, 100F, "MC is 0.5 by default. A larger number here means more variation in cave tunnel size.");
		caveTunnelSizeVar2 	= config.getFloat("Tunnel_Var2", caveSettings, 8F, 0.5F, 100F, "MC is 0.5 by default. A larger number here means more variation in cave tunnel size.");
		caveVar3 			= config.get(caveSettings, "CaveVar3", 2, "Do not Change this").getDouble();
	}
	private static void configVillageOptions(Configuration config)
	{
		String villageSettings = "Village Settings";
		config.addCustomCategoryComment(villageSettings, "Use these settings to Enable or Disable Village Options");
		enableVillageStable = config.get(villageSettings, "Enable Village Stable", true).getBoolean();
	}
	private static void configDebugOptions(Configuration config){
		String debug = "Dev Debug Options";
		config.addCustomCategoryComment(debug, "Use these settings to Enable Dev Debug Options");
		enableDebugging 		= config.get(debug, "enable Debugging", false).getBoolean();
		enableStructureDebug 	= config.get(debug, "enableStructureDebug", false).getBoolean();

	}
	private static void configRecipeOptions(Configuration config)
	{
		String recipes = "Recipe Options";
		config.addCustomCategoryComment(recipes, "These settings change which recipes and recipe types are loadedand used.");
		useThaumcraftRecipes	= config.get(recipes, "use Thaumcraft Recipes", true).getBoolean();
	}
}
