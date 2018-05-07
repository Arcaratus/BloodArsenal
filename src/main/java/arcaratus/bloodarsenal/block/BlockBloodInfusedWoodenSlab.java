package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.block.BlockBloodInfusedWoodenLog.EnumType;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockBloodInfusedWoodenSlab extends BlockSlab implements IVariantProvider, IBABlock
{
    public static final PropertyEnum<BlockBloodInfusedWoodenLog.EnumType> VARIANT = PropertyEnum.create("variant", BlockBloodInfusedWoodenLog.EnumType.class);

    public BlockBloodInfusedWoodenSlab(String name)
    {
        super(Material.WOOD, EnumType.BLOOD_INFUSED.getMapColor());

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
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

        setDefaultState(iblockstate.withProperty(VARIANT, BlockBloodInfusedWoodenLog.EnumType.BLOOD_INFUSED));
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
    public String getUnlocalizedName(int meta)
    {
        return super.getUnlocalizedName();
    }

    @Override
    public IProperty getVariantProperty()
    {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockBloodInfusedWoodenLog.EnumType.BLOOD_INFUSED;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState blockState = getDefaultState().withProperty(VARIANT, BlockBloodInfusedWoodenLog.EnumType.byMetadata(meta & 7));
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
        return isDouble() ? new BlockStateContainer(this, new IProperty[]{VARIANT}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    public ItemBlock getItem()
    {
        return new ItemBlock(this);
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
            setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        }

        public boolean isDouble()
        {
            return false;
        }
    }
}
