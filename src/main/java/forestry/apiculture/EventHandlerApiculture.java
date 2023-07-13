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
                    tooltip.add(StringUtil.localize("frame.tooltip.territory") + getColorFormatted(territory));

                    float mutation = modifier.getMutationModifier(null, null, 1.0F);
                    tooltip.add(StringUtil.localize("frame.tooltip.mutationRate") + getColorFormatted(mutation));

                    float lifespan = modifier.getLifespanModifier(null, null, 1.0F);
                    tooltip.add(StringUtil.localize("frame.tooltip.lifespan") + getColorFormatted(lifespan));

                    float production = modifier.getProductionModifier(null, 1.0F);
                    tooltip.add(StringUtil.localize("frame.tooltip.production") + getColorFormatted(production, true));

                    float decay = modifier.getGeneticDecay(null, 1.0F);
                    tooltip.add(StringUtil.localize("frame.tooltip.geneticDecay") + getColorFormatted(decay));
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

    private static String getColorFormatted(float value, boolean additive) {
        EnumChatFormatting color;
        if (value <= 0.5F) {
            color = EnumChatFormatting.DARK_RED; // "bad" stat
        } else if (value < 1.0F) {
            color = EnumChatFormatting.RED; // "below average" stat
        } else if (value == 1.0F) {
            color = EnumChatFormatting.GOLD; // "average" stat
        } else if (value <= 2.0F) {
            color = EnumChatFormatting.GREEN; // "above average" stat
        } else {
            color = EnumChatFormatting.AQUA; // "great" stat
        }
        String formatted = color.toString();

        // Set symbol for if this value is additive or multiplicative
        if (additive) {
            formatted += " +";
        } else {
            formatted += " x";
        }

        // trim trailing zeroes if no decimal-precision is needed
        if (value == (long) value) {
            return formatted + (long) value;
        }
        return formatted + value;
    }

    private static String getColorFormatted(float value) {
        return getColorFormatted(value, false);
    }
}
