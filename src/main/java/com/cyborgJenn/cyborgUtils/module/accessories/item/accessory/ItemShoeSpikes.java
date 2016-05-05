package com.cyborgJenn.cyborgUtils.module.accessories.item.accessory;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.api.AccessoryType;
import com.cyborgJenn.cyborgUtils.api.IAccessory;
import com.cyborgJenn.cyborgUtils.core.network.PacketHandler;
import com.cyborgJenn.cyborgUtils.core.network.PacketResetFallDistance;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShoeSpikes extends Item implements IAccessory
{
	private String name;
	public ItemShoeSpikes(String name)
	{
		super();
		this.name = name;
		this.setUnlocalizedName(name);
		this.setCreativeTab(CyborgUtils.tabCyborgCore);

	}
	@Override
	public AccessoryType getAccessoryType(ItemStack itemstack)
	{
		return AccessoryType.Accessory;
	}

	private boolean hasJumped = false;
	private boolean stuckToWall = false;
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{

		if(player.isCollidedHorizontally && player.isSneaking())
		{
			if(name.equalsIgnoreCase("shoeSpikes") || name.equalsIgnoreCase("climbingClaws"))
			{
				player.motionY = -0.0153D;
				player.fallDistance = 0.0F;
				PacketHandler.INSTANCE.sendToServer(new PacketResetFallDistance((EntityPlayer) player));
			}
			else 
			{
				player.motionY = 0.0D;
				player.fallDistance = 0.0F;
				PacketHandler.INSTANCE.sendToServer(new PacketResetFallDistance((EntityPlayer) player));
			}
			stuckToWall = true;
		}else 
		{
			stuckToWall = false;
		}
		if(stuckToWall && Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			if(name.equalsIgnoreCase("shoeSpikes") || name.equalsIgnoreCase("climbingClaws"))
			{
				player.addVelocity(0, 0.42F, 0);

			} 
			else
			{
				player.addVelocity(0, 0.51F, 0);
			}
			stuckToWall = false;
		}


		/*
		hasJumped = false;
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && (player.onGround = true) && player.motionY < 0.07 && !hasJumped)  //Waaaaaay more checks than necessary
		{
			//checks for armour/abilities...
			player.addVelocity(0, 0.1, 0);
			hasJumped = true;
			player.fallDistance -= 0.003F;

		}
		if(!player.isAirBorne)
			hasJumped = false;
		 */
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
		tooltip.add("Sure, I can climb that, no problem. Allows Players to cling to walls");
	}
}
