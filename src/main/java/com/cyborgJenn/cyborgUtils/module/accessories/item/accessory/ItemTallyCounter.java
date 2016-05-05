package com.cyborgJenn.cyborgUtils.module.accessories.item.accessory;

import java.util.List;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.api.AccessoryType;
import com.cyborgJenn.cyborgUtils.api.IAccessory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTallyCounter extends Item implements IAccessory
{
	Minecraft minecraft;
	public ItemTallyCounter()
	{
		super();
		this.setUnlocalizedName("tallyCounter");
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
		Side side = FMLCommonHandler.instance().getEffectiveSide();   
		if(side == Side.CLIENT)
		{
			//this.minecraft.getNetHandler().addToSendQueue(new CPacketClientStatus(CPacketClientStatus.State.REQUEST_STATS));
			 CyborgUtils.logger.info("Packet: RequestStats sent from: "+ player.getName());
			
		} else if (side == Side.SERVER)
		{
			CyborgUtils.logger.info("We appear to be on the server ? "+ player.getName());
			//this.minecraft.getNetHandler().addToSendQueue(new SPacketStatistics(sdss,sdsda));
			//this.minecraft.getNetHandler().addToSendQueue(new CPacketClientStatus(CPacketClientStatus.State.REQUEST_STATS));
		}
		
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
		tooltip.add("Animal friendly");
		tooltip.add("Displays total count of monsters killed");
	}
}
