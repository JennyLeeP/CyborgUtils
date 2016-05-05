package com.cyborgJenn.cyborgUtils.module.accessories.item.accessory;

import java.util.List;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.api.AccessoryType;
import com.cyborgJenn.cyborgUtils.api.IAccessory;
import com.cyborgJenn.cyborgUtils.module.accessories.render.RenderHud;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFancyCompass extends Item implements IAccessory 
{
	public ItemFancyCompass()
	{
		super();
		this.setMaxStackSize(1);
		this.setUnlocalizedName("fancyCompass");
		this.setCreativeTab(CyborgUtils.tabCyborgCore);
	}
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return super.getUnlocalizedName();
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
		tooltip.add("Not sure which way is what? Always getting lost? Try this nifty dodad and find your way home.");
	}
}
