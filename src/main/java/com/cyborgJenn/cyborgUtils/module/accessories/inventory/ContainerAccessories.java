package com.cyborgJenn.cyborgUtils.module.accessories.inventory;

import com.cyborgJenn.cyborgUtils.api.AccessoryType;
import com.cyborgJenn.cyborgUtils.api.IAccessory;
import com.cyborgJenn.cyborgUtils.core.handlers.PlayerHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ContainerAccessories extends Container {

	private static final EntityEquipmentSlot[] field_185003_h = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
	public IInventory craftResult = new InventoryCraftResult();
	public InventoryAccessories accessories;
	/**
	 * Determines if inventory manipulation should be handled.
	 */
	public boolean isLocalWorld;
	private final EntityPlayer thePlayer;

	public ContainerAccessories(InventoryPlayer playerInv, boolean localWorld, EntityPlayer player)
	{
		this.isLocalWorld = localWorld;
		this.thePlayer = player;
		accessories = new InventoryAccessories(player);
		accessories.setEventHandler(this);
		if (!player.worldObj.isRemote) {
			accessories.stackList = PlayerHandler.getPlayerAccessories(player).stackList;
		}
		
		/*
		 * Adds Accessory Slots
		 * Slots 0 to 7 = 8 slots.
		 */

		this.addSlotToContainer(new SlotAccessories(accessories, AccessoryType.Companion, 0, 80, 8));
		this.addSlotToContainer(new SlotAccessories(accessories, AccessoryType.Accessory, 1, 80, 26));
		this.addSlotToContainer(new SlotAccessories(accessories, AccessoryType.Accessory, 2, 80, 44));
		this.addSlotToContainer(new SlotAccessories(accessories, AccessoryType.Accessory, 3, 80, 62));
		
		this.addSlotToContainer(new SlotAccessories(accessories, AccessoryType.Cosmetic,  4, 98, 8));
		this.addSlotToContainer(new SlotAccessories(accessories, AccessoryType.Accessory, 5, 98, 26));
		this.addSlotToContainer(new SlotAccessories(accessories, AccessoryType.Accessory, 6, 98, 44));
		this.addSlotToContainer(new SlotAccessories(accessories, AccessoryType.Accessory, 7, 98, 62));
		
		/*
		 * Adds Player Inventory
		 * Slots 8 to 34 = 27 slots
		 */
		for (int i = 0; i < 3; ++i) // Rows
		{
			for (int j = 0; j < 9; ++j) // Columns
			{
				this.addSlotToContainer(new Slot(playerInv, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		/*
		 * Adds Player Action Bar
		 * Slots 35 to 43 = 9 slots.
		 */
		for (int i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}
		
		/*
		 * Adds Armor Slots 
		 * Slots 44 to 47 = 4 slots.
		 */
		for (int i = 0; i < 4; ++i)
		{
			final EntityEquipmentSlot entityequipmentslot = field_185003_h[i];
			//final int k = i;
			this.addSlotToContainer(new Slot(playerInv, playerInv.getSizeInventory() - 1 - i, 8, 8 + i * 18)
			{
				@Override
				public int getSlotStackLimit() { return 1; }
				@Override
				public boolean isItemValid(ItemStack par1ItemStack)
				{
					if (par1ItemStack == null) return false;
					//return par1ItemStack.getItem().isValidArmor(par1ItemStack, k, thePlayer);
					return par1ItemStack.getItem().isValidArmor(par1ItemStack, entityequipmentslot, thePlayer);
				}
			});
		}
		
		/*
		 *  Adds Crafting slots
		 *  Slots 48 to 52 = 5 slots. 4 crafting 1 craft result.
		 */
		this.addSlotToContainer(new SlotCrafting(playerInv.player, this.craftMatrix, this.craftResult, 0, 136, 57));
		
		for (int i = 0; i < 2; ++i)
		{
			for (int j = 0; j < 2; ++j)
			{
				this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 2, 127 + j * 18, 14 + i * 18));
			}
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}
	
	/**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }
    
    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        for (int i = 0; i < 4; ++i)
        {
            ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

            if (itemstack != null)
            {
                playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }

        this.craftResult.setInventorySlotContents(0, (ItemStack)null);
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
	
	/**
	 * Take a stack from the specified inventory slot. - i.e shift click
	 * @param player
	 * @param slot
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedSlot)
	{
		ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(clickedSlot);
        
        if(slot != null && slot.getHasStack())
        {
        	ItemStack itemstack1 = slot.getStack();
        	itemstack = itemstack1.copy();
        	/*
        	 * Shift clicked from one of our accessory slots.
        	 */
        	if(clickedSlot >= 0 && clickedSlot < 8)
        	{
        		if(!this.mergeItemStack(itemstack1, 8, 45, true))
        		{
        			return null;
        		}
        		slot.onSlotChange(itemstack1, itemstack);
        	}
        	
        	/*
        	 * Shift Clicked from inventory
        	 */
        	else if (clickedSlot >= 8 && clickedSlot < 44)
        	{
        		int j = 1;
				if (itemstack.getItem() instanceof IAccessory && 
                		((IAccessory)itemstack.getItem()).getAccessoryType(itemstack)==AccessoryType.Accessory &&
        				((IAccessory)itemstack.getItem()).canEquip(itemstack, thePlayer) &&
                		!((Slot)this.inventorySlots.get(1)).getHasStack())
        		
                if (!this.mergeItemStack(itemstack1, j, j + 1, false))
                {
                    return null;
                }
				 j = 2;
				if (itemstack.getItem() instanceof IAccessory && 
                		((IAccessory)itemstack.getItem()).getAccessoryType(itemstack)==AccessoryType.Accessory &&
        				((IAccessory)itemstack.getItem()).canEquip(itemstack, thePlayer) &&
                		!((Slot)this.inventorySlots.get(2)).getHasStack())
        		
                if (!this.mergeItemStack(itemstack1, j, j + 1, false))
                {
                    return null;
                }
        	}
        	/*
        	 * Shift Clicked from Armor slots.
        	 */
        	else if (clickedSlot >= 44 && clickedSlot < 48)
        	{
        		if(!this.mergeItemStack(itemstack1, 8, 45, true))
        		{
        			return null;
        		}
        		slot.onSlotChange(itemstack1, itemstack);
        	}
        	
        	if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }
        	
        	slot.onPickupFromSlot(player, itemstack1);	
        }

        
        return itemstack;
    }
	
	private void unequipAccessory(ItemStack stack) {}
    
    @Override
	public void putStacksInSlots(ItemStack[] p_75131_1_) {
    	accessories.blockEvents=true;
		super.putStacksInSlots(p_75131_1_);
	}
    
	protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4, Slot ss)
    {
        boolean flag1 = false;
        int k = par2;

        if (par4)
        {
            k = par3 - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (par1ItemStack.isStackable())
        {
            while (par1ItemStack.stackSize > 0 && (!par4 && k < par3 || par4 && k >= par2))
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.getItem() == par1ItemStack.getItem() && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1))
                {
                    int l = itemstack1.stackSize + par1ItemStack.stackSize;
                    if (l <= par1ItemStack.getMaxStackSize())
                    {
                    	if (ss instanceof SlotAccessories) unequipAccessory(par1ItemStack);
                    	par1ItemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize())
                    {
                    	if (ss instanceof SlotAccessories) unequipAccessory(par1ItemStack);
                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = par1ItemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (par4)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        if (par1ItemStack.stackSize > 0)
        {
            if (par4)
            {
                k = par3 - 1;
            }
            else
            {
                k = par2;
            }

            while (!par4 && k < par3 || par4 && k >= par2)
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null)
                {
                	if (ss instanceof SlotAccessories) unequipAccessory(par1ItemStack);
                    slot.putStack(par1ItemStack.copy());
                    slot.onSlotChanged();
                    par1ItemStack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (par4)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }
        return flag1;
    }
}
