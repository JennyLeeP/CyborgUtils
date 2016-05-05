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

public class ItemFlippers extends Item implements IAccessory
{
	public ItemFlippers()
	{
		super();
		this.setUnlocalizedName("flippers");
		this.setCreativeTab(CyborgUtils.tabCyborgCore);

	}
	@Override
	public AccessoryType getAccessoryType(ItemStack itemstack)
	{
		return AccessoryType.Accessory;
	}
private float boost = 1.0F;
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
	
		if(player.isInWater())
		{
			player.motionX *= 1.0912F;
			player.motionY *= 0.7912F;
			player.motionZ *= 1.0912F;
		}
		
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
		tooltip.add("Just like a fish.");
	}
}
