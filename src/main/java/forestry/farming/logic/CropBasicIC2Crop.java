package forestry.farming.logic;

import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import forestry.core.utils.vect.IVect;
import forestry.core.utils.vect.Vect;
import forestry.plugins.compat.PluginIC2;

public class CropBasicIC2Crop extends Crop {

    private final TileEntity tileEntity;

    public CropBasicIC2Crop(World world, TileEntity tileEntity, Vect position) {
        super(world, position);
        this.tileEntity = tileEntity;
    }

    @Override
    protected boolean isCrop(IVect pos) {
        return PluginIC2.instance.canHarvestCrop(this.tileEntity);
    }

    @Override
    protected Collection<ItemStack> harvestBlock(IVect pos) {
        return PluginIC2.instance.getCropDrops(this.tileEntity);
    }
}
