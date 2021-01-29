package arcaratus.bloodarsenal.common.item;

import arcaratus.bloodarsenal.common.BloodArsenal;
import arcaratus.bloodarsenal.common.util.helper.TextHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyBindingMap;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class BloodArsenalItem extends Item
{
    private final boolean desc;
    private final boolean extended;

    public BloodArsenalItem(Properties builder, boolean desc, boolean extended)
    {
        super(builder);
        this.desc = desc;
        this.extended = extended;
    }

    public BloodArsenalItem(Properties builder, boolean desc)
    {
        this(builder, desc, false);
    }

    public BloodArsenalItem(Properties builder)
    {
        this(builder, false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        if (desc)
        {
            long windowHandler = Minecraft.getInstance().getMainWindow().getHandle();
            if (extended && !InputMappings.isKeyDown(windowHandler, 340))
            {
                tooltip.add(new TranslationTextComponent(TextHelper.localizeEffect("tooltip.bloodarsenal.more_info")).mergeStyle(TextFormatting.GRAY));
            }
            else
            {
                tooltip.add(new TranslationTextComponent("tooltip.bloodarsenal." + getRegistryName().getPath() + ".desc").mergeStyle(TextFormatting.GRAY));
            }
        }
    }
}
