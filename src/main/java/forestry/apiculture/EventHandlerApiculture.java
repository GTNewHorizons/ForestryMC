package forestry.apiculture;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import forestry.core.proxy.Proxies;
import forestry.core.utils.StringUtil;

public class EventHandlerApiculture {

    @SubscribeEvent
    public void addFrameTooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.itemStack;
        if (itemStack != null && itemStack.getItem() instanceof IHiveFrame) {
            IHiveFrame frame = (IHiveFrame) itemStack.getItem();
            IBeeModifier modifier = frame.getBeeModifier();
            List<String> tooltip = event.toolTip;

            if (!Proxies.common.isShiftDown()) {
                tooltip.add(EnumChatFormatting.ITALIC + "<" + StringUtil.localize("gui.tooltip.tmi") + ">");
            } else {
                if (frame.getFrameTooltip() != null) {
                    // use the override if it exists
                    tooltip.addAll(frame.getFrameTooltip());
                } else {
                    // otherwise use the auto-generated info
                    int durability = itemStack.getMaxDamage();
                    tooltip.add(StringUtil.localize("frame.tooltip.durability") + getDurabilityFormatted(durability));

                    float territory = modifier.getTerritoryModifier(null, 1.0F);
                    tooltip.add(
                            StringUtil.localize("frame.tooltip.territory")
                                    + getModifierFormatted(territory, false, false));
                    float mutation = modifier.getMutationModifier(null, null, 1.0F);
                    tooltip.add(
                            StringUtil.localize("frame.tooltip.mutationRate")
                                    + getModifierFormatted(mutation, false, false));
                    float lifespan = modifier.getLifespanModifier(null, null, 1.0F);
                    // lower lifespan is better
                    tooltip.add(
                            StringUtil.localize("frame.tooltip.lifespan")
                                    + getModifierFormatted(lifespan, false, true));
                    float production = modifier.getProductionModifier(null, 0.0F);
                    tooltip.add(
                            StringUtil.localize("frame.tooltip.production")
                                    + getModifierFormatted(production, true, false));
                    float flowering = modifier.getFloweringModifier(null, 1.0F);
                    tooltip.add(
                            StringUtil.localize("frame.tooltip.flowering")
                                    + getModifierFormatted(flowering, false, false));
                    float decay = modifier.getGeneticDecay(null, 1.0F);
                    // lower genetic decay is better
                    tooltip.add(
                            StringUtil.localize("frame.tooltip.geneticDecay")
                                    + getModifierFormatted(decay, false, true));
                }
            }
        }
    }

    private static String getDurabilityFormatted(int durability) {
        EnumChatFormatting color;
        if (durability < 100) {
            color = EnumChatFormatting.DARK_RED;
        } else if (durability < 200) {
            color = EnumChatFormatting.RED;
        } else if (durability < 250) {
            color = EnumChatFormatting.GOLD;
        } else if (durability < 750) {
            color = EnumChatFormatting.GREEN;
        } else {
            color = EnumChatFormatting.AQUA;
        }
        return color + " " + durability;
    }

    private static String getModifierFormatted(float value, boolean additive, boolean undesireable) {
        EnumChatFormatting color;
        float lo_threshold = 0.5F, mid_threshold = 1.0F, hi_threshold = 2.0F;
        float discriminant = value;
        if (additive) {
            lo_threshold = -1.0F;
            mid_threshold = 0.0F;
            hi_threshold = 2.0F; // this one is the same
            if (undesireable) {
                discriminant = -value;
            }
        } else if (undesireable) {
            discriminant = 1.0F / value;
        }
        if (discriminant <= lo_threshold) {
            color = EnumChatFormatting.DARK_RED; // "bad" stat
        } else if (discriminant < mid_threshold) {
            color = EnumChatFormatting.RED; // "below average" stat
        } else if (discriminant == mid_threshold) {
            color = EnumChatFormatting.GOLD; // "average" stat
        } else if (discriminant <= hi_threshold) {
            color = EnumChatFormatting.GREEN; // "above average" stat
        } else {
            color = EnumChatFormatting.AQUA; // "great" stat
        }
        String formatted = color.toString();

        // Set symbol for if this value is additive or multiplicative
        if (!additive) {
            formatted += " x";
        } else if (value >= 0.0F) {
            formatted += " +";
        } else {
            formatted += " "; // the "-" will be applied, but we still need a space or minecraft will crash
        }

        // trim trailing zeroes if no decimal-precision is needed
        if (value == (long) value) {
            return formatted + (long) value;
        }
        return formatted + value;
    }
}
