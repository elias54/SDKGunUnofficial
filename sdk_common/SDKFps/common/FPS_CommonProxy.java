package SDKFps.common;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import SDK_Flasher.common.Point3d;

public class FPS_CommonProxy {


    public World deathWorld;
    public Point3d deathPosition;
    
    public static Random random = new Random();
	
	public void render()
	{
		
	}
	
    public void setDeathPosition(Minecraft minecraft)
    {
        deathWorld = minecraft.theWorld;
        deathPosition = new Point3d(Double.valueOf(minecraft.thePlayer.posX), Double.valueOf(minecraft.thePlayer.posY), Double.valueOf(minecraft.thePlayer.posZ));
    }

    public void clearDeathPosition()
    {
    	deathWorld = null;
    	deathPosition = null;
    }
	
}
