package com.cyborgJenn.cyborgUtils.module.commands;

import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;

public class CommandInventory implements ICommand {

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "inventory";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "<OPS ONLY> Use this command to view another players inventory";
	}

	@Override
	public List<String> getCommandAliases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static class PlayerInventoryWrapper implements IInventory {

		public PlayerInventoryWrapper(ICommandSender sender, EntityPlayerMP targetPlayer) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasCustomName() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getSizeInventory() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public ItemStack getStackInSlot(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemStack decrStackSize(int index, int count) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemStack removeStackFromSlot(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setInventorySlotContents(int index, ItemStack stack) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getInventoryStackLimit() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void markDirty() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer player) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void openInventory(EntityPlayer player) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void closeInventory(EntityPlayer player) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isItemValidForSlot(int index, ItemStack stack) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getField(int id) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setField(int id, int value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getFieldCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public ITextComponent getDisplayName() {
			// TODO Auto-generated method stub
			return null;
		}

        

	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!(sender instanceof EntityPlayerMP)) throw new PlayerNotFoundException("This command cannot be used from the console");
		//EntityPlayerMP player = (EntityPlayerMP)getCommandSenderEntity(sender);
		EntityPlayerMP targetPlayer = server.getPlayerList().getPlayerByUsername(args[0]);
		((EntityPlayer) sender).displayGUIChest(new PlayerInventoryWrapper(sender, targetPlayer));
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			net.minecraft.util.math.BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}    
}
