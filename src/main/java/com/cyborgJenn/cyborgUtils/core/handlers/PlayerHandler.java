package com.cyborgJenn.cyborgUtils.core.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.module.accessories.inventory.InventoryAccessories;
import com.google.common.io.Files;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerHandler
{
	private static HashMap<String, InventoryAccessories> playerAccessories = new HashMap<String, InventoryAccessories>();

	public static void clearPlayerAccessories(EntityPlayer player)
	{
		playerAccessories.remove(player.getDisplayNameString());
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public static InventoryAccessories getPlayerAccessories(EntityPlayer player)
	{
		if (!playerAccessories.containsKey(player.getDisplayNameString()))
		{
			InventoryAccessories inventory = new InventoryAccessories(player);
			playerAccessories.put(player.getDisplayNameString(), inventory);
		}
		return playerAccessories.get(player.getDisplayNameString());
	}
	
	/**
	 * 
	 * @param player
	 * @param inventory
	 */
	public static void setPlayerAccessories(EntityPlayer player,
			InventoryAccessories inventory) {
		playerAccessories.put(player.getDisplayNameString(), inventory);
	}
	
	/**
	 * 
	 * @param player
	 * @param file1
	 * @param file2
	 */
	public static void loadPlayerAccessories(EntityPlayer player, File file1, File file2)
	{
		if (player != null && !player.worldObj.isRemote)
		{
			try {
				NBTTagCompound data = null;
				boolean save = false;
				if (file1 != null && file1.exists())
				{
					try {
						FileInputStream inputstream = new FileInputStream(
								file1);
						data = CompressedStreamTools
								.readCompressed(inputstream);
						inputstream.close();
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				if (file1 == null || !file1.exists() || data == null
						|| data.hasNoTags())
				{
					CyborgUtils.logger.warn("Accesssory Data not found for " + player.getDisplayNameString() + ". Trying to load backup data.");
					if (file2 != null && file2.exists())
					{
						try {
							FileInputStream fileinputstream = new FileInputStream(
									file2);
							data = CompressedStreamTools
									.readCompressed(fileinputstream);
							fileinputstream.close();
							save = true;
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}

				if (data != null)
				{
					InventoryAccessories inventory = new InventoryAccessories(player);
					inventory.readFromNBT(data);
					playerAccessories.put(player.getDisplayNameString(), inventory);
					if (save)
						savePlayerAccessories(player, file1, file2);
				}
			} catch (Exception exception1)
			{
				CyborgUtils.logger.fatal("Error loading accessory inventory");
				exception1.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param player
	 * @param file1
	 * @param file2
	 */
	public static void savePlayerAccessories(EntityPlayer player, File file1, File file2)
	{
		if (player != null && !player.worldObj.isRemote)
		{
			try {
				if (file1 != null && file1.exists())
				{
					try {
						Files.copy(file1, file2);
					} catch (Exception e)
					{
						CyborgUtils.logger.error("Could not backup accessory file for player " + player.getDisplayNameString());
					}
				}

				try {
					if (file1 != null)
					{
						InventoryAccessories inventory = getPlayerAccessories(player);
						NBTTagCompound data = new NBTTagCompound();
						inventory.writeToNBT(data);

						FileOutputStream outputstream = new FileOutputStream(
								file1);
						CompressedStreamTools.writeCompressed(data, outputstream);
						outputstream.close();

					}
				} catch (Exception e)
				{
					CyborgUtils.logger.error("Could not save accessory file for player " + player.getDisplayNameString());
					e.printStackTrace();
					if (file1.exists())
					{
						try {
							file1.delete();
						} catch (Exception e2)
						{ }
					}
				}
			} catch (Exception exception1)
			{
				CyborgUtils.logger.fatal("Error saving Accessory inventory");
				exception1.printStackTrace();
			}
		}
	}

}
