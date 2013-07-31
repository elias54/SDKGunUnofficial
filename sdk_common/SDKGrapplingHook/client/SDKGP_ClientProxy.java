package SDKGrapplingHook.client;

import SDKGrapplingHook.common.EntityGrapplingHook;
import SDKGrapplingHook.common.SDKGP_CommonProxy;
import SDKGrapplingHook.common.SDK_GrapplingHook;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class SDKGP_ClientProxy extends SDKGP_CommonProxy {

	@Override
	public void loadRender()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityGrapplingHook.class, new RenderGrapplingHook(0.5F));
	}
	@Override
	public void loadBlocksRenderer() 
	{
		SDK_GrapplingHook.grapplingRenderType = RenderingRegistry.getNextAvailableRenderId();
		SDK_GrapplingHook.ropeRenderType = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderBlockGrapplingHook());
		RenderingRegistry.registerBlockHandler(new RenderBlockRope());	
	}
	
}
