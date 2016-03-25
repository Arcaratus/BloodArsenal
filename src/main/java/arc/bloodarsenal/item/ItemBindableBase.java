package arc.bloodarsenal.item;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.impl.ItemBindable;
import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import com.google.common.base.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBindableBase extends ItemBindable
{
    protected final String tooltipBase;
    private final String name;

    public ItemBindableBase(String name)
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

        NBTHelper.checkNBT(stack);

        if (!Strings.isNullOrEmpty(stack.getTagCompound().getString(Constants.NBT.OWNER_UUID)))
            tooltip.add(TextHelper.localizeEffect("tooltip.BloodArsenal.currentOwner", PlayerHelper.getUsernameFromStack(stack)));

        super.addInformation(stack, player, tooltip, advanced);
    }
}
