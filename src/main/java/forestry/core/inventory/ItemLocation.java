package forestry.core.inventory;

/**
 * Location of items in inventory.
 */
public class ItemLocation {
    public enum TYPE {
        UNKNOWN,
        HELD_BY_PLAYER,
        PLAYER_INVENTORY
    }

    public static final ItemLocation UNKNOWN = new ItemLocation(TYPE.UNKNOWN, -1);
    public static final ItemLocation HELD_BY_PLAYER = new ItemLocation(TYPE.HELD_BY_PLAYER, -1);

    private final TYPE type;
    private final int slotIdx;


    public ItemLocation(TYPE type, int slotIdx) {
        this.type = type;
        this.slotIdx = slotIdx;
    }

    public TYPE type() {
        return type;
    }

    public int slotIdx() {
        return slotIdx;
    }
}
