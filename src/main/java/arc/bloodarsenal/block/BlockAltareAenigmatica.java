package arc.bloodarsenal.block;

import WayofTime.bloodmagic.api.altar.IBloodAltar;
import WayofTime.bloodmagic.api.orb.IBloodOrb;
import WayofTime.bloodmagic.client.IVariantProvider;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.tile.TileAltareAenigmatica;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockAltareAenigmatica extends Block implements IVariantProvider
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
        builder.put(EnumFacing.DOWN, new AxisAlignedBB(0, 0, 0, 1, 0.3125, 1));
        builder.put(EnumFacing.UP, new AxisAlignedBB(0, 0.6875, 0, 1, 1, 1));
        builder.put(EnumFacing.NORTH, new AxisAlignedBB(0, 0, 0, 1, 1, 0.3125));
        builder.put(EnumFacing.SOUTH, new AxisAlignedBB(0, 0, 0.6875, 1, 1, 1));
        builder.put(EnumFacing.EAST, new AxisAlignedBB(0.6875, 0, 0, 1, 1, 1));
        builder.put(EnumFacing.WEST, new AxisAlignedBB(0, 0, 0, 0.3125, 1, 1));

        BOUNDS = builder.build();
    }

    public BlockAltareAenigmatica(String name)
    {
        super(Material.ROCK);
        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 0);
        setSoundType(SoundType.METAL);

        this.setDefaultState(getBlockState().getBaseState().withProperty(FACING, EnumFacing.DOWN).withProperty(NORTH, false).withProperty(EAST, false).withProperty(NORTHEAST, false).withProperty(NORTHWEST, false));
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        checkFacingAltar(worldIn, pos);
        return super.eventReceived(state, worldIn, pos, id, param);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileAltareAenigmatica();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldStack, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileAltareAenigmatica)
        {
            checkFacingAltar(world, pos);
            if (player.getHeldItemMainhand().getItem() instanceof IBloodOrb)
                return ((TileAltareAenigmatica) tile).setLinkedOrbOwner(player);
        }

        player.openGui(BloodArsenal.INSTANCE, Constants.Gui.ALTARE_AENIGMATICA_GUI, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockState)
    {
        TileAltareAenigmatica tile = (TileAltareAenigmatica) world.getTileEntity(pos);
        if (tile != null)
            tile.dropItems();

        super.breakBlock(world, pos, blockState);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileAltareAenigmatica)
            checkFacingAltar(world, pos);
    }

    private void checkFacingAltar(IBlockAccess world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileAltareAenigmatica)
        {
            BlockPos altarPos = pos.offset(world.getBlockState(pos).getValue(FACING).getOpposite());
            if (world.getTileEntity(altarPos) instanceof IBloodAltar)
                ((TileAltareAenigmatica) tile).setAltarPos(altarPos);
            else
                ((TileAltareAenigmatica) tile).setAltarPos(BlockPos.ORIGIN);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
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

        return this.getDefaultState().withProperty(FACING, face);
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
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, ItemStack itemStack)
    {
        return this.getDefaultState().withProperty(FACING, facing.getOpposite());
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDS.get(state.getValue(FACING));
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
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "normal"));
        return ret;
    }
}
