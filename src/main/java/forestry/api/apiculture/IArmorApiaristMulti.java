package forestry.api.apiculture;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IArmorApiaristMulti extends IArmorApiarist {

    /**
     * Called to determine how many pieces an armor "counts for". For instance, if this returns 4, the single piece will
     * act as full protection.
     *
     * @param entity Entity being attacked
     * @param armor  Armor item
     * @param cause  Optional cause of attack, such as a bee effect identifier
     * @return Amount of effective protected slots
     */
    default int getProtectionCount(EntityLivingBase entity, ItemStack armor, String cause) {
        return 1;
    }
}
