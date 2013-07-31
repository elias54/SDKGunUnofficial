package SDKUtility.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerGrinder extends Container
{
    private TileEntityGrinder grinder;
    private int cookTime;
    private int burnTime;
    private int currentItemBurnTime;

    public ContainerGrinder(IInventory iinventory, TileEntityGrinder sdktileentitygrinder)
    {
        cookTime = 0;
        burnTime = 0;
        currentItemBurnTime = 0;
        grinder = sdktileentitygrinder;
        addSlotToContainer(new Slot(sdktileentitygrinder, 0, 56, 17));
        addSlotToContainer(new Slot(sdktileentitygrinder, 1, 56, 53));
        addSlotToContainer(new Slot(sdktileentitygrinder, 2, 116, 35));

        for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 9; k++)
            {
                addSlotToContainer(new Slot(iinventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
            addSlotToContainer(new Slot(iinventory, j, 8 + j * 18, 142));
        }
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);

            if (cookTime != grinder.cookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, grinder.cookTime);
            }

            if (burnTime != grinder.burnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, grinder.burnTime);
            }

            if (currentItemBurnTime != grinder.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, grinder.currentItemBurnTime);
            }
        }

        cookTime = grinder.cookTime;
        burnTime = grinder.burnTime;
        currentItemBurnTime = grinder.currentItemBurnTime;
    }

    @Override
    public void updateProgressBar(int i, int j)
    {
        if (i == 0)
        {
            grinder.cookTime = j;
        }

        if (i == 1)
        {
            grinder.burnTime = j;
        }

        if (i == 2)
        {
            grinder.currentItemBurnTime = j;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return grinder.isUseableByPlayer(entityplayer);
    }
}
