package SDKGrapplingHook.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGrapplingHook extends Item{

	public ItemGrapplingHook(int par1) {
		super(par1);
		maxStackSize = 1;
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
    {
        return true;
    }
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }
	
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        if (SDK_GrapplingHook.grapplingHooks.get(player) != null)
        {
            int i = ((EntityGrapplingHook)SDK_GrapplingHook.grapplingHooks.get(player)).catchFish();
            player.swingItem();
        }
        else
        {
            world.playSoundAtEntity(player, "sdk.grunt", 1.0F, 1.0F / (itemRand.nextFloat() * 0.1F + 0.95F));

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(new EntityGrapplingHook(world, player));
            }

            player.swingItem();
        }

        return itemstack;
    }
}
