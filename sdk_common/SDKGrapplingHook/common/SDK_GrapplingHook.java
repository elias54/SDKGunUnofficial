package SDKGrapplingHook.common;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@Mod(modid = "sdk_grapplinghook", name = "SDK - Grappling Hook", version = "V1")

public class SDK_GrapplingHook {

	@SidedProxy(clientSide = "SDKGrapplingHook.client.SDKGP_ClientProxy", serverSide = "SDKGrapplingHook.common.SDKGP_CommonProxy")
	public static SDKGP_CommonProxy proxy;
	
	@Instance("sdk_grapplinghook")
	public static SDK_GrapplingHook instance;
	
	public static Logger SDKGPLogger;
	
	public static Block blockGrapplingHook, blockRope;
	public static Item grapplingIconThrowable, itemGrapplingHook, itemRope;
	
	public static int grapplingRenderType, 
					  ropeRenderType;
	
    public static Map grapplingHooks = new HashMap();
	
	public static int blockGrapplingHookID, 
					  blockRopeID,
					  itemGrapplingHookID, 
					  itemRopeID;
    
	public static final CreativeTabs sdk_grapplingHookTab = new CreativeTabs("SDK_GP_TAB");
	
	@Mod.EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		SDKGPLogger = event.getModLog();
		SDKGPLogger.log(Level.INFO, "Loading all configs...");
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try {
			config.load();
			itemGrapplingHookID = config.getItem("Item Grappling Hook ID", 9500).getInt();
			itemRopeID = config.getItem("Item Rope ID", 9501).getInt();
			// SEPARATOR //
			blockGrapplingHookID = config.getBlock("Block Grappling Hook ID", 2500).getInt();
			blockRopeID = config.getBlock("Block Rope ID", 2501).getInt();

		} finally {
			if(config.hasChanged())
			{
				config.save();
			}
		}
	}
	
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		blockGrapplingHook = new BlockGrapplingHook(blockGrapplingHookID).setHardness(0.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("SdkGuns:blockGrapplingHook").func_111022_d("SdkGuns:blockGrapplingHook");
		blockRope = new BlockRope(blockRopeID).setBlockUnbreakable().setUnlocalizedName("SdkGuns:blockRope").func_111022_d("SdkGuns:blockRope");
		itemGrapplingHook =  new ItemGrapplingHook(itemGrapplingHookID).setCreativeTab(sdk_grapplingHookTab).setUnlocalizedName("SdkGuns:itemGrapplingHook").func_111206_d("SdkGuns:itemGrapplingHook");
		itemRope = new Item(itemRopeID).setCreativeTab(sdk_grapplingHookTab).setUnlocalizedName("SdkGuns:itemRope").func_111206_d("SdkGuns:itemRope");
		grapplingIconThrowable = new Item(31000).setUnlocalizedName("SdkGuns:itemGrapplingHookThrown").func_111206_d("SdkGuns:itemGrapplingHookThrown");


		EntityRegistry.registerModEntity(EntityGrapplingHook.class, "GrapplingHook", 320, this, 40, 1, true);
		GameRegistry.registerTileEntity(TileEntityRope.class, "Rope");
		proxy.loadRender();
		proxy.loadBlocksRenderer();
	}
	
	@Mod.EventHandler
	public void postLoad(FMLPostInitializationEvent event)
	{
		GameRegistry.registerBlock(blockGrapplingHook, "grapplinghook");
		GameRegistry.registerBlock(blockRope, "rope");
		LanguageRegistry.addName(itemGrapplingHook, "Grappling Hook");
		LanguageRegistry.addName(itemRope, "Rope");
		GameRegistry.addRecipe(new ItemStack(itemGrapplingHook, 1), new Object[] {"X", "#", "#", '#', itemRope, 'X', Item.ingotIron});
		GameRegistry.addRecipe(new ItemStack(itemRope, 1), new Object[]{"##", "##", "##", '#', Item.silk});
		LanguageRegistry.instance().addStringLocalization("SDK_GP_TAB", "SDK - Grappling Hook Tab");
	}
	
	
}
