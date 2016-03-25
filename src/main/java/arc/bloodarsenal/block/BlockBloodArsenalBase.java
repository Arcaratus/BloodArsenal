package arc.bloodarsenal.block;

import arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBloodArsenalBase extends Block
{
    public BlockBloodArsenalBase(String name, Material material, boolean inCreativeTabs)
    {
        super(material);

        setUnlocalizedName(BloodArsenal.MOD_ID + name);
        if (inCreativeTabs)
            setCreativeTab(BloodArsenal.tabBloodArsenal);
    }

    public BlockBloodArsenalBase(String name, Material material)
    {
        this(name, material, true);
    }
}
