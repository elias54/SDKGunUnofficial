package SDKUtility.common;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemGhillieArmor extends ItemArmor {

	public ItemGhillieArmor(int par1, int wide, int wide2) {
		super(par1, EnumArmorMaterial.CHAIN, wide, wide2);
		// TODO Auto-generated constructor stub
	}

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
	{
    	if(stack.itemID == SDK_Utility.itemGhillieHelmet.itemID)
    	{
    		return "sdk_utility:textures/models/armor/ghillie_1.png";
    	}
    	if(stack.itemID == SDK_Utility.itemGhillieChest.itemID)
    	{
    		return "sdk_utility:textures/models/armor/ghillie_1.png";
    	} else {
    		return "sdk_utility:textures/models/armor/ghillie_2.png";
    	}
	}
	
}
