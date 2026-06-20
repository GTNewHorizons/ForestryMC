/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser Public License v3 which accompanies this distribution, and is available
 * at http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to: SirSengir (original work), CovertJaguar, Player, Binnie,
 * MysteriousAges
 ******************************************************************************/
package forestry.core.recipes.nei;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * @author bdew
 */
public class SetRecipeCommandHandler {

    private final Class<? extends Container>[] containerClasses;
    private final Class<? extends Slot> slotClass;

    public SetRecipeCommandHandler(Class<? extends Container>[] containerClasses, Class<? extends Slot> slotClass) {
        this.containerClasses = containerClasses;
        this.slotClass = slotClass;
    }

    public void handle(NBTTagCompound data, EntityPlayerMP player) {
        NBTTagList stacks = data.getTagList("stacks", 10);
        Container cont = player.openContainer;

        if (!isContainerClassCorrect(cont)) {
            return;
        }

        Map<Integer, ItemStack> stmap = new HashMap<>();
        for (int i = 0; i < stacks.tagCount(); i++) {
            NBTTagCompound itemdata = stacks.getCompoundTagAt(i);
            stmap.put(itemdata.getInteger("slot"), ItemStack.loadItemStackFromNBT(itemdata));
        }
        for (Slot slot : cont.inventorySlots) {
            if (!slotClass.isInstance(slot)) {
                continue;
            }
            slot.putStack(stmap.getOrDefault(slot.getSlotIndex(), null));
        }
    }

    private boolean isContainerClassCorrect(Container cont) {
        for (Class<? extends Container> containerClass : containerClasses) {
            if (containerClass.isInstance(cont)) {
                return true;
            }
        }
        return false;
    }
}
