/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser Public License v3 which accompanies this distribution, and is available
 * at http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to: SirSengir (original work), CovertJaguar, Player, Binnie,
 * MysteriousAges
 ******************************************************************************/
package forestry.core.gui;

import static forestry.core.inventory.ItemLocation.TYPE.PLAYER_INVENTORY;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;
import forestry.api.core.ForestryAPI;
import forestry.core.inventory.ItemLocation;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static void openGui(EntityPlayer entityplayer, IGuiHandlerEntity guiHandler) {
        openGui(entityplayer, guiHandler, (short) 0);
    }

    public static void openGui(EntityPlayer entityplayer, IGuiHandlerEntity guiHandler, short data) {
        int guiData = encodeGuiData(guiHandler, data);
        entityplayer.openGui(ForestryAPI.instance, guiData, entityplayer.worldObj, guiHandler.getIdOfEntity(), 0, 0);
    }

    public static void openGui(EntityPlayer entityplayer, IGuiHandlerItem guiHandler) {
        openGui(entityplayer, guiHandler, (short) 0);
    }

    public static void openGui(EntityPlayer entityplayer, IGuiHandlerItem guiHandler, short data) {
        int guiData = encodeGuiData(guiHandler, data);
        entityplayer.openGui(ForestryAPI.instance, guiData, entityplayer.worldObj, 0, 0, 0);
    }

    /**
     * Opens gui for item placed in player's inventory.
     *
     * @param entityPlayer Player.
     * @param guiHandler   GUI handler for item.
     * @param data         Data for item.
     * @param invSlotIdx   Index or slot in player inventory should be in range [0, 40).
     */
    public static void openGui(EntityPlayer entityPlayer, IGuiHandlerItem guiHandler, short data, int invSlotIdx) {
        int guiData = encodeGuiData(guiHandler, data, invSlotIdx);
        entityPlayer.openGui(ForestryAPI.instance, guiData, entityPlayer.worldObj, 0, 0, 0);
    }

    public static void openGui(EntityPlayer entityplayer, IGuiHandlerTile guiHandler) {
        openGui(entityplayer, guiHandler, (short) 0);
    }

    public static void openGui(EntityPlayer entityplayer, IGuiHandlerTile guiHandler, short data) {
        int guiData = encodeGuiData(guiHandler, data);
        ChunkCoordinates coordinates = guiHandler.getCoordinates();
        entityplayer.openGui(
                ForestryAPI.instance,
                guiData,
                entityplayer.worldObj,
                coordinates.posX,
                coordinates.posY,
                coordinates.posZ);
    }

    private static int encodeGuiData(IGuiHandlerForestry guiHandler, short data) {
        return encodeGuiData(guiHandler, data, -1);
    }

    /**
     * Encodes data required for GUI handler to function properly.
     * Because of minecraft implementation we are limited to 32 bits.
     *
     * @param guiHandler GUI handler.
     * @param data       High bits which were encoded by GUI handler.
     * @param invSlotIdx Slot index in the player's inventory should be in range [0,40).
     * @return Encoded data.
     */
    private static int encodeGuiData(IGuiHandlerForestry guiHandler, short data, int invSlotIdx) {
        // inventory slot idx will be encoded in third byte
        // player have 40 different inventory which means that we need 6 bits to encode all values.
        // first 2 bits are reserved for type currently values 0(heldItem) and 1(item inventory) have meaning,
        // 2 and 3 are reserved for possible extension.
        if (invSlotIdx < -1 || invSlotIdx >= 40) {
            throw new IllegalArgumentException("Inventory slot index should be in range [-1, 40)");
        }

        int invData = invSlotIdx == -1 ? 0 : (0x40 | invSlotIdx);

        GuiId guiId = GuiIdRegistry.getGuiIdForGuiHandler(guiHandler);

        return data << 16 | invData << 8 | guiId.getId();
    }

    private static GuiId decodeGuiID(int guiData) {
        int guiId = guiData & 0xFF;
        return GuiIdRegistry.getGuiId(guiId);
    }

    private static short decodeGuiData(int guiId) {
        return (short) (guiId >> 16);
    }

    private static int decodeInvSlotIdx(int guiData) {
        boolean invEncoded = (0x00_00_40_00 & guiData) != 0;

        return invEncoded ? (guiData & 0x00_00_3F_00) >> 8 : -1;
    }

    @Override
    public Object getClientGuiElement(int guiData, EntityPlayer player, World world, int x, int y, int z) {
        return getGuiElement(false, guiData, player, world, x, y, z);
    }

    @Override
    public Object getServerGuiElement(int guiData, EntityPlayer player, World world, int x, int y, int z) {
        return getGuiElement(true, guiData, player, world, x, y, z);
    }

    private @Nullable Object getGuiElement(boolean isServerSide, int guiData, EntityPlayer player, World world, int x, int y, int z) {
        GuiId guiId = decodeGuiID(guiData);
        if (guiId == null) {
            return null;
        }
        short data = decodeGuiData(guiData);

        switch (guiId.getGuiType()) {
            case Item: {
                int slotIdx = decodeInvSlotIdx(guiData);
                ItemStack heldItem;
                ItemLocation loc;
                if (slotIdx == -1) {
                    heldItem = player.getCurrentEquippedItem();
                    loc = ItemLocation.HELD_BY_PLAYER;
                } else {
                    heldItem = player.inventory.getStackInSlot(slotIdx);
                    loc = new ItemLocation(PLAYER_INVENTORY, slotIdx);
                }

                if (heldItem != null) {
                    Item item = heldItem.getItem();
                    if (guiId.getGuiHandlerClass().isInstance(item)) {
                        if (isServerSide) {
                            return ((IGuiHandlerItem) item).getContainer(player, heldItem, data, loc);
                        }

                        return ((IGuiHandlerItem) item).getGui(player, heldItem, data, loc);
                    }
                }
                break;
            }
            case Tile: {
                TileEntity tileEntity = world.getTileEntity(x, y, z);
                if (guiId.getGuiHandlerClass().isInstance(tileEntity)) {
                    if (isServerSide) {
                        return ((IGuiHandlerTile) tileEntity).getContainer(player, data);
                    }
                    return ((IGuiHandlerTile) tileEntity).getGui(player, data);
                }
                break;
            }
            case Entity: {
                Entity entity = world.getEntityByID(x);
                if (guiId.getGuiHandlerClass().isInstance(entity)) {
                    if (isServerSide) {
                        return ((IGuiHandlerEntity) entity).getContainer(player, data);
                    }
                    return ((IGuiHandlerEntity) entity).getGui(player, data);
                }
                break;
            }
        }
        return null;
    }
}
