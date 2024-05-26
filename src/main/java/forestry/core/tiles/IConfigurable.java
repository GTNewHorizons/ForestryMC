package forestry.core.tiles;

import java.io.IOException;

import forestry.core.network.DataInputStreamForestry;
import forestry.core.network.DataOutputStreamForestry;

public interface IConfigurable {

    void writeConfigurationData(DataOutputStreamForestry data) throws IOException;

    void readConfigurationData(DataInputStreamForestry data) throws IOException;
}
