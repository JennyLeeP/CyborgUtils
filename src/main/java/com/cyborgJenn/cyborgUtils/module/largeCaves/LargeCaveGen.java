package com.cyborgJenn.cyborgUtils.module.largeCaves;

import java.util.Random;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.core.utils.Config;
import com.google.common.base.Objects;

import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;

public class LargeCaveGen extends MapGenCaves{

	@Override
	protected void func_180703_a(long seed, int chunkX, int chunkZ, ChunkPrimer chunkprimer, double posX, double posY, double posZ)
	{
		if (Config.enableDebugging){
			CyborgUtils.logger.info("large node at:" + posX +", "+ posY +", "+ posZ);
		}
		this.func_180702_a(seed, chunkX, chunkZ, chunkprimer, posX, posY, posZ, 1.0F + this.rand.nextFloat() * Config.largeNodeMultiplier, 0.0F, 0.0F, -1, -1, 0.5D);
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void func_180702_a(long seed, int chunkX, int chunkZ, ChunkPrimer chunkprimer, double posX, double posY, double posZ, float p_151541_12_, float p_151541_13_, float p_151541_14_, int p_151541_15_, int p_151541_16_, double p_151541_17_)
	{
		double d4 = (double)(chunkX * 16 + 8);
		double d5 = (double)(chunkZ * 16 + 8);
		float f3 = 0.0F;
		float f4 = 0.0F;
		Random random = new Random(seed);

		if (p_151541_16_ <= 0)
		{
			int j1 = this.range * 16 - 16;
			p_151541_16_ = j1 - random.nextInt(j1 / 4);
		}

		boolean flag2 = false;

		if (p_151541_15_ == -1)
		{
			p_151541_15_ = p_151541_16_ / 2;
			flag2 = true;
		}

		int k1 = random.nextInt(p_151541_16_ / 2) + p_151541_16_ / 4;

		for (boolean flag = random.nextInt(6) == 0; p_151541_15_ < p_151541_16_; ++p_151541_15_)
		{
			double d6 = 1.5D + (double)(MathHelper.sin((float)p_151541_15_ * (float)Math.PI / (float)p_151541_16_) * p_151541_12_ * 1.0F);
			double d7 = d6 * p_151541_17_;
			float f5 = MathHelper.cos(p_151541_14_);
			float f6 = MathHelper.sin(p_151541_14_);
			posX += (double)(MathHelper.cos(p_151541_13_) * f5);
			posY += (double)f6;
			posZ += (double)(MathHelper.sin(p_151541_13_) * f5);

			if (flag)
			{
				p_151541_14_ *= 0.92F;
			}
			else
			{
				p_151541_14_ *= 0.7F;
			}

			p_151541_14_ += f4 * 0.1F;
			p_151541_13_ += f3 * 0.1F;
			f4 *= 0.9F;
			f3 *= 0.75F;
			f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
//flag2 = true;
			if (!flag2 && p_151541_15_ == k1 && p_151541_12_ > 1.0F && p_151541_16_ > 0)
			{
				this.func_180702_a(random.nextLong(), chunkX, chunkZ, chunkprimer, posX, posY, posZ, (float) (random.nextFloat() * Config.caveTunnelSizeVar1 + 0.5F), p_151541_13_ - ((float)Math.PI / 2F), p_151541_14_ / 3.0F, p_151541_15_, p_151541_16_, 1.0D);
				this.func_180702_a(random.nextLong(), chunkX, chunkZ, chunkprimer, posX, posY, posZ, (float) (random.nextFloat() * Config.caveTunnelSizeVar2 + 0.5F), p_151541_13_ + ((float)Math.PI / 2F), p_151541_14_ / 3.0F, p_151541_15_, p_151541_16_, 1.0D);
				if (Config.enableDebugging){
					CyborgUtils.logger.info("var1 and 2 fired at: " + posX +", "+ posY+", " + posZ);
				}
				return;
			}

			if (flag2 || random.nextInt(4) != 0)
			{
				double d8 = posX - d4;
				double d9 = posZ - d5;
				double d10 = (double)(p_151541_16_ - p_151541_15_);
				double d11 = (double)(p_151541_12_ + 2.0F + 16.0F);

				if (d8 * d8 + d9 * d9 - d10 * d10 > d11 * d11)
				{
					return;
				}

				if (posX >= d4 - 16.0D - d6 * 2.0D && posZ >= d5 - 16.0D - d6 * 2.0D && posX <= d4 + 16.0D + d6 * 2.0D && posZ <= d5 + 16.0D + d6 * 2.0D)
				{
					int i4 = MathHelper.floor_double(posX - d6) - chunkX * 16 - 1;
					int l1 = MathHelper.floor_double(posX + d6) - chunkX * 16 + 1;
					int j4 = MathHelper.floor_double(posY - d7) - 1;
					int i2 = MathHelper.floor_double(posY + d7) + 1;
					int k4 = MathHelper.floor_double(posZ - d6) - chunkZ * 16 - 1;
					int j2 = MathHelper.floor_double(posZ + d6) - chunkZ * 16 + 1;

					if (i4 < 0)
					{
						i4 = 0;
					}

					if (l1 > 16)
					{
						l1 = 16;
					}

					if (j4 < 1)
					{
						j4 = 1;
					}

					if (i2 > 248)
					{
						i2 = 248;
					}

					if (k4 < 0)
					{
						k4 = 0;
					}

					if (j2 > 16)
					{
						j2 = 16;
					}

					boolean oceanBlock = false;
					int k2;
					int j3;

					for (k2 = i4; !oceanBlock && k2 < l1; ++k2)
					{
						for (int l2 = k4; !oceanBlock && l2 < j2; ++l2)
						{
							for (int i3 = i2 + 1; !oceanBlock && i3 >= j4 - 1; --i3)
							{
								j3 = (k2 * 16 + l2) * 256 + i3;

								if (i3 >= 0 && i3 < 256)
								{
									IBlockState iblockstate = chunkprimer.getBlockState(k2, i3, l2);

									if (isOceanBlock(chunkprimer, k2, i3, l2, chunkX, chunkZ))
									{
										oceanBlock = true;
									}

									if (i3 != j4 - 1 && k2 != i4 && k2 != l1 - 1 && l2 != k4 && l2 != j2 - 1)
									{
										i3 = j4;
									}
								}
							}
						}
					}

					if (!oceanBlock)
					{
						BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
						for (k2 = i4; k2 < l1; ++k2)
						{
							double d13 = ((double)(k2 + chunkX * 16) + 0.5D - posX) / d6;

							for (j3 = k4; j3 < j2; ++j3)
							{
								double d14 = ((double)(j3 + chunkZ * 16) + 0.5D - posZ) / d6;
								int k3 = (k2 * 16 + j3) * 256 + i2;
								boolean foundTop = false;

								if (d13 * d13 + d14 * d14 < 1.0D)
								{
									for (int l3 = i2 - 1; l3 >= j4; --l3)
									{
										double d12 = ((double)l3 + Config.caveVar3 - posY) / d7;
										if (Config.enableDebugging){
											CyborgUtils.logger.info("var3 fired at: "+ posX +", "+ posY+", " + posZ);
										}
										if (d12 > -0.7D && d13 * d13 + d12 * d12 + d14 * d14 < 1.0D)
										{
											IBlockState iblockstate1 = chunkprimer.getBlockState(k2, l3, j3);
                                            IBlockState iblockstate2 = (IBlockState)Objects.firstNonNull(chunkprimer.getBlockState(k2, l3 + 1, j3), Blocks.air.getDefaultState());

											if (isTopBlock(chunkprimer, k2, l3, j3, chunkX, chunkZ))
											{
												foundTop = true;
											}
											digBlock(chunkprimer, k2, l3, j3, chunkX, chunkZ, foundTop, iblockstate1, iblockstate2);
										}

										--k3;
									}
								}
							}
						}

						if (flag2)
						{
							break;
						}
					}
				}
			}
		}
	}
	@Override
	protected boolean func_175793_a(IBlockState p_175793_1_, IBlockState p_175793_2_)
    {
        return p_175793_1_.getBlock() == Blocks.stone ? true : (p_175793_1_.getBlock() == Blocks.dirt ? true : (p_175793_1_.getBlock() == Blocks.grass ? true : (p_175793_1_.getBlock() == Blocks.hardened_clay ? true : (p_175793_1_.getBlock() == Blocks.stained_hardened_clay ? true : (p_175793_1_.getBlock() == Blocks.sandstone ? true : (p_175793_1_.getBlock() == Blocks.red_sandstone ? true : (p_175793_1_.getBlock() == Blocks.mycelium ? true : (p_175793_1_.getBlock() == Blocks.snow_layer ? true : (p_175793_1_.getBlock() == Blocks.sand || p_175793_1_.getBlock() == Blocks.gravel) && p_175793_2_.getMaterial() != Material.water))))))));
    }
	
	@Override
	protected void recursiveGenerate(World world, int p_151538_2_, int p_151538_3_, int p_151538_4_, int p_151538_5_, ChunkPrimer chunkPrimerIn)
	{
		int i1 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);

		if (this.rand.nextInt(7) != 0)
		{
			i1 = 0;
		}

		for (int j1 = 0; j1 < i1; ++j1)
		{
			double d0 = (double)(p_151538_2_ * 16 + this.rand.nextInt(16));
			double d1 = (double)this.rand.nextInt(this.rand.nextInt(120) + 8);
			double d2 = (double)(p_151538_3_ * 16 + this.rand.nextInt(16));
			int k1 = 1;

			if (this.rand.nextInt(Config.largeNodeFrequency) == 0)
			{
				this.func_180703_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, chunkPrimerIn, d0, d1, d2);
				k1 += this.rand.nextInt(4);
			}

			for (int l1 = 0; l1 < k1; ++l1)
			{
				float f = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();

				if (this.rand.nextInt(Config.nodeFrequency) == 0)
				{
					f2 *= this.rand.nextFloat() * this.rand.nextFloat() * Config.nodeMultiplier + 1.0F;
				}

				this.func_180702_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
			}
		}
	}

	protected boolean isOceanBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        net.minecraft.block.Block block = data.getBlockState(x, y, z).getBlock();
        return block== Blocks.flowing_water || block == Blocks.water;
    }

    //Exception biomes to make sure we generate like vanilla
    private boolean isExceptionBiome(net.minecraft.world.biome.BiomeGenBase biome)
    {
    	if (biome == net.minecraft.init.Biomes.beach) return true;
        if (biome == net.minecraft.init.Biomes.desert) return true;
        return false;
    }

    //Determine if the block at the specified location is the top block for the biome, we take into account
    //Vanilla bugs to make sure that we generate the map the same way vanilla does.
    private boolean isTopBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        net.minecraft.world.biome.BiomeGenBase biome = worldObj.getBiomeGenForCoords(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState state = data.getBlockState(x, y, z);
        return (isExceptionBiome(biome) ? state.getBlock() == Blocks.grass : state.getBlock() == biome.topBlock);
    }

    /**
     * Digs out the current block, default implementation removes stone, filler, and top block
     * Sets the block to lava if y is less then 10, and air other wise.
     * If setting to air, it also checks to see if we've broken the surface and if so
     * tries to make the floor the biome's top block
     *
     * @param data Block data array
     * @param index Pre-calculated index into block data
     * @param x local X position
     * @param y local Y position
     * @param z local Z position
     * @param chunkX Chunk X position
     * @param chunkZ Chunk Y position
     * @param foundTop True if we've encountered the biome's top block. Ideally if we've broken the surface.
     */
    protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, IBlockState state, IBlockState up)
    {
        net.minecraft.world.biome.BiomeGenBase biome = worldObj.getBiomeGenForCoords(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState top = biome.topBlock;
        IBlockState filler = biome.fillerBlock;

        if (this.func_175793_a(state, up) || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock())
        {
            if (y < 10)
            {
                data.setBlockState(x, y, z, Blocks.lava.getDefaultState());
            }
            else
            {
                data.setBlockState(x, y, z, Blocks.air.getDefaultState());

                if (up.getBlock() == Blocks.sand)
                {
                    data.setBlockState(x, y + 1, z, up.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? Blocks.red_sandstone.getDefaultState() : Blocks.sandstone.getDefaultState());
                }

                if (foundTop && data.getBlockState(x, y - 1, z).getBlock() == filler.getBlock())
                {
                    data.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
                }
            }
        }
    }
}
