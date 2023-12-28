/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser Public License v3 which accompanies this distribution, and is available
 * at http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to: SirSengir (original work), CovertJaguar, Player, Binnie,
 * MysteriousAges
 ******************************************************************************/
package forestry.core.config;

import forestry.Tags;

/**
 * With permission from pahimar.
 *
 * @author Pahimar
 */
public class Version {

    @Deprecated
    public enum EnumUpdateState {
        CURRENT,
        OUTDATED,
        CONNECTION_ERROR
    }

    public static final String VERSION = Tags.VERSION;
    @Deprecated
    public static final String BUILD_NUMBER = "";
    @Deprecated
    public static final String[] FAILED_CHANGELOG = new String[] {
            String.format("Unable to retrieve changelog for %s", Constants.MOD) };
    @Deprecated
    public static EnumUpdateState currentVersion = EnumUpdateState.CURRENT;

    public static String getVersion() {
        return VERSION;
    }

    @Deprecated
    public static boolean isOutdated() {
        return false;
    }

    @Deprecated
    public static boolean needsUpdateNoticeAndMarkAsSeen() {
        return false;
    }

    @Deprecated
    public static String getRecommendedVersion() {
        return VERSION;
    }

    @Deprecated
    public static void versionCheck() {}

    @Deprecated
    public static String[] getChangelog() {
        return FAILED_CHANGELOG;
    }

    @Deprecated
    public static String[] grabChangelog(String version) {
        return FAILED_CHANGELOG;
    }
}
