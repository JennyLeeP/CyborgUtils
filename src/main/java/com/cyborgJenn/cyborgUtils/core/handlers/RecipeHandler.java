package com.cyborgJenn.cyborgUtils.core.handlers;

import com.cyborgJenn.cyborgUtils.core.utils.Config;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeHandler 
{
	public void initRecipes()
	{
		if(Config.enableModuleMachine)
		{
			addMachineRecipes();
		}

	}

	private void addMachineRecipes()
	{
		if(ModHelper.isThaumcraftLoaded && Config.useThaumcraftRecipes)
		{
			//TODO add ThaumcraftRecipes
		}
		else
		{
			//TODO add regular recipes.
		}

	}
}
