package SDKUtility.common;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemScubaTank extends ItemArmor {

	public ItemScubaTank(int par1) {
		super(par1, EnumArmorMaterial.CHAIN, 1, 1);
		// TODO Auto-generated constructor stub
	}
	
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
	{
    	return "sdk_utility:textures/models/armor/scubatank.png";
	}

}
