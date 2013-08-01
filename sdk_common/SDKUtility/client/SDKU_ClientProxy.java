package SDKUtility.client;

import SDKUtility.common.EntityParachute;
import SDKUtility.common.SDKU_CommonProxy;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class SDKU_ClientProxy extends SDKU_CommonProxy {

	@Override
	public void loadRender() {
		TickRegistry.registerTickHandler(new TickClientHandler(), Side.CLIENT);
		RenderingRegistry.registerEntityRenderingHandler(EntityParachute.class, new RenderParachute());
	}
	
	@Override
	public void loadBlocksRenderer() 
	{
	}
	
}
