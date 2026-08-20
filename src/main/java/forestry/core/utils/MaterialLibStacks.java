package forestry.core.utils;

import net.minecraft.item.ItemStack;

import com.ruling_0.materiallib.api.StackResolver;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;

/// Resolves the `ml:<Material>:<shape>` stack string form, which names a MaterialLib item by material and shape.
///
/// MaterialLib types are named only here, so [Stack] can gate loading this class on `materiallib` being present.
public abstract class MaterialLibStacks {

    public static Stack parse(String mlString) {
        String[] parts = mlString.split(":+");
        if (parts.length != 3) {
            Log.warning(
                    "Stack string (" + mlString
                            + ") isn't formatted properly. The MaterialLib format is ml:<Material>:<shape>, e.g. ml:Aluminium:ore_stone");
            return null;
        }

        ItemStack stack = StackResolver.getStack(parts[1], parts[2], 1);
        if (stack == null) {
            return null;
        }

        UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        if (id == null) {
            Log.warning("Stack string (" + mlString + ") resolved to an item with no registry name.");
            return null;
        }

        return new Stack(id.toString(), stack.getItemDamage());
    }
}
