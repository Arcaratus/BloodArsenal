package arcaratus.bloodarsenal.item.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.item.ItemBlockSpecial;

import javax.annotation.Nonnull;

public class ItemBlockBloodBurnedString extends ItemBlockSpecial implements IVariantProvider
{
    public ItemBlockBloodBurnedString(String name)
    {
        super(RegistrarBloodArsenalBlocks.BLOCK_BLOOD_BURNED_STRING);

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
