package SDKUtility.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@Mod(modid = "sdk_utility", name = "SDK - Utility", version = "V1")

public class SDK_Utility 
{
	@SidedProxy(clientSide = "SDKUtility.client.SDKU_ClientProxy", serverSide = "SDKUtility.common.SDKU_CommonProxy")
	public static SDKU_CommonProxy proxy;
	
	@Instance("sdk_utility")
	public static SDK_Utility instance;
	
	public static Logger SDKU_Logger;
	
    public static Block blockGrinder;
	
    public static Item itemGoldCoin, itemLightometer, itemNightvisionGoggles, itemScubaTank, itemParachute, itemTelescope, itemGhillieHelmet, itemGhillieChest, itemGhilliePants, itemGhillieBoots;
    
    public static int blockGrinderID,
    				  itemGoldCoinID, itemLightometerID, itemNightvisionGogglesID, itemScubaTankID, itemParachuteID, itemTelescopeID, itemGhillieHelmetID, itemGhillieChestID, itemGhilliePantsID, itemGhillieBootsID;
    
    private static final float DEFAULT_ZOOM = 1F;
    private static final float MAX_ZOOMS[] =
    {
        1.0F, 0.5F, 0.25F, 0.125F, 0.0625F
    };
    private static final float ZOOM_INCREMENT = 0.0625F;
    private static float currentZoom;
    private static int currentZoomIndex = 0;
    private static int lastZoomSlot = 0;
    private static float lastZoom;
    private static int parachuteKey = 25;
    private static boolean parachuteKeyDown = false;
    private static boolean nightvisionKeyDown = false;
    public static boolean nightvisionEnabled = false;
    
    public Logger getModLog(FMLPreInitializationEvent event)
    {
        Logger log = Logger.getLogger("SDK - Utility");
        log.setParent(FMLLog.getLogger());
        return log;
    }
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		SDKU_Logger = getModLog(event);
		SDKU_Logger.log(Level.INFO, "Loading Config manager...");
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try {
			cfg.load();
			blockGrinderID = cfg.getBlock("Block Grinder ID", 2138).getInt();
			
			itemGoldCoinID = cfg.getItem("Item Gold Coin ID", 22140).getInt();
			itemLightometerID = cfg.getItem("Item Lightometer ID", 22141).getInt();
			itemNightvisionGogglesID = cfg.getItem("Item Nightvision Goggles ID", 22142).getInt();
			itemScubaTankID = cfg.getItem("Item Scuba Tank ID", 22143).getInt();
			itemParachuteID = cfg.getItem("Item Parachute ID", 22144).getInt();
			itemTelescopeID = cfg.getItem("Item Telescope ID", 22145).getInt();
			itemGhillieHelmetID = cfg.getItem("Item Ghillie Helmet ID", 22146).getInt();
			itemGhillieChestID = cfg.getItem("Item Ghillie Chestplate ID", 22147).getInt();
			itemGhilliePantsID = cfg.getItem("Item Ghillie Leggings ID", 22148).getInt();
			itemGhillieBootsID = cfg.getItem("Item Ghillie Boots ID", 22149).getInt();
			
		} 
		finally 
		{
			if(cfg.hasChanged())
			{
				cfg.save();
			}
		}
	}
	
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
        blockGrinder = (new BlockGrinder(blockGrinderID)).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("blockGrinder");
        /*itemGoldCoin =                 |
        itemLightometer =                |
        itemNightvisionGoggles =         |
        itemScubaTank =                  | 
        itemParachute =                  |
        itemTelescope =                  | TODO
        itemGhillieHelmet =              |
        itemGhillieChest =               |
        itemGhilliePants =               |
        itemGhillieBoots =               | */
        NetworkRegistry.instance().registerGuiHandler(this, new SDKUtilityGuiHandler());
	}
	
	@Mod.EventHandler
	public void postLoad(FMLPostInitializationEvent event)
	{
		GameRegistry.registerBlock(blockGrinder, "GRINDER");
        LanguageRegistry.addName(blockGrinder, "Grinder");
        GameRegistry.registerTileEntity(TileEntityGrinder.class, "Grinder");
        proxy.loadBlocksRenderer();
        proxy.loadRender();
	}
	
}
