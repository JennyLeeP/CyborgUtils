package com.cyborgJenn.cyborgUtils.module.machine.block;

import com.cyborgJenn.cyborgUtils.CyborgUtils;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBlueSteelPulverizer extends BlockContainer{

	private final boolean isActive;
	
	
    
	protected BlockBlueSteelPulverizer(boolean isActive) {
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
