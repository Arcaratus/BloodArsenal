package arc.bloodarsenal.block;

import WayofTime.bloodmagic.block.base.BlockString;
import WayofTime.bloodmagic.client.IVariantProvider;
import arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class BlockBloodInfusedWood extends BlockString implements IVariantProvider
{
    public static final String[] names = { "log", "planks" };

    public BlockBloodInfusedWood()
    {
        super(Material.wood, names);

        setUnlocalizedName(BloodArsenal.MOD_ID + ".blood_infused_wood.");
        setCreativeTab(BloodArsenal.tabBloodArsenal);
        setSoundType(SoundType.WOOD);
        setHardness(3.0F);
        setResistance(4.0F);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        for (int i = 0; i < names.length; i++)
            ret.add(new ImmutablePair<Integer, String>(i, "type=" + names[i]));
        return ret;
    }
}
