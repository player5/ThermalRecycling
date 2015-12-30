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

import org.blockartistry.mod.ThermalRecycling.data.ScrapValue;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public final class ModBuildCraftBuilders extends ModPlugin {

	static final String[] scrapValuesNone = new String[] { "blueprintItem", "templateItem", "markerBlock",
			"pathMarkerBlock", };

	static final String[] scrapValuesPoor = new String[] { };

	static final String[] scrapValuesStandard = new String[] {
			"libraryBlock"
	};

	static final String[] scrapValuesSuperior = new String[] { "machineBlock", "builderBlock", "architectBlock" };

	public ModBuildCraftBuilders() {
		super(SupportedMod.BUILDCRAFT_BUILDERS);
	}

	@Override
	public boolean initialize() {

		registerScrapValues(ScrapValue.NONE, scrapValuesNone);
		registerScrapValues(ScrapValue.POOR, scrapValuesPoor);
		registerScrapValues(ScrapValue.STANDARD, scrapValuesStandard);
		registerScrapValues(ScrapValue.SUPERIOR, scrapValuesSuperior);

		// Misc block machines
		sawmill.append("BuildCraft|Builders:libraryBlock").output(Blocks.planks, 6).secondaryOutput(Items.book, 3)
				.save();

		sawmill.append("BuildCraft|Builders:builderBlock").output(Blocks.planks, 12).secondaryOutput(Items.diamond, 8)
				.save();

		sawmill.append("BuildCraft|Builders:architectBlock").output(Blocks.planks, 4).secondaryOutput(Items.diamond, 8)
				.save();

		return true;
	}
}
