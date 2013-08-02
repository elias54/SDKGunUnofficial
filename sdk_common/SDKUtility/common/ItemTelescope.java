package SDKUtility.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import SDKUtility.client.TickClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTelescope extends Item{
	
	public ItemTelescope(int i) 
	{
		super(i);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			SDK_Utility.telescopeActivated = !SDK_Utility.telescopeActivated;
		}
		return stack;
	}
	
}
