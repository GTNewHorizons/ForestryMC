package forestry.core.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MergedInventories implements IInventory {

    private final IInventory first;
    private final IInventory second;

    public MergedInventories(IInventory first, IInventory second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int getSizeInventory() {
        return first.getSizeInventory() + second.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slotIn) {
        if (slotIn < first.getSizeInventory()) {
            return first.getStackInSlot(slotIn);
        }
        return second.getStackInSlot(slotIn - first.getSizeInventory());
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index < first.getSizeInventory()) {
            return first.decrStackSize(index, count);
        }
        return second.decrStackSize(index - first.getSizeInventory(), count);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        if (index < first.getSizeInventory()) {
            return first.getStackInSlotOnClosing(index);
        }
        return second.getStackInSlotOnClosing(index - first.getSizeInventory());
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < first.getSizeInventory()) {
            first.setInventorySlotContents(index, stack);
        } else {
            second.setInventorySlotContents(index - first.getSizeInventory(), stack);
        }
    }

    @Override
    public String getInventoryName() {
        return first.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return first.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return Math.min(first.getInventoryStackLimit(), second.getInventoryStackLimit());
    }

    @Override
    public void markDirty() {
        first.markDirty();
        second.markDirty();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return first.isUseableByPlayer(player) && second.isUseableByPlayer(player);
    }

    @Override
    public void openInventory() {
        first.openInventory();
        second.openInventory();
    }

    @Override
    public void closeInventory() {
        first.closeInventory();
        second.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index < first.getSizeInventory()) {
            return first.isItemValidForSlot(index, stack);
        }
        return second.isItemValidForSlot(index - first.getSizeInventory(), stack);
    }
}
