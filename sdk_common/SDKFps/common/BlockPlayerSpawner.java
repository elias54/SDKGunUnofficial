package SDKFps.common;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPlayerSpawner extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	public Icon sideTexture;
	
    protected BlockPlayerSpawner(int i)
    {
        super(i, Material.iron);
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
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int i, Random random, int j)
    {
        return SDK_FPS.itemPlayerSpawner.itemID;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        TileEntityPlayerSpawner sdktileentityplayerspawner = (TileEntityPlayerSpawner)world.getBlockTileEntity(i, j, k);
        sdktileentityplayerspawner.register();
    }

    public void breakBlock(World par1World, int i, int j, int k, int l, int m)
    {
        TileEntityPlayerSpawner sdktileentityplayerspawner = (TileEntityPlayerSpawner)par1World.getBlockTileEntity(i, j, k);
        sdktileentityplayerspawner.unregister();
    	super.breakBlock(par1World, i, j, k, l, m);
    }

    public Icon getIcon(int side, int metadata)
    {
    	if(side == 1)
    	{
    		return blockIcon;
    	}else{
    		return sideTexture;
    	}
    }
    
	@Override
	public TileEntity createNewTileEntity(World world) 
	{
		return new TileEntityPlayerSpawner();
	}
	
	public void registerIcons(IconRegister icon)
	{
		sideTexture = icon.registerIcon("sdk_fps:blockSpawnerSide");
		blockIcon = icon.registerIcon("sdk_fps:blockPlayerSpawner");
	}
	
}
