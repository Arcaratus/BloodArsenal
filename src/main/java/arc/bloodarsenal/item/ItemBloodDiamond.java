package arc.bloodarsenal.item;

import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ItemBloodDiamond extends Item implements IVariantProvider
{
    public static String[] names = { "normal", "inert", "infused", "bound" };

    public ItemBloodDiamond(String name)
    {
        super();

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name + ".");
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName(stack) + names[stack.getItemDamage()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List<ItemStack> list)
    {
        for (int i = 0; i < names.length; i++)
            list.add(new ItemStack(id, 1, i));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (I18n.hasKey("tooltip.BloodArsenal.bloodDiamond." + names[stack.getItemDamage()] + ".desc"))
            tooltip.add(TextHelper.localizeEffect("tooltip.BloodArsenal.bloodDiamond." + names[stack.getItemDamage()] +".desc"));

        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "type=normal"));
        ret.add(new ImmutablePair<>(1, "type=inert"));
        ret.add(new ImmutablePair<>(2, "type=infused"));
        ret.add(new ImmutablePair<>(3, "type=bound"));
        return ret;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }
}
