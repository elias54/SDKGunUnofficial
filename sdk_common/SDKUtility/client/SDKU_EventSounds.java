package SDKUtility.client;

import java.util.logging.Level;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import SDKUtility.common.SDK_Utility;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

@SideOnly(Side.CLIENT)
public class SDKU_EventSounds {

	@ForgeSubscribe
	public void onSound(SoundLoadEvent event)
	{
		try {
			event.manager.soundPoolSounds.addSound("sdk_utility:parachute.ogg");
		} catch(RuntimeException ex)
		{
			SDK_Utility.SDKU_Logger.log(Level.SEVERE, "FAILED TO LOAD SOUNDS ! THEY ARE NOW DISABLED !");
			ex.printStackTrace();
		}
	}
	
}
