package arcaratus.bloodarsenal.common.item;

import net.minecraft.client.Minecraft;
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
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class BloodArsenalItem extends Item
{
    private final String desc;
    private final boolean extended;

    public BloodArsenalItem(Properties builder, String desc, boolean extended)
    {
        super(builder);
        this.desc = desc;
        this.extended = extended;
    }

    public BloodArsenalItem(Properties builder, String desc)
    {
        this(builder, desc, false);
    }

    public BloodArsenalItem(Properties builder)
    {
        this(builder, "");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        if (!desc.isEmpty())
        {
            long windowHandle = Minecraft.getInstance().getMainWindow().getHandle();
            if (extended && !InputMappings.isKeyDown(windowHandle, GLFW.GLFW_KEY_LEFT_SHIFT))
            {
                tooltip.add(new TranslationTextComponent("tooltip.bloodarsenal.more_info").mergeStyle(TextFormatting.GRAY));
            }
            else
            {
                tooltip.add(new TranslationTextComponent("tooltip.bloodarsenal." + desc).mergeStyle(TextFormatting.GRAY));
            }
        }
    }
}
