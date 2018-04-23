package arcaratus.bloodarsenal.item.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockSpecial;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ItemBlockSpecialBloodArsenal extends ItemBlockSpecial implements IVariantProvider
{
    private final String name;

    public ItemBlockSpecialBloodArsenal(String name, Block block)
    {
        super(block);

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);

        this.name = name;
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "type=" + name));
        return ret;
    }
}
