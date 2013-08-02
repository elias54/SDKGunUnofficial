package SDKUtility.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGrinder extends TileEntity implements ISidedInventory
{
    private static final int[] slots_top = new int[] {0};
    private static final int[] slots_bottom = new int[] {2, 1};
    private static final int[] slots_sides = new int[] {1};
    private ItemStack itemStacks[] = new ItemStack[3];
    public int burnTime;
    public int currentItemBurnTime;
    public int cookTime;
    public boolean isActive;
    private Random random;

    public TileEntityGrinder()
    {
        isActive = false;
        random = new Random();
        burnTime = 0;
        currentItemBurnTime = 0;
        cookTime = 0;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return itemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int i)
    {
        return itemStacks[i];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.itemStacks[par1] != null)
        {
            ItemStack itemstack;

            if (this.itemStacks[par1].stackSize <= par2)
            {
                itemstack = this.itemStacks[par1];
                this.itemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.itemStacks[par1].splitStack(par2);

                if (this.itemStacks[par1].stackSize == 0)
                {
                    this.itemStacks[par1] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }
    
    @Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
        if (itemStacks[i] != null)
        {
            ItemStack itemstack = itemStacks[i];
            itemStacks[i] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.itemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "Grinder";
    }

	public boolean isInvNameLocalized() 
	{
		return false;
	}
    
    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        this.itemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.itemStacks.length)
            {
                this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.burnTime = nbttagcompound.getShort("BurnTime");
        this.cookTime = nbttagcompound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.itemStacks[1]);
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("BurnTime", (short)this.burnTime);
        nbttagcompound.setShort("CookTime", (short)this.cookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.itemStacks.length; ++i)
        {
            if (this.itemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.itemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int i)
    {
        return (cookTime * i) / 200;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int i)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }
            return burnTime * i / currentItemBurnTime;
    }

    
    
    public boolean isBurning()
    {
        return burnTime > 0;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        boolean flag = this.burnTime > 0;
        boolean flag1 = false;

        if (this.burnTime > 0)
        {
            --this.burnTime;
        }

        if (!worldObj.isRemote)
        {
            if (burnTime == 0 && canSmelt())
            {
                currentItemBurnTime = burnTime = getItemBurnTime(itemStacks[1]);

                if (burnTime > 0)
                {
                    flag1 = true;

                    if (this.itemStacks[1] != null)
                    {
                        --this.itemStacks[1].stackSize;

                        if (this.itemStacks[1].stackSize == 0)
                        {
                            this.itemStacks[1] = this.itemStacks[1].getItem().getContainerItemStack(itemStacks[1]);
                        }
                    }
                }
            }

            if (isBurning() && canSmelt())
            {
                cookTime++;

                if (cookTime == 200)
                {
                    cookTime = 0;
                    smeltItem();
                    flag1 = true;
                }
            }
            else
            {
                cookTime = 0;
            }

            if (flag != this.burnTime > 0)
            {
            	isActive = true;
                flag1 = true;
            }
        }

        if (flag1)
        {
            this.onInventoryChanged();
        }
    }

    private boolean canSmelt()
    {
        if (itemStacks[0] == null)
        {
            return false;
        }

        ItemStack itemstack = null;

        if (itemStacks[0].itemID == Block.gravel.blockID || itemStacks[0].itemID == Item.flint.itemID)
        {
            itemstack = new ItemStack(Item.gunpowder);
        }

        if (itemstack == null)
        {
            return false;
        }

        if (itemStacks[2] == null)
        {
            return true;
        }

        if (!itemStacks[2].isItemEqual(itemstack))
        {
            return false;
        }

        if (itemStacks[2].stackSize < getInventoryStackLimit() && itemStacks[2].stackSize < itemStacks[2].getMaxStackSize())
        {
            return true;
        }
        else
        {
            return itemStacks[2].stackSize < itemstack.getMaxStackSize();
        }
    }

    public void smeltItem()
    {
        if (!canSmelt())
        {
            return;
        }

        ItemStack itemstack = null;

        if (itemStacks[0].itemID == Item.flint.itemID)
        {
            itemstack = new ItemStack(Item.gunpowder);
        }
        else if (itemStacks[0].itemID == Block.gravel.blockID && random.nextInt(4) == 0)
        {
            itemstack = new ItemStack(Item.gunpowder);
        }
        else
        {
            itemStacks[0].stackSize--;

            if (itemStacks[0].stackSize <= 0)
            {
                itemStacks[0] = null;
            }

            return;
        }

        if (itemStacks[2] == null)
        {
            itemStacks[2] = itemstack.copy();
        }
        else if (itemStacks[2].itemID == itemstack.itemID)
        {
            itemStacks[2].stackSize += itemstack.stackSize;
        }

        if (itemStacks[0].getItem().hasContainerItem())
        {
            itemStacks[0] = new ItemStack(itemStacks[0].getItem().getContainerItem());
        }
        else
        {
            itemStacks[0].stackSize--;
        }

        if (itemStacks[0].stackSize <= 0)
        {
            itemStacks[0] = null;
        }
    }

    public static int getItemBurnTime(ItemStack itemstack)
    {
        if (itemstack != null)
        {
            if (itemstack.getItem().itemID == Item.diamond.itemID)
            {
                return 12800;
            }

            if (itemstack.getItem().itemID == Item.ingotIron.itemID)
            {
                return 1600;
            }
        }

        return 0;
    }

    public static boolean isItemFuel(ItemStack par0ItemStack)
    {
        return getItemBurnTime(par0ItemStack) > 0;
    }
    
    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}
	

	
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 == 2 ? false : (par1 == 1 ? isItemFuel(par2ItemStack) : true);
    }

	@Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return false;
    }
}
