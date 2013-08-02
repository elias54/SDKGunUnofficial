package SDKFps.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemReed;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import SDK_Flasher.common.Point3d;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@Mod(modid = "sdk_fps", name = "SDK - FPS", version = "V1", dependencies = "required-after:sdk_flasher"/*;required-after:sdk_guns"*/)

public class SDK_FPS {

	@SidedProxy(clientSide = "SDKFps.common.FPS_CommonProxy", serverSide = "SDKFps.client.FPS_ClientProxy")
	public static FPS_CommonProxy proxy;
	
	@Instance("sdk_fps")
	public static SDK_FPS FpsInstance;
	
	public static Block blockPlayerSpawner, blockWeaponSpawnerNotGuns;
	public static BlockWeaponSpawnerGuns blockWeaponSpawnerGuns;
	public static Item itemPlayerSpawner, itemWeaponSpawnerGuns, itemWeaponSpawnerNotGuns;
	
	public static int blockPlayerSpawnerID, blockWeaponSpawnerGunsID, blockWeaponSpawnerNotGunsID, itemPlayerSpawnerID, itemWeaponSpawnerGunsID, itemWeaponSpawnerNotGunsID; 
	
	
    public static boolean blocksMineable = true;
    public static boolean blocksExplodable = true;
    public static boolean blocksBurnable = true;
    public static boolean enablePlayerSpawners = true;
    public static boolean useRandomPlayerSpawner = true;
    public static boolean enableWeaponSpawners = true;
    public static boolean enableSpawnerRecipes = false;
    public static boolean restrictInventory = false;
    public static boolean respawnWithPistol = false;
    public static int weaponSpawnerRespawnDelay = 30;
    public static boolean playersDropInventory = true;
    public static int blockIdPlayerSpawner = 136;
    public static int blockIdWeaponSpawnerGuns = 137;
    public static int blockIdWeaponSpawnerNotGuns = 138;
    public static ArrayList spawnPoints = new ArrayList();
    public static Map weaponNotGunsIdItemMap = new HashMap();
    public static Map weaponNotGunsIdTextureMap = new HashMap();
    private static boolean areBlocksMineable = true;
    private static float blockHardness[];
    private static boolean areBlocksExplodable = true;
    private static float blockResistance[];
    private static boolean areBlocksBurnable = true;
    private static int blockChanceToEncourageFire[];
    private static int blockAbilityToCatchFire[];
	
