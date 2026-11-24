package forestry.mail;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

import forestry.core.utils.PosteaCompat;

/// A mail storage in the world's `MapStorage`, with saved item stacks migrated through Postea under the storage id
/// `forestry:<mapName>`.
///
/// `MapStorage` swallows load exceptions and keeps the half-read storage. A storage whose migration failed is never
/// saved. [PostRegistry] rethrows the failure instead of serving the storage. A delivery loads every storage it writes
/// to before changing any of them. A failed storage then stops the whole delivery.
public abstract class MailSavedData extends WorldSavedData {

    private RuntimeException loadFailure;
    private NBTTagCompound savedStamps;

    protected MailSavedData(String mapName) {
        super(mapName);
    }

    @Override
    public final void readFromNBT(NBTTagCompound root) {
        try {
            PosteaCompat.transform("forestry:" + mapName, root);
        } catch (RuntimeException e) {
            loadFailure = e;
            throw e;
        }
        savedStamps = PosteaCompat.savedStamps(root);
        readData(root);
    }

    @Override
    public final void writeToNBT(NBTTagCompound root) {
        writeData(root);
        PosteaCompat.stamp(root, savedStamps);
    }

    @Override
    public boolean isDirty() {
        return loadFailure == null && super.isDirty();
    }

    /// Throws the exception that failed this storage's migration, if one did.
    void throwIfLoadFailed() {
        if (loadFailure != null) throw loadFailure;
    }

    protected abstract void readData(NBTTagCompound root);

    protected abstract void writeData(NBTTagCompound root);
}
