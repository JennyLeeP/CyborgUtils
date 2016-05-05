package com.cyborgJenn.cyborgUtils.module.accessories.item.companion;

import com.cyborgJenn.cyborgUtils.api.AccessoryType;
import com.cyborgJenn.cyborgUtils.api.IAccessory;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCompanion extends Item implements IAccessory{

	@Override
	public AccessoryType getAccessoryType(ItemStack itemstack) {
		return AccessoryType.Companion;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		return false;
	}

}
