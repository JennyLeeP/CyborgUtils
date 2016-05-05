package com.cyborgJenn.cyborgUtils.core.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;

public class SoundHandler 
{
	public SoundHandler()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void onSoundsLoad(SoundLoadEvent event)
	{

	}

	public static void onEntityPlay(String name,World world,Entity entityName,float volume ,float pitch)
	{
		//world.plSoundAtEntity(entityName,(Reference.MODID + ":" + name), (float)volume,(float) pitch);
	}
}
