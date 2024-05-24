package forestry.core.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import forestry.core.network.DataInputStreamForestry;
import forestry.core.network.DataOutputStreamForestry;
import forestry.core.network.IForestryPacketClient;
import forestry.core.network.PacketIdClient;
import forestry.core.proxy.Proxies;
import forestry.core.tiles.IConfigurable;

/**
 * Used to sync the tile configuration from Server to Client.
 */
public class PacketTileConfiguration extends PacketCoordinates implements IForestryPacketClient {

    private TileEntity tileEntity = null;

    public PacketTileConfiguration() {}

    public PacketTileConfiguration(TileEntity tileEntity) {
        super(tileEntity);
        this.tileEntity = tileEntity;
    }

    @Override
    protected void writeData(DataOutputStreamForestry data) throws IOException {
        super.writeData(data);
        if (tileEntity instanceof IConfigurable) {
            ((IConfigurable) tileEntity).writeConfigurationData(data);
        }
    }

    @Override
    public void onPacketData(DataInputStreamForestry data, EntityPlayer player) throws IOException {
        TileEntity tile = getTarget(Proxies.common.getRenderWorld());
        if (tile instanceof IConfigurable) {
            ((IConfigurable) tile).readConfigurationData(data);
        }
    }

    @Override
    public PacketIdClient getPacketId() {
        return PacketIdClient.TILE_CONFIGURATION;
    }
}
