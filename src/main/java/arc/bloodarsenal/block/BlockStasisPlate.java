package arc.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.ModBlocks;
import arc.bloodarsenal.tile.TileInventory;
import arc.bloodarsenal.tile.TileStasisPlate;
import arc.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockStasisPlate extends Block implements IVariantProvider
{
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.1875, 1);

    public BlockStasisPlate(String name)
    {
        super(Material.GLASS);
        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 0);
        setSoundType(SoundType.GLASS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if (this == ModBlocks.STASIS_PLATE)
        {
            if (blockState != iblockstate)
                return true;

            if (block == this)
                return false;
        }

        return block != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return AABB;
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
        return new TileStasisPlate();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldStack, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (player.isSneaking())
            return false;

        if (tile instanceof TileInventory)
            BloodArsenalUtils.insertItemIntoTile(player, player.getHeldItemMainhand() != null ? player.getHeldItemMainhand().stackSize : 0, (TileInventory) tile);

        world.notifyBlockUpdate(pos, state, state, 3);
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockState)
    {
        TileStasisPlate tile = (TileStasisPlate) world.getTileEntity(pos);
        if (tile != null)
            tile.dropItems();

        super.breakBlock(world, pos, blockState);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
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
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "normal"));
        return ret;
    }
}
