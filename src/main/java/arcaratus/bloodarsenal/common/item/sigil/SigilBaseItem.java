package arcaratus.bloodarsenal.common.item.sigil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wayoftime.bloodmagic.common.item.ItemSigil;
import wayoftime.bloodmagic.common.item.sigil.ItemSigilToggleable;

import java.util.List;

public class SigilBaseItem extends ItemSigil
{
    public SigilBaseItem(Properties properties, int lpUsed)
    {
        super(properties, lpUsed);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        tooltip.add(new TranslationTextComponent("tooltip.bloodarsenal." + getRegistryName().getPath() + ".desc").mergeStyle(TextFormatting.ITALIC).mergeStyle(TextFormatting.GRAY));

        super.addInformation(stack, world, tooltip, flag);
    }

    public static class Toggleable extends ItemSigilToggleable
    {
        public Toggleable(Properties properties, int lpUsed)
        {
            super(properties, lpUsed);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
        {
            tooltip.add(new TranslationTextComponent("tooltip.bloodarsenal." + getRegistryName().getPath() + ".desc").mergeStyle(TextFormatting.ITALIC).mergeStyle(TextFormatting.GRAY));

            super.addInformation(stack, world, tooltip, flag);
            if (!stack.hasTag())
                return;

            tooltip.add(new TranslationTextComponent("tooltip.bloodmagic." + (getActivated(stack) ? "activated"
                    : "deactivated")).mergeStyle(TextFormatting.GRAY));
        }
    }
}
