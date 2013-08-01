package SDKUtility.common;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemParachute extends ItemArmor{
    public ItemParachute(int i)
    {
        super(i, EnumArmorMaterial.CHAIN, 1, 1);
        setMaxDamage(7);
    }
    
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
	{
    	return "sdk_utility:textures/models/armor/parachute.png";
	}
}
