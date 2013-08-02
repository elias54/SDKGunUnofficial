package SDK_Main.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "sdk_analyzer", name = "SDK Detector", version = "N/A")

@SideOnly(Side.CLIENT)
public class SDK_Main {

	public static boolean modLoaded;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		modLoaded = false;
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		GameRegistry.registerPlayerTracker(new SDK_PlayerTracker());
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
