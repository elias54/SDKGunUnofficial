package SDKGrapplingHook.common;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityGrapplingHook extends Entity
{
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private boolean inGround;
    public EntityPlayer owner;
    private int ticksInGround;
    private int ticksInAir;
    private int ticksCatchable;

    /**
     * The entity that the fishing rod is connected to, if any. When you right click on the fishing rod and the hook
     * falls on to an entity, this it that entity.
     */
    public Entity bobber;
    private int fishPosRotationIncrements;
    private double fishX;
    private double fishY;
    private double fishZ;
    private double fishYaw;
    private double fishPitch;    
    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;
    private double startPosX;
    private double startPosZ;

    public EntityGrapplingHook(World world)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        ticksInAir = 0;
        ticksCatchable = 0;
        bobber = null;
        setSize(0.25F, 0.25F);
        ignoreFrustumCheck = true;
    }

    public EntityGrapplingHook(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    public EntityGrapplingHook(World world, EntityPlayer entityplayer)
    {
        this(world);
        owner = entityplayer;
        SDK_GrapplingHook.grapplingHooks.put(entityplayer, this);
        setLocationAndAngles(entityplayer.posX, (entityplayer.posY + 1.62D) - (double)entityplayer.yOffset, entityplayer.posZ, entityplayer.rotationYaw, entityplayer.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        posY -= 0.1D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        float f = 0.4F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI) * f;
        motionZ = MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI) * f;
        motionY = -MathHelper.sin((rotationPitch / 180F) * (float)Math.PI) * f;
        calculateVelocity(motionX, motionY, motionZ, 1.5F, 1.0F);
        startPosX = owner.posX;
        startPosZ = owner.posZ;
    }

    protected void entityInit()
    {
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    public boolean isInRangeToRenderDist(double d)
    {
        return true;
    }

    public void calculateVelocity(double d, double d1, double d2, float f, float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0075D * (double)f1;
        d1 += rand.nextGaussian() * 0.0075D * (double)f1;
        d2 += rand.nextGaussian() * 0.0075D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / Math.PI);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / Math.PI);
        ticksInGround = 0;
    }

    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation2(double d, double d1, double d2, float f, float f1, int i)
    {
        fishX = d;
        fishY = d1;
        fishZ = d2;
        fishYaw = f;
        fishPitch = f1;
        fishPosRotationIncrements = i;
        motionX = velocityX;
        motionY = velocityY;
        motionZ = velocityZ;
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    public void setVelocity(double d, double d1, double d2)
    {
        velocityX = motionX = d;
        velocityY = motionY = d1;
        velocityZ = motionZ = d2;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (fishPosRotationIncrements > 0)
        {
            double d = posX + (fishX - posX) / (double)fishPosRotationIncrements;
            double d1 = posY + (fishY - posY) / (double)fishPosRotationIncrements;
            double d2 = posZ + (fishZ - posZ) / (double)fishPosRotationIncrements;
            double d4;

            for (d4 = fishYaw - (double)rotationYaw; d4 < -180D; d4 += 360D) { }

            for (; d4 >= 180D; d4 -= 360D) { }

            rotationYaw += d4 / (double)fishPosRotationIncrements;
            rotationPitch += (fishPitch - (double)rotationPitch) / (double)fishPosRotationIncrements;
            fishPosRotationIncrements--;
            setPosition(d, d1, d2);
            setRotation(rotationYaw, rotationPitch);
            return;
        }

        if (!worldObj.isRemote)
        {
            if (owner == null)
            {
                setEntityDead();
                return;
            }

            ItemStack itemstack = owner.getCurrentEquippedItem();

            if (owner.isDead || !owner.isEntityAlive() || itemstack == null || itemstack.getItem() != SDK_GrapplingHook.itemGrapplingHook || getDistanceSqToEntity(owner) > 1024D)
            {
                this.setEntityDead();
                
                return;
            }

            if (bobber != null)
            {
                if (bobber.isDead)
                {
                    bobber = null;
                }
                else
                {
                    posX = bobber.posX;
                    posY = bobber.boundingBox.minY + (double)bobber.height * 0.8D;
                    posZ = bobber.posZ;
                    return;
                }
            }
        }

        if (inGround)
        {
            int i = worldObj.getBlockId(xTile, yTile, zTile);

            if (i != inTile)
            {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksInGround = 0;
                ticksInAir = 0;
            }
            else
            {
                ticksInGround++;

                if (ticksInGround == 1200)
                {
                    setEntityDead();
                }

                return;
            }
        }
        else
        {
            ticksInAir++;
        }
        
        Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
        Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.clip(vec3, vec31);
        vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
        vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        
        if (movingobjectposition != null)
        {
            vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        Entity entity = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
        double d4 = 0.0D;
        double d5;

        for (int j = 0; j < list.size(); ++j)
        {
            Entity entity1 = (Entity)list.get(j);

            if (entity1.canBeCollidedWith() && (entity1 != this.owner || this.ticksInAir >= 5))
            {
                float f = 0.3F;
                AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f, (double)f, (double)f);
                MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

                if (movingobjectposition1 != null)
                {
                    d5 = vec3.distanceTo(movingobjectposition1.hitVec);

                    if (d5 < d4 || d4 == 0.0D)
                    {
                        entity = entity1;
                        d4 = d5;
                    }
                }
            }
        }

        if (entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        if (movingobjectposition != null)
        {
            if (movingobjectposition.entityHit != null)
            {
                if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, owner), 0))
                {
                    bobber = movingobjectposition.entityHit;
                }
            }
            else
            {
                double d7 = motionX;
                double d6 = motionZ;
                xTile = movingobjectposition.blockX;
                yTile = movingobjectposition.blockY;
                zTile = movingobjectposition.blockZ;
                inTile = worldObj.getBlockId(xTile, yTile, zTile);
                motionX = (float)(movingobjectposition.hitVec.xCoord - posX);
                motionY = (float)(movingobjectposition.hitVec.yCoord - posY);
                motionZ = (float)(movingobjectposition.hitVec.zCoord - posZ);
                float f3 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                posX -= (motionX / (double)f3) * 0.05D;
                posY -= (motionY / (double)f3) * 0.05D;
                posZ -= (motionZ / (double)f3) * 0.05D;
                int l = yTile;

                if (movingobjectposition.hitVec.yCoord - (double)yTile == 0.125D && worldObj.getBlockId(xTile, yTile, zTile) == Block.snow.blockID)
                {
                    l--;
                }

                if ((movingobjectposition.hitVec.yCoord - (double)l == 1.0D && worldObj.getBlockId(xTile, l + 1, zTile) == 0 || movingobjectposition.hitVec.yCoord - (double)l == 1.125D && worldObj.getBlockId(xTile, l + 1, zTile) == Block.snow.blockID) && l + 1 < 128)
                {
                    if (d7 == 0.0D || d6 == 0.0D)
                    {
                        d5 = posX - startPosX;
                        d6 = posZ - startPosZ;
                    }

                    byte byte0 = ((byte)(d7 <= 0.0D ? -1 : 1));
                    byte byte1 = ((byte)(d6 <= 0.0D ? -1 : 1));
                    boolean flag = (worldObj.getBlockId(xTile - byte0, l, zTile) == 0 || worldObj.getBlockId(xTile - byte0, l, zTile) == Block.snow.blockID) && worldObj.getBlockId(xTile - byte0, l + 1, zTile) == 0;
                    boolean flag1 = (worldObj.getBlockId(xTile, l, zTile - byte1) == 0 || worldObj.getBlockId(xTile, l, zTile - byte1) == Block.snow.blockID) && worldObj.getBlockId(xTile, l + 1, zTile - byte1) == 0;
                    int l1 = xTile;
                    int i2 = l;
                    int j2 = zTile;
                    byte byte2 = 0;
                    boolean flag2 = false;

                    if (flag && !flag1 || flag && flag1 && Math.abs(d7) > Math.abs(d6))
                    {
                        l1 -= byte0;
                        flag2 = true;

                        if (byte0 > 0)
                        {
                            byte2 = 4;
                        }
                        else
                        {
                            byte2 = 5;
                        }
                    }
                    else if (!flag && flag1 || flag && flag1 && Math.abs(d7) <= Math.abs(d6))
                    {
                        j2 -= byte1;
                        flag2 = true;

                        if (byte1 > 0)
                        {
                            byte2 = 2;
                        }
                        else
                        {
                            byte2 = 3;
                        }
                    }

                    if (flag2)
                    {
                    	worldObj.setBlock(xTile, l + 1, zTile, SDK_GrapplingHook.blockGrapplingHook.blockID);
                        worldObj.setBlockMetadataWithNotify(xTile, l + 1, zTile, byte2, 3);
                        worldObj.setBlock(l1, i2, j2, SDK_GrapplingHook.blockRope.blockID);
                        worldObj.setBlockMetadataWithNotify(l1, i2, j2, byte2, 3);
                        
                        if (owner != null)
                        {
                            owner.destroyCurrentEquippedItem();
                        }

                        setEntityDead();
                    }
                    else
                    {
                        inGround = true;
                    }
                }
            }
        }

        if (inGround)
        {
            return;
        }

        moveEntity(motionX, motionY, motionZ);
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);

        for (rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / Math.PI); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }

        for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }

        for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }

        for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 0.92F;

        if (onGround || isCollidedHorizontally)
        {
            f1 = 0.5F;
        }

        int k = 5;
        double d7 = 0.0D;

        for (int i1 = 0; i1 < k; i1++)
        {
            double d10 = ((boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(i1 + 0)) / (double)k) - 0.125D) + 0.125D;
            double d11 = ((boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(i1 + 1)) / (double)k) - 0.125D) + 0.125D;
            AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(boundingBox.minX, d10, boundingBox.minZ, boundingBox.maxX, d11, boundingBox.maxZ);
        }

        if (d7 > 0.0D)
        {
            if (ticksCatchable > 0)
            {
                ticksCatchable--;
            }
            else if (rand.nextInt(500) == 0)
            {
                ticksCatchable = rand.nextInt(30) + 10;
                motionY -= 0.2D;
                worldObj.playSoundAtEntity(this, "random.splash", 0.25F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
                float f4 = MathHelper.floor_double(boundingBox.minY);

                for (int j1 = 0; (float)j1 < 1.0F + width * 20F; j1++)
                {
                    float f5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    float f7 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("bubble", posX + (double)f5, f4 + 1.0F, posZ + (double)f7, motionX, motionY - (double)(rand.nextFloat() * 0.2F), motionZ);
                }

                for (int k1 = 0; (float)k1 < 1.0F + width * 20F; k1++)
                {
                    float f6 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    float f8 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("splash", posX + (double)f6, f4 + 1.0F, posZ + (double)f8, motionX, motionY, motionZ);
                }
            }
        }

        if (ticksCatchable > 0)
        {
            motionY -= (double)(rand.nextFloat() * rand.nextFloat() * rand.nextFloat()) * 0.2D;
        }

        double d9 = d7 * 2D - 1.0D;
        motionY += 0.04D * d9;

        if (d7 > 0.0D)
        {
            f1 = (float)((double)f1 * 0.9D);
            motionY *= 0.8D;
        }

        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        setPosition(posX, posY, posZ);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)xTile);
        nbttagcompound.setShort("yTile", (short)yTile);
        nbttagcompound.setShort("zTile", (short)zTile);
        nbttagcompound.setByte("inTile", (byte)inTile);
        nbttagcompound.setByte("inGround", (byte)(inGround ? 1 : 0));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
        inTile = nbttagcompound.getByte("inTile") & 0xff;
        inGround = nbttagcompound.getByte("inGround") == 1;
    }
    
    public float getShadowSize()
    {
        return 0.0F;
    }

    public int catchFish()
    {
        byte byte0 = 0;

        if (bobber != null)
        {
            double d = owner.posX - posX;
            double d1 = owner.posY - posY;
            double d2 = owner.posZ - posZ;
            double d3 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
            double d4 = 0.1D;
            bobber.motionX += d * d4;
            bobber.motionY += d1 * d4 + (double)MathHelper.sqrt_double(d3) * 0.08D;
            bobber.motionZ += d2 * d4;
            byte0 = 3;
        }

        if (inGround)
        {
            byte0 = 2;
        }

        setEntityDead();
        return byte0;
    }

    /**
     * Will get destroyed next tick
     */
    public void setEntityDead()
    {
        super.setDead();
        SDK_GrapplingHook.grapplingHooks.remove(owner);
        owner = null;
    }
}
