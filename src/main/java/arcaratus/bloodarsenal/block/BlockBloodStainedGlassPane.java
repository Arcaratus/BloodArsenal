package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBloodStainedGlassPane extends BlockPane implements IVariantProvider, IBABlock
{
    public BlockBloodStainedGlassPane(String name)
    {
        super(Material.GLASS, false);

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(1.0F);
        setResistance(3.0F);
        setHarvestLevel("axe", 0);
        setSoundType(SoundType.GLASS);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public ItemBlock getItem()
    {
        return new ItemBlock(this);
    }
}
