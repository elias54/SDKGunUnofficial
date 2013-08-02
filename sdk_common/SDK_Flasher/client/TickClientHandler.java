package SDK_Flasher.client;

import java.util.EnumSet;
import java.util.Iterator;

import SDK_Flasher.common.LitBlock;
import SDK_Flasher.common.SDK_Flasher;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickClientHandler implements ITickHandler {

    public void onTickInGameTick(Minecraft minecraft)
    {
        if (minecraft.theWorld != null)
        {
            for (Iterator iterator = SDK_Flasher.litBlocks.entrySet().iterator(); iterator.hasNext();)
            {
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                int i = ((Integer)entry.getValue()).intValue() - 1;

                if (i <= 0)
                {
                	SDK_Flasher.unlightBlock(minecraft.theWorld, (LitBlock)entry.getKey());
                    iterator.remove();
                }
                else
                {
                    entry.setValue(Integer.valueOf(i));
                }
            }
        }
    }
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		final Minecraft mc = FMLClientHandler.instance().getClient();
		if(type.equals(EnumSet.of(TickType.CLIENT)))
		{
			onTickInGameTick(mc);
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "SDK Flasher Tick Client Handler";
	}

}
