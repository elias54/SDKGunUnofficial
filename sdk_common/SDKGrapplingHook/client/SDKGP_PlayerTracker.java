package SDKGrapplingHook.client;

import SDKGrapplingHook.common.SDK_GrapplingHook;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class SDKGP_PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		SDK_GrapplingHook.modLoaded = true;
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
