package com.cyborgJenn.cyborgUtils.core.network;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.core.utils.Reference;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenAccessoryInventory implements IMessage , IMessageHandler<PacketOpenAccessoryInventory, IMessage>{

	public PacketOpenAccessoryInventory() {}
	
	public PacketOpenAccessoryInventory(EntityPlayer player) {}
	
	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
	
	@Override
	public IMessage onMessage(PacketOpenAccessoryInventory message, MessageContext ctx) {
		ctx.getServerHandler().playerEntity.openGui(CyborgUtils.instance, Reference.ACCESSORYGUI, ctx.getServerHandler().playerEntity.worldObj, (int)ctx.getServerHandler().playerEntity.posX, (int)ctx.getServerHandler().playerEntity.posY, (int)ctx.getServerHandler().playerEntity.posZ);
		return null;
	}
}
