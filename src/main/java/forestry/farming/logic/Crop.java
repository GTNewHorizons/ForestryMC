/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser Public License v3 which accompanies this distribution, and is available
 * at http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to: SirSengir (original work), CovertJaguar, Player, Binnie,
 * MysteriousAges
 ******************************************************************************/
package forestry.farming.logic;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import forestry.api.farming.ICrop;
import forestry.core.config.Constants;
import forestry.core.utils.vect.IVect;
import forestry.core.utils.vect.Vect;

public abstract class Crop implements ICrop {

    protected final World world;
    protected final Vect position;

    protected Crop(World world, Vect position) {
        this.world = world;
        this.position = position;
    }

    protected final void setBlock(IVect position, Block block, int meta) {
        world.setBlock(position.getX(), position.getY(), position.getZ(), block, meta, Constants.FLAG_BLOCK_SYNCH);
    }

    protected final Block getBlock(IVect position) {
        return world.getBlock(position.getX(), position.getY(), position.getZ());
    }

    protected final int getBlockMeta(IVect position) {
        return world.getBlockMetadata(position.getX(), position.getY(), position.getZ());
    }

    protected abstract boolean isCrop(IVect pos);

    protected abstract Collection<ItemStack> harvestBlock(IVect pos);

    @Override
    public Collection<ItemStack> harvest() {
        if (!isCrop(position)) {
            return null;
        }

        return harvestBlock(position);
    }
}
