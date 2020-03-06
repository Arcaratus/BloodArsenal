package arcaratus.bloodarsenal.block;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.util.IComplexVariantProvider;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockBloodBurnedString extends BlockTripWire implements IComplexVariantProvider, IBABlock
{
    public BlockBloodBurnedString(String name)
    {
        super();

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return RegistrarBloodArsenalItems.BLOOD_BURNED_STRING;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(RegistrarBloodArsenalItems.BLOOD_BURNED_STRING);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(NORTH, isConnectedTo(worldIn, pos, EnumFacing.NORTH)).withProperty(EAST, isConnectedTo(worldIn, pos, EnumFacing.EAST)).withProperty(SOUTH, isConnectedTo(worldIn, pos, EnumFacing.SOUTH)).withProperty(WEST, isConnectedTo(worldIn, pos, EnumFacing.WEST));
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.setBlockState(pos, state, 3);
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!worldIn.isRemote)
        {
            if (!state.getValue(POWERED))
                updateState(worldIn, pos);

            Random rand = new Random();
            if (rand.nextInt(5) == 4)
                entityIn.setFire(3);
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (worldIn.getBlockState(pos).getValue(POWERED))
                updateState(worldIn, pos);
        }
    }

    private void updateState(World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        boolean flag = iblockstate.getValue(POWERED);
        boolean flag1 = false;
        List<? extends Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(null, iblockstate.getBoundingBox(worldIn, pos).offset(pos));

        if (!list.isEmpty())
        {
            for (Entity entity : list)
            {
                if (!entity.doesEntityNotTriggerPressurePlate())
                {
                    flag1 = true;
                    break;
                }
            }
        }

        if (flag1 != flag)
        {
            iblockstate = iblockstate.withProperty(POWERED, flag1);
            worldIn.setBlockState(pos, iblockstate, 3);
        }

        if (flag1)
            worldIn.scheduleUpdate(new BlockPos(pos), this, tickRate(worldIn));
    }

    private static boolean isConnectedTo(IBlockAccess worldIn, BlockPos pos, EnumFacing direction)
    {
        return worldIn.getBlockState(pos.offset(direction)).getBlock() == RegistrarBloodArsenalBlocks.BLOCK_BLOOD_BURNED_STRING;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IProperty[] getIgnoredProperties()
    {
        return new IProperty[]{DISARMED, POWERED};
    }
}
