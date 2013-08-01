package SDKUtility.common;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemNightVisionGoggles extends ItemArmor {
	
	public ItemNightVisionGoggles(int par1) {
		super(par1, EnumArmorMaterial.CHAIN, 0, 0);
		// TODO Auto-generated constructor stub
	}

    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
    {
    	SDK_Utility.nightvisionEnabled = true;
    }
	
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
	{
    	return "sdk_utility:textures/models/armor/nightvisiongoggles.png";
	}
	
	
}
