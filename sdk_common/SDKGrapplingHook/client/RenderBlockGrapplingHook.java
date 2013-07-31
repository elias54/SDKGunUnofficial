package SDKGrapplingHook.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import SDKGrapplingHook.common.SDK_GrapplingHook;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockGrapplingHook implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {}

	@Override
	public boolean renderWorldBlock(IBlockAccess iblockaccess, int i, int j, int k, Block block, int l, RenderBlocks renderblocks) 
	{
		if (l == SDK_GrapplingHook.blockGrapplingHook.getRenderType())
		{
			return renderGrapplingHook(renderblocks, block, i, j, k, iblockaccess);
		}
		return false;
	}

    public static boolean renderGrapplingHook(RenderBlocks renderblocks, Block block, int i, int j, int k, IBlockAccess iblockaccess)
    {
        Tessellator tessellator = Tessellator.instance;
        Icon icon = renderblocks.getBlockIconFromSide(block, 1);

        if (renderblocks.hasOverrideBlockTexture())
        {
            icon = renderblocks.overrideBlockTexture;
        }

        float f = 0.015625F;
        double d0 = (double)icon.getMinU();
        double d1 = (double)icon.getMinV();
        double d2 = (double)icon.getMaxU();
        double d3 = (double)icon.getMaxV();
        long l = (long)(i * 3129871) ^ (long)k * 116129781L ^ (long)j;
        l = l * l * 42317861L + l * 11L;
        int i1 = (int)(l >> 16 & 3L);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, i, j, k));
        float f1 = (float)i + 0.5F;
        float f2 = (float)k + 0.5F;
        float f3 = (float)(i1 & 1) * 0.5F * (float)(1 - i1 / 2 % 2 * 2);
        float f4 = (float)(i1 + 1 & 1) * 0.5F * (float)(1 - (i1 + 1) / 2 % 2 * 2);
        tessellator.setColorOpaque_I(block.getBlockColor());
        tessellator.addVertexWithUV((double)(f1 + f3 - f4), (double)((float)j + f), (double)(f2 + f3 + f4), d0, d1);
        tessellator.addVertexWithUV((double)(f1 + f3 + f4), (double)((float)j + f), (double)(f2 - f3 + f4), d2, d1);
        tessellator.addVertexWithUV((double)(f1 - f3 + f4), (double)((float)j + f), (double)(f2 - f3 - f4), d2, d3);
        tessellator.addVertexWithUV((double)(f1 - f3 - f4), (double)((float)j + f), (double)(f2 + f3 - f4), d0, d3);
        tessellator.setColorOpaque_I((block.getBlockColor() & 16711422) >> 1);
        tessellator.addVertexWithUV((double)(f1 - f3 - f4), (double)((float)j + f), (double)(f2 + f3 - f4), d0, d3);
        tessellator.addVertexWithUV((double)(f1 - f3 + f4), (double)((float)j + f), (double)(f2 - f3 - f4), d2, d3);
        tessellator.addVertexWithUV((double)(f1 + f3 + f4), (double)((float)j + f), (double)(f2 - f3 + f4), d2, d1);
        tessellator.addVertexWithUV((double)(f1 + f3 - f4), (double)((float)j + f), (double)(f2 + f3 + f4), d0, d1);
        return true;
    }
	
	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return SDK_GrapplingHook.grapplingRenderType;
	}

}
