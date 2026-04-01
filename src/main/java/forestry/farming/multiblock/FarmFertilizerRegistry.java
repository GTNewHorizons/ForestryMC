package forestry.farming.multiblock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import forestry.api.multiblock.IFarmFertilizerRegistry;
import forestry.core.utils.ItemStackUtil;

public class FarmFertilizerRegistry implements IFarmFertilizerRegistry {

    // the current list isn't big enough to require a hash map, and is faster as a list.
    private final List<ItemStack> fertilizers = new ArrayList<>(5);

    @Override
    public Collection<ItemStack> getFertilizers() {
        return fertilizers;
    }

    @Override
    public void addFertilizer(ItemStack itemstack) {
        // maybe throw warn? but could also be nice to leave as no warn for easier/cleaner optional mod dependencies
        if (itemstack == null || itemstack.getItem() == null || itemstack.stackSize <= 0) return;
        fertilizers.add(itemstack);
    }

    @Override
    public void removeFertilizer(ItemStack itemstack) {
        // maybe throw warn? but could also be nice to leave as no warn for easier/cleaner optional mod dependencies
        if (itemstack == null || itemstack.getItem() == null) return;
        fertilizers.removeIf(fert -> {
            // in stack first so it wild card can be used for a wild-card clear.
            return ItemStackUtil.isIdenticalItem(itemstack, fert);
        });
    }

    @Override
    public boolean isFertilizer(ItemStack itemstack) {
        if (itemstack == null) return false;
        for (ItemStack fert : this.fertilizers) {
            // fert first to allow for wild card checks
            if (ItemStackUtil.isIdenticalItem(fert, itemstack)) {
                return true;
            }
        }
        return false;
    }
}
