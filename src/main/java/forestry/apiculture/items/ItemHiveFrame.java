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
package forestry.apiculture.items;

import forestry.api.apiculture.DefaultBeeModifier;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.core.Tabs;
import forestry.core.items.ItemForestry;
import forestry.core.proxy.Proxies;
import forestry.core.utils.StringUtil;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemHiveFrame extends ItemForestry implements IHiveFrame {

    private final IBeeModifier beeModifier;

    public ItemHiveFrame(int maxDamage, float geneticDecay) {
        setMaxStackSize(1);
        setMaxDamage(maxDamage);
        setCreativeTab(Tabs.tabApiculture);

        this.beeModifier = new HiveFrameBeeModifier(geneticDecay);
    }

    @Override
    public ItemStack frameUsed(IBeeHousing housing, ItemStack frame, IBee queen, int wear) {
        frame.setItemDamage(frame.getItemDamage() + wear);
        if (frame.getItemDamage() >= frame.getMaxDamage()) {
            return null;
        } else {
            return frame;
        }
    }

    @Override
    public IBeeModifier getBeeModifier() {
        return beeModifier;
    }

    private static class HiveFrameBeeModifier extends DefaultBeeModifier {
        private final float geneticDecay;

        public HiveFrameBeeModifier(float geneticDecay) {
            this.geneticDecay = geneticDecay;
        }

        @Override
        public float getProductionModifier(IBeeGenome genome, float currentModifier) {
            return (currentModifier < 10f) ? 2f : 1f;
        }

        @Override
        public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
            return this.geneticDecay;
        }
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean flag) {
        addFrameInfomation(list, getBeeModifier());
    }

    public void addFrameInfomation(List list, IBeeModifier iBeeModifier) {
        if (Proxies.common.isShiftDown()) {
            list.add(StringUtil.localize("frame.tooltip.durability") + ": " + EnumChatFormatting.RED
                    + this.getMaxDamage());
            list.add(StringUtil.localize("frame.tooltip.territory") + ": " + EnumChatFormatting.AQUA + "x"
                    + iBeeModifier.getTerritoryModifier(null, 1f));
            list.add(StringUtil.localize("frame.tooltip.mutation_rate") + ": " + EnumChatFormatting.GOLD + "x"
                    + iBeeModifier.getMutationModifier(null, null, 1f));
            list.add(StringUtil.localize("frame.tooltip.lifespan") + ": " + EnumChatFormatting.YELLOW + "x"
                    + iBeeModifier.getLifespanModifier(null, null, 1f));
            list.add(StringUtil.localize("frame.tooltip.production") + ": " + EnumChatFormatting.DARK_GREEN + "x"
                    + iBeeModifier.getProductionModifier(null, 1f));
            list.add(StringUtil.localize("frame.tooltip.flowering") + ": " + EnumChatFormatting.LIGHT_PURPLE + "x"
                    + iBeeModifier.getFloweringModifier(null, 1f));
            list.add(StringUtil.localize("frame.tooltip.genetic_decay") + ": " + EnumChatFormatting.BLUE + "x"
                    + iBeeModifier.getGeneticDecay(null, 1f));
        } else {
            list.add(EnumChatFormatting.ITALIC + "<" + StringUtil.localize("gui.tooltip.tmi") + ">");
        }
    }
}
