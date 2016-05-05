package com.cyborgJenn.cyborgUtils.core.item;

import com.cyborgJenn.cyborgUtils.core.utils.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ItemRenderRegister 
{

	public static void registerAccessoryItemRenderer()
	{
		register(ModItems.FancyCompass);
		register(ModItems.ClimbingClaws);
		register(ModItems.ClimbingGear);
		register(ModItems.DepthMeter);
		register(ModItems.GPS);
		register(ModItems.Sextant);
		register(ModItems.ShoeSpikes);
		register(ModItems.StopWatch);
		register(ModItems.TallyCounter);
		register(ModItems.BalloonPufferfish);
		register(ModItems.SailfishBoots);
		register(ModItems.FlightBoost);
		register(ModItems.Flippers);
		register(ModItems.SwimGoggles);
		register(ModItems.LuckyHorshoe);
	}

	public static void registerCombatItemRenderer()
	{
		register(ModItems.Sword1);
		register(ModItems.Sword2);
		register(ModItems.Sword3);
		register(ModItems.Sword4);
		register(ModItems.Sword5);
	}

	public static void register(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}


}
