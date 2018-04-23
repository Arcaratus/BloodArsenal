package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBloodInfusedWoodenLog extends BlockLog implements IVariantProvider, IBABlock
{
    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class, apply -> true);

    public BlockBloodInfusedWoodenLog(String name)
    {
        super();

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(3.0F);
        setResistance(6.0F);
        setHarvestLevel("axe", 0);

        setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumType.BLOOD_INFUSED).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        // Override method that actually does something with nothing
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(LOG_AXIS, BlockLog.EnumAxis.values()[meta >> 2]).withProperty(VARIANT, EnumType.BLOOD_INFUSED);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    @SuppressWarnings("incomplete-switch")
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(VARIANT).getMetadata();

        switch (state.getValue(LOG_AXIS))
        {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case NONE:
                i |= 12;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{VARIANT, LOG_AXIS});
    }

//    @Override
//    protected ItemStack createStackedBlock(IBlockState state)
//    {
//        return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(VARIANT).getMetadata());
//    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public ItemBlock getItem()
    {
        return new ItemBlock(this);
    }

    public enum EnumType implements IStringSerializable
    {
        BLOOD_INFUSED(0, "blood_infused", MapColor.RED);

        private static final EnumType[] META_LOOKUP = new EnumType[values().length];
        private final int meta;
        private final String name;
        private final MapColor mapColor;

        EnumType(int metaIn, String nameIn, MapColor mapColorIn)
        {
            this.meta = metaIn;
            this.name = nameIn;
            this.mapColor = mapColorIn;
        }

        public int getMetadata()
        {
            return meta;
        }

        public MapColor getMapColor()
        {
            return mapColor;
        }

        @Override
        public String toString()
        {
            return name;
        }

        public static EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        @Override
        public String getName()
        {
            return name;
        }

        static
        {
            for (EnumType type : values())
            {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
    }
}
