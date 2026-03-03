/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.farming;

import net.minecraftforge.common.util.ForgeDirection;

public enum FarmDirection {

    NORTH(ForgeDirection.NORTH),
    EAST(ForgeDirection.EAST),
    SOUTH(ForgeDirection.SOUTH),
    WEST(ForgeDirection.WEST);

    private final ForgeDirection forgeDirection;

    /**
     * Cached values() array for frequent read-only operations, the array should NOT be mutated.
     */
    public static final FarmDirection[] VALUES = values();

    FarmDirection(ForgeDirection forgeDirection) {
        this.forgeDirection = forgeDirection;
    }

    public ForgeDirection getForgeDirection() {
        return forgeDirection;
    }

    public static FarmDirection getFarmDirection(ForgeDirection forgeDirection) {
        return switch (forgeDirection) {
            case NORTH -> NORTH;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> null;
        };
    }

    public static FarmDirection fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
}
