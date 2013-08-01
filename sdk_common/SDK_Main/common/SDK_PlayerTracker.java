package SDK_Main.common;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.Loader;

public class SDK_PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		if(!SDK_Main.modLoaded)
		{
			if(modLoaded("sdk_grapplinghook"))
			{
				player.addChatMessage("[SDK] Grappling Hook detected !");
			}
			if(modLoaded("sdk_utility"))
			{
				player.addChatMessage("[SDK] Misc detected !");
			}
			if(modLoaded("sdk_flasher"))
			{
				player.addChatMessage("[SDK] Flasher detected !");
			}
			if(modLoaded("sdk_fps"))
			{
				player.addChatMessage("[SDK] FPS detected !");
			}
			if(modLoaded("sdk_guns"))
			{
				player.addChatMessage("[SDK] Guns detected !");
			}
			SDK_Main.modLoaded = true;
		}
	}

	public boolean modLoaded(String s)
	{
		return Loader.isModLoaded(s);
	}
	
	@Override
	public void onPlayerLogout(EntityPlayer player) {
		SDK_Main.modLoaded = true;
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

}
