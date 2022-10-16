/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.apiculture;

import forestry.api.genetics.IAlleleSpeciesCustom;
import net.minecraft.item.ItemStack;

public interface IAlleleBeeSpeciesCustom extends IAlleleBeeSpecies, IAlleleSpeciesCustom {

    /**
     * Add a product for this bee species.
     * Chance is between 0 and 1.
     */
    IAlleleBeeSpeciesCustom addProduct(ItemStack product, Float chance);

    /**
     * Add a specialty product for this bee species.
     * Bees only produce their specialty when they are Jubilant (see IJubilanceProvider)
     * Chance is between 0 and 1.
     */
    IAlleleBeeSpeciesCustom addSpecialty(ItemStack specialty, Float chance);

    /**
     * Set the Jubilance Provider for this bee species.
     * Bees only produce their specialty when they are Jubilant (see IJubilanceProvider)
     */
    IAlleleBeeSpeciesCustom setJubilanceProvider(IJubilanceProvider provider);

    /**
     * Get the Jubilance Provider for this bee species.
     */
    default IJubilanceProvider getJubilanceProvider() {
        return null;
    }

    /**
     * Make this species only active at night.
     */
    IAlleleBeeSpeciesCustom setNocturnal();

    /** Use this if you have custom icons for bees. */
    IAlleleBeeSpeciesCustom setCustomBeeIconProvider(IBeeIconProvider beeIconProvider);

    /** Use this if you have custom icon colours for bees (other than the default static primary + secondary colours). */
    IAlleleBeeSpeciesCustom setCustomBeeIconColourProvider(IBeeIconColourProvider beeIconColourProvider);
}
