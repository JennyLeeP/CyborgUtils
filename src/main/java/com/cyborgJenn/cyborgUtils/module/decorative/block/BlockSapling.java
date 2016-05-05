package com.cyborgJenn.cyborgUtils.module.decorative.block;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class BlockSapling extends BlockBush implements IGrowable
{
	 
	@Override
	public boolean canGrow(World worldIn, net.minecraft.util.math.BlockPos pos, IBlockState state, boolean isClient) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, net.minecraft.util.math.BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void grow(World worldIn, Random rand, net.minecraft.util.math.BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		
	}

}
