package SDKUtility.common;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TickCommonHandler implements ITickHandler {

	public final Random random = new Random();
    private void useParachute(EntityPlayer entityplayer)
    {
    	if (entityplayer.getCurrentItemOrArmor(3) != null ) {
    		ItemStack itemstack = entityplayer.getCurrentItemOrArmor(3);
    		if(itemstack.getItem() == SDK_Utility.itemParachute)
    		{
		        if (!entityplayer.onGround && !entityplayer.isInWater())
		        {
		            entityplayer.setInWeb();
		            itemstack.damageItem(1, entityplayer);
		            entityplayer.worldObj.playSoundAtEntity(entityplayer, "sdk_utility:parachute", 0.5F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
		            EntityParachute parachute = new EntityParachute(entityplayer.worldObj, entityplayer);
		            if (!entityplayer.worldObj.isRemote)
		            {
		            	entityplayer.worldObj.spawnEntityInWorld(parachute);
		            }
		        }
    		}
    	}
    }
    
    public void onTickInPlayer(EntityPlayer player)
    {
		ItemStack itemstack = player.inventory.armorItemInSlot(3);
        if (itemstack != null && itemstack.itemID == SDK_Utility.itemScubaTank.itemID)
        {
            player.setAir(300);
        }
        
		ItemStack itemstack1 = player.inventory.armorItemInSlot(3);
		if (itemstack1 == null || itemstack1.itemID != SDK_Utility.itemNightvisionGoggles.itemID)
        {
			SDK_Utility.nightvisionEnabled = false;
        }
    }
    
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
    	if (type.equals(EnumSet.of(TickType.PLAYER)))
    	{
    		useParachute((EntityPlayer)tickData[0]);
    		onTickInPlayer((EntityPlayer)tickData[0]);
    	}
    }

    @Override
    public EnumSet<TickType> ticks() 
    {
     return EnumSet.of(TickType.PLAYER, TickType.SERVER);
    }

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "SDK - UTILITY TICK HANDLER";
	}

}
