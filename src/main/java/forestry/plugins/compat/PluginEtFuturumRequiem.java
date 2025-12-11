package forestry.plugins.compat;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.FlowerManager;
import forestry.core.utils.ModUtil;
import forestry.plugins.ForestryPlugin;
import forestry.plugins.Plugin;
import forestry.plugins.PluginManager;

@Plugin(
        pluginID = "EtFuturumRequiem",
        name = "EtFuturumRequiem",
        unlocalizedDescription = "for.plugin.etfuturum.description")
public class PluginEtFuturumRequiem extends ForestryPlugin {

    private static final String EFR = "etfuturum";

    @Override
    public boolean isAvailable() {
        return ModUtil.isModLoaded(EFR);
    }

    @Override
    public String getFailMessage() {
        return "EtFuturumRequiem not found";
    }

    @Override
    public void doInit() {
        super.doInit();
        if (PluginManager.Module.APICULTURE.isEnabled()) {
            addFlowers();
        }
    }

    private void addFlowers() {
        Block CornFlower = GameRegistry.findBlock(EFR, "cornflower");
        Block Lily = GameRegistry.findBlock(EFR, "lily_of_the_valley");
        Block Petals = GameRegistry.findBlock(EFR, "pink_petals");
        Block Rose = GameRegistry.findBlock(EFR, "rose");
        Block WitherRose = GameRegistry.findBlock(EFR,"wither_rose");

        FlowerManager.flowerRegistry.registerAcceptableFlower(CornFlower, FlowerManager.FlowerTypeVanilla);
        FlowerManager.flowerRegistry.registerAcceptableFlower(Lily, FlowerManager.FlowerTypeVanilla);
        FlowerManager.flowerRegistry.registerAcceptableFlower(Petals, FlowerManager.FlowerTypeVanilla);
        FlowerManager.flowerRegistry.registerAcceptableFlower(Rose, FlowerManager.FlowerTypeVanilla);
        FlowerManager.flowerRegistry.registerAcceptableFlower(WitherRose,FlowerManager.FlowerTypeNether);
    }
}
