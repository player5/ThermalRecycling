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

package org.blockartistry.mod.ThermalRecycling.data;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.blockartistry.mod.ThermalRecycling.ItemManager;
import org.blockartistry.mod.ThermalRecycling.items.RecyclingScrap;
import org.blockartistry.mod.ThermalRecycling.machines.ProcessingCorePolicy;
import org.blockartistry.mod.ThermalRecycling.util.ItemStackHelper;
import org.blockartistry.mod.ThermalRecycling.util.ItemStackWeightTable;
import org.blockartistry.mod.ThermalRecycling.util.ItemStackWeightTable.ItemStackItem;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public final class ScrappingTables {

	static final ItemStack keep = new ItemStack(Blocks.dirt);
	static final ItemStack dust = new ItemStack(Blocks.cobblestone);
	static final ItemStack poorScrap = new ItemStack(
			ItemManager.recyclingScrap, 1, RecyclingScrap.POOR);
	static final ItemStack standardScrap = new ItemStack(
			ItemManager.recyclingScrap, 1, RecyclingScrap.STANDARD);
	static final ItemStack superiorScrap = new ItemStack(
			ItemManager.recyclingScrap, 1, RecyclingScrap.SUPERIOR);

	static final ArrayList<ItemStackWeightTable> componentScrap = new ArrayList<ItemStackWeightTable>();
	static final ArrayList<ItemStackWeightTable> extractionDust = new ArrayList<ItemStackWeightTable>();

	static boolean keepIt(ItemStack stack) {
		return stack != null && stack.isItemEqual(keep);
	}

	static boolean dustIt(ItemStack stack) {
		return stack != null && stack.isItemEqual(dust);
	}

	static {
		
		// Each scrap quality has two tables in componentScrap.  The first is for when
		// an item is a component of a device that is being taken apart.  The second
		// table is when the item is being directly scrapped.

		// The "NONE" scrap value. This is used when breaking a recipe down and
		// this item is one of the components.
		ItemStackWeightTable t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 29));
		t.add(t.new ItemStackItem(keep, 1));
		t.add(t.new ItemStackItem(dust, 2));
		t.add(t.new ItemStackItem(poorScrap, 1));
		componentScrap.add(t);

		// The "NONE" must scrap table. No scrap to be had.
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 1));
		componentScrap.add(t);

		// The "POOR" scrap table.
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 93));
		t.add(t.new ItemStackItem(keep, 62));
		t.add(t.new ItemStackItem(dust, 62));
		t.add(t.new ItemStackItem(poorScrap, 12));
		t.add(t.new ItemStackItem(standardScrap, 4));
		componentScrap.add(t);

		// The "POOR" scrap MUST table.
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 155));
		t.add(t.new ItemStackItem(poorScrap, 32));
		t.add(t.new ItemStackItem(standardScrap, 24));
		t.add(t.new ItemStackItem(superiorScrap, 20));
		componentScrap.add(t);

		// The "STANDARD" scrap table
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 68));
		t.add(t.new ItemStackItem(keep, 62));
		t.add(t.new ItemStackItem(dust, 62));
		t.add(t.new ItemStackItem(poorScrap, 8));
		t.add(t.new ItemStackItem(standardScrap, 24));
		t.add(t.new ItemStackItem(superiorScrap, 2));
		componentScrap.add(t);

		// The "STANDARD" scrap MUST table
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 130));
		t.add(t.new ItemStackItem(poorScrap, 28));
		t.add(t.new ItemStackItem(standardScrap, 48));
		t.add(t.new ItemStackItem(superiorScrap, 22));
		componentScrap.add(t);

		// The "SUPERIOR" scrap table
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 33));
		t.add(t.new ItemStackItem(keep, 62));
		t.add(t.new ItemStackItem(dust, 62));
		t.add(t.new ItemStackItem(poorScrap, 4));
		t.add(t.new ItemStackItem(standardScrap, 16));
		t.add(t.new ItemStackItem(superiorScrap, 48));
		componentScrap.add(t);

		// The "SUPERIOR" scrap MUST table
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 95));
		t.add(t.new ItemStackItem(poorScrap, 24));
		t.add(t.new ItemStackItem(standardScrap, 36));
		t.add(t.new ItemStackItem(superiorScrap, 68));
		componentScrap.add(t);

		// Extraction tables - first table is a dummy because
		// the logic below assumes there is an entry for NONE
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 1));
		extractionDust.add(t);

		// POOR
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 113));
		t.add(t.new ItemStackItem(standardScrap, 3));
		t.add(t.new ItemStackItem(ItemStackHelper.dustWood, 3));
		t.add(t.new ItemStackItem(ItemStackHelper.boneMeal, 3));
		t.add(t.new ItemStackItem(ItemStackHelper.dustIron, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustTin, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustCopper, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustSilver, 1));
		t.add(t.new ItemStackItem(ItemStackHelper.dustLead, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustNickel, 2));
		extractionDust.add(t);

		// STANDARD
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 30));
		t.add(t.new ItemStackItem(superiorScrap, 3));
		t.add(t.new ItemStackItem(ItemStackHelper.boneMeal, 1));
		t.add(t.new ItemStackItem(ItemStackHelper.dustIron, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustTin, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustCopper, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustSilver, 1));
		t.add(t.new ItemStackItem(ItemStackHelper.dustLead, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustNickel, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustGold, 1));
		extractionDust.add(t);

		// SUPERIOR
		t = new ItemStackWeightTable();
		t.add(t.new ItemStackItem(null, 9));
		t.add(t.new ItemStackItem(ItemStackHelper.dustSilver, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustGold, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustPlatinum, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustElectrum, 2));
		t.add(t.new ItemStackItem(ItemStackHelper.dustSignalum, 1));
		t.add(t.new ItemStackItem(ItemStackHelper.dustLumium, 1));
		t.add(t.new ItemStackItem(ItemStackHelper.dustEnderium, 1));
		extractionDust.add(t);
	}

	protected static ItemStackWeightTable getTable(ItemStack core,
			ItemStack stack) {

		boolean mustScrap = core == null;
		int scrappingValue = ItemScrapData.get(stack).getScrapValue().ordinal();

		if (!ProcessingCorePolicy.isExtractionCore(core))
			scrappingValue *= 2;

		if (mustScrap)
			scrappingValue++;

		if (core == null || ProcessingCorePolicy.isDecompositionCore(core))
			return componentScrap.get(scrappingValue);

		return extractionDust.get(scrappingValue);
	}

	public static List<ItemStack> getScrapPossibilities(ItemStack core,
			ItemStack stack) {

		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		ItemStackWeightTable t = getTable(core, stack);

		for (ItemStackItem w : t.getEntries()) {

			ItemStack temp = w.getStack();
			if (temp != null) {

				double percent = w.itemWeight * 100F / t.getTotalWeight();
				ItemStackHelper.setItemLore(temp,
						String.format("%-1.2f%% chance", percent));
				result.add(temp);
			}
		}

		return result;
	}

	public static List<ItemStack> extractItems(ItemStack stack) {
		if (stack == null || stack.stackSize < 1)
			return null;

		return null;
	}

	public static List<ItemStack> scrapItems(ItemStack core, ItemStack stack) {
		if (stack == null || stack.stackSize < 1)
			return null;

		List<ItemStack> result = null;
		List<ItemStack> processingList = null;

		// If a decomp core is installed, get a list of outputs from the recipes.
		// If an extraction core and the item is a scrap box, convert to scrap
		// with a bonus - 10 pieces total.
		if (ProcessingCorePolicy.isDecompositionCore(core))
			processingList = RecipeData.getRecipe(stack);
		else if(ProcessingCorePolicy.isExtractionCore(core) && stack.getItem() == ItemManager.recyclingScrapBox) {
			ItemStack t = new ItemStack(ItemManager.recyclingScrap, 10, stack.getItemDamage());
			processingList = new ArrayList<ItemStack>();
			processingList.add(t);
		}

		// If we get here just scrap the input
		if(processingList == null) {
			processingList = new ArrayList<ItemStack>();
			processingList.add(stack);
		}
		
		result = new ArrayList<ItemStack>();

		for (ItemStack item: processingList) {
			
			ItemStackWeightTable t = getTable(core, item);

			for(int count = 0; count < item.stackSize; count++) {
			
				ItemStack cupieDoll = t.nextStack();
	
				if (cupieDoll != null) {
					ItemStack temp = item.copy();
					temp.stackSize = 1;
	
					// Post process and return
					if (keepIt(cupieDoll)) {
						result.add(temp);
					} else if (dustIt(cupieDoll)) {
						result.add(ItemStackHelper
								.convertToDustIfPossible(temp));
					} else {
						result.add(cupieDoll);
					}
				}
			}
		}
		
		return result;
	}

	public static boolean canBeScrapped(ItemStack stack) {

		return !(ItemHelper.isBlock(stack) || ItemHelper.isDust(stack)
				|| ItemHelper.isIngot(stack) || ItemHelper.isNugget(stack)
				|| ItemHelper.isOre(stack)
				|| stack.getItem() == ItemManager.recyclingScrap || stack
					.getItem() == ItemManager.recyclingScrapBox);
	}

	public static void writeDiagnostic(Writer writer) throws Exception {

		writer.write("Scrapping Tables:\n");
		writer.write("=================================================================\n");

		componentScrap.get(0).diagnostic("NONE", writer);
		componentScrap.get(1).diagnostic("NONE Must", writer);
		componentScrap.get(2).diagnostic("POOR", writer);
		componentScrap.get(3).diagnostic("POOR Must", writer);
		componentScrap.get(4).diagnostic("STANDARD", writer);
		componentScrap.get(5).diagnostic("STANDARD Must", writer);
		componentScrap.get(6).diagnostic("SUPERIOR", writer);
		componentScrap.get(7).diagnostic("SUPERIOR Must", writer);

		extractionDust.get(1).diagnostic("Poor Scrap", writer);
		extractionDust.get(2).diagnostic("Standard Scrap", writer);
		extractionDust.get(3).diagnostic("Superior Scrap", writer);

		writer.write("=================================================================\n");
	}
}