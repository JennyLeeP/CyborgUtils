package com.cyborgJenn.cyborgUtils.module.accessories.render;

import org.lwjgl.opengl.GL11;

import com.cyborgJenn.cyborgUtils.api.AccessoriesAPI;
import com.cyborgJenn.cyborgUtils.core.item.ModItems;
import com.cyborgJenn.cyborgUtils.core.utils.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHud extends Gui
{
	private Minecraft minecraft;
	World world;
	private static int FONTCOLOR = 0xffffff;
	private final static int        compassIndexDefault         = 3;
	private static int              compassIndex                = compassIndexDefault;
	private int 					screenHeight;
	public int 						screenWidth;
	
	private static final ResourceLocation COMPASSTEXTURE 		= new ResourceLocation(Reference.TEXTURE + "/textures/gui/accessoryhud.png");

	public RenderHud(Minecraft minecraft)
	{
		super();
		this.minecraft = minecraft;
	}
	
	//
	// This event is called by GuiIngameForge during each frame by
	// GuiIngameForge.pre() and GuiIngameForge.post().
	//
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event)
	{
		//minecraft.getTextureManager().bindTexture(COMPASSTEXTURE);
		if(event.isCancelable() || event.getType() != ElementType.EXPERIENCE)
		{      
			return;
		}
		if (isBeingHeld(ModItems.FancyCompass) || isAccessoryEquipped(ModItems.FancyCompass) && !isAccessoryEquipped(ModItems.GPS))
		{
			renderFancyCompass();
		}
		if (isBeingHeld(ModItems.DepthMeter) || isAccessoryEquipped(ModItems.DepthMeter))
		{
			renderDepthMeter();
		}
		if (isBeingHeld(ModItems.GPS) || isAccessoryEquipped(ModItems.GPS))
		{
			renderGPS();
		}
		if (isBeingHeld(ModItems.Sextant) || isAccessoryEquipped(ModItems.Sextant))
		{
			renderSextant();
		}
		if (isBeingHeld(ModItems.StopWatch) || isAccessoryEquipped(ModItems.StopWatch))
		{
			renderStopWatch();
		}
		if (isBeingHeld(ModItems.TallyCounter) || isAccessoryEquipped(ModItems.TallyCounter))
		{
			renderTallyCounter();
		}
	}

	@SuppressWarnings("unused")
	private void renderReticleCompass()
	{
		int xPos = (screenWidth / 2) - 2;
		int yPos = (screenHeight / 2) + 3;
		int direction = (int)minecraft.thePlayer.rotationYaw;
		direction += 22;	//+22 centers the compass (45degrees/2)
		direction %= 360;

		if (direction < 0)
			direction += 360;

		int heading = direction / 45; //  360degrees divided by 45 == 8 zones
		String compassDirection = "";
		switch(heading){
		case 0:
			compassDirection = " S ";
			break;
		case 1:
			compassDirection = " SW";
			break;
		case 2:
			compassDirection = " W ";
			break;
		case 3:
			compassDirection = " NW";
			break;
		case 4:
			compassDirection = " N ";
			break;
		case 5:
			compassDirection = " NE";
			break;
		case 6:
			compassDirection = " E ";
			break;
		default:
			compassDirection = " SE";
			break;
		}
		minecraft.fontRendererObj.drawStringWithShadow(compassDirection, xPos, yPos, 0xffffff);
	}

	private void renderCompassCore()
	{
		minecraft.getTextureManager().bindTexture(COMPASSTEXTURE);
		int direction = MathHelper.floor_double(((minecraft.thePlayer.rotationYaw * 256F) / 360F) + 0.5D) & 255;
		float xBase = (screenWidth / 2) - 32.5F; // Centers compass in game window.
		float yBase = 3;
		if (direction < 128)
			this.drawTexturedModalRect(xBase, yBase, direction, (compassIndex * 24), 65, 12);
		else
			this.drawTexturedModalRect(xBase, yBase, direction - 128, (compassIndex * 24) + 12, 65, 12);
	}

	
	private void renderPosition(int xPosGui, int yPosGui)
	{
		GL11.glPushMatrix();
		int xPos = (int) Math.floor(minecraft.thePlayer.posX);
		int yPos = (int) Math.floor(minecraft.thePlayer.posY);
		int zPos = (int) Math.floor(minecraft.thePlayer.posZ);
		String position = new String("X: "+ xPos + " Y: " + yPos + " Z: " + zPos);
		int stringLength = minecraft.fontRendererObj.getStringWidth(position);
		int stringLengthCentered = stringLength / 2;
		float scaledCenterPoss = (getCenteredScreenPos() * 2) - stringLengthCentered; // should = twice the viewport size
		GL11.glScalef(0.5F,0.5F, 1.0F);
		minecraft.fontRendererObj.drawString(position, scaledCenterPoss, yPosGui, FONTCOLOR, false);
		GL11.glScalef(1.0F,1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	private void renderTime(float xPosGui, float yPosGui)
	{
		GL11.glPushMatrix();
		int hours = getWorldHours();
		int minutes = getWorldMinutes();
		String time = hours + ":" + minutes;
		float calcPoss = getCenteredScreenPos() + xPosGui;
		GL11.glScalef(0.5F,0.5F, 1.0F);
		minecraft.fontRendererObj.drawString(time, calcPoss, yPosGui, FONTCOLOR, false);
		GL11.glScalef(1.0F,1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	private void renderCanSnow(float xPosGui, float yPosGui)
	{
		//minecraft.getTextureManager().bindTexture(COMPASSTEXTURE);
		BlockPos pos = minecraft.thePlayer.getPosition();
		float tempFloat = minecraft.theWorld.getBiomeGenForCoords(pos).getFloatTemperature(pos);
		//boolean canSnow = minecraft.theWorld.getBiomeGenForCoords(pos).getEnableSnow();
		float rainRate = minecraft.theWorld.getBiomeGenForCoords(pos).getRainfall();
		if (tempFloat < 0.148 && rainRate > 0.0){
			//System.out.println("SNOW");
		}
		//System.out.println("Temp: " + tempFloat + " CanSnow: " + canSnow + " RainFloat: "+ rainRate);
	}
	private void renderTemp(float xPosGui, float yPosGui)
	{
		GL11.glPushMatrix();
		minecraft.getTextureManager().bindTexture(COMPASSTEXTURE);
		BlockPos pos = minecraft.thePlayer.getPosition();
		TempCategory temp = minecraft.theWorld.getBiomeGenForCoords(pos).getTempCategory();
		String temperature = temp.toString();
		//String BiomeTemp;
		//int xIconPos = 0;
		int yIconPos = 0;
		//int color = 0xffffff;
		if(temperature!= null && temperature == "WARM"){
			
			yIconPos = 204;
		}
		else if (temperature == "COLD") {
			
			yIconPos = 192;
		}else {
			
			yIconPos = 198;
		}
		//float calcPoss = getCenteredScreenPos() + xPosGui;
		
		
		GL11.glScalef(0.5F,0.5F, 1.0F);
		this.drawTexturedModalRect(xPosGui * 2, yPosGui * 2, yIconPos, 16, 7, 14);
		GL11.glScalef(1.0F,1.0F, 1.0F);
		GL11.glPopMatrix();
		
	}
	
	private void renderBedLoc()
	{
		GL11.glPushMatrix();
		minecraft.getTextureManager().bindTexture(COMPASSTEXTURE);
		double dir = 0.0D;
		double posX = minecraft.thePlayer.posX;
		double posZ = minecraft.thePlayer.posZ;
		double rotYaw = minecraft.thePlayer.rotationYaw;
		BlockPos blockpos = minecraft.theWorld.getSpawnPoint();
        double bedX = (double)blockpos.getX() - posX;
        double bedZ = (double)blockpos.getZ() - posZ;
        rotYaw = rotYaw % 360.0D; //  = Player Rotation in 360 degrees
        //dir = -((rotYaw - 90) * Math.PI / 180.0D - Math.atan2(bedZ, bedX)); // radians
        dir = rotYaw / Math.PI - Math.atan2(bedZ, bedX);
        //int direction = MathHelper.floor_double(((minecraft.thePlayer.rotationYaw * 256F) / 360F) + 0.5D) & 255;
		float xBase = (screenWidth / 2) - 32.5F; // Centers compass in game window.
		float yBase = 3;
		if (dir < 128)
			this.drawTexturedModalRect(xBase, yBase, (int) dir, 128, 65, 12);
		else
			this.drawTexturedModalRect(xBase, yBase, (int) (dir - 128), 128 + 12, 65, 12);
		//System.out.println(dir);
		GL11.glPopMatrix();
	}
	
	private void renderFancyCompass()
	{
		renderCompassCore();
		float xBaseOverlay = (screenWidth / 2) - 46.5F;
		float yBaseOverlay = 1;
		this.drawTexturedModalRect(xBaseOverlay, yBaseOverlay, 0, 96, 93, 16);
	}

	private void renderGPS()
	{
		renderCompassCore();
		float xBaseOverlay = (screenWidth / 2) - 86.5F;
		float yBaseOverlay = 1;
		this.drawTexturedModalRect(xBaseOverlay, yBaseOverlay, 0, 112, 173, 16);
		renderPosition(0, 45);
		renderBedLoc();
		renderTime(300, 9.5F);
		renderTemp(280.0F, 5.5F);
		renderCanSnow(0,0);
	}

	private void renderDepthMeter()
	{
		//TODO render Depth Meter
	}
	
	private void renderStopWatch()
	{


		//double speed = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();

		//System.out.println("Speed = " + speed);
	}

	private void renderSextant()
	{
		int moonPhase = minecraft.theWorld.getMoonPhase();
		switch(moonPhase)
		{
		case 0:
			//Full Moon
			break;
		case 1:
			//Waning Gibbous
			break;
		case 2:
			//Last Quarter
			break;
		case 3:
			//Waning Crescent
			break;
		case 4:
			//New Moon
			break;
		case 5:
			//Waxing Crescent
			break;
		case 6:
			//First Quarter
			break;
		case 7:
			//Waxing Gibbous
			break;
		}

	}

	private void renderTallyCounter()
	{
		int monsterkills = minecraft.thePlayer.getStatFileWriter().readStat(StatList.mobKills);	
		//System.out.println(monsterkills);
	}

	private boolean isBeingHeld(Item item)
	{
		if(minecraft.thePlayer.getHeldItemMainhand() != null){
			if (minecraft.thePlayer.getHeldItemMainhand().getItem() == item){
				return true;
			}
		}
		return false;
	}
	private boolean isAccessoryEquipped(Item item)
	{
		EntityPlayer player = minecraft.thePlayer;
		IInventory accessories = AccessoriesAPI.getAccessories(player);

		for (int i = 0; i < accessories.getSizeInventory(); i++)
		{
			if(accessories.getStackInSlot(i) != null && accessories.getStackInSlot(i).getItem() == item)
			{
				return true;
			}			
		}
		return false;
	}
	
	public int getWorldMinutes() {
		int time = (int) Math.abs((minecraft.theWorld.getWorldTime() + 6000) % 24000);
		return (time % 1000) * 6 / 100;
	}

	public int getWorldHours() {
		int time = (int)Math.abs((minecraft.theWorld.getWorldTime()+ 6000) % 24000);
		//int adjTime = (int)((float)time / 1000F);

		return (int)((float)time / 1000F);
	}
	private int getCenteredScreenPos()
	{
		ScaledResolution scaledResolution = new ScaledResolution(minecraft);
		screenWidth = scaledResolution.getScaledWidth();
		int screenWidthCentered = screenWidth / 2;
		return screenWidthCentered;
	}

}
