/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.fuels;

import net.minecraft.item.ItemStack;

public class RainSubstrate {

    public enum WeatherState {
        CLEAR,
        RAIN,
        STORM
    }

    /**
     * Rain substrate capable of activating the rainmaker.
     */
    public ItemStack item;
    /**
     * Duration of the weather event triggered by this substrate in Minecraft ticks.
     */
    public int duration;
    /**
     * Speed of activation sequence triggered.
     */
    public float speed;
    /**
     * The type of weather triggered.
     */
    public WeatherState weather;

    public RainSubstrate(ItemStack item, float speed, WeatherState weather) {
        this(item, 0, speed, weather);
    }

    public RainSubstrate(ItemStack item, int duration, float speed, WeatherState weather) {
        this.item = item;
        this.duration = duration;
        this.speed = speed;
        this.weather = weather;
    }
}
