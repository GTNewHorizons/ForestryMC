package forestry.core.inventory;

/**
 * Location of items in inventory.
 */
public class ItemLocation {

    public enum Type {
        UNKNOWN,
        HELD_BY_PLAYER,
        PLAYER_INVENTORY
    }

    public static final ItemLocation UNKNOWN = new ItemLocation(Type.UNKNOWN, -1);
    public static final ItemLocation HELD_BY_PLAYER = new ItemLocation(Type.HELD_BY_PLAYER, -1);

    private final Type type;
    private final int slotIdx;

    public ItemLocation(Type type, int slotIdx) {
        this.type = type;
        this.slotIdx = slotIdx;
    }

    public Type type() {
        return type;
    }

    public int slotIdx() {
        return slotIdx;
    }
}
