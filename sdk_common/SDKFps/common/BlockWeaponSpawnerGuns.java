package SDKFps.common;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWeaponSpawnerGuns extends BlockContainer{

	public String sType[] = { "ak47", "deagle", "flamethrower", "laser", "m4", "minigun", "mp5", "rocketLauncher", "rocketLauncherLaser", "sg552", "shotgun", "sniper"};
	
	public Icon sideIcon;
	private Icon[] IconArray;
	
    public BlockWeaponSpawnerGuns(int i)
    {
        super(i, Material.rock);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int side, int metadata)
    {
        if (side != 1)
        {
        	return sideIcon;
        }
        return metadata < sType.length && metadata >= 0 ? IconArray[metadata] : IconArray[0];
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return new TileEntityWeaponSpawnerGuns();
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTabs, List list)
	{
		for(int metadata = 0; metadata < sType.length; metadata++)
		{
			list.add(new ItemStack(id, 1, metadata));
		}
	}
	public int damageDropped(int metadata)
	{
		return metadata;
	}
	public void registerIcons(IconRegister icon)
	{
		IconArray = new Icon[sType.length];
		for(int i = 0; i < sType.length; i++)
		{
			IconArray[i] = icon.registerIcon("sdk_fps:" + sType[i]);
		}
		sideIcon = icon.registerIcon("sdk_fps:blockSpawnerSide");
	}
	
}
