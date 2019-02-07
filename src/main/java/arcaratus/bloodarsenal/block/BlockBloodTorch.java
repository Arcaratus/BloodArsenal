package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;

public class BlockBloodTorch extends BlockTorch implements IVariantProvider, IBABlock
{
    public BlockBloodTorch(String name)
    {
        super();

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(0.0F);
        setLightLevel(1.0F);
        setTickRandomly(true);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public ItemBlock getItem()
    {
        return new ItemBlock(this);
    }
}
