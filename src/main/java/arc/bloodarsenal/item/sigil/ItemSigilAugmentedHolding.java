package arc.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.api.iface.IAltarReader;
import WayofTime.bloodmagic.api.iface.IBindable;
import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import WayofTime.bloodmagic.client.key.IKeybindable;
import WayofTime.bloodmagic.client.key.KeyBindings;
import WayofTime.bloodmagic.util.Utils;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.util.BloodArsenalUtils;
import com.google.common.base.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class ItemSigilAugmentedHolding extends ItemSigilBase implements IKeybindable, IAltarReader
{
    public static final int INVENTORY_SIZE = 9;

    public ItemSigilAugmentedHolding()
    {
        super("augmentedHolding");
    }

    @Override
    public void onKeyPressed(ItemStack stack, EntityPlayer player, KeyBindings key, boolean showInChat)
    {
        if (stack != null && stack == player.getHeldItemMainhand() && stack.getItem() instanceof ItemSigilAugmentedHolding && key.equals(KeyBindings.OPEN_HOLDING))
        {
            Utils.setUUID(stack);
            player.openGui(BloodArsenal.INSTANCE, Constants.Gui.SIGIL_AUGMENTED_HOLDING_GUI, player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }

    @Override
    public String getHighlightTip(ItemStack stack, String displayName)
    {
        List<ItemStack> inv = getInternalInventory(stack);

        int currentSlot = getCurrentItemOrdinal(stack);
        ItemStack item = inv.get(currentSlot);

        if (item == null)
            return displayName;
        else
            return TextHelper.localizeEffect("item.bloodmagic.sigil.holding.display", displayName, item.getDisplayName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TextHelper.localizeEffect("tooltip.BloodMagic.sigil.holding.press", KeyBindings.OPEN_HOLDING.getKey().getDisplayName()));

        if (!stack.hasTagCompound())
            return;

        List<ItemStack> inv = getInternalInventory(stack);

        int currentSlot = getCurrentItemOrdinal(stack);
        ItemStack item = inv.get(currentSlot);

        for (int i = 0; i < INVENTORY_SIZE; i++)
        {
            ItemStack invStack = inv.get(i);
            if (invStack != null)
                if (item != null && invStack == item)
                    tooltip.add(TextHelper.localizeEffect("tooltip.BloodMagic.sigil.holding.sigilInSlot", i + 1, "&o&n" + invStack.getDisplayName()));
                else
                    tooltip.add(TextHelper.localizeEffect("tooltip.BloodMagic.sigil.holding.sigilInSlot", i + 1, invStack.getDisplayName()));
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
            return EnumActionResult.FAIL;

        int currentSlot = getCurrentItemOrdinal(stack);
        List<ItemStack> inv = getInternalInventory(stack);

        ItemStack itemUsing = inv.get(currentSlot);

        if (itemUsing == null || Strings.isNullOrEmpty(((IBindable) itemUsing.getItem()).getOwnerUUID(itemUsing)))
            return EnumActionResult.PASS;

        EnumActionResult result = itemUsing.getItem().onItemUse(itemStack, player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        saveInventory(stack, inv);

        return result;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
            return ActionResult.newResult(EnumActionResult.FAIL, stack);

        int currentSlot = getCurrentItemOrdinal(stack);
        List<ItemStack> inv = getInternalInventory(stack);
        ItemStack itemUsing = inv.get(currentSlot);

        if (itemUsing != null || Strings.isNullOrEmpty(((IBindable) itemUsing.getItem()).getOwnerUUID(itemUsing)))
            return ActionResult.newResult(EnumActionResult.PASS, stack);

        itemUsing.getItem().onItemRightClick(itemStack, world, player, hand);

        saveInventory(stack, inv);

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    public void saveInventory(ItemStack itemStack, List<ItemStack> inventory)
    {
        NBTTagCompound itemTag = itemStack.getTagCompound();

        if (itemTag == null)
            itemStack.setTagCompound(new NBTTagCompound());

        NBTTagList itemList = new NBTTagList();

        for (int i = 0; i < INVENTORY_SIZE; i++)
        {
            if (inventory.get(i) != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte(Constants.NBT.SLOT, (byte) i);
                inventory.get(i).writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }

        itemTag.setTag(Constants.NBT.ITEMS, itemList);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (!itemStack.hasTagCompound())
        {
            this.tickInternalInventory(itemStack, world, entity, itemSlot, isSelected);
        }
    }

    public void tickInternalInventory(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        List<ItemStack> inv = getInternalInventory(itemStack);

        for (int i = 0; i < INVENTORY_SIZE; i++)
        {
            ItemStack stack = inv.get(i);
            if (stack == null)
            {
                continue;
            }

            stack.getItem().onUpdate(stack, world, entity, itemSlot, isSelected);
        }
    }

    private static void initModeTag(ItemStack stack)
    {
        if (!stack.hasTagCompound())
        {
            NBTHelper.checkNBT(stack);
            stack.getTagCompound().setInteger(Constants.NBT.CURRENT_SIGIL, INVENTORY_SIZE);
        }
    }

    public static ItemStack getItemStackInSlot(ItemStack itemStack, int slot)
    {
        if (itemStack != null && itemStack.getItem() instanceof ItemSigilAugmentedHolding)
        {
            List<ItemStack> itemStacks = getInternalInventory(itemStack);
            if (itemStacks != null && !itemStacks.isEmpty())
                return itemStacks.get(slot == INVENTORY_SIZE ? INVENTORY_SIZE - 1 : slot);
            else
                return null;
        }

        return null;
    }

    public static int getCurrentItemOrdinal(ItemStack itemStack)
    {
        if (itemStack != null && itemStack.getItem() instanceof ItemSigilAugmentedHolding)
        {
            initModeTag(itemStack);
            int currentSigil = itemStack.getTagCompound().getInteger(Constants.NBT.CURRENT_SIGIL);
            currentSigil = MathHelper.clamp_int(currentSigil, 0, INVENTORY_SIZE - 1);
            return currentSigil;
        }

        return 0;
    }

    public static List<ItemStack> getInternalInventory(ItemStack stack)
    {
        List<ItemStack> inv = new ArrayList<>(INVENTORY_SIZE);

        if (stack != null)
        {
            initModeTag(stack);
            NBTTagCompound tagCompound = stack.getTagCompound();

            if (tagCompound == null)
            {
                return null;
            }

            NBTTagList tagList = tagCompound.getTagList(Constants.NBT.ITEMS, 10);

            if (tagList.hasNoTags())
            {
                return null;
            }

            for (int i = 0; i < tagList.tagCount(); i++)
            {
                NBTTagCompound data = tagList.getCompoundTagAt(i);
                byte j = data.getByte(Constants.NBT.SLOT);

                if (j >= 0 && j < inv.size())
                {
                    inv.set(j, ItemStack.loadItemStackFromNBT(data));
                }
            }
        }

        return inv;
    }

    public static void cycleToNextSigil(ItemStack itemStack, int mode)
    {
        if (itemStack != null && itemStack.getItem() instanceof ItemSigilAugmentedHolding)
        {
            initModeTag(itemStack);

            int index = mode;
            if (mode == 120 || mode == -120)
            {
                int currentIndex = getCurrentItemOrdinal(itemStack);
                ItemStack currentItemStack = getItemStackInSlot(itemStack, currentIndex);
                if (currentItemStack == null)
                    return;
                if (mode < 0)
                {
                    index = BloodArsenalUtils.next(currentIndex, INVENTORY_SIZE);
                    currentItemStack = getItemStackInSlot(itemStack, index);

                    while (currentItemStack == null)
                    {
                        index = BloodArsenalUtils.next(currentIndex, INVENTORY_SIZE);
                        currentItemStack = getItemStackInSlot(itemStack, index);
                    }
                }
                else
                {
                    index = BloodArsenalUtils.prev(currentIndex, INVENTORY_SIZE);
                    currentItemStack = getItemStackInSlot(itemStack, index);

                    while (currentItemStack == null)
                    {
                        index = BloodArsenalUtils.prev(currentIndex, INVENTORY_SIZE);
                        currentItemStack = getItemStackInSlot(itemStack, index);
                    }
                }
            }

            itemStack.getTagCompound().setInteger(Constants.NBT.CURRENT_SIGIL, index);
        }
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        return Collections.emptyList();
    }
}
