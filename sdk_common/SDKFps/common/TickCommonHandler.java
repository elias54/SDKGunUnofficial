package SDKFps.common;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TickCommonHandler implements ITickHandler {
	
	@SideOnly(Side.CLIENT)
	public static final Minecraft mc = Minecraft.getMinecraft();
	
    public void onTickInGameTick(EntityPlayer player)
    {
    	final Minecraft mc = Minecraft.getMinecraft();
        if (player != null)
        {
            if (SDK_FPS.proxy.deathWorld == null)
            {
                if (!player.isEntityAlive())
                {
                	SDK_FPS.proxy.setDeathPosition(mc);
                }
            }
            else if (SDK_FPS.proxy.deathWorld != mc.theWorld)
            {
            	SDK_FPS.proxy.clearDeathPosition();
            }
            else if (player.isEntityAlive())
            {
            	SDK_FPS.setSpawnPoint(player, ((Double)SDK_FPS.proxy.deathPosition.x).doubleValue(), ((Double)SDK_FPS.proxy.deathPosition.y).doubleValue(), ((Double)SDK_FPS.proxy.deathPosition.z).doubleValue(), true);
                SDK_FPS.proxy.clearDeathPosition();
            }
        }
    }
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		final EntityPlayer player = mc.thePlayer;
		if(type.equals(EnumSet.of(TickType.SERVER)))
		{
			onTickInGameTick(player);
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.PLAYER, TickType.SERVER);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "SDK FPS COMMON TICK HANDLER";
	}

}
