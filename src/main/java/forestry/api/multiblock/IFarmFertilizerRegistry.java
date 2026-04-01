package forestry.api.multiblock;

import java.util.Collection;

import net.minecraft.item.ItemStack;

public interface IFarmFertilizerRegistry {

    /**
     * Gets a list of all fertilizer stacks in the registry.
     *
     * @return The current fertilizer registry.
     */
    Collection<ItemStack> getFertilizers();

    /**
     * Adds a new fertilizer to the registry.
     *
     * @apiNote The stack size doesn't matter it always consumes 1.
     * @apiNote You can use OreDictionary.WILDCARD_VALUE to specific that any meta of the item will be ok.
     *
     * @param itemstack The item to add to the registry.
     */
    void addFertilizer(ItemStack itemstack);

    /**
     * Removes a fertilizer from the fretilizer registry.
     *
     * @param itemstack The item to remove from the registry.
     */
    void removeFertilizer(ItemStack itemstack);

    /**
     * Checks if an item is a usable multi-farm fertilizer.
     *
     * @param itemstack - The stack to check with.
     * @return True if the item is a fertilizer item.
     */
    boolean isFertilizer(ItemStack itemstack);
}
