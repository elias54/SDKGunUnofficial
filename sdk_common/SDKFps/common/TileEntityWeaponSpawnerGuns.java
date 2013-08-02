package SDKFps.common;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityWeaponSpawnerGuns extends TileEntity{

    private int spawnDelay;
    private byte weaponId;
    private static final int MAX_SPAWN_DELAY;

	public String sType[] = { "ak47", "deagle", "flamethrower", "laser", "m4", "minigun", "mp5", "rocketLauncher", "rocketLauncherLaser", "sg552", "shotgun", "sniper"};

    
    public TileEntityWeaponSpawnerGuns()
    {
        spawnDelay = -1;
        weaponId = 0;
    }

    public void setWeaponId(byte byte0)
    {
        weaponId = byte0;

        if (spawnDelay != -1)
        {
            spawnDelay = MAX_SPAWN_DELAY;
        }
    }
    
    public void updateEntity()
    {
        if (!worldObj.isRemote && SDK_FPS.enableWeaponSpawners)
        {
        	if (spawnDelay == -1)
            {
                boolean flag = false;
                List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1));
                int i = 0;

                do
                {
                    if (i >= list.size())
                    {
                        break;
                    }

                    EntityItem entityitem1 = (EntityItem)list.get(i);

                    if (entityitem1.getEntityItem().itemID == Item.appleGold.itemID)
                    {
                        flag = true;
                        break;
                    }

                    i++;
                }
                while (true);

                if (!flag)
                {
                    spawnDelay = MAX_SPAWN_DELAY;
                }
            }
            else if (spawnDelay == 0)
            {
                ItemStack itemstack = new ItemStack(Item.appleRed.itemID,Item.appleRed.maxStackSize, 0);
                EntityItem entityitem = new EntityItem(worldObj, (double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D, itemstack);
                entityitem.motionX = 0.0D;
                entityitem.motionY = 0.0D;
                entityitem.motionZ = 0.0D;
                worldObj.spawnEntityInWorld(entityitem);
                spawnDelay--;
            } else if (spawnDelay > 0)
            {
                spawnDelay--;
            }
        	
        }
        super.updateEntity();
    }
    

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        spawnDelay = nbttagcompound.getInteger("SpawnDelay");
        weaponId = nbttagcompound.getByte("WeaponId");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("SpawnDelay", spawnDelay);
        nbttagcompound.setByte("WeaponId", weaponId);
    }

    static
    {
        MAX_SPAWN_DELAY = 20 * SDK_FPS.weaponSpawnerRespawnDelay;
    }
	
}
