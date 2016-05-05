package com.cyborgJenn.cyborgUtils.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IAccessory {
	/**
	 * This method returns the type of Accessory this is. 
	 * Type is used to determine the slots it can be placed into.
	 */
	public AccessoryType getAccessoryType(ItemStack itemstack);
	
	/**
	 * This method is called once per tick if the accessory is being worn by a player
	 */
	public void onWornTick(ItemStack itemstack, EntityLivingBase player);
	
	/**
	 * This method is called when the Accessory is equipped by a player
	 */
	public void onEquipped(ItemStack itemstack, EntityLivingBase player);
	
	/**
	 * This method is called when the Accessory is un-equipped by a player
	 */
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player);

	/**
	 * can this Accessory be placed in a Accessory slot
	 */
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player);
	
	/**
	 * Can this Accessory be removed from an Accessory slot
	 */
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player);
}
