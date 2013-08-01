package SDKUtility.client;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import SDKUtility.common.SDK_Utility;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.TickType;

public class TickClientHandler implements ITickHandler {
	
	public static final ResourceLocation telescopeOverlay = new ResourceLocation("sdk_utility", "textures/blur/miscTelescope.png");
	public static final ResourceLocation nVisionOverlay = new ResourceLocation("sdk_utility", "textures/blur/miscNightvision.png");

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		final Minecraft minecraft = FMLClientHandler.instance().getClient();
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
	       final Minecraft minecraft = FMLClientHandler.instance().getClient();
	       final EntityPlayerSP player = minecraft.thePlayer;
			if (type.equals(EnumSet.of(TickType.CLIENT)))
	        {
				if(SDK_Utility.telescopeActivated)
				{
					if(minecraft.gameSettings.thirdPersonView == 0 && minecraft.currentScreen == null)
					{
						zoom(6F);
					}
				}else {
					zoom(1.0F);
				}
				
	        }
			if (type.equals(EnumSet.of(TickType.RENDER)))
	        {
				if(SDK_Utility.telescopeActivated && minecraft.gameSettings.thirdPersonView == 0 && minecraft.currentScreen == null)
				{
					renderTextureOverlay(telescopeOverlay, 1.0F);
				}
				
				if(SDK_Utility.nightvisionEnabled && minecraft.gameSettings.thirdPersonView == 0 && minecraft.currentScreen == null)
				{
					renderTextureOverlay(nVisionOverlay, 1.0F);
				}
	        }
	        if (minecraft.currentScreen == null && SDK_Utility.nightvisionKeyDown)
	        {
	            SDK_Utility.handleNightvisionKey();
	        }
	}
	
    public static void renderTextureOverlay(ResourceLocation s, float f)
    {
    	Minecraft minecraft = FMLClientHandler.instance().getClient();
        ScaledResolution scaledresolution = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        minecraft.func_110434_K().func_110577_a(s);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, j, -90D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
    }
	
	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	
    public static void zoom(float value)
    {
    	ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, FMLClientHandler.instance().getClient().entityRenderer, value, "cameraZoom");
    }
	
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "SDK - UTILITY TICK HANDLER (Client)";
	}
}
