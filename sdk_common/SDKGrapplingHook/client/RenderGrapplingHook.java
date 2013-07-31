package SDKGrapplingHook.client;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import SDKGrapplingHook.common.EntityGrapplingHook;
import SDKGrapplingHook.common.SDK_GrapplingHook;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGrapplingHook extends Render
{
    private static final ResourceLocation field_110792_a = new ResourceLocation("SDKGuns:itemGrapplingHookThrown.png");
    
    private float field_77002_a;

    public RenderGrapplingHook(float par1)
    {
        this.field_77002_a = par1;
    }

    /**
     * Actually renders the fishing line and hook
     */
    public void doRenderFishHook(EntityGrapplingHook sdkentitygrapplinghook, double d, double d1, double d2, float f, float f1)
    {
        GL11.glPushMatrix();
        this.func_110777_b(sdkentitygrapplinghook);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f2 = this.field_77002_a;
        GL11.glScalef(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
        Icon icon = SDK_GrapplingHook.grapplingIconThrowable.getIconFromDamage(0);
        Tessellator tessellator = Tessellator.instance;
        float f3 = icon.getMinU();
        float f4 = icon.getMaxU();
        float f5 = icon.getMinV();
        float f6 = icon.getMaxV();
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV((double)(0.0F - f8), (double)(0.0F - f9), 0.0D, (double)f3, (double)f6);
        tessellator.addVertexWithUV((double)(f7 - f8), (double)(0.0F - f9), 0.0D, (double)f4, (double)f6);
        tessellator.addVertexWithUV((double)(f7 - f8), (double)(1.0F - f9), 0.0D, (double)f4, (double)f5);
        tessellator.addVertexWithUV((double)(0.0F - f8), (double)(1.0F - f9), 0.0D, (double)f3, (double)f5);
        tessellator.draw();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();

        if (sdkentitygrapplinghook.owner != null)
        {
            float f91 = ((sdkentitygrapplinghook.owner.prevRotationYaw + (sdkentitygrapplinghook.owner.rotationYaw - sdkentitygrapplinghook.owner.prevRotationYaw) * f1) * (float)Math.PI) / 180F;
            float f10 = ((sdkentitygrapplinghook.owner.prevRotationPitch + (sdkentitygrapplinghook.owner.rotationPitch - sdkentitygrapplinghook.owner.prevRotationPitch) * f1) * (float)Math.PI) / 180F;
            double d3 = MathHelper.sin(f91);
            double d4 = MathHelper.cos(f91);
            double d5 = MathHelper.sin(f10);
            double d6 = MathHelper.cos(f10);
            double d7 = (sdkentitygrapplinghook.owner.prevPosX + (sdkentitygrapplinghook.owner.posX - sdkentitygrapplinghook.owner.prevPosX) * (double)f1) - d4 * 0.7D - d3 * 0.5D * d6;
            double d8 = (sdkentitygrapplinghook.owner.prevPosY + (sdkentitygrapplinghook.owner.posY - sdkentitygrapplinghook.owner.prevPosY) * (double)f1) - d5 * 0.5D;
            double d9 = ((sdkentitygrapplinghook.owner.prevPosZ + (sdkentitygrapplinghook.owner.posZ - sdkentitygrapplinghook.owner.prevPosZ) * (double)f1) - d3 * 0.7D) + d4 * 0.5D * d6;

            if (renderManager.options.thirdPersonView > 0)
            {
                float f11 = ((sdkentitygrapplinghook.owner.prevRenderYawOffset + (sdkentitygrapplinghook.owner.renderYawOffset - sdkentitygrapplinghook.owner.prevRenderYawOffset) * f1) * (float)Math.PI) / 180F;
                double d11 = MathHelper.sin(f11);
                double d13 = MathHelper.cos(f11);
                d7 = (sdkentitygrapplinghook.owner.prevPosX + (sdkentitygrapplinghook.owner.posX - sdkentitygrapplinghook.owner.prevPosX) * (double)f1) - d13 * 0.35D - d11 * 0.85D;
                d8 = (sdkentitygrapplinghook.owner.prevPosY + (sdkentitygrapplinghook.owner.posY - sdkentitygrapplinghook.owner.prevPosY) * (double)f1) - 0.45D;
                d9 = ((sdkentitygrapplinghook.owner.prevPosZ + (sdkentitygrapplinghook.owner.posZ - sdkentitygrapplinghook.owner.prevPosZ) * (double)f1) - d11 * 0.35D) + d13 * 0.85D;
            }

            double d10 = sdkentitygrapplinghook.prevPosX + (sdkentitygrapplinghook.posX - sdkentitygrapplinghook.prevPosX) * (double)f1;
            double d12 = sdkentitygrapplinghook.prevPosY + (sdkentitygrapplinghook.posY - sdkentitygrapplinghook.prevPosY) * (double)f1 + 0.25D;
            double d14 = sdkentitygrapplinghook.prevPosZ + (sdkentitygrapplinghook.posZ - sdkentitygrapplinghook.prevPosZ) * (double)f1;
            double d15 = (float)(d7 - d10);
            double d16 = (float)(d8 - d12);
            double d17 = (float)(d9 - d14);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            tessellator.startDrawing(3);
            tessellator.setColorOpaque_I(0);
            int i = 16;

            for (int j = 0; j <= i; j++)
            {
                float f12 = (float)j / (float)i;
                tessellator.addVertex(d + d15 * (double)f12, d1 + d16 * (double)(f12 * f12 + f12) * 0.5D + 0.25D, d2 + d17 * (double)f12);
            }

            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
    }

    protected ResourceLocation func_110791_a(EntityGrapplingHook par1EntityFishHook)
    {
        return TextureMap.field_110576_c;
    }

    protected ResourceLocation func_110775_a(Entity par1Entity)
    {
        return this.func_110791_a((EntityGrapplingHook)par1Entity);
    }
    
    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRenderFishHook((EntityGrapplingHook)entity, d, d1, d2, f, f1);
    }
}