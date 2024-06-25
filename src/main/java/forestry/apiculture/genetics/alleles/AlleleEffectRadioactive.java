/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser Public License v3 which accompanies this distribution, and is available
 * at http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to: SirSengir (original work), CovertJaguar, Player, Binnie,
 * MysteriousAges
 ******************************************************************************/
package forestry.apiculture.genetics.alleles;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;
import forestry.apiculture.multiblock.AlvearyController;
import forestry.core.utils.DamageSourceForestry;
import forestry.core.utils.vect.Vect;

public class AlleleEffectRadioactive extends AlleleEffectThrottled {

    private static final DamageSource damageSourceBeeRadioactive = new DamageSourceForestry("bee.radioactive");

    public AlleleEffectRadioactive() {
        super("radioactive", true, 40, false, true);
    }

    @Override
    public IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
        harmEntities(genome, housing);
        if (isMobGriefingEnabled(housing)) destroyEnvironment(genome, housing);

        return storedData;
    }

    private boolean isMobGriefingEnabled(IBeeHousing housing) {
        return housing.getWorld().getGameRules().getGameRuleBooleanValue("mobGriefing");
    }

    private void harmEntities(IBeeGenome genome, IBeeHousing housing) {
        List<EntityLivingBase> entities = getEntitiesInRange(genome, housing, EntityLivingBase.class);
        for (EntityLivingBase entity : entities) {
            int damage = 8;

            // Entities are not attacked if they wear a full set of apiarist's armor.
            int count = BeeManager.armorApiaristHelper.wearsItems(entity, getUID(), true);
            damage -= (count * 2);
            if (damage <= 0) {
                continue;
            }

            entity.attackEntityFrom(damageSourceBeeRadioactive, damage);
        }
    }

    private static void destroyEnvironment(IBeeGenome genome, IBeeHousing housing) {
        World world = housing.getWorld();
        Random rand = world.rand;

        int[] areaAr = genome.getTerritory();
        Vect area = new Vect(areaAr).multiply(2);
        Vect offset = area.multiply(-1 / 2.0f);
        Vect posHousing = new Vect(housing.getCoordinates());

        boolean isAlveary = housing instanceof AlvearyController;

        for (int i = 0; i < 20; i++) {
            Vect randomPos = Vect.getRandomPositionInArea(rand, area);
            Vect posBlock = randomPos.add(posHousing).add(offset);

            // Don't destroy blocks in the protected 3x3x4 area if housing is an Alveary.
            // Stops the bee destroying itself. Silly behaviour.
            if (isInAlvearyProtectedArea(posHousing, posBlock)) {
                continue;
            }

            // Don't destroy the block directly below the bee.
            if (isAlveary) {
                if (posBlock.y == posHousing.y - 3) {
                    continue;
                }
            } else {
                if (posBlock.y == posHousing.y - 1) {
                    continue;
                }
            }

            Block block = world.getBlock(posBlock.x, posBlock.y, posBlock.z);

            // Never delete a tile entity. Allows far too much griefing and abuse otherwise.
            TileEntity tile = world.getTileEntity(posBlock.x, posBlock.y, posBlock.z);
            if (tile != null) {
                continue;
            }

            // Don't let us delete blocks that are not supposed to be breakable by players.
            if (block.getBlockHardness(world, posBlock.x, posBlock.y, posBlock.z) < 0) {
                continue;
            }

            // Some mods might use this logic? Idk, just a safety check. Might stop griefing.
            if (!world.canMineBlock(
                    housing.getWorld().func_152378_a(housing.getOwner().getId()),
                    posHousing.x,
                    posHousing.y,
                    posHousing.z)) {
                continue;
            }

            world.setBlockToAir(posBlock.x, posBlock.y, posBlock.z);
            break;
        }
    }

    private static boolean isInAlvearyProtectedArea(Vect posHousing, Vect posBlock) {
        // Alveary protection area is a 3x3x4 area centered on the top middle, down one block.
        int startX = posHousing.x - 1;
        int endX = posHousing.x + 1;
        int startZ = posHousing.z - 1;
        int endZ = posHousing.z + 1;
        int startY = posHousing.y - 2;
        int endY = posHousing.y + 1;

        return posBlock.x >= startX && posBlock.x <= endX
                && posBlock.z >= startZ
                && posBlock.z <= endZ
                && posBlock.y >= startY
                && posBlock.y <= endY;
    }
}
