package arc.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.api.impl.ItemSigilToggleable;
import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import WayofTime.bloodmagic.client.IMeshProvider;
import WayofTime.bloodmagic.util.Utils;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.client.mesh.CustomMeshDefinitionActivatable;
import com.google.common.base.Strings;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class ItemSigilBaseToggleable extends ItemSigilToggleable implements IMeshProvider
{
    protected final String tooltipBase;
    private final String name;

    public ItemSigilBaseToggleable(String name, int lpUsed)
    {
        super(lpUsed);

        setUnlocalizedName(BloodArsenal.MOD_ID + ".sigil." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);

        this.name = name;
        this.tooltipBase = "tooltip.bloodarsenal.sigil." + name + ".";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (TextHelper.canTranslate(tooltipBase + "desc"))
            tooltip.addAll(Arrays.asList(TextHelper.cutLongString(TextHelper.localizeEffect(tooltipBase + "desc"))));

        if (!stack.hasTagCompound())
            return;

        tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic." + (getActivated(stack) ? "activated" : "deactivated")));

        if (!Strings.isNullOrEmpty(getOwnerName(stack)))
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.currentOwner", PlayerHelper.getUsernameFromStack(stack)));

        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return new CustomMeshDefinitionActivatable("ItemSigil" + Utils.toFancyCasing(name));
    }

    @Override
    public ResourceLocation getCustomLocation()
    {
        return null;
    }

    @Override
    public List<String> getVariants()
    {
        List<String> ret = new ArrayList<>();
        ret.add("active=false");
        ret.add("active=true");
        return ret;
    }
}