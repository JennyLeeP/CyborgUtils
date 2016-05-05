package com.cyborgJenn.cyborgUtils.module.accessories.item.accessory;

import java.util.List;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.api.AccessoryType;
import com.cyborgJenn.cyborgUtils.api.IAccessory;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSailfishBoots extends Item implements IAccessory
{
	private static final AttributeModifier speedBoostModifier = (new AttributeModifier("speed boost", 0.90000001192092896D, 2)).setSaved(false);
	public ItemSailfishBoots()
	{
		super();
		this.setUnlocalizedName("sailfishBoots");
		this.setCreativeTab(CyborgUtils.tabCyborgCore);

	}
	@Override
	public AccessoryType getAccessoryType(ItemStack itemstack)
	{
		return AccessoryType.Accessory;
	}
	private boolean applied = false;
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{	
		if(!applied)
		{
			IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			iattributeinstance.applyModifier(speedBoostModifier);
			player.stepHeight = 1.0F;
			applied = true;
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{

	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
		applied = false;
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		iattributeinstance.removeModifier(speedBoostModifier);
		player.stepHeight = 0.5F;
		
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
		tooltip.add("Boots Make you go fast");
	}
}
