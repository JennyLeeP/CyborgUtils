package com.cyborgJenn.cyborgUtils.api;

import java.lang.reflect.Method;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.common.FMLLog;

public class AccessoriesAPI
{
	static Method getAccessories;

	/**
	 * Retrieves the accessories inventory for the supplied player
	 */
	public static IInventory getAccessories(EntityPlayer player)
	{
		IInventory ot = null;

		try
		{
			if(getAccessories == null) 
			{
				Class<?> fake = Class.forName("com.cyborgJenn.cyborgUtils.core.handlers.PlayerHandler");
				getAccessories = fake.getMethod("getPlayerAccessories", EntityPlayer.class);
			}

			ot = (IInventory) getAccessories.invoke(null, player);
		} 
		catch(Exception ex) 
		{ 
			FMLLog.warning("[Accessories API] Could not invoke com.cyborgJenn.CyborgUtils.handlers.PlayerHandler method getPlayerAccessories");
		}

		return ot;
	}
}
