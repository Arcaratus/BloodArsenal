package arcaratus.bloodarsenal.item;

import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemBloodOrange extends ItemFood implements IVariantProvider
{
    public ItemBloodOrange(String name)
    {
        super(4, 2.0F, false);

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setAlwaysEdible();
        setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 80), 0.5F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.blood_orange.desc"));

        super.addInformation(stack, world, tooltip, flag);
    }

    @Override
    public void gatherVariants(@Nonnull Int2ObjectMap<String> variants)
    {
        variants.put(0, "type=normal");
    }
}
