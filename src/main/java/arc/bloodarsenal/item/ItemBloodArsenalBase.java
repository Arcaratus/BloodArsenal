package arc.bloodarsenal.item;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBloodArsenalBase extends Item
{
    protected final String tooltipBase;
    private final String name;

    public ItemBloodArsenalBase(String name)
    {
        super();
        setUnlocalizedName(BloodArsenal.MOD_ID + name);
        setCreativeTab(BloodArsenal.tabBloodArsenal);

        this.name = name;
        this.tooltipBase = "tooltip.BloodArsenal." + name + ".";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (I18n.canTranslate(tooltipBase + "desc"))
            tooltip.add(TextHelper.localizeEffect(tooltipBase + "desc"));

        super.addInformation(stack, player, tooltip, advanced);
    }
}
