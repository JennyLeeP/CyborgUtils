package com.cyborgJenn.cyborgUtils.core.proxy;

import com.cyborgJenn.cyborgUtils.core.handlers.KeyInputHandler;
import com.cyborgJenn.cyborgUtils.core.item.ItemRenderRegister;
import com.cyborgJenn.cyborgUtils.core.utils.Reference;
import com.cyborgJenn.cyborgUtils.module.accessories.gui.GuiAccessory;
import com.cyborgJenn.cyborgUtils.module.accessories.render.RenderHud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy
{
	public void registerRenderers()
	{
		MinecraftForge.EVENT_BUS.register(new RenderHud(Minecraft.getMinecraft()));
		
	}
	public void registerAccessoryItemRenderers()
	{
		ItemRenderRegister.registerAccessoryItemRenderer();
	}
	public void registerCombatItemRenderers()
	{
		ItemRenderRegister.registerCombatItemRenderer();
	}
	@Override
	public void registerKeyBindings() {
		keyHandler = new KeyInputHandler();
		MinecraftForge.EVENT_BUS.register(keyHandler);

	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (world instanceof WorldClient) {
			switch (ID) {
				case Reference.ACCESSORYGUI: return new GuiAccessory(player);
			}
		}
		return null;
	}
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
}
