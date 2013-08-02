package SDKUtility.client;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import SDKUtility.common.EntityParachute;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderParachute extends RenderLiving
{
	protected static final ResourceLocation texture = new ResourceLocation("sdk_utility", "textures/models/entity/mobParachute.png");
	
    public RenderParachute()
    {
        super(new ModelParachute(), 0.0F);
    }

    public ResourceLocation getTexture(EntityParachute parachute)
    {
    	return texture;
    }
    
	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		// TODO Auto-generated method stub
		return getTexture((EntityParachute) entity);
	}
}