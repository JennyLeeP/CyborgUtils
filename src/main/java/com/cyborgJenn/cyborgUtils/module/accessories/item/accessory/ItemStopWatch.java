package com.cyborgJenn.cyborgUtils.module.accessories.item.accessory;

import java.util.List;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.api.AccessoryType;
import com.cyborgJenn.cyborgUtils.api.IAccessory;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStopWatch extends Item implements IAccessory
{
	public ItemStopWatch()
	{
		super();
		this.setUnlocalizedName("stopWatch");
		this.setCreativeTab(CyborgUtils.tabCyborgCore);
	}
	@Override
	public AccessoryType getAccessoryType(ItemStack itemstack)
	{
		return AccessoryType.Accessory;
	}
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
		
	}
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{
		
	}
	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
		
	}
	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}
	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add("88 Miles an Hour.  Displays player speed on the HUD.");
	}
}
