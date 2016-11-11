package arc.bloodarsenal.item;

import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.ModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ItemBloodArsenalBase extends Item implements IVariantProvider
{
    protected final String tooltipBase;
    private final String name;

    public ItemBloodArsenalBase(String name)
    {
        super();
        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);

        this.name = name;
        this.tooltipBase = "tooltip.bloodarsenal." + name + ".";
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (player.getName().equals("Arcaratus") && stack.getItem() == ModItems.BLOOD_INFUSED_STICK && stack.getCount() == 1)
        {
            stack.setTagCompound(new NBTTagCompound());
            stack.setStackDisplayName(TextHelper.getFormattedText("&r&cThe Living Stick"));
            stack.getTagCompound().setBoolean("living", true);
        }

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (I18n.hasKey(tooltipBase + "desc"))
            tooltip.add(TextHelper.localizeEffect(tooltipBase + "desc"));

        {
            if (stack.getItem() == ModItems.GLASS_SHARD)
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                    tooltip.add(TextHelper.localizeEffect(tooltipBase + "info"));
                else
                    tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.holdShift"));
            else if (stack.getItem() == ModItems.REAGENT_LIGHTNING)
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                    for (int i = 0; i < 8; i++)
                        tooltip.add(TextHelper.localizeEffect(tooltipBase + "info" + i));
                else
                    tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.holdShift"));
        }

        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "type=" + name));
        return ret;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        if (stack.getItem() == ModItems.BLOOD_INFUSED_GLOWSTONE_DUST || stack.getItem() == ModItems.BLOOD_INFUSED_IRON_INGOT) return EnumRarity.UNCOMMON;
        if (stack.getItem() == ModItems.REAGENT_DIVINITY) return EnumRarity.EPIC;

        return super.getRarity(stack);
    }
}
