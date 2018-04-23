package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.PlayerSacrificeHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class BlockGlassShards extends Block implements IVariantProvider, IBABlock
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool NORTHEAST = PropertyBool.create("northeast");
    public static final PropertyBool NORTHWEST = PropertyBool.create("northwest");

    private static final ImmutableMap<EnumFacing, AxisAlignedBB> BOUNDS;

    static
    {
        ImmutableMap.Builder<EnumFacing, AxisAlignedBB> builder = ImmutableMap.builder();
        builder.put(EnumFacing.DOWN, new AxisAlignedBB(0.125, 0, 0.125, 0.875, 0.25, 0.875));
        builder.put(EnumFacing.UP, new AxisAlignedBB(0.125, 0.75, 0.125, 0.875, 1, 0.875));
        builder.put(EnumFacing.NORTH, new AxisAlignedBB(0.125, 0.125, 0, 0.875, 0.875, 0.25));
        builder.put(EnumFacing.SOUTH, new AxisAlignedBB(0.125, 0.125, 0.75, 0.875, 0.875, 1));
        builder.put(EnumFacing.EAST, new AxisAlignedBB(0.75, 0.125, 0.125, 1, 0.875, 0.875));
        builder.put(EnumFacing.WEST, new AxisAlignedBB(0, 0.125, 0.125, 0.25, 0.875, 0.875));

        BOUNDS = builder.build();
    }

    public BlockGlassShards(String name)
    {
        super(Material.GLASS);

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(0.75F);
        setSoundType(SoundType.GLASS);

        setDefaultState(getBlockState().getBaseState().withProperty(FACING, EnumFacing.DOWN).withProperty(NORTH, false).withProperty(EAST, false).withProperty(NORTHEAST, false).withProperty(NORTHWEST, false));
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(RegistrarBloodArsenalItems.GLASS_SHARD, RANDOM.nextInt(6)));

        return list;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, NORTH, EAST, NORTHEAST, NORTHWEST);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (meta >= EnumFacing.values().length)
        {
            meta = EnumFacing.DOWN.ordinal();
        }

        EnumFacing face = EnumFacing.values()[meta];

        return getDefaultState().withProperty(FACING, face);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).ordinal();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);

        int off = -facing.ordinal() % 2;

        EnumFacing face1 = EnumFacing.values()[(facing.ordinal() + 2) % 6];
        EnumFacing face2 = EnumFacing.values()[(facing.ordinal() + 4 + off) % 6];

        // North/East Connector
        IBlockState north = worldIn.getBlockState(pos.offset(face1));
        IBlockState east = worldIn.getBlockState(pos.offset(face2));

        if (north.getBlock() == this && north.getValue(FACING) == facing)
        {
            state = state.withProperty(NORTH, true);
        }
        if (east.getBlock() == this && east.getValue(FACING) == facing)
        {
            state = state.withProperty(EAST, true);
        }

        // Diagonal connections
        IBlockState northeast = worldIn.getBlockState(pos.offset(face1).offset(face2));
        IBlockState northwest = worldIn.getBlockState(pos.offset(face1).offset(face2.getOpposite()));

        if (northeast.getBlock() == this && northeast.getValue(FACING) == facing)
        {
            state = state.withProperty(NORTHEAST, true);
        }
        if (northwest.getBlock() == this && northwest.getValue(FACING) == facing)
        {
            state = state.withProperty(NORTHWEST, true);
        }

        return state;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        EnumFacing enumfacing = facing.getOpposite();

        return getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return worldIn.isSideSolid(pos.offset(side.getOpposite()), side, true);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        EnumFacing facing = state.getValue(FACING);

        if (!worldIn.isSideSolid(pos.offset(facing), facing.getOpposite(), true))
        {
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDS.get(state.getValue(FACING));
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn instanceof EntityLivingBase)
        {
            double damage = ConfigHandler.values.glassShardsDamage;

            if (entityIn.fallDistance > 0)
            {
                damage += entityIn.fallDistance * 1.5 + 1;
            }

            if (entityIn.attackEntityFrom(BloodArsenal.getDamageSourceGlass(), (float) damage))
                PlayerSacrificeHelper.findAndFillAltar(worldIn, (EntityLivingBase) entityIn, (int) Math.round(damage * RANDOM.nextInt(101)), false);
//            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModPotions.BLEEDING, RANDOM.nextInt(100), 0));
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public ItemBlock getItem()
    {
        return new ItemBlock(this);
    }
}
