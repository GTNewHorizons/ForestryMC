package forestry.core.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import forestry.core.inventory.MergedInventories;

public enum IngredientsStorage {

    INTERNAL_INVENTORY,
    PLAYER_INVENTORY,
    ALL_INVENTORIES;

    public static final IngredientsStorage[] VALUES = values();

    public static IngredientsStorage fromInt(int value) {
        value = value % VALUES.length;
        if (value < 0) {
            value += VALUES.length;
        }
        return VALUES[value];
    }

    public IngredientsStorage next() {
        return fromInt(ordinal() + 1);
    }

    public IngredientsStorage previous() {
        return fromInt(ordinal() - 1);
    }

    public IInventory getInventory(IInventory internal, EntityPlayer player) {
        switch (this) {
            case PLAYER_INVENTORY:
                return player.inventory;
            case ALL_INVENTORIES:
                return new MergedInventories(internal, player.inventory);
            default:
                return internal;
        }
    }
}
