/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.apiculture;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public interface IHiveFrame {

    /**
     * Wears out a frame.
     *
     * @param housing IBeeHousing the frame is contained in.
     * @param frame   ItemStack containing the actual frame.
     * @param queen   Current queen in the caller.
     * @param wear    Integer denoting the amount worn out. The wear modifier of the current beekeeping mode has already
     *                been taken into account.
     * @return ItemStack containing the actual frame with adjusted damage, or null if it has been broken.
     */
    ItemStack frameUsed(IBeeHousing housing, ItemStack frame, IBee queen, int wear);

    IBeeModifier getBeeModifier();

    /**
     * Provides an override for the "Hold Shift" tooltip info for a frame.<br>
     * Called by {@link forestry.apiculture.EventHandlerApiculture#addFrameTooltip(ItemTooltipEvent)}.
     *
     * @return The info to display for this frame, or null if default generated info.
     */
    @Nullable
    default List<String> getFrameTooltip() {
        return null;
    }
}
