package forestry.storage.inventory;

import forestry.core.inventory.ItemLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import forestry.api.storage.IBackpackDefinition;
import forestry.core.inventory.ItemInventory;
import forestry.storage.items.ItemBackpack;

public class ItemInventoryBackpack extends ItemInventory {

    private final IBackpackDefinition backpackDefinition;

    public ItemInventoryBackpack(EntityPlayer player, int size, ItemStack parent) {
        this(player, size, parent, ItemLocation.UNKNOWN);
    }

    public ItemInventoryBackpack(EntityPlayer player, int size, ItemStack parent, ItemLocation location) {
        super(player, size, parent, location);

        if (parent == null) {
            throw new IllegalArgumentException("Parent cannot be null.");
        }

        Item item = parent.getItem();
        if (!(item instanceof ItemBackpack)) {
            throw new IllegalArgumentException("Parent must be a backpack.");
        }

        this.backpackDefinition = ((ItemBackpack) item).getDefinition();

        if (this.backpackDefinition == null) {
            throw new IllegalArgumentException("Backpack must have a backpack definition.");
        }
    }

    @Override
    public boolean canSlotAccept(int slotIndex, ItemStack itemStack) {
        return backpackDefinition.isValidItem(itemStack);
    }
}
