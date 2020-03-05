package arcaratus.bloodarsenal.item;

import WayofTime.bloodmagic.BloodMagic;
import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.registry.Constants;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import joptsimple.internal.Strings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemModifierTome extends Item implements IVariantProvider
{
    public ItemModifierTome(String name)
    {
        super();

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodMagic.TAB_TOMES);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> list)
    {
        if (creativeTab == BloodMagic.TAB_TOMES)
        {
            List<String> blackListedTomes = Arrays.asList(Constants.Modifiers.BAD_POTION, Constants.Modifiers.BENEFICIAL_POTION, Constants.Modifiers.SIGIL);
            for (Map.Entry<String, Integer> entry : ModifierHandler.modifierMaxLevelMap.entrySet())
            {
                String key = entry.getKey();
                if (blackListedTomes.contains(key))
                    continue;

                int maxLevel = entry.getValue();
                for (int i = 0; i < maxLevel; i++)
                {
                    ItemStack stack = new ItemStack(this);
                    ModifierHelper.setKey(stack, key);
                    ModifierHelper.setLevel(stack, i);
                    ModifierHelper.setReady(stack, false);
                    list.add(stack);
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if (world.isRemote || hand != EnumHand.MAIN_HAND)
            return super.onItemRightClick(world, player, hand);

        ItemStack itemStack = player.getHeldItem(hand);
        Pair<Modifier, ModifierTracker> pair = ModifierHelper.getModifierAndTracker(itemStack);
        Modifier modifier = pair.getLeft();
        ModifierTracker tracker = pair.getRight();
        NBTTagCompound specialNBT = null;

        if (modifier != Modifier.EMPTY_MODIFIER)
        {
            ItemStack otherStack = player.getHeldItem(EnumHand.OFF_HAND);
            if (!otherStack.isEmpty() && otherStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(otherStack);
                if (modifiable.canApplyModifier(modifier) && modifiable.applyModifier(pair))
                {
                    boolean hasSpecialNBT = modifier.getSpecialNBT(itemStack) != null;
                    if (hasSpecialNBT)
                        specialNBT = modifier.getSpecialNBT(itemStack);

                    modifier.removeSpecialNBT(otherStack); // Needed here in order to reset NBT data

                    if (hasSpecialNBT)
                        modifier.writeSpecialNBT(otherStack, new ItemStack(specialNBT.getCompoundTag(Constants.NBT.ITEMSTACK)), tracker.getLevel());
                    else
                        modifier.writeSpecialNBT(otherStack, tracker.getLevel());

                    modifiable.setMod(itemStack);
                    String name = modifier.hasAltName() ? TextHelper.localize(modifier.getAlternateName(itemStack)) : TextHelper.localize(modifier.getUnlocalizedName());
                    player.sendStatusMessage(new TextComponentString(TextHelper.localizeEffect("chat.bloodarsenal.modifier_added", name, tracker.getLevel() + 1, otherStack.getDisplayName())), true);
                    itemStack.shrink(1);
                }
                else
                {
                    String name = modifier.hasAltName() ? TextHelper.localize(modifier.getAlternateName(itemStack)) : TextHelper.localize(modifier.getUnlocalizedName());
                    player.sendStatusMessage(new TextComponentString(TextHelper.localizeEffect("chat.bloodarsenal.modifier_incompatible", name, otherStack.getDisplayName())), true);
                    return ActionResult.newResult(EnumActionResult.FAIL, itemStack);
                }
            }
        }

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (!itemStack.hasTagCompound())
            return;

        Pair<Modifier, ModifierTracker> pair = ModifierHelper.getModifierAndTracker(itemStack);
        Modifier modifier = pair.getLeft();
        ModifierTracker tracker = pair.getRight();
        if (modifier != null)
        {
            String name = modifier.hasAltName() && !Strings.isNullOrEmpty(modifier.getAlternateName(itemStack)) ? TextHelper.localize(modifier.getAlternateName(itemStack)) : TextHelper.localize(modifier.getUnlocalizedName());
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.modifier.level", name, tracker.getLevel() + 1, (tracker.isReadyToUpgrade() ? "+" : "")));
        }
    }

    @Override
    public void gatherVariants(@Nonnull Int2ObjectMap<String> variants)
    {
        variants.put(0, "type=normal");
    }
}
