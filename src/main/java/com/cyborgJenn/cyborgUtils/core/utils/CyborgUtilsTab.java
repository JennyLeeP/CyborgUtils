package com.cyborgJenn.cyborgUtils.core.utils;

import com.cyborgJenn.cyborgUtils.core.item.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CyborgUtilsTab extends CreativeTabs{

	public CyborgUtilsTab(int id, String name)
	  {
	    super(id, name);
	    this.setBackgroundImageName("cyborgutils_search.png");
	    
	  }
	
	@Override
	public Item getTabIconItem() {
		if (Config.enableModuleAccessories)
		{
			return ModItems.Sextant;
		}else {
			return Items.diamond_sword;
		}
		
	}
	@Override
	public boolean hasSearchBar()
	{
		return true;
	}
}