    public static final CreativeTabs sdk_fpsTab = new CreativeTabs("Sdk_FpsTab");
    
    
    
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		if(event.getSide().isClient())
		{
			//Event sound coming here
		}
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try {
			config.load();
			blockPlayerSpawnerID = config.getBlock("Block Player Spawner ID", 2502).getInt();
			blockWeaponSpawnerGunsID = config.getBlock("Block Weapon Spawner Guns ID", 2503).getInt();
			blockWeaponSpawnerNotGunsID = config.getBlock("Block Weapon Spawner Not Guns ID", 2504).getInt();
			itemPlayerSpawnerID = config.getItem("Item Player Spawner ID", 9502).getInt();
			itemWeaponSpawnerGunsID = config.getItem("Item Weapon Spawner Guns ID", 9503).getInt();
			itemWeaponSpawnerNotGunsID = config.getItem("Item Weapon Spawner Not Guns ID", 9504).getInt();
		} finally {
			if(config.hasChanged())
			{
				config.save();
			}
		}
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
        blockPlayerSpawner = (new BlockPlayerSpawner(blockPlayerSpawnerID)).setHardness(6000000F).setResistance(6000000F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockPlayerSpawner");
        blockWeaponSpawnerGuns = (BlockWeaponSpawnerGuns) (new BlockWeaponSpawnerGuns(blockWeaponSpawnerGunsID)).setHardness(6000000F).setResistance(6000000F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockWeaponSpawnerGuns");
        //blockWeaponSpawnerNotGuns = (new BlockWeaponSpawnerNotGuns(blockWeaponSpawnerNotGunsID, weaponSpawnerNotGunsTextureIndices[0])).setHardness(6000000F).setResistance(6000000F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockWeaponSpawnerNotGuns");
	
		
        itemPlayerSpawner = (new ItemReed(itemPlayerSpawnerID, blockPlayerSpawner)).setCreativeTab(sdk_fpsTab).setUnlocalizedName("sdk_fps:itemPlayerSpawner").func_111206_d("sdk_fps:itemPlayerSpawner");
		itemWeaponSpawnerGuns = (new ItemReed(itemWeaponSpawnerGunsID, blockWeaponSpawnerGuns)).setCreativeTab(sdk_fpsTab).setUnlocalizedName("sdk_fps:itemWeaponSpawner").func_111206_d("sdk_fps:itemWeaponSpawner");
		//itemWeaponSpawnerNotGuns = 
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
        GameRegistry.registerTileEntity(TileEntityPlayerSpawner.class, "entityPlayerSpawner");
		GameRegistry.registerBlock(blockPlayerSpawner, "playerSpawner");
		
        GameRegistry.registerTileEntity(TileEntityWeaponSpawnerGuns.class, "entityWeaponSpawner");
		GameRegistry.registerBlock(blockWeaponSpawnerGuns, "weaponSpawnerGuns");
		
		GameRegistry.registerItem(itemPlayerSpawner, "playerSpawnerItem");
		LanguageRegistry.instance().addStringLocalization("itemGroup.sdk_fpsTab", "SDK - FPS");
		
		LanguageRegistry.addName(blockPlayerSpawner, "Block Player Spawner");
		LanguageRegistry.addName(itemPlayerSpawner, "Player Spawner");
		TickRegistry.registerTickHandler(new TickCommonHandler(), Side.SERVER);

        buildWeaponMaps();
	}
	
    public void buildWeaponMaps() {
        try
        {
        }catch (Throwable throwable)
        {
        	FMLLog.log(Level.SEVERE, "MAPPING FAILURE");
        	return;
        }
	}

	public static void registerPlayerSpawner(int i, int j, int k)
    {
        synchronized (SDK_FPS.class)
        {
            Point3d sdkpoint3d = new Point3d(Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k));

            if (!spawnPoints.contains(sdkpoint3d))
            {
                spawnPoints.add(sdkpoint3d);
            }
        }
    }

    public static void unregisterPlayerSpawner(int i, int j, int k)
    {
        synchronized (SDK_FPS.class)
        {
            Point3d sdkpoint3d = new Point3d(Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k));

            if (spawnPoints.contains(sdkpoint3d))
            {
                spawnPoints.remove(new Point3d(Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)));
            }
        }
    }

    public static void setSpawnPoint(EntityPlayer entityplayer, boolean flag)
    {
        setSpawnPoint(entityplayer, entityplayer.posX, entityplayer.posY, entityplayer.posZ, flag);
    }
	
    public static void setSpawnPoint(EntityPlayer entityplayer, double d, double d1, double d2, boolean flag)
    {
        synchronized (SDK_FPS.class)
        {
            if (FMLClientHandler.instance().getClient().isSingleplayer() && (flag))
            {
                if (enablePlayerSpawners && spawnPoints.size() > 0)
                {
                    Point3d sdkpoint3d;

                    if (useRandomPlayerSpawner)
                    {
                        sdkpoint3d = (Point3d)spawnPoints.get(proxy.random.nextInt(spawnPoints.size()));
                    }
                    else
                    {
                        double d3 = Double.MAX_VALUE;
                        int i = 0;

                        for (int j = 0; j < spawnPoints.size(); j++)
                        {
                            Point3d sdkpoint3d1 = (Point3d)spawnPoints.get(j);
                            double d4 = (double)((Integer)sdkpoint3d1.x).intValue() - d;
                            double d5 = (double)((Integer)sdkpoint3d1.y).intValue() - d1;
                            double d6 = (double)((Integer)sdkpoint3d1.z).intValue() - d2;
                            double d7 = d4 * d4 + d5 * d5 + d6 * d6;

                            if (d7 < d3)
                            {
                                d3 = d7;
                                i = j;
                            }
                        }

                        sdkpoint3d = (Point3d)spawnPoints.get(i);
                    }

                    entityplayer.setPosition((double)((Integer)sdkpoint3d.x).intValue() + 0.5D, (float)((Integer)sdkpoint3d.y).intValue() + 1.6825F, (double)((Integer)sdkpoint3d.z).intValue() + 0.5D);
                }
            }
        }
    }
    
}
