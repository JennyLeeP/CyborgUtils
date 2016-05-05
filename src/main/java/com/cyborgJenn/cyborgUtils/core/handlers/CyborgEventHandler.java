package com.cyborgJenn.cyborgUtils.core.handlers;

import com.cyborgJenn.cyborgUtils.CyborgUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CyborgEventHandler 
{
	public static int dimension;
	
	@SubscribeEvent    
	public void playerLoggedInEvent (PlayerEvent.PlayerLoggedInEvent event)
	{    
		Side side = FMLCommonHandler.instance().getEffectiveSide();        
		if (side == Side.SERVER)
		{
			EventHandlerEntity.syncSchedule.add(event.player.getEntityId());
		}
	}

	public static void syncaccessories(EntityPlayer player)
	{
		for (int a = 0; a < 8; a++) {
			PlayerHandler.getPlayerAccessories(player).syncSlotToClients(a);
		}
	}

	/*
	 * Gets the MinecraftForge WorldEvent.
	 */
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void getWorldEvent(WorldEvent event)
	{
		dimension = event.getWorld().provider.getDimension();
		//System.out.println("Is this working ?");
	}

	/*
	 * Gets the MinecraftForge MapGen event type
	 */
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void getMapGenEvent(InitMapGenEvent event)
	{
		if (dimension == 0){
			switch(event.getType()){
			case CAVE:
				event.setNewGen(CyborgUtils.caveGen); //= CyborgUtils.caveGen;
				break;
			case CUSTOM:
				break;
			case MINESHAFT:
				break;
			case NETHER_BRIDGE:
				break;
			case NETHER_CAVE:
				break;
			case RAVINE:// TODO larger Ravines
				break;
			case SCATTERED_FEATURE:
				break;
			case STRONGHOLD:
				break;
			case VILLAGE:
				break;
			case OCEAN_MONUMENT: //TODO Uncomment this line for MC 1.8
				break;
			default:
				break;
				
			}
			
		}


	}
}
