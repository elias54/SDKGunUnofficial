package SDK_Flasher.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "sdk_flasher", name = "SDK - Flasher", version = "V1")

public class SDK_Flasher {
	
	@SidedProxy(clientSide = "SDK_Flasher.client.Flasher_ClientProxy", serverSide = "SDK_Flasher.common.Flasher_CommonProxy")
	public static Flasher_CommonProxy proxy;
	
	public static Map litBlocks = new HashMap();
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.render();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
    public static void LightEntity(World world, Entity entity, int i, int j)
    {
        LightBlock(world, (int)Math.floor(entity.posX), (int)Math.floor(entity.posY), (int)Math.floor(entity.posZ), i, j);
    }

    public static void LightBlock(World world, int i, int j, int k, int l, int i1)
    {
        LightBlock(world, new Point3d(Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)), l, i1);
    }

    public static void LightBlock(World world, Point3d sdkpoint3d, int i, int j)
    {
        clearLitBlocks(world);
        int k = i - 1;
        int l = k * 2 + 1;
        int ai[] = new int[l * l * l];

        for (int i1 = -k; i1 <= k; i1++)
        {
            for (int j1 = -k; j1 <= k; j1++)
            {
                for (int k1 = -k; k1 <= k; k1++)
                {
                    int l1 = (k1 + k) * l * l + (j1 + k) * l + (i1 + k);
                    ai[l1] = -1;

                    if (world.getBlockId(((Integer)sdkpoint3d.x).intValue() + i1, ((Integer)sdkpoint3d.y).intValue() + j1, ((Integer)sdkpoint3d.z).intValue() + k1) != 0)
                    {
                        continue;
                    }

                    int i2 = Math.abs(i1) + Math.abs(j1) + Math.abs(k1);
                    int j2 = Math.max(0, i - i2);
                    int k2 = world.getBlockLightValue(((Integer)sdkpoint3d.x).intValue() + i1, ((Integer)sdkpoint3d.y).intValue() + j1, ((Integer)sdkpoint3d.z).intValue() + k1);

                    if (j2 > k2)
                    {
                        ai[l1] = k2;
                        world.setLightValue(EnumSkyBlock.Block, ((Integer)sdkpoint3d.x).intValue() + i1, ((Integer)sdkpoint3d.y).intValue() + j1, ((Integer)sdkpoint3d.z).intValue() + k1, j2);
                    }
                }
            }
        }

        LitBlock sdklitblock = new LitBlock(sdkpoint3d, ai, i);
        litBlocks.put(sdklitblock, Integer.valueOf(j));
    }

    private static void clearLitBlocks(World world)
    {
        for (Iterator iterator = litBlocks.entrySet().iterator(); iterator.hasNext(); iterator.remove())
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            unlightBlock(world, (LitBlock)entry.getKey());
        }
    }

    public static void unlightBlock(World world, LitBlock sdklitblock)
    {
        Point3d sdkpoint3d = sdklitblock.getBlockLocation();
        int ai[] = sdklitblock.getLightValues();
        int i = sdklitblock.getLightLevel();
        int j = i - 1;
        int k = j * 2 + 1;

        for (int l = -j; l <= j; l++)
        {
            for (int i1 = -j; i1 <= j; i1++)
            {
                for (int j1 = -j; j1 <= j; j1++)
                {
                    int k1 = (j1 + j) * k * k + (i1 + j) * k + (l + j);

                    if (ai[k1] != -1 && world.getBlockId(((Integer)sdkpoint3d.x).intValue() + l, ((Integer)sdkpoint3d.y).intValue() + i1, ((Integer)sdkpoint3d.z).intValue() + j1) == 0)
                    {
                        world.setLightValue(EnumSkyBlock.Block, ((Integer)sdkpoint3d.x).intValue() + l, ((Integer)sdkpoint3d.y).intValue() + i1, ((Integer)sdkpoint3d.z).intValue() + j1, ai[k1]);
                    }
                }
            }
        }
    }

}
