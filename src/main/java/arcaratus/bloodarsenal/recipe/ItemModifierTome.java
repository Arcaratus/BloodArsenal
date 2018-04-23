package arcaratus.bloodarsenal.item;

import WayofTime.bloodmagic.BloodMagic;
import WayofTime.bloodmagic.util.ChatUtil;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class ItemModifierTome extends ItemBloodArsenalBase
{
    public ItemModifierTome(String name)
    {
        super(name);
        setCreativeTab(BloodMagic.TAB_TOMES);
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void getSubItems(Item id, CreativeTabs creativeTab, NonNullList<ItemStack> list)
//    {
//        if (creativeTab == BloodMagic.tabUpgradeTome)
//        {
//            for (Entry<String, Integer> entry : ModifierHandler.modifierMaxLevelMap.entrySet())
//            {
//                String key = entry.getKey();
//                int maxLevel = entry.getValue();
//                for (int i = 0; i < maxLevel; i++)
//                {
//                    ItemStack stack = new ItemStack(this);
//                    ModifierHelper.setKey(stack, key);
//                    ModifierHelper.setLevel(stack, i);
//                    list.add(stack);
//                }
//            }
//        }
//    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if (world.isRemote || hand != EnumHand.MAIN_HAND)
            return super.onItemRightClick(world, player, hand);

        ItemStack itemStack = player.getHeldItem(hand);
        Modifier modifier = ModifierHelper.getModifier(itemStack);
        NBTTagCompound specialNBT = null;

        if (modifier != Modifier.EMPTY_MODIFIER)
        {
            ItemStack otherStack = player.getHeldItem(EnumHand.OFF_HAND);
            if (!otherStack.isEmpty() && otherStack.getItem() instanceof IModifiableItem)
            {
                StasisModifiable modifiable = StasisModifiable.getStasisModifiable(otherStack);
                if (modifiable != null)
                {
                    if (modifiable.canApplyModifier(modifier) && modifiable.applyModifier(modifier))
                    {
                        if (modifier.getSpecialNBT(itemStack) != null)
                            specialNBT = modifier.getSpecialNBT(itemStack);

                        modifier.removeSpecialNBT(otherStack); // Needed here in order to reset NBT data
                        modifier.writeSpecialNBT(otherStack, new ItemStack(specialNBT.getCompoundTag(Constants.NBT.ITEMSTACK)));
                        StasisModifiable.setStasisModifiable(otherStack, modifiable);
                        String name = modifier.hasAltName() ? TextHelper.localize(modifier.getAlternateName(itemStack)) : TextHelper.localize(modifier.getUnlocalizedName());
                        ChatUtil.sendChat(player, TextHelper.localizeEffect("chat.bloodarsenal.modifierAdded", name, modifier.getLevel() + 1, otherStack.getDisplayName()));
                        itemStack.shrink(1);
                    }
                }
            }
        }

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (!itemStack.hasTagCompound())
            return;

        Modifier modifier = ModifierHelper.getModifier(itemStack);
        if (modifier != null)
        {
            String name = modifier.hasAltName() ? TextHelper.localize(modifier.getAlternateName(itemStack)) : TextHelper.localize(modifier.getUnlocalizedName());
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.modifier.level", name, modifier.getLevel() + 1, (modifier.readyForUpgrade() ? "+" : "")));
        }
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "normal"));
        return ret;
    }
}
