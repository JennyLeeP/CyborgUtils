package com.cyborgJenn.cyborgUtils.core.network;

import com.cyborgJenn.cyborgUtils.core.utils.Reference;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler{

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID.toLowerCase());

	public static void init()
    {
        INSTANCE.registerMessage(PacketOpenAccessoryInventory.class, PacketOpenAccessoryInventory.class, 0, Side.SERVER);
        //INSTANCE.registerMessage(PacketOpenNormalInventory.class, PacketOpenNormalInventory.class, 1, Side.SERVER);
        INSTANCE.registerMessage(PacketSyncAccessory.class, PacketSyncAccessory.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(PacketResetFallDistance.class, PacketResetFallDistance.class, 3, Side.SERVER);
    }

}
