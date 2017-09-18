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
package forestry.greenhouse.inventory;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import forestry.core.inventory.InventoryAdapterTile;
import forestry.greenhouse.tiles.TileHygroregulator;

public class InventoryHygroregulator extends InventoryAdapterTile<TileHygroregulator> {
	public static final short SLOT_INPUT = 0;

	public InventoryHygroregulator(TileHygroregulator alvearyHygroregulator) {
		super(alvearyHygroregulator, 1, "CanInv");
	}

	@Override
	public boolean canSlotAccept(int slotIndex, ItemStack itemStack) {
		if (slotIndex == SLOT_INPUT) {
			FluidStack fluid = FluidUtil.getFluidContained(itemStack);
			return fluid != null && tile.getTankManager().canFillFluidType(fluid);
		}
		return false;
	}
}