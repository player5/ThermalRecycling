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

package org.blockartistry.mod.ThermalRecycling.blocks;

import java.util.Random;

import org.blockartistry.mod.ThermalRecycling.BlockManager;
import org.blockartistry.mod.ThermalRecycling.CreativeTabManager;
import org.blockartistry.mod.ThermalRecycling.ItemManager;
import org.blockartistry.mod.ThermalRecycling.items.Material;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFarmland;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class FertileLand extends BlockFarmland {

	@SideOnly(Side.CLIENT)
	private IIcon surfaceIcon;

	public FertileLand() {
		super();

		setHardness(0.6F);
		setStepSound(soundTypeGravel);

		setTickRandomly(false);
		setBlockName("FertileLand");
		setBlockTextureName("farmland");
		
		setCreativeTab(CreativeTabManager.tab);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {

	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity,
			float what) {

	}

	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
		final EnumPlantType plant = plantable.getPlantType(world, x, y + 1, z);
        // Crops or reeds
		return plant == EnumPlantType.Crop || plant == EnumPlantType.Beach;
    }

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return super.getIcon(side, 7);
	}

	public void register() {
		GameRegistry.registerBlock(this, getUnlocalizedName());

		GameRegistry.addShapelessRecipe(
				new ItemStack(BlockManager.fertileLand),
				new ItemStack(Blocks.dirt),
				new ItemStack(Items.water_bucket),
				new ItemStack(ItemManager.material, 1, Material.WORMS));
	}
}