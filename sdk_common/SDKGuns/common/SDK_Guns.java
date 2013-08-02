package SDKGuns.common;

import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@Mod(modid = "sdk_guns", name = "SDK - GUNS", version = "V1")

public class SDK_Guns {

	@SidedProxy(clientSide = "SDKGuns.client.SDKGuns_ClientProxy", serverSide = "SDKGuns.common.SDKGuns_CommonProxy")
	public static SDKGuns_CommonProxy proxy;
	
	@Instance("sdk_guns")
	public static SDK_Guns instance;
	
    public static Block blockNuke, blockLighter, blockOil, blockCannon;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.render();
	}
	
	
}
