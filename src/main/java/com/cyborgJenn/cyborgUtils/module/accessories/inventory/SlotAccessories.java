package com.cyborgJenn.cyborgUtils.module.accessories.inventory;

import com.cyborgJenn.cyborgUtils.api.AccessoryType;
import com.cyborgJenn.cyborgUtils.api.IAccessory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAccessories extends Slot
{
	AccessoryType type;
	/**
	 * 
	 * @param Inventory
	 * @param type
	 * @param index
	 * @param xPosition
	 * @param yPosition
	 */
	public SlotAccessories(IInventory inventory, AccessoryType type, int index, int xPosition, int yPosition)
	{
		super(inventory, index, xPosition, yPosition);
        this.type = type;
		
	}
	/**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
    	return stack!=null && stack.getItem() !=null &&
        	   stack.getItem() instanceof IAccessory && 
        	   ((IAccessory)stack.getItem()).getAccessoryType(stack)==this.type &&
        	   ((IAccessory)stack.getItem()).canEquip(stack, ((InventoryAccessories)this.inventory).player.get());
    }
    

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return this.getStack()!=null &&
			   ((IAccessory)this.getStack().getItem()).canUnequip(this.getStack(), player);
	}


	@Override
    public int getSlotStackLimit()
    {
        return 1;
    }
}
