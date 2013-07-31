package SDKUtility.common;

import SDKUtility.client.GuiGrinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class SDKUtilityGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		if(tileentity instanceof TileEntityGrinder)
		{
			return new ContainerGrinder(player.inventory, (TileEntityGrinder)tileentity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);

		if(tileentity instanceof TileEntityGrinder)
		{
			return new GuiGrinder(player.inventory, (TileEntityGrinder)tileentity);
		}
		return null;
	}

}
