package SDK_Flasher.client;

import SDK_Flasher.common.Flasher_CommonProxy;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class Flasher_ClientProxy extends Flasher_CommonProxy {

	@Override
	public void render()
	{
		TickRegistry.registerTickHandler(new TickClientHandler(), Side.CLIENT);
	}
	
}
