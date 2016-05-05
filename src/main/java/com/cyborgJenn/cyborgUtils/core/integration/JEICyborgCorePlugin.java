package com.cyborgJenn.cyborgUtils.core.integration;

import com.cyborgJenn.cyborgUtils.CyborgUtils;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class JEICyborgCorePlugin implements IModPlugin{
	
	@SuppressWarnings("unused")
	private IJeiHelpers jeiHelpers;
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime arg0) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unused")
	@Override
	public void register(IModRegistry registry) {
		IItemRegistry itemRegistry = registry.getItemRegistry();
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();

		CyborgUtils.logger.info("Initializing CyborgUtils JEI plugin...");

		//registry.addDescription(new ItemStack(AccessoryItems.FancyCompass), "This Compass Shows your direction at the top of the Game Screen");
		jeiHelpers.getGuiHelper().createBlankDrawable(1, 3);
	}

}
