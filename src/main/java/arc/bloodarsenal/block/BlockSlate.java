package arc.bloodarsenal.block;

import WayofTime.bloodmagic.block.base.BlockString;
import WayofTime.bloodmagic.client.IVariantProvider;
import arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class BlockSlate extends BlockString implements IVariantProvider
{
    public static final String[] names = { "blank", "reinforced", "imbued", "demonic", "ethereal" };

    public BlockSlate(String name)
    {
        super(Material.ROCK, names);

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name + ".");
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setSoundType(SoundType.STONE);
        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        for (int i = 0; i < names.length; i++)
            ret.add(new ImmutablePair<>(i, "type=" + names[i]));
        return ret;
    }
}
