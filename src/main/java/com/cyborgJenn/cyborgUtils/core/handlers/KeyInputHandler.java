package com.cyborgJenn.cyborgUtils.core.handlers;

import org.lwjgl.input.Keyboard;

import com.cyborgJenn.cyborgUtils.core.network.PacketHandler;
import com.cyborgJenn.cyborgUtils.core.network.PacketOpenAccessoryInventory;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyInputHandler 
{	
	public KeyBinding key = new KeyBinding(I18n.translateToLocal("keybind.accessoryinventory"), 
			Keyboard.KEY_LBRACKET, "key.categories.inventory");
	
	public KeyInputHandler()
	{
		 ClientRegistry.registerKeyBinding(key);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void playerTick(PlayerTickEvent event)
	{
		if (event.side == Side.SERVER) return;
		if (event.phase == Phase.START ) {
			if (key.isPressed() && FMLClientHandler.instance().getClient().inGameHasFocus) {
					PacketHandler.INSTANCE.sendToServer(new PacketOpenAccessoryInventory(event.player));
			}
		}
	}
	
}
