package com.cyborgJenn.cyborgUtils.core.network;
import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.core.handlers.PlayerHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncAccessory implements IMessage, IMessageHandler<PacketSyncAccessory, IMessage>{

	int slot;
	int playerId;
	ItemStack accessory=null;
	
	public PacketSyncAccessory() {}
	
	public PacketSyncAccessory(EntityPlayer player, int slot) {
		this.slot = slot;
		this.accessory = PlayerHandler.getPlayerAccessories(player).getStackInSlot(slot);
		this.playerId = player.getEntityId();
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeByte(slot);
		buffer.writeInt(playerId);
		ByteBufUtils.writeItemStack(buffer, accessory);
	}

	@Override
	public void fromBytes(ByteBuf buffer) 
	{
		slot = buffer.readByte();
		playerId = buffer.readInt();
		accessory = ByteBufUtils.readItemStack(buffer);
	}

	@Override
	public IMessage onMessage(PacketSyncAccessory message, MessageContext ctx) {
		World world = CyborgUtils.proxy.getClientWorld();
		if (world==null) return null;
		Entity p = world.getEntityByID(message.playerId);
		if (p !=null && p instanceof EntityPlayer) {
			PlayerHandler.getPlayerAccessories((EntityPlayer) p).stackList[message.slot]=message.accessory;
		}
		return null;
	}

}
