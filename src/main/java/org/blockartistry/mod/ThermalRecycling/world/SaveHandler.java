/*
 * This file is part of Restructured, licensed under the MIT License (MIT).
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

package org.blockartistry.mod.ThermalRecycling.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;

/**
 * A do-nothing save handler in order to instantiate a fake world.
 */
public final class SaveHandler implements ISaveHandler {
	@Override
	public WorldInfo loadWorldInfo() {
		return null;
	}

	@Override
	public void checkSessionLock() throws MinecraftException {
	}

	@Override
	public IChunkLoader getChunkLoader(WorldProvider provider) {
		return null;
	}

	@Override
	public void saveWorldInfoWithPlayer(WorldInfo info, NBTTagCompound compound) {
	}

	@Override
	public void saveWorldInfo(WorldInfo info) {
	}

	@Override
	public IPlayerFileData getSaveHandler() {
		return null;
	}

	@Override
	public void flush() {
	}

	@Override
	public File getWorldDirectory() {
		return null;
	}

	@Override
	public File getMapFileFromName(String name) {
		return null;
	}

	@Override
	public String getWorldDirectoryName() {
		return null;
	}
}
