package SDKUtility.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import SDKUtility.common.ContainerGrinder;
import SDKUtility.common.TileEntityGrinder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class GuiGrinder extends GuiContainer
{
    private TileEntityGrinder grinder;
    private static final ResourceLocation GuiLocation = new ResourceLocation("sdk_utility","textures/gui/guiGrinder.png");

    public GuiGrinder(InventoryPlayer inventoryplayer, TileEntityGrinder sdktileentitygrinder)
    {
        super(new ContainerGrinder(inventoryplayer, sdktileentitygrinder));
        grinder = sdktileentitygrinder;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	super.drawGuiContainerForegroundLayer(par1, par2);
        fontRenderer.drawString("Grinder", xSize / 2 - fontRenderer.getStringWidth("Grinder") / 2, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }
    
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        this.mc.func_110434_K().func_110577_a(GuiLocation);
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

        if (grinder.isBurning())
        {
            int j1 = grinder.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(l + 56, (i1 + 36 + 12) - j1, 176, 12 - j1, 14, j1 + 2);
        }

        int k1 = grinder.getCookProgressScaled(24);
        drawTexturedModalRect(l + 79, i1 + 34, 176, 14, k1 + 1, 16);
	}
}