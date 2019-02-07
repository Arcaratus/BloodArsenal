package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;

public class BlockBloodInfusedWoodenStairs extends BlockStairs implements IVariantProvider, IBABlock
{
    public BlockBloodInfusedWoodenStairs(String name)
    {
        super(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_WOODEN_PLANKS.getDefaultState());

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(3.0F);
        setResistance(4.0F);
        setHarvestLevel("axe", 0);
        setSoundType(SoundType.WOOD);
        useNeighborBrightness = true;
    }

    @Override
    public ItemBlock getItem()
    {
        return new ItemBlock(this);
    }
}
