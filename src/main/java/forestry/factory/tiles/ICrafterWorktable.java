package forestry.factory.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ICrafterWorktable {

    ItemStack getResult();

    boolean canTakeStack(EntityPlayer player, int slotIndex);

    boolean onCraftingStart(EntityPlayer player);

    void onCraftingComplete(EntityPlayer player);
}
