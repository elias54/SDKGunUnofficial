package SDKFps.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPlayerSpawner extends TileEntity{
	
    public void register()
    {
        SDK_FPS.registerPlayerSpawner(xCoord, yCoord, zCoord);
    }

    public void unregister()
    {
    	SDK_FPS.unregisterPlayerSpawner(xCoord, yCoord, zCoord);
    }
    
    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        register();
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
    }
    
}
