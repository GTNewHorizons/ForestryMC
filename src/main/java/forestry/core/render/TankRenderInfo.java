/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.core.render;

import forestry.core.fluids.Fluids;
import java.awt.Color;
import javax.annotation.Nonnull;
import net.minecraftforge.fluids.IFluidTank;

public class TankRenderInfo {
    public static final TankRenderInfo EMPTY = new TankRenderInfo(Fluids.WATER.getColor(), EnumTankLevel.EMPTY);

    @Nonnull
    private final Color fluidColor;

    @Nonnull
    private final EnumTankLevel level;

    public TankRenderInfo(@Nonnull IFluidTank fluidTank) {
        this(Fluids.getFluidColor(fluidTank.getFluid()), EnumTankLevel.rateTankLevel(fluidTank));
    }

    public TankRenderInfo(@Nonnull Color fluidColor, @Nonnull EnumTankLevel level) {
        this.fluidColor = fluidColor;
        this.level = level;
    }

    @Nonnull
    public Color getFluidColor() {
        return fluidColor;
    }

    @Nonnull
    public EnumTankLevel getLevel() {
        return level;
    }
}
