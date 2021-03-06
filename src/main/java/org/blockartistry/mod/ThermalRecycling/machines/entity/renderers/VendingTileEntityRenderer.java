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

package org.blockartistry.mod.ThermalRecycling.machines.entity.renderers;

import org.blockartistry.mod.ThermalRecycling.ModOptions;
import org.blockartistry.mod.ThermalRecycling.ThermalRecycling;
import org.blockartistry.mod.ThermalRecycling.machines.MachineBase;
import org.blockartistry.mod.ThermalRecycling.machines.entity.VendingTileEntity;
import org.blockartistry.mod.ThermalRecycling.util.DyeHelper;
import org.blockartistry.mod.ThermalRecycling.util.InventoryHelper;
import org.blockartistry.mod.ThermalRecycling.world.FantasyIsland;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public final class VendingTileEntityRenderer extends TileEntitySpecialRenderer
		implements IItemRenderer {

	private static final int RENDER_STYLE_NORMAL = 0;
	private static final int RENDER_STYLE_AVAILABLE = 1;
	private static final int RENDER_STYLE_OUT_OF_STOCK = 2;
	private static final int RENDER_STYLE_FREE = 3;

	private static final int[] RENDER_FORE_COLORS = new int[] {
			DyeHelper.getDyeRenderColor(DyeHelper.COLOR_WHITE),
			DyeHelper.getDyeRenderColor(DyeHelper.COLOR_LIME),
			DyeHelper.getDyeRenderColor(DyeHelper.COLOR_RED),
			DyeHelper.getDyeRenderColor(DyeHelper.COLOR_LIME), };

	private static final ResourceLocation texture = new ResourceLocation(
			ThermalRecycling.MOD_ID, "textures/blocks/VendingModel.png");
	private static final ModelBase model = new VendingModel();
	private static final String FREE = StatCollector
			.translateToLocal("msg.MachineVending.free");

	private static final RenderItem itemRenderer = new RenderItem();
	private static final EntityItem item = new EntityItem(
			FantasyIsland.instance);

	private static final float BLOCK_SCALE = 0.35F;
	private static final float LEFT_EDGE_OFFSET = 0.25F;
	private static final float TOP_EDGE_OFFSET = 0.1F;
	private static final float FRONT_EDGE_OFFSET = -0.12F;
	private static final float IMAGE_SIZE = 0.28F;

	private static final float f = 0.5F; // 1.6F;
	private static final float f1 = 0.01666667F * f;

	// These are squared distance measures
	private static final double ITEM_RENDER_RANGE = Math.pow(
			ModOptions.getVendingItemRenderRange(), 2);
	private static final double ITEM_QUANTITY_RENDER_RANGE = Math.pow(
			ModOptions.getVendingQuantityRenderRange(), 2);
	private static final double VENDING_TITLE_RENDER_RANGE = Math.pow(
			ModOptions.getVendingNameRenderRange(), 2);

	private static final float[] xOffset = new float[] {
			LEFT_EDGE_OFFSET - 0 * IMAGE_SIZE,
			LEFT_EDGE_OFFSET - 1 * IMAGE_SIZE,
			LEFT_EDGE_OFFSET - 2 * IMAGE_SIZE, };

	private static final float[] yOffset = new float[] {
			TOP_EDGE_OFFSET - 0 * IMAGE_SIZE, TOP_EDGE_OFFSET - 1 * IMAGE_SIZE,
			TOP_EDGE_OFFSET - 2 * IMAGE_SIZE, TOP_EDGE_OFFSET - 3 * IMAGE_SIZE,
			TOP_EDGE_OFFSET - 4 * IMAGE_SIZE, TOP_EDGE_OFFSET - 5 * IMAGE_SIZE, };

	private static final float FREE_X_OFFSET = xOffset[0] - IMAGE_SIZE / 2;

	private static final int[] rotationFacings = new int[] { 0, 0, 0, 180, 270,
			90, 0, 0 };

	// Dynamic setting
	private double playerRange = 0;

	public VendingTileEntityRenderer() {
		super();

		item.hoverStart = 0F;
		itemRenderer.setRenderManager(RenderManager.instance);
	}

	protected void setColor(final int color, final float alpha) {
		final float red = (float) (color >> 16 & 255) / 255.0F;
		final float blue = (float) (color >> 8 & 255) / 255.0F;
		final float green = (float) (color & 255) / 255.0F;
		Tessellator.instance.setColorRGBA_F(red, blue, green, alpha);
	}

	protected boolean playerInRange(final double range) {
		return playerRange <= range;
	}

	protected void renderItem(final ItemStack stack, final int x, final int y,
			final boolean includeQuantity, final int renderStyle) {

		if (stack == null)
			return;

		item.setEntityItemStack(stack);

		GL11.glPushMatrix();

		// Flip
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(xOffset[x], yOffset[y], FRONT_EDGE_OFFSET);
		GL11.glPushMatrix();

		GL11.glScalef(BLOCK_SCALE * 1.1F, BLOCK_SCALE * 1.1F,
				BLOCK_SCALE * 1.1F);

		final boolean save = RenderItem.renderInFrame;
		RenderItem.renderInFrame = true;
		GL11.glTranslatef(0.0F, 0.11F, 0.0F);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		itemRenderer.doRender(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		RenderItem.renderInFrame = save;
		GL11.glPopMatrix();

		if (includeQuantity) {

			final FontRenderer font = Minecraft.getMinecraft().fontRenderer;

			// Move the number slightly in front of the item
			GL11.glTranslatef(0.02F, 0.02F, -0.11F);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glScalef(-f1, -f1, f1);

			GL11.glDisable(2896);
			GL11.glDisable(3042);

			String amt = String.valueOf(stack.stackSize);
			font.drawString(amt, 13 - font.getStringWidth(amt), -12,
					RENDER_FORE_COLORS[renderStyle]);
			GL11.glEnable(2896);
		}

		GL11.glPopMatrix();
	}

	protected void renderName(final String name, final int fColor,
			final int bColor) {
		if (name.isEmpty())
			return;

		final FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		final Tessellator tessellator = Tessellator.instance;
		final int color = DyeHelper.getDyeRenderColor(fColor);
		final int backColor = DyeHelper.getDyeRenderColor(bColor);
		final int nameWidth = font.getStringWidth(name) / 2;

		GL11.glPushMatrix();

		// Flip
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(0F, 0.48F, -0.51F);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glScalef(-f1, -f1, f1);
		GL11.glDisable(2896);
		GL11.glDepthMask(false);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		final byte byte0 = 0;
		GL11.glDisable(3553);
		tessellator.startDrawingQuads();
		setColor(backColor, 0.75F);
		tessellator.addVertex(-nameWidth - 1, -1 + byte0, 0.0D);
		tessellator.addVertex(-nameWidth - 1, 8 + byte0, 0.0D);
		tessellator.addVertex(nameWidth + 1, 8 + byte0, 0.0D);
		tessellator.addVertex(nameWidth + 1, -1 + byte0, 0.0D);
		tessellator.draw();
		GL11.glEnable(3553);
		font.drawString(name, -nameWidth, byte0, backColor);
		GL11.glDepthMask(true);
		font.drawString(name, -nameWidth, byte0, color);
		GL11.glEnable(2896);
		GL11.glDisable(3042);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	protected void renderFREE(final int y) {

		final FontRenderer font = Minecraft.getMinecraft().fontRenderer;

		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(FREE_X_OFFSET, yOffset[y] + 0.15F, FRONT_EDGE_OFFSET);

		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glScalef(-f1 * 1.3F, -f1 * 1.3F, f1 * 1.3F);

		GL11.glDisable(2896);
		GL11.glDisable(3042);

		font.drawString(FREE, -font.getStringWidth(FREE) / 2, 0,
				RENDER_FORE_COLORS[RENDER_STYLE_FREE]);
		GL11.glEnable(2896);

		GL11.glPopMatrix();
	}

	protected void renderTradeInventory(final TileEntity te) {

		final VendingTileEntity vte = (VendingTileEntity) te;
		final boolean includeQuantity = playerInRange(ITEM_QUANTITY_RENDER_RANGE);

		for (int i = 0; i < 6; i++) {

			final int base = i + VendingTileEntity.CONFIG_SLOT_START;
			final ItemStack input1 = vte.getStackInSlot(base);
			final ItemStack input2 = vte.getStackInSlot(base + 6);
			final ItemStack trade = vte.getStackInSlot(base + 12);

			if (input1 != null)
				renderItem(input1, 0, i, includeQuantity, RENDER_STYLE_NORMAL);

			if (input2 != null)
				renderItem(input2, 1, i, includeQuantity, RENDER_STYLE_NORMAL);

			if (trade != null) {

				if (input1 == null && input2 == null) {
					renderFREE(i);
				}

				boolean colorCode = false;
				if (!vte.isAdminMode() && trade != null) {
					colorCode = !InventoryHelper.doesInventoryContain(
							vte.getRawInventory(),
							VendingTileEntity.INVENTORY_SLOT_START,
							VendingTileEntity.GENERAL_INVENTORY_SIZE - 1,
							trade, null);
				}

				renderItem(trade, 2, i, includeQuantity,
						colorCode ? RENDER_STYLE_OUT_OF_STOCK
								: RENDER_STYLE_AVAILABLE);
			}
		}
	}

	@Override
	public void renderTileEntityAt(final TileEntity te, final double x,
			final double y, final double z, final float scale) {

		final VendingTileEntity vte = (VendingTileEntity) te;
		if (vte != null) {
			playerRange = Minecraft.getMinecraft().thePlayer.getDistanceSq(
					vte.xCoord, vte.yCoord, vte.zCoord);
		}

		GL11.glPushMatrix();

		// Offset our image so it aligns right with
		// the voxel grid
		GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
		bindTexture(texture);

		GL11.glPushMatrix();

		// Flip
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

		int rotation = 0;

		if (vte != null) {
			// Rotate based on facing
			rotation = rotationFacings[vte.getBlockMetadata() & MachineBase.META_SIDE_MASK];
			GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		} else {
			// Render the item in inventory
			GL11.glTranslated(0F, 0.5F, 0F);
			GL11.glRotatef(90, 0F, 1.0F, 0F);
		}

		// Render
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		// Render the contents of the trade inventory
		if (vte != null) {

			if (playerInRange(ITEM_RENDER_RANGE))
				renderTradeInventory(vte);

			if (playerInRange(VENDING_TITLE_RENDER_RANGE))
				renderName(vte.getOwnerName(), vte.getNameColor(),
						vte.getNameBackgroundColor());
		}

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	@Override
	public boolean handleRenderType(final ItemStack itemStack,
			final ItemRenderType itemRenderType) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(final ItemRenderType itemRenderType,
			final ItemStack itemStack,
			final ItemRendererHelper itemRendererHelper) {
		return true;
	}

	@Override
	public void renderItem(final ItemRenderType type, final ItemStack item,
			final Object... data) {
		renderTileEntityAt(null, 0D, 0D, 0D, 0f);
	}
}
