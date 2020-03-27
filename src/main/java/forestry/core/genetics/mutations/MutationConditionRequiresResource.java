/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.core.genetics.mutations;

import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutationCondition;
import forestry.core.utils.BlockUtil;
import forestry.core.utils.InventoryUtil;
import forestry.core.utils.ItemStackUtil;
import forestry.core.utils.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

//import forestry.core.utils.ItemStackUtil;

public class MutationConditionRequiresResource implements IMutationCondition {

	private final ItemStack blockRequired;

	public MutationConditionRequiresResource(Block block, int meta) {
		blockRequired = new ItemStack(block, 1, meta);
	}

	@Override
	public float getChance(World world, int x, int y, int z, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
/*
		Block block;
		TileEntity tile;
		int meta;
		int i = 1;
		do {
			block = world.getBlock(x, y - i, z);
			meta = world.getBlockMetadata(x, y - i, z);
			tile = world.getTileEntity(x, y - i, z);
			i++;
		} while (tile instanceof IBeeHousing);

		return ItemStackUtil.equals(block, meta, blockRequired) ? 1 : 0;
*/
		ItemStack stk = BlockUtil.getItemStackFromBlockBelow(world, x, y, z,
				(TileEntity tile) -> (tile instanceof IBeeHousing)
		);

		return InventoryUtil.isItemEqual(blockRequired, stk, true, true) ? 1 : 0;
	}

	@Override
	public String getDescription() {
		return StringUtil.localizeAndFormat("mutation.condition.resource", blockRequired.getDisplayName());
	}
}
