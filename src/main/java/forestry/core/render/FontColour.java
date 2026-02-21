/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser Public License v3 which accompanies this distribution, and is available
 * at http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to: SirSengir (original work), CovertJaguar, Player, Binnie,
 * MysteriousAges
 ******************************************************************************/
package forestry.core.render;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class FontColour {

    private static final ResourceLocation colourDefinitions = new ForestryResource("config/colour.properties");
    private static final String defaultColourDefinitions = "/assets/forestry/config/colour.properties";

    private final Properties defaultMappings = new Properties();
    private final Properties mappings = new Properties();

    public FontColour(IResourceManager texturepack) {
        load(texturepack);
    }

    public synchronized int get(String key) {
        return Integer.parseInt(mappings.getProperty(key, defaultMappings.getProperty(key, "d67fff")), 16);
    }

    public void load(IResourceManager texturepack) {
        mappings.clear();
        defaultMappings.clear();

        try (InputStream defaultFontStream = FontColour.class.getResourceAsStream(defaultColourDefinitions)) {
            if (defaultFontStream != null) {
                defaultMappings.load(defaultFontStream);
                mappings.putAll(defaultMappings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (texturepack == null) {
            return;
        }

        try {
            IResource resource = texturepack.getResource(colourDefinitions);
            try (InputStream fontStream = resource.getInputStream()) {
                mappings.load(fontStream);
            }
        } catch (FileNotFoundException ignored) {
            // Resource pack does not provide an override.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
