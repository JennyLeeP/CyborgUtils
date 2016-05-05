package com.cyborgJenn.cyborgUtils.module.combat.item;

import java.util.List;

import com.cyborgJenn.cyborgUtils.CyborgUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Sword1 extends ItemSword 
{
	private final Item.ToolMaterial material;
	private float attackDamage;
	
	public Sword1(Item.ToolMaterial material, String name)
	{
		super(material);
		this.material = material;
		this.setCreativeTab(CyborgUtils.tabCyborgCore);
		this.setUnlocalizedName(name);
		this.attackDamage = 4.0F + material.getDamageVsEntity();
	}
	@SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
		tooltip.add(TextFormatting.DARK_PURPLE + "This is a sword, hit things with it");
    }
}
