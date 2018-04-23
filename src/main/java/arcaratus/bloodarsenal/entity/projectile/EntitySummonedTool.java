package arcaratus.bloodarsenal.entity.projectile;

import arcaratus.bloodarsenal.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import arcaratus.bloodarsenal.util.Vector3;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public class EntitySummonedTool extends EntityThrowable implements IThrowableEntity, IEntityAdditionalSpawnData
{
    public EntityPlayer player;
    protected int ticksInAir = 0;
    protected int maxTicksInAir = 16;
    protected int delay = 10;

    private ItemStack stack = ItemStack.EMPTY;

    // This constructor is needed here
    public EntitySummonedTool(World world)
    {
        super(world);
        setSize(0.6F, 1F);
    }

    public EntitySummonedTool(World world, double x, double y, double z)
    {
        this(world);
        setPosition(x, y, z);
    }

    public EntitySummonedTool(World world, EntityPlayer player, ItemStack stack)
    {
        super(world, player);
        this.player = player;
        this.stack = stack;
        setSize(0.6F, 1F);
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0F;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!getEntityWorld().isRemote && (player == null || player.isDead))
        {
            setDead();
            return;
        }

        if (ticksExisted < delay)
        {
            motionX = motionY = motionZ = 0;
        }
        else
        {
            Vector3 playerLook;
            RayTraceResult look = BloodArsenalUtils.rayTrace(getEntityWorld(), player, true);
            if (look == null)
                playerLook = new Vector3(player.getLookVec()).multiply(64).add(Vector3.fromEntity(player));
            else
                playerLook = new Vector3(look.getBlockPos().getX() + 0.5, look.getBlockPos().getY() + 0.5, look.getBlockPos().getZ() + 0.5);

            Vector3 thisVec = Vector3.fromEntityCenter(this);
            Vector3 motionVec = playerLook.subtract(thisVec).normalize().multiply(0.5);

            motionX = motionVec.x;
            motionY = motionVec.y;
            motionZ = motionVec.z;
        }

        if ((!ItemStack.areItemsEqualIgnoreDurability(getStack(), player.getHeldItemMainhand())) || ticksExisted > (maxTicksInAir + delay))
        {
            setDead();
        }
    }

    @Override
    protected void onImpact(RayTraceResult mop)
    {
        if (mop.entityHit != player)
        {
            if (mop.entityHit instanceof EntityLivingBase)
            {
                ((EntityLivingBase) mop.entityHit).setRevengeTarget(player);
                player.attackTargetEntityWithCurrentItem(mop.entityHit);
            }
            else if (mop.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                IBlockState block = getEntityWorld().getBlockState(mop.getBlockPos());

                if (BloodArsenalUtils.canItemBreakBlock(player, getStack(), block) && block.getBlockHardness(getEntityWorld(), mop.getBlockPos()) != -1F)
                {
                    getEntityWorld().destroyBlock(mop.getBlockPos(), true);
                }
            }

            setDead();
        }
    }

    @Override
    public void writeSpawnData(ByteBuf data)
    {
        data.writeByte(ticksInAir);
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        ticksInAir = data.readByte();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setInteger(Constants.NBT.PROJECTILE_TICKS_IN_AIR, ticksInAir);
        nbt.setInteger(Constants.NBT.PROJECTILE_MAX_TICKS_IN_AIR, maxTicksInAir);
        nbt.setInteger(Constants.NBT.DELAY, delay);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        ticksInAir = nbt.getInteger(Constants.NBT.PROJECTILE_TICKS_IN_AIR);
        maxTicksInAir = nbt.getInteger(Constants.NBT.PROJECTILE_MAX_TICKS_IN_AIR);
        delay = nbt.getInteger(Constants.NBT.DELAY);
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
            player = (EntityPlayer) entity;
    }

    public ItemStack getStack()
    {
        return stack;
    }
}
