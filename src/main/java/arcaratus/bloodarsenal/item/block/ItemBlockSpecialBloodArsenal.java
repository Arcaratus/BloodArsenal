package arcaratus.bloodarsenal.item.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockSpecial;

import javax.annotation.Nonnull;

public class ItemBlockSpecialBloodArsenal extends ItemBlockSpecial implements IVariantProvider
{
    public ItemBlockSpecialBloodArsenal(String name, Block block)
    {
        super(block);

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }

    @Override
    public void gatherVariants(@Nonnull Int2ObjectMap<String> variants)
    {
        variants.put(0, "type=normal");
    }
}
