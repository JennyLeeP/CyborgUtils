package com.cyborgJenn.cyborgUtils.core.handlers;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.api.AccessoriesAPI;
import com.cyborgJenn.cyborgUtils.api.IAccessory;
import com.cyborgJenn.cyborgUtils.core.item.ModItems;
import com.cyborgJenn.cyborgUtils.module.accessories.inventory.InventoryAccessories;
import com.google.common.io.Files;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class EventHandlerEntity {
	static HashSet<Integer> syncSchedule = new HashSet<Integer>();
	@SubscribeEvent
	public void playerTick(PlayerEvent.LivingUpdateEvent event)
	{
		// player events
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();

			if (!syncSchedule.isEmpty() && syncSchedule.contains(player.getEntityId()))
			{
				CyborgEventHandler.syncaccessories(player);
				syncSchedule.remove(player.getEntityId());
			}

			InventoryAccessories accessories = PlayerHandler.getPlayerAccessories(player);
			for (int a = 0; a < accessories.getSizeInventory(); a++)
			{
				if (accessories.getStackInSlot(a) != null
						&& accessories.getStackInSlot(a).getItem() instanceof IAccessory)
				{
					((IAccessory) accessories.getStackInSlot(a).getItem()).onWornTick(accessories.getStackInSlot(a), player);
				}
			}

		}

	}

	@SubscribeEvent
	public void playerDeath(PlayerDropsEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer
				&& !event.getEntity().worldObj.isRemote
				&& !event.getEntity().worldObj.getGameRules().getBoolean("keepInventory"))
		{
			PlayerHandler.getPlayerAccessories(event.getEntityPlayer()).dropItemsAt(event.getDrops(),event.getEntityPlayer());
		}

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void playerJump(LivingJumpEvent event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			if(checkForAccessoryItem(ModItems.BalloonPufferfish, (EntityPlayer)event.getEntity()))
			{
				event.getEntity().motionY += 0.6;
				//event.entity.jumpMovementFactor = 150.0F;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void fogModifier(FogDensity event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			if(checkForAccessoryItem(ModItems.SwimGoggles, (EntityPlayer)event.getEntity()))
			{
				if(event.getState().getBlock() != null && event.getState().getBlock() == Blocks.water)
				{
					event.setDensity(0.0F);
					event.setCanceled(true);
				}
			}

		}
	}
	
	@SubscribeEvent
	public void playerFall(LivingFallEvent event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			if(checkForAccessoryItem(ModItems.LuckyHorshoe, (EntityPlayer)event.getEntity()))
			{
				if(event.isCancelable())
				{
					event.setCanceled(true);
				}
			}
		}
	}
	
	private boolean checkForAccessoryItem(Item item, EntityPlayer player)
	{
		IInventory accessories = AccessoriesAPI.getAccessories(player);
		for (int i = 0; i < accessories.getSizeInventory(); i++)
		{
			if(accessories.getStackInSlot(i) != null && accessories.getStackInSlot(i).getItem() == item)
			{
				return true;
			}			
		}
		return false;
	}

	@SubscribeEvent
	public void playerLoad(PlayerEvent.LoadFromFile event)
	{
		PlayerHandler.clearPlayerAccessories(event.getEntityPlayer());

		File file1 = getPlayerFile("acc", event.getPlayerDirectory(), event.getEntityPlayer().getDisplayNameString());
		if (!file1.exists())
		{
			File playerfile = event.getPlayerFile("acc");
			if (playerfile.exists())
			{
				try {
					Files.copy(playerfile, file1);					
					CyborgUtils.logger.info("Using and converting UUID Accessories savefile for "+event.getEntityPlayer().getDisplayNameString());
					playerfile.delete();
					File fb = event.getPlayerFile("accback");
					if (fb.exists()) fb.delete();					
				} catch (IOException e) {}
			} 
			else
			{				 
				File fileq = getLegacy1110FileFromPlayer("acc", event.getPlayerDirectory(), event.getEntityPlayer().getDisplayNameString());
				if (fileq.exists()) {
					try {
						Files.copy(fileq, file1);
						fileq.deleteOnExit();
						CyborgUtils.logger.info("Using pre 1.1.1.1 Accessories savefile for "+event.getEntityPlayer().getDisplayNameString());
					} catch (IOException e) {}
				} else {
					File filet = getLegacy1710FileFromPlayer(event.getEntityPlayer());
					if (filet.exists()) {
						try {
							Files.copy(filet, file1);
							filet.deleteOnExit();
							CyborgUtils.logger.info("Using pre MC 1.7.10 Accessories savefile for "+event.getEntityPlayer().getDisplayNameString());
						} catch (IOException e) {}
					}
				}				
			}
		}

		PlayerHandler.loadPlayerAccessories(event.getEntityPlayer(), file1, getPlayerFile("accback", event.getPlayerDirectory(), event.getEntityPlayer().getDisplayNameString()));
		EventHandlerEntity.syncSchedule.add(event.getEntityPlayer().getEntityId());
	}

	public File getPlayerFile(String suffix, File playerDirectory, String playername)
	{
		if ("dat".equals(suffix)) throw new IllegalArgumentException("The suffix 'dat' is reserved");
		return new File(playerDirectory, "_"+playername+"."+suffix);
	}

	public static File getLegacy1710FileFromPlayer(EntityPlayer player)
	{
		try {
			File playersDirectory = new File(player.worldObj.getSaveHandler().getWorldDirectory(), "players");
			return new File(playersDirectory, player.getDisplayNameString() + ".acc");
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}

	public File getLegacy1110FileFromPlayer(String suffix, File playerDirectory, String playername)
	{
		if ("dat".equals(suffix)) throw new IllegalArgumentException("The suffix 'dat' is reserved");
		return new File(playerDirectory, playername+"."+suffix);
	}



	@SubscribeEvent
	public void playerSave(PlayerEvent.SaveToFile event) {
		PlayerHandler.savePlayerAccessories(event.getEntityPlayer(), 
				getPlayerFile("acc", event.getPlayerDirectory(), event.getEntityPlayer().getDisplayNameString()),
				getPlayerFile("accback", event.getPlayerDirectory(), event.getEntityPlayer().getDisplayNameString()));
	}
}
