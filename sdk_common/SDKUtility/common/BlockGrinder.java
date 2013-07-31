package SDKUtility.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import SDKUtility.client.GuiGrinder;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGrinder extends BlockContainer {
	
	public static final Random furnaceRandom = new Random();
    private static boolean keepFurnaceInventory;
    protected BlockGrinder(int i)
    {
        super(i, Material.rock);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata)
    {
    	if(side == 1)
    	{
    		return blockIcon;
    	} else {
    		return Block.stone.getIcon(0, 0);
    	}
    }
    
	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return new TileEntityGrinder();
	}
    
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        TileEntityGrinder sdktileentitygrinder = (TileEntityGrinder)world.getBlockTileEntity(i, j, k);
        if (sdktileentitygrinder.isActive)
        {
            world.spawnParticle("smoke", (float)i + (random.nextFloat() * 10F + 3F) / 16F, (float)j + 1.0F, (float)k + (random.nextFloat() * 10F + 3F) / 16F, 0.0D, 0.0D, 0.0D);
        }
    }
	
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityGrinder sdktileentitygrinder = (TileEntityGrinder)world.getBlockTileEntity(i, j, k);
            FMLNetworkHandler.openGui(entityplayer, SDK_Utility.instance, 0, world, i, j, k);
            return true;
        }
    }
    
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepFurnaceInventory)
        {
            TileEntityGrinder tileentityfurnace = (TileEntityGrinder)par1World.getBlockTileEntity(par2, par3, par4);

            if (tileentityfurnace != null)
            {
                for (int j1 = 0; j1 < tileentityfurnace.getSizeInventory(); ++j1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(j1);

                    if (itemstack != null)
                    {
                        float f = this.furnaceRandom.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.furnaceRandom.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.furnaceRandom.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int k1 = this.furnaceRandom.nextInt(21) + 10;

                            if (k1 > itemstack.stackSize)
                            {
                                k1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)this.furnaceRandom.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)this.furnaceRandom.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)this.furnaceRandom.nextGaussian() * f3);
                            par1World.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                par1World.func_96440_m(par2, par3, par4, par5);
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister icon)
    {
    	blockIcon = icon.registerIcon("sdk_utility:blockGrinder");
    }
    
    
    
}
