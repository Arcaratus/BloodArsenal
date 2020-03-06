package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

public class BlockBloodInfusedWoodenStairs extends BlockStairs implements IVariantProvider, IBABlock
{
    public BlockBloodInfusedWoodenStairs(IBlockState blockState, String name)
    {
        super(blockState);

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(3.0F);
        setResistance(4.0F);
        setHarvestLevel("axe", 0);
        setSoundType(SoundType.WOOD);
        useNeighborBrightness = true;
    }
}
