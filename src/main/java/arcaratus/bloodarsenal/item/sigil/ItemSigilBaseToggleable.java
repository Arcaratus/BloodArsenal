package arcaratus.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.client.IMeshProvider;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.item.sigil.ItemSigilToggleable;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.client.mesh.CustomMeshDefinitionActivatable;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class ItemSigilBaseToggleable extends ItemSigilToggleable implements IMeshProvider
{
    protected final String tooltipBase;
    private final String name;

    public ItemSigilBaseToggleable(String name, int lpUsed)
    {
        super(lpUsed);

        setTranslationKey(BloodArsenal.MOD_ID + ".sigil." + name);
        setRegistryName("sigil_" + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);

        this.name = name;
        this.tooltipBase = "tooltip.bloodarsenal.sigil." + name + ".";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        super.addInformation(stack, world, tooltip, flag);
        if (!stack.hasTagCompound())
            return;

        tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic." + (getActivated(stack) ? "activated" : "deactivated")));

        Binding binding = getBinding(stack);
        if (binding != null)
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.currentOwner", binding.getOwnerName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return new CustomMeshDefinitionActivatable("sigil_" + name.toLowerCase(Locale.ROOT));
    }

    @Override
    public void gatherVariants(Consumer<String> variants)
    {
        variants.accept("active=false");
        variants.accept("active=true");
    }
}