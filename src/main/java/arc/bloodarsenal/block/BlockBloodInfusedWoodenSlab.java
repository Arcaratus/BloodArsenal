package arc.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.ModBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public abstract class BlockBloodInfusedWoodenSlab extends BlockSlab implements IVariantProvider
{
    public static final PropertyEnum<BlockBloodInfusedWoodenLog.EnumType> VARIANT = PropertyEnum.create("variant", BlockBloodInfusedWoodenLog.EnumType.class);

    public BlockBloodInfusedWoodenSlab()
    {
        super(Material.WOOD);

        setHardness(3.0F);
        setResistance(6.0F);
        setHarvestLevel("axe", 0);
        setSoundType(SoundType.WOOD);
        useNeighborBrightness = true;

        IBlockState iblockstate = this.blockState.getBaseState();

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(iblockstate.withProperty(VARIANT, BlockBloodInfusedWoodenLog.EnumType.BLOOD_INFUSED));
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        list.add(new ItemStack(itemIn, 1, 0));
    }

    @Override
    public MapColor getMapColor(IBlockState state)
    {
        return state.getValue(VARIANT).getMapColor();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.BLOOD_INFUSED_WOODEN_SLAB);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ModBlocks.BLOOD_INFUSED_WOODEN_SLAB);
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
        IBlockState blockState = this.getDefaultState().withProperty(VARIANT, BlockBloodInfusedWoodenLog.EnumType.byMetadata(meta & 7));
        return this.isDouble() ? blockState : blockState.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[]{VARIANT}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(VARIANT).getMetadata();
    }

    public static class BlockBloodInfusedWoodenDoubleSlab extends BlockBloodInfusedWoodenSlab
    {
        public boolean isDouble()
        {
            return true;
        }
    }

    public static class BlockBloodInfusedWoodenHalfSlab extends BlockBloodInfusedWoodenSlab
    {
        public boolean isDouble()
        {
            return false;
        }
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "normal"));
        return ret;
    }
}
