package arc.bloodarsenal.entity.projectile;

import WayofTime.bloodmagic.api.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public class EntitySummonedSword extends EntityThrowable implements IThrowableEntity, IEntityAdditionalSpawnData
{
    public EntityPlayer shootingEntity;
    protected int ticksInAir = 0;
    protected int maxTicksInAir = 6;

    private ItemStack stack;

    public EntitySummonedSword(World world)
    {
        super(world);
        setSize(0.6F, 1F);
    }

    public EntitySummonedSword(World world, double x, double y, double z)
    {
        this(world);
        setPosition(x, y, z);
    }

    public EntitySummonedSword(World world, EntityPlayer player)
    {
        super(world, player);
        shootingEntity = player;
        float par3 = 0.8F;
        setSize(0.6F, 1F);
        setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
        posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        posY -= 0.2D;
        posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        setPosition(posX, posY, posZ);
        motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
        motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
        motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);
        setThrowableHeading(motionX, motionY, motionZ, par3 * 1.5F, 2.0F);
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0F;
    }

    @Override
    public void setThrowableHeading(double var1, double var3, double var5, float var7, float var8)
    {
        float var9 = MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);
        var1 /= var9;
        var3 /= var9;
        var5 /= var9;
        var1 += rand.nextGaussian() * 0.007499999832361937D * var8;
        var3 += rand.nextGaussian() * 0.007499999832361937D * var8;
        var5 += rand.nextGaussian() * 0.007499999832361937D * var8;
        var1 *= var7;
        var3 *= var7;
        var5 *= var7;
        motionX = var1;
        motionY = var3;
        motionZ = var5;
        float var10 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
        prevRotationYaw = rotationYaw = (float) (Math.atan2(var1, var5) * 180.0D / Math.PI);
        prevRotationPitch = rotationPitch = (float) (Math.atan2(var3, var10) * 180.0D / Math.PI);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if (getStack() != null && getStack() != shootingEntity.getHeldItemMainhand() || this.ticksExisted > this.maxTicksInAir)
        {
            setDead();
        }
    }

    @Override
    protected void onImpact(RayTraceResult mop)
    {
        this.onImpact(mop.entityHit);
        this.setDead();
    }

    protected void onImpact(Entity mop)
    {
        if (mop == shootingEntity && ticksInAir > 3)
        {
            shootingEntity.attackEntityFrom(DamageSource.causeMobDamage(shootingEntity), 10);
            this.setDead();
        } else
        {
            if (mop instanceof EntityLivingBase)
            {
                ((EntityLivingBase) mop).setRevengeTarget(shootingEntity);
                shootingEntity.attackTargetEntityWithCurrentItem(mop);
            }
        }

        // spawnHitParticles("magicCrit", 8);
//        worldObj.createExplosion(mop, posX, posY, posZ, 1F, false);
        this.setDead();
    }

    protected void doDamage(float i, Entity mop)
    {
        mop.attackEntityFrom(this.getDamageSource(), i);
    }

    public DamageSource getDamageSource()
    {
        return DamageSource.causeMobDamage(shootingEntity);
    }

    @Override
    public void writeSpawnData(ByteBuf data)
    {
        data.writeByte(this.ticksInAir);
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        this.ticksInAir = data.readByte();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setInteger(Constants.NBT.PROJECTILE_TICKS_IN_AIR, ticksInAir);
        nbt.setInteger(Constants.NBT.PROJECTILE_MAX_TICKS_IN_AIR, maxTicksInAir);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        ticksInAir = nbt.getInteger(Constants.NBT.PROJECTILE_TICKS_IN_AIR);
        maxTicksInAir = nbt.getInteger(Constants.NBT.PROJECTILE_MAX_TICKS_IN_AIR);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    public void setThrower(Entity entity)
    {
        if (entity instanceof EntityLivingBase)
            this.shootingEntity = (EntityPlayer) entity;
    }

    public ItemStack getStack()
    {
        return stack;
    }

    public void setStack(ItemStack stack)
    {
        this.stack = stack;
    }
}
