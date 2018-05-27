package arcaratus.bloodarsenal.item;

import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.item.ItemEnum;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.item.types.EnumGemTypes;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemGem extends ItemEnum.Variant<EnumGemTypes> implements IVariantProvider
{
    public ItemGem(String name)
    {
        super(EnumGemTypes.class, "");

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }
}
