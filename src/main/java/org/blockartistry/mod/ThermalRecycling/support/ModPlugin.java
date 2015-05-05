/*
 * This file is part of ThermalRecycling, licensed under the MIT License (MIT).
 *
 * Copyright (c) OreCruncher
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.blockartistry.mod.ThermalRecycling.support;

import org.blockartistry.mod.ThermalRecycling.ModLog;
import org.blockartistry.mod.ThermalRecycling.data.ItemScrapData;
import org.blockartistry.mod.ThermalRecycling.data.ScrapValue;
import org.blockartistry.mod.ThermalRecycling.support.recipe.BlastRecipeBuilder;
import org.blockartistry.mod.ThermalRecycling.support.recipe.FluidTransposerRecipeBuilder;
import org.blockartistry.mod.ThermalRecycling.support.recipe.FurnaceRecipeBuilder;
import org.blockartistry.mod.ThermalRecycling.support.recipe.PulverizerRecipeBuilder;
import org.blockartistry.mod.ThermalRecycling.support.recipe.SawmillRecipeBuilder;
import org.blockartistry.mod.ThermalRecycling.support.recipe.SmelterRecipeBuilder;
import org.blockartistry.mod.ThermalRecycling.support.recipe.ThermalRecyclerRecipeBuilder;
import org.blockartistry.mod.ThermalRecycling.util.ItemStackHelper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

public abstract class ModPlugin {

	protected final SupportedMod mod;
	protected final String MOD_CONFIG_SECTION;

	SawmillRecipeBuilder sawmill = new SawmillRecipeBuilder();
	PulverizerRecipeBuilder pulverizer = new PulverizerRecipeBuilder();
	FurnaceRecipeBuilder furnace = new FurnaceRecipeBuilder();
	ThermalRecyclerRecipeBuilder recycler = new ThermalRecyclerRecipeBuilder();
	SmelterRecipeBuilder smelter = new SmelterRecipeBuilder();
	FluidTransposerRecipeBuilder fluid = new FluidTransposerRecipeBuilder();
	BlastRecipeBuilder blast = new BlastRecipeBuilder();

	public ModPlugin(SupportedMod m) {
		mod = m;
		MOD_CONFIG_SECTION = "recycle.recipe.control." + mod.getModId();
	}

	public String getModId() {
		return mod.getModId();
	}

	public String getName() {
		return mod.getName();
	}

	public void init(Configuration config) {
		// Nothing by default - override in a derived class to get
		// config options for the plugin
	}

	public abstract void apply();

	protected String makeName(String name) {
		String result;
		
		if(name.startsWith("^"))
			result = name.substring(1);
		else
			result = getModId() + ":" + name;
		
		return result;
	}
	
	// NOTE THAT THESE REGISTER ROUTINES PREFIX THE STRING WITH
	// THE CURRENT MOD NAME!  IF IT NEEDS TO BE ESCAPED PUT A
	// ^ CHARACTER AT THE FRONT!
	protected void registerRecipesToIgnore(String... list) {
		for (String s : list) {
			String name = makeName(s);
			ItemStack stack = ItemStackHelper.getItemStack(name);
			if (stack != null)
				ItemScrapData.setRecipeIgnored(stack, true);
			else
				ModLog.warn("[%s] registerRecipesToIgnore(): unknown item '%s'", mod.getName(), name);
		}
	}

	protected void registerRecipesToReveal(String... list) {
		for (String s : list) {
			String name = makeName(s);
			ItemStack stack = ItemStackHelper.getItemStack(name);
			if (stack != null)
				ItemScrapData.setRecipeIgnored(stack, false);
			else
				ModLog.warn("[%s] registerRecipesToReveal(): unknown item '%s'", mod.getName(), name);
		}
	}

	protected void registerScrapValues(ScrapValue value, String... list) {
		for (String s : list) {
			String name = makeName(s);
			ItemStack stack = ItemStackHelper.getItemStack(name);
			if(stack != null)
				ItemScrapData.setValue(stack, value);
			else
				ModLog.warn("[%s] registerScrapValues(): unknown item '%s'", mod.getName(), name);
		}
	}

	protected void registerScrubFromOutput(String... list) {
		for (String s : list) {
			String name = makeName(s);
			ItemStack stack = ItemStackHelper.getItemStack(name);
			if (stack != null)
				ItemScrapData.setScrubbedFromOutput(stack, true);
			else
				ModLog.warn("[%s] registerScrubFromOutput(): unknown item '%s'", mod.getName(), name);
		}
	}

	protected void registerNotScrubFromOutput(String... list) {
		for (String s : list) {
			String name = makeName(s);
			ItemStack stack = ItemStackHelper.getItemStack(name);
			if (stack != null)
				ItemScrapData.setScrubbedFromOutput(stack, false);
			else
				ModLog.warn("[%s] registerNotScrubFromOutput(): unknown item '%s'", mod.getName(), name);
		}
	}
}
