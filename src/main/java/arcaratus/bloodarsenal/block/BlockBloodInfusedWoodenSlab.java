package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.block.BlockBloodInfusedWoodenLog.EnumType;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import arcaratus.bloodarsenal.item.block.ItemBlockBloodInfusedWoodenSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockBloodInfusedWoodenSlab extends BlockSlab implements IVariantProvider, IBABlock
{
    public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

    public BlockBloodInfusedWoodenSlab(String name)
    {
        super(Material.WOOD, EnumType.BLOOD_INFUSED.getMapColor());

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setHardness(3.0F);
        setResistance(6.0F);
        setHarvestLevel("axe", 0);
        setSoundType(SoundType.WOOD);
        useNeighborBrightness = true;

        IBlockState iblockstate = blockState.getBaseState();

        if (!isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }

        setDefaultState(iblockstate.withProperty(VARIANT, Variant.DEFAULT));
    }

    @Override
    public ItemBlock getItemBlock(Block block)
    {
        return new ItemBlockBloodInfusedWoodenSlab();
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_SLAB);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_SLAB);
    }

    @Override
    public String getTranslationKey(int meta)
    {
        return super.getTranslationKey();
    }

    @Override
    public IProperty<?> getVariantProperty()
    {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return Variant.DEFAULT;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState blockState = getDefaultState().withProperty(VARIANT, Variant.DEFAULT);
        return isDouble() ? blockState : blockState.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (!isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (isDouble())
        {
            return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        }
        else if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side))
        {
            return false;
        }
        else if (false) // Forge: Additional logic breaks doesSideBlockRendering and is no longer useful.
        {
            IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
            boolean flag = isHalfSlab(iblockstate) && iblockstate.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;
            boolean flag1 = isHalfSlab(blockState) && blockState.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;

            if (flag1)
            {
                if (side == EnumFacing.DOWN)
                {
                    return true;
                }
                else if (side == EnumFacing.UP && super.shouldSideBeRendered(blockState, blockAccess, pos, side))
                {
                    return true;
                }
                else
                {
                    return !isHalfSlab(iblockstate) || !flag;
                }
            }
            else if (side == EnumFacing.UP)
            {
                return true;
            }
            else if (side == EnumFacing.DOWN && super.shouldSideBeRendered(blockState, blockAccess, pos, side))
            {
                return true;
            }
            else
            {
                return !isHalfSlab(iblockstate) || flag;
            }
        }
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @SideOnly(Side.CLIENT)
    protected static boolean isHalfSlab(IBlockState state)
    {
        Block block = state.getBlock();
        return block == RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_SLAB;
    }

    public static class Double extends BlockBloodInfusedWoodenSlab
    {
        public Double(String name)
        {
            super(name);
        }

        public boolean isDouble()
        {
            return true;
        }
    }

    public static class Half extends BlockBloodInfusedWoodenSlab
    {
        public Half(String name)
        {
            super(name);
        }

        public boolean isDouble()
        {
            return false;
        }
    }

    public enum Variant implements IStringSerializable
    {
        DEFAULT;

        public String getName()
        {
            return "default";
        }
    }
}
