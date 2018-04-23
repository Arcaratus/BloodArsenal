package arcaratus.bloodarsenal.item;

import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public class ItemGem extends Item implements IVariantProvider
{
    protected final String tooltipBase;
    private final String name;

    public ItemGem(String name)
    {
        super();
        setUnlocalizedName(BloodArsenal.MOD_ID + ".gem" + WordUtils.capitalize(name));
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);

        this.name = name;
        this.tooltipBase = "tooltip.bloodarsenal.gem" + WordUtils.capitalize(name) + ".";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (I18n.hasKey(tooltipBase + "desc"))
            tooltip.add(TextHelper.localizeEffect(tooltipBase + "desc"));

        super.addInformation(stack, world, tooltip, flag);
    }

//    @Override
//    public List<Pair<Integer, String>> getVariants()
//    {
//        List<Pair<Integer, String>> ret = new ArrayList<>();
//        ret.add(new ImmutablePair<>(0, "type=" + name));
//        return ret;
//    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }
}