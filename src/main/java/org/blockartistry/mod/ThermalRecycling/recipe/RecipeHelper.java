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

package org.blockartistry.mod.ThermalRecycling.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.blockartistry.mod.ThermalRecycling.ModLog;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public final class RecipeHelper {

	protected static HashMap<String, ItemStack> preferred = null;

	protected static void initializePreferred() {

		if (preferred != null)
			return;

		preferred = new HashMap<String, ItemStack>();

		// Cache builtins
		preferred.put("ingotIron", new ItemStack(Items.iron_ingot));
		preferred.put("ingotGold", new ItemStack(Items.gold_ingot));
		preferred.put("nuggetGold", new ItemStack(Items.gold_nugget));
		preferred.put("blockGold", new ItemStack(Blocks.gold_block));
		preferred.put("blockIron", new ItemStack(Blocks.iron_block));

		Item materialBase = GameData.getItemRegistry().getObject(
				"ThermalFoundation:material");
		Item storageBase = GameData.getItemRegistry().getObject(
				"ThermalFoundation:Storage");

		preferred.put("ingotCopper", new ItemStack(materialBase, 1, 64));
		preferred.put("ingotTin", new ItemStack(materialBase, 1, 65));
		preferred.put("ingotSilver", new ItemStack(materialBase, 1, 66));
		preferred.put("ingotLead", new ItemStack(materialBase, 1, 67));
		preferred.put("ingotNickel", new ItemStack(materialBase, 1, 68));
		preferred.put("ingotPlatinum", new ItemStack(materialBase, 1, 69));
		preferred.put("ingotManaInfused", new ItemStack(materialBase, 1, 70));
		preferred.put("ingotElectrum", new ItemStack(materialBase, 1, 71));
		preferred.put("ingotInvar", new ItemStack(materialBase, 1, 72));
		preferred.put("ingotBronze", new ItemStack(materialBase, 1, 73));
		preferred.put("ingotSignalum", new ItemStack(materialBase, 1, 74));
		preferred.put("ingotLumium", new ItemStack(materialBase, 1, 75));
		preferred.put("ingotEnderium", new ItemStack(materialBase, 1, 76));

		preferred.put("nuggetIron", new ItemStack(materialBase, 1, 8));
		preferred.put("nuggetCopper", new ItemStack(materialBase, 1, 96));
		preferred.put("nuggetTin", new ItemStack(materialBase, 1, 97));
		preferred.put("nuggetSilver", new ItemStack(materialBase, 1, 98));
		preferred.put("nuggetLead", new ItemStack(materialBase, 1, 99));
		preferred.put("nuggetNickel", new ItemStack(materialBase, 1, 100));
		preferred.put("nuggetPlatinum", new ItemStack(materialBase, 1, 101));
		preferred.put("nuggetManaInfused", new ItemStack(materialBase, 1, 102));
		preferred.put("nuggetElectrum", new ItemStack(materialBase, 1, 103));
		preferred.put("nuggetInvar", new ItemStack(materialBase, 1, 104));
		preferred.put("nuggetBronze", new ItemStack(materialBase, 1, 105));
		preferred.put("nuggetSignalum", new ItemStack(materialBase, 1, 106));
		preferred.put("nuggetLumium", new ItemStack(materialBase, 1, 107));
		preferred.put("nuggetEnderium", new ItemStack(materialBase, 1, 108));

		preferred.put("blockCopper", new ItemStack(storageBase, 1, 0));
		preferred.put("blockTin", new ItemStack(storageBase, 1, 1));
		preferred.put("blockSilver", new ItemStack(storageBase, 1, 2));
		preferred.put("blockLead", new ItemStack(storageBase, 1, 3));
		preferred.put("blockFerrous", new ItemStack(storageBase, 1, 4));
		preferred.put("blockPlatinum", new ItemStack(storageBase, 1, 5));
		preferred.put("blockManaInfused", new ItemStack(storageBase, 1, 6));
		preferred.put("blockElectrum", new ItemStack(storageBase, 1, 7));
		preferred.put("blockInvar", new ItemStack(storageBase, 1, 8));
		preferred.put("blockBronze", new ItemStack(storageBase, 1, 9));
		preferred.put("blockSignalum", new ItemStack(storageBase, 1, 10));
		preferred.put("blockLumium", new ItemStack(storageBase, 1, 11));
		preferred.put("blockEnderium", new ItemStack(storageBase, 1, 12));
	}

	public static ItemStack getItemStack(String name, int quantity) {

		if (preferred == null)
			initializePreferred();

		// Check our preferred list first.  If we have a hit, use it.
		ItemStack result = preferred.get(name);

		if (result != null) {

			result = result.copy();
			result.stackSize = quantity;

		} else {

			// Parse out the possible subtype from the end of the string
			int subType = -1;
			String workingName = StringUtils.substringBeforeLast(name, ":");
			String num = StringUtils.substringAfterLast(name, ":");
			
			if (num != null && !num.isEmpty()) {
				try {
					subType = Integer.parseInt(num);
				} catch (Exception e) {
					// It appears malformed - assume the incoming name is
					// the real name and continue.
					workingName = name;
				}
			}

			// Check the OreDictionary first for any alias matches.  Otherwise
			// go to the game registry to find a match.
			ArrayList<ItemStack> ores = OreDictionary.getOres(workingName);
			if (!ores.isEmpty()) {
				result = ores.get(0).copy();
				result.stackSize = quantity;
			} else {
				Item i = GameData.getItemRegistry().getObject(workingName);
				if (i != null) {
					result = new ItemStack(i, quantity);
				}
			}

			// If we did have a hit on a base item, set the subtype
			// as needed.
			if (result != null && subType != -1) {
				result.setItemDamage(subType);
			}
		}

		// Log if we didn't find an item - it's possible that the recipe has a type
		// or the mod has changed where the item no longer exists.
		if (result == null)
			ModLog.info("Unable to locate item: " + name);

		return result;
	}

	public static List<ItemStack> getItemStackRange(String name,
			int startSubtype, int endSubtype, int quantity) {

		ArrayList<ItemStack> result = new ArrayList<ItemStack>();

		ItemStack stack = getItemStack(name, 1);

		for (int i = startSubtype; i <= endSubtype; i++) {
			ItemStack s = stack.copy();
			s.stackSize = quantity;
			s.setItemDamage(i);
			result.add(s);
		}

		return result;
	}

	public static List<ItemStack> getItemStackRange(Item item, int start,
			int end, int quantity) {

		ArrayList<ItemStack> result = new ArrayList<ItemStack>();

		for (int i = start; i <= end; i++) {
			result.add(new ItemStack(item, quantity, i));
		}

		return result;
	}

	public static List<ItemStack> getItemStackRange(Block block, int start,
			int end, int quantity) {

		ArrayList<ItemStack> result = new ArrayList<ItemStack>();

		for (int i = start; i <= end; i++) {
			result.add(new ItemStack(block, quantity, i));
		}

		return result;
	}

	public static FluidStack getFluidStack(String name, int quantity) {
		return FluidRegistry.getFluidStack(name, quantity);
	}

	public static String resolveName(ItemStack stack) {
		String result = null;

		if (stack != null) {

			try {
				result = stack.getDisplayName();
			} catch (Exception e) {
				;
			}

			if (result == null) {
				try {
					result = stack.getUnlocalizedName();
				} catch (Exception e) {
					;
				}
			}
		}

		return result == null || result.isEmpty() ? "UNKNOWN" : result;
	}

	public void dumpSubItems(Logger log, String itemId) {
		ItemStack stack = getItemStack(itemId, 1);
		if (stack != null) {

			try {
				for (int i = 0; i < 1024; i++) {
					stack.setItemDamage(i);
					String name = resolveName(stack);
					if (name != null && !name.isEmpty()
							&& !name.contains("(Destroy)"))
						log.info(String.format("%s:%d = %s", itemId, i, name));
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				;
			}
		}
	}

	public void dumpItemStack(Logger log, String title, ItemStack... items) {

		log.info("");
		log.info(title);
		log.info(StringUtils.repeat('-', 32));

		if (items == null || items.length == 0) {
			log.info("No items in list");
			return;
		}

		for (ItemStack stack : items) {
			log.info(String.format("%s (%s)", resolveName(stack),
					stack.toString()));
		}
		log.info(StringUtils.repeat('-', 32));
		log.info(String.format("Total: %d item stacks", items.length));
	}

	public static void dumpFluidRegistry(Logger log) {

		log.info("Fluid Registry:");

		for (Entry<String, Fluid> e : FluidRegistry.getRegisteredFluids()
				.entrySet()) {
			log.info(String.format("%s: %s", e.getKey(), e.getValue().getName()));
		}
	}
}