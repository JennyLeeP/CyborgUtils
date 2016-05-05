package com.cyborgJenn.cyborgUtils.module.machine.block;

import java.util.Random;

import com.cyborgJenn.cyborgUtils.CyborgUtils;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBlueSteelFurnace extends BlockContainer{

	private final boolean isActive;
	private static boolean keepInventory;
	private Random rand = new Random();

	protected BlockBlueSteelFurnace(boolean isActive) {
		super(Material.iron);
		this.isActive = isActive;
		if (!isActive){
			this.setCreativeTab(CyborgUtils.tabCyborgCore);
		}else {
			this.setLightLevel(1.0F);
		}
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
