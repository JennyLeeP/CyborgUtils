package com.cyborgJenn.cyborgUtils.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketResetFallDistance implements IMessage , IMessageHandler<PacketResetFallDistance, IMessage>{

	public PacketResetFallDistance(){}
	
	public PacketResetFallDistance(EntityPlayer player){}
	
	@Override
	public IMessage onMessage(PacketResetFallDistance message, MessageContext ctx) {
		ctx.getServerHandler().playerEntity.fallDistance = 0.0F;
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

}
