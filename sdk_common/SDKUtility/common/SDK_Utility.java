package SDKUtility.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

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
    
    private static int parachuteKey = 25;
    public static int nightvisionKey = 49;
    private static boolean parachuteKeyDown = false;
    public static boolean nightvisionKeyDown = false;
    public static boolean nightvisionEnabled;
    
    
    public static boolean telescopeActivated;
    
    public static final CreativeTabs sdk_UtilityTab = new CreativeTabs("SDK_u");
    
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
        itemGoldCoin = addSimpleItem(itemGoldCoinID, "goldCoin", "itemGoldCoin", "Gold Coins");
        itemLightometer = addSimpleItem(itemLightometerID, "lightOmeter", "itemLightometer", "Lightometer");
        itemParachute = new ItemParachute(itemParachuteID).setCreativeTab(sdk_UtilityTab).setUnlocalizedName("itemParachute").func_111206_d("sdk_utility:itemParachute");
        itemTelescope = new ItemTelescope(itemTelescopeID).setCreativeTab(sdk_UtilityTab).setUnlocalizedName("itemTelescope").func_111206_d("sdk_utility:itemTelescope");
        itemNightvisionGoggles = new ItemNightVisionGoggles(itemNightvisionGogglesID).setCreativeTab(sdk_UtilityTab).setUnlocalizedName("itemNightvisionGoggles").func_111206_d("sdk_utility:itemNightvisionGoggles");
        itemScubaTank = new ItemScubaTank(itemScubaTankID).setCreativeTab(sdk_UtilityTab).setUnlocalizedName("itemScubaTank").func_111206_d("sdk_utility:itemScubaTank");
        itemGhillieHelmet = new ItemGhillieArmor(itemGhillieHelmetID, 0, 0).setCreativeTab(sdk_UtilityTab).setUnlocalizedName("itemGhillieHelmet");
        itemGhillieChest = new ItemGhillieArmor(itemGhillieChestID, 0, 1).setCreativeTab(sdk_UtilityTab).setUnlocalizedName("itemGhillieChest");
        itemGhilliePants = new ItemGhillieArmor(itemGhilliePantsID, 0, 2).setCreativeTab(sdk_UtilityTab).setUnlocalizedName("itemGhilliePants");
        itemGhillieBoots = new ItemGhillieArmor(itemGhillieBootsID, 0, 3).setCreativeTab(sdk_UtilityTab).setUnlocalizedName("itemGhillieBoots");
        NetworkRegistry.instance().registerGuiHandler(this, new SDKUtilityGuiHandler());
        EntityRegistry.registerGlobalEntityID(EntityParachute.class, "Parachute", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityParachute.class, "Parachute", 301, this, 20, 1, true);
	}
	public Item addSimpleItem(int id, String unlocalizedName, String textureDir, String ingameName)
	{
		Item item = new Item(id).setCreativeTab(sdk_UtilityTab).setUnlocalizedName(unlocalizedName).func_111206_d("sdk_utility:" + textureDir);
		LanguageRegistry.addName(item, ingameName);
		return item;
	}
	
    public static void handleNightvisionKey()
    {
        nightvisionEnabled = !nightvisionEnabled;
    }
    
	@Mod.EventHandler
	public void postLoad(FMLPostInitializationEvent event)
	{
		GameRegistry.registerBlock(blockGrinder, "GRINDER");
        GameRegistry.registerTileEntity(TileEntityGrinder.class, "Grinder");

        GameRegistry.registerItem(itemParachute, "parachute", "sdk_utility");
        GameRegistry.registerItem(itemTelescope, "telescope", "sdk_utility");
        GameRegistry.registerItem(itemNightvisionGoggles, "nVisionGoggles", "sdk_utility");
        proxy.loadBlocksRenderer();
        proxy.loadRender();
        LanguageRegistry.addName(blockGrinder, "Grinder");
        LanguageRegistry.addName(itemParachute, "Parachute");
        LanguageRegistry.addName(itemTelescope, "Telescope");
        LanguageRegistry.addName(itemNightvisionGoggles, "Nightvision Goggles");
        LanguageRegistry.addName(itemScubaTank, "Scuba Tank");
        LanguageRegistry.addName(itemGhillieHelmet, "Ghillie Helmet");
        LanguageRegistry.addName(itemGhillieChest, "Ghillie Chestplate");
        LanguageRegistry.addName(itemGhilliePants, "Ghillie Pants");
        LanguageRegistry.addName(itemGhillieBoots, "Ghillie Boots");
        LanguageRegistry.instance().addStringLocalization("itemGroup.SDK_u", "SDK - Utility");
        
        GameRegistry.addShapedRecipe(new ItemStack(itemGoldCoin, 4), new Object[]
                {
                    "#", '#', Item.ingotGold
                });
        GameRegistry.addShapedRecipe(new ItemStack(Item.ingotGold, 1), new Object[]
                {
                    "##", "##", '#', itemGoldCoin
                });
        GameRegistry.addShapedRecipe(new ItemStack(itemLightometer, 1), new Object[]
                {
                    " # ", "#X#", " # ", '#', Item.ingotIron, 'X', Item.glowstone
                });
        GameRegistry.addShapedRecipe(new ItemStack(itemParachute, 1), new Object[]
                {
                    "###", "XYX", "YYY", '#', Block.cloth, 'X', Item.silk, 'Y', Item.leather
                });
        GameRegistry.addShapedRecipe(new ItemStack(itemTelescope), new Object[]
                {
                    "#", "X", "X", '#', Item.diamond, 'X', Item.ingotGold
                });
        GameRegistry.addShapedRecipe(new ItemStack(itemNightvisionGoggles, 1), new Object[]
                {
                    "#X#", '#', Item.diamond, 'X', Item.silk
                });
        GameRegistry.addShapedRecipe(new ItemStack(itemScubaTank, 1), new Object[]
                {
                    "###", "# #", "###", '#', Item.ingotIron
                });
        GameRegistry.addShapedRecipe(new ItemStack(blockGrinder, 1), new Object[]
                {
                    "###", "#X#", "###", '#', Block.cobblestone, 'X', Item.diamond
                });
        GameRegistry.addShapedRecipe(new ItemStack(itemGhillieHelmet), new Object[]
                {
                    "###", "# #", '#', Block.vine
                });
        GameRegistry.addShapedRecipe(new ItemStack(itemGhillieChest), new Object[]
                {
                    "# #", "###", "###", '#', Block.vine
                });
        GameRegistry.addShapedRecipe(new ItemStack(itemGhilliePants), new Object[]
                {
                    "###", "# #", "# #", '#', Block.vine
                });
        GameRegistry.addShapedRecipe(new ItemStack(itemGhillieBoots), new Object[]
                {
                    "# #", "# #", '#', Block.vine
                });
        TickRegistry.registerTickHandler(new TickCommonHandler(), Side.SERVER);
        
	}
	
}
