package com.cyborgJenn.cyborgUtils.module.machine.block;

import java.util.Random;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.core.utils.Reference;
import com.cyborgJenn.cyborgUtils.module.machine.tileEnitity.TileEntityRedstoneEmitter;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRedstoneEmitter extends BlockContainer{

	private boolean particles = true;
	protected BlockRedstoneEmitter() {
		super(Material.iron);
		this.setLightLevel(1.0F);
		this.setCreativeTab(CyborgUtils.tabCyborgCore);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityRedstoneEmitter();
	}

	
    
}
