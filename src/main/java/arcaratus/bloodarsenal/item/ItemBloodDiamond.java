package arcaratus.bloodarsenal.item;

import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.item.ItemEnum;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.item.types.EnumDiamondTypes;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBloodDiamond extends ItemEnum.Variant<EnumDiamondTypes> implements IVariantProvider
{
    public ItemBloodDiamond(String name)
    {
        super(EnumDiamondTypes.class, "");

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (I18n.hasKey("tooltip.bloodarsenal.blood_diamond." + EnumDiamondTypes.values()[stack.getItemDamage()].getInternalName() + ".desc"))
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.blood_diamond." + EnumDiamondTypes.values()[stack.getItemDamage()].getInternalName() +".desc"));

        super.addInformation(stack, world, tooltip, flag);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }
}
