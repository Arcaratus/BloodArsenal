package arc.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class BlockBloodArsenalBase extends Block implements IVariantProvider
{
    public BlockBloodArsenalBase(String name, Material material, boolean inCreativeTabs)
    {
        super(material);

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        if (inCreativeTabs)
            setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }

    public BlockBloodArsenalBase(String name, Material material)
    {
        this(name, material, true);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "normal"));
        return ret;
    }
}
