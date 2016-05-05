package com.cyborgJenn.cyborgUtils.module.accessories.inventory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.api.AccessoryType;
import com.cyborgJenn.cyborgUtils.api.IAccessory;
import com.cyborgJenn.cyborgUtils.core.network.PacketHandler;
import com.cyborgJenn.cyborgUtils.core.network.PacketSyncAccessory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class InventoryAccessories implements IInventory{

	public ItemStack[] stackList;
	private Container eventHandler;
	public WeakReference<EntityPlayer> player;
	public boolean blockEvents=false;

	public InventoryAccessories(EntityPlayer player)
	{
		this.stackList = new ItemStack[8];
		this.player = new WeakReference<EntityPlayer>(player);
	}

	public Container getEventHandler()
	{
		return eventHandler;
	}

	public void setEventHandler(Container eventHandler)
	{
		this.eventHandler = eventHandler;
	}

	@Override
	public String getName() {return "";}

	@Override
	public boolean hasCustomName() {return false;}

	@Override
	public ITextComponent getDisplayName() 
	{
		return null;
	}

	@Override
	public int getSizeInventory() 
	{
		return this.stackList.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) 
	{
		return index >= this.getSizeInventory() ? null : this.stackList[index];
	}

	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		if (this.stackList[slot] != null)
		{
			ItemStack itemstack;

			if (this.stackList[slot].stackSize <= count)
			{
				itemstack = this.stackList[slot];

				if (itemstack != null && itemstack.getItem() instanceof IAccessory)
				{
					((IAccessory) itemstack.getItem()).onUnequipped(itemstack, player.get());
				}

				this.stackList[slot] = null;

				if (eventHandler != null)
					this.eventHandler.onCraftMatrixChanged(this);
				syncSlotToClients(slot);
				return itemstack;
			} else {
				itemstack = this.stackList[slot].splitStack(count);

				if (itemstack != null && itemstack.getItem() instanceof IAccessory)
				{
					((IAccessory) itemstack.getItem()).onUnequipped(itemstack, player.get());
				}

				if (this.stackList[slot].stackSize == 0) {
					this.stackList[slot] = null;
				}

				if (eventHandler != null)
					this.eventHandler.onCraftMatrixChanged(this);
				syncSlotToClients(slot);
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index) 
	{
		if (this.stackList[index] != null)
		{
			ItemStack itemstack = this.stackList[index];
			this.stackList[index] = null;
			return itemstack;
		} else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) 
	{
		if(!blockEvents && this.stackList[slot] != null)
		{
			((IAccessory)stackList[slot].getItem()).onUnequipped(stackList[slot], player.get());
		}
		this.stackList[slot] = stack;
		if (!blockEvents && stack != null && stack.getItem() instanceof IAccessory)
		{
			if (player.get()!=null)
				((IAccessory) stack.getItem()).onEquipped(stack, player.get());
		}
		if (eventHandler != null)
			this.eventHandler.onCraftMatrixChanged(this);
		syncSlotToClients(slot);

	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 1;
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved
	 * to disk later - the game won't think it hasn't changed and skip it.
	 */
	@Override
	public void markDirty()
	{
		try
		{
			player.get().inventory.markDirty();
		} catch (Exception e) {
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) 
	{
		if (stack == null || !(stack.getItem() instanceof IAccessory)
				|| !((IAccessory) stack.getItem()).canEquip(stack, player.get()))
			return false;
		if (index == 0
				&& ((IAccessory) stack.getItem()).getAccessoryType(stack) == AccessoryType.Companion)
			return true;
		if ((index == 1 || index == 2 || index == 3)
				&& ((IAccessory) stack.getItem()).getAccessoryType(stack) == AccessoryType.Accessory)
			return true;
		if (index == 4
				&& ((IAccessory) stack.getItem()).getAccessoryType(stack) == AccessoryType.Cosmetic)
			return true;
		if ((index == 5 || index == 6 || index == 7)
				&& ((IAccessory) stack.getItem()).getAccessoryType(stack) == AccessoryType.Accessory)
			return true;
		return false;
	}

	@Override
	public int getField(int id) {return 0;}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {return 0;}

	@Override
	public void clear() 
	{
		for(int i = 0; i < stackList.length; i++)
		{
			stackList[i] = null;
		}

	}

	public void writeToNBT(EntityPlayer player)
	{
		NBTTagCompound compound = player.getEntityData();
		writeToNBT(compound);
	}

	public void writeToNBT(NBTTagCompound compound)
	{
		NBTTagList items = new NBTTagList();
		NBTTagCompound invSlot;
		for (int i = 0; i < this.stackList.length; ++i)
		{
			if (this.stackList[i] != null)
			{
				invSlot = new NBTTagCompound();
				invSlot.setByte("Slot", (byte) i);
				this.stackList[i].writeToNBT(invSlot);
				items.appendTag(invSlot);
			}
		}
		compound.setTag("Accessories.Inventory", items);
	}

	public void readFromNBT(EntityPlayer player)
	{
		NBTTagCompound compound = player.getEntityData();
		readFromNBT(compound);
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		NBTTagList tagList = compound.getTagList("Accessories.Inventory", 10);
		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound) tagList.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
			if (itemstack != null)
			{
				this.stackList[j] = itemstack;
			}
		}
	}
	public void dropItems(ArrayList<EntityItem> drops)
	{
		for (int i = 0; i < 8; ++i) {
			if (this.stackList[i] != null)
			{
				EntityItem entityitem = new EntityItem(player.get().worldObj, player.get().posX, player.get().posY
						+ player.get().getEyeHeight(), player.get().posZ, this.stackList[i].copy());
				entityitem.setPickupDelay(40);
				float f1 = player.get().worldObj.rand.nextFloat() * 0.5F;
				float f2 = player.get().worldObj.rand.nextFloat()
						* (float) Math.PI * 2.0F;
				entityitem.motionX = (double) (-MathHelper.sin(f2) * f1);
				entityitem.motionZ = (double) (MathHelper.cos(f2) * f1);
				entityitem.motionY = 0.20000000298023224D;
				drops.add(entityitem);
				this.stackList[i] = null;
				syncSlotToClients(i);
			}
		}
	}

	public void dropItemsAt(List<EntityItem> drops, Entity entity)
	{
		for (int i = 0; i < 8; ++i)
		{
			if (this.stackList[i] != null)
			{
				EntityItem entityitem = new EntityItem(entity.worldObj,
						entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ,
						this.stackList[i].copy());
				entityitem.setPickupDelay(40);
				float f1 = entity.worldObj.rand.nextFloat() * 0.5F;
				float f2 = entity.worldObj.rand.nextFloat() * (float) Math.PI * 2.0F;
				entityitem.motionX = (double) (-MathHelper.sin(f2) * f1);
				entityitem.motionZ = (double) (MathHelper.cos(f2) * f1);
				entityitem.motionY = 0.20000000298023224D;
				drops.add(entityitem);
				this.stackList[i] = null;
				syncSlotToClients(i);
			}
		}
	}

	public void syncSlotToClients(int slot)
	{
		try
		{
			if (CyborgUtils.proxy.getClientWorld() == null)
			{
				PacketHandler.INSTANCE.sendToAll(new PacketSyncAccessory(player.get(), slot));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
