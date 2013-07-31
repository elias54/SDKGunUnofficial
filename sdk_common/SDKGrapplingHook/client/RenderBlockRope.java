package SDKGrapplingHook.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import SDKGrapplingHook.common.SDK_GrapplingHook;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockRope implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		 try
	        {
	            if (modelId == SDK_GrapplingHook.blockRope.getRenderType())
	            {
	                return renderRope(renderer, block, x, y, z, world);
	            }
	        } catch (Exception exception){
	        	
	        }
		return false;
	}

	
	
    public static boolean renderRope(RenderBlocks renderblocks, Block block, int i, int j, int k, IBlockAccess iblockaccess)
    {
        Tessellator tessellator = Tessellator.instance;
        Icon icon = renderblocks.getBlockIconFromSide(block, 0);

        if (renderblocks.hasOverrideBlockTexture())
        {
            icon = renderblocks.overrideBlockTexture;
        }

        tessellator.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, i, j, k));
        float f = 1.0F;
        tessellator.setColorOpaque_F(f, f, f);
        double d0 = (double)icon.getMinU();
        double d1 = (double)icon.getMinV();
        double d2 = (double)icon.getMaxU();
        double d3 = (double)icon.getMaxV();
        int l = renderblocks.blockAccess.getBlockMetadata(i, j, k);
        double d4 = 0.0D;
        double d5 = 0.05000000074505806D;


        if (l == 5)
        {
            tessellator.addVertexWithUV((double)i + d5, (double)(j + 1) + d4, (double)(k + 1) + d4, d0, d1);
            tessellator.addVertexWithUV((double)i + d5, (double)(j + 0) - d4, (double)(k + 1) + d4, d0, d3);
            tessellator.addVertexWithUV((double)i + d5, (double)(j + 0) - d4, (double)(k + 0) - d4, d2, d3);
            tessellator.addVertexWithUV((double)i + d5, (double)(j + 1) + d4, (double)(k + 0) - d4, d2, d1);
        }

        if (l == 4)
        {
            tessellator.addVertexWithUV((double)(i + 1) - d5, (double)(j + 0) - d4, (double)(k + 1) + d4, d2, d3);
            tessellator.addVertexWithUV((double)(i + 1) - d5, (double)(j + 1) + d4, (double)(k + 1) + d4, d2, d1);
            tessellator.addVertexWithUV((double)(i + 1) - d5, (double)(j + 1) + d4, (double)(k + 0) - d4, d0, d1);
            tessellator.addVertexWithUV((double)(i + 1) - d5, (double)(j + 0) - d4, (double)(k + 0) - d4, d0, d3);
        }

        if (l == 3)
        {
            tessellator.addVertexWithUV((double)(i + 1) + d4, (double)(j + 0) - d4, (double)k + d5, d2, d3);
            tessellator.addVertexWithUV((double)(i + 1) + d4, (double)(j + 1) + d4, (double)k + d5, d2, d1);
            tessellator.addVertexWithUV((double)(i + 0) - d4, (double)(j + 1) + d4, (double)k + d5, d0, d1);
            tessellator.addVertexWithUV((double)(i + 0) - d4, (double)(j + 0) - d4, (double)k + d5, d0, d3);
        }

        if (l == 2)
        {
            tessellator.addVertexWithUV((double)(i + 1) + d4, (double)(j + 1) + d4, (double)(k + 1) - d5, d0, d1);
            tessellator.addVertexWithUV((double)(i + 1) + d4, (double)(j + 0) - d4, (double)(k + 1) - d5, d0, d3);
            tessellator.addVertexWithUV((double)(i + 0) - d4, (double)(j + 0) - d4, (double)(k + 1) - d5, d2, d3);
            tessellator.addVertexWithUV((double)(i + 0) - d4, (double)(j + 1) + d4, (double)(k + 1) - d5, d2, d1);
        }

        return true;
    }

	@Override
	public boolean shouldRender3DInInventory() 
	{
		return false;
	}

	@Override
	public int getRenderId() 
	{
		return SDK_GrapplingHook.ropeRenderType;
	}

}
