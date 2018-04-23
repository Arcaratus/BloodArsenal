package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.block.base.BlockEnum;
import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.block.enums.EnumSlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSlate extends BlockEnum<EnumSlate> implements IVariantProvider, IBABlock
{
    public BlockSlate(String name)
    {
        super(Material.ROCK, EnumSlate.class);

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name + ".");
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setSoundType(SoundType.STONE);
        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
    }
}
