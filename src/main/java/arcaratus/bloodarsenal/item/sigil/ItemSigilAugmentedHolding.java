package arcaratus.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.client.IMeshProvider;
import WayofTime.bloodmagic.client.key.IKeybindable;
import WayofTime.bloodmagic.client.key.KeyBindings;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.iface.*;
import WayofTime.bloodmagic.util.Utils;
import WayofTime.bloodmagic.util.helper.*;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
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

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

public class ItemSigilAugmentedHolding extends ItemSigilBase implements IKeybindable, IAltarReader, ISigil.Holding, IMeshProvider
{
    public static final int INVENTORY_SIZE = 9;

    public ItemSigilAugmentedHolding()
    {
        super("augmented_holding");
    }

    @Override
    public void onKeyPressed(ItemStack stack, EntityPlayer player, KeyBindings key, boolean showInChat)
    {
        if (stack == player.getHeldItemMainhand() && stack.getItem() instanceof ItemSigilAugmentedHolding && key.equals(KeyBindings.OPEN_HOLDING))
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

        if (item.isEmpty())
            return displayName;
        else
            return TextHelper.localizeEffect("item.bloodmagic.sigil.holding.display", displayName, item.getDisplayName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.sigil.holding.press", KeyBindings.OPEN_HOLDING.getKey().getDisplayName()));

        if (!stack.hasTagCompound())
            return;

        List<ItemStack> inv = getInternalInventory(stack);

        int currentSlot = getCurrentItemOrdinal(stack);
        ItemStack item = inv.get(currentSlot);

        for (int i = 0; i < INVENTORY_SIZE; i++)
        {
            ItemStack invStack = inv.get(i);
            if (!invStack.isEmpty())
                if (!item.isEmpty() && invStack == item)
                    tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.sigil.holding.sigilInSlot", i + 1, "&o&n" + invStack.getDisplayName()));
                else
                    tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.sigil.holding.sigilInSlot", i + 1, invStack.getDisplayName()));
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
            return EnumActionResult.FAIL;

        int currentSlot = getCurrentItemOrdinal(stack);
        List<ItemStack> inv = getInternalInventory(stack);

        ItemStack itemUsing = inv.get(currentSlot);

        if (itemUsing.isEmpty() || ((IBindable) itemUsing.getItem()).getBinding(itemUsing) == null)
            return EnumActionResult.PASS;

        EnumActionResult result = itemUsing.getItem().onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        saveInventory(stack, inv);

        return result;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
            return ActionResult.newResult(EnumActionResult.FAIL, stack);

        int currentSlot = getCurrentItemOrdinal(stack);
        List<ItemStack> inv = getInternalInventory(stack);
        ItemStack itemUsing = inv.get(currentSlot);

        if (itemUsing.isEmpty() || ((IBindable) itemUsing.getItem()).getBinding(itemUsing) == null)
            return ActionResult.newResult(EnumActionResult.PASS, stack);

        itemUsing.getItem().onItemRightClick(world, player, hand);

        saveInventory(stack, inv);

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Nonnull
    @Override
    public ItemStack getHeldItem(ItemStack holdingStack, EntityPlayer player)
    {
        return getInternalInventory(holdingStack).get(getCurrentItemOrdinal(holdingStack));
    }

    public void saveInventory(ItemStack itemStack, List<ItemStack> inventory)
    {
        NBTTagCompound itemTag = itemStack.getTagCompound();

        if (itemTag == null)
            itemStack.setTagCompound(itemTag = new NBTTagCompound());

        NBTTagCompound inventoryTag = new NBTTagCompound();
        NBTTagList itemList = new NBTTagList();

        for (int i = 0; i < INVENTORY_SIZE; i++)
        {
            if (!inventory.get(i).isEmpty())
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte(Constants.NBT.SLOT, (byte) i);
                inventory.get(i).writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }

        inventoryTag.setTag(Constants.NBT.ITEMS, itemList);
        itemTag.setTag(Constants.NBT.ITEM_INVENTORY, inventoryTag);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (itemStack.hasTagCompound())
            tickInternalInventory(itemStack, world, entity, itemSlot, isSelected);
    }

    public void tickInternalInventory(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        for (ItemStack stack : getInternalInventory(itemStack))
        {
            if (stack.isEmpty() || !(stack.getItem() instanceof IBindable) || !(stack.getItem() instanceof ISigil))
                continue;

            Binding binding = ((IBindable) stack.getItem()).getBinding(stack);
            if (binding == null)
                continue;

            stack.getItem().onUpdate(stack, world, entity, itemSlot, isSelected);
        }
    }

    @Override
    public void gatherVariants(@Nonnull Int2ObjectMap<String> variants)
    {
        // No-op - Just here to stop the super from running since we're using a mesh provider
    }

    @Override
    public ItemMeshDefinition getMeshDefinition()
    {
        return stack -> {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("color"))
                return new ModelResourceLocation(getRegistryName(), "type=color");

            return new ModelResourceLocation(getRegistryName(), "type=normal");
        };
    }

    @Override
    public void gatherVariants(Consumer<String> variants)
    {
        variants.accept("type=normal");
        variants.accept("type=color");
    }

    public static int next(int mode)
    {
        int index = mode + 1;

        if (index >= INVENTORY_SIZE)
            index = 0;

        return index;
    }

    public static int prev(int mode)
    {
        int index = mode - 1;

        if (index < 0)
            index = INVENTORY_SIZE;

        return index;
    }

    private static void initModeTag(ItemStack stack)
    {
        if (!stack.hasTagCompound())
        {
            stack = NBTHelper.checkNBT(stack);
            stack.getTagCompound().setInteger(Constants.NBT.CURRENT_SIGIL, INVENTORY_SIZE);
        }
    }

    public static ItemStack getItemStackInSlot(ItemStack itemStack, int slot)
    {
        if (itemStack.getItem() instanceof ItemSigilAugmentedHolding)
        {
            List<ItemStack> itemStacks = getInternalInventory(itemStack);
            if (!itemStacks.isEmpty())
                return itemStacks.get(slot == INVENTORY_SIZE ? INVENTORY_SIZE - 1 : slot);
            else
                return ItemStack.EMPTY;
        }

        return ItemStack.EMPTY;
    }

    public static int getCurrentItemOrdinal(ItemStack itemStack)
    {
        if (itemStack.getItem() instanceof ItemSigilAugmentedHolding)
        {
            initModeTag(itemStack);
            int currentSigil = itemStack.getTagCompound().getInteger(Constants.NBT.CURRENT_SIGIL);
            currentSigil = MathHelper.clamp(currentSigil, 0, INVENTORY_SIZE - 1);
            return currentSigil;
        }

        return 0;
    }

    public static List<ItemStack> getInternalInventory(ItemStack stack)
    {
        initModeTag(stack);
        NBTTagCompound tagCompound = stack.getTagCompound();

        if (tagCompound == null)
        {
            return NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
        }

        NBTTagCompound inventoryTag = tagCompound.getCompoundTag(Constants.NBT.ITEM_INVENTORY);
        NBTTagList tagList = inventoryTag.getTagList(Constants.NBT.ITEMS, 10);

        if (tagList.isEmpty())
        {
            return NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
        }

        List<ItemStack> inv = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound data = tagList.getCompoundTagAt(i);
            byte j = data.getByte(Constants.NBT.SLOT);

            if (j >= 0 && j < inv.size())
            {
                inv.set(j, new ItemStack(data));
            }
        }

        return inv;
    }

    public static void cycleToNextSigil(ItemStack itemStack, int mode)
    {
        if (itemStack.getItem() instanceof ItemSigilAugmentedHolding)
        {
            initModeTag(itemStack);

            int index = mode;
            if (mode == 120 || mode == -120)
            {
                int currentIndex = getCurrentItemOrdinal(itemStack);
                ItemStack currentItemStack = getItemStackInSlot(itemStack, currentIndex);
                if (currentItemStack.isEmpty())
                    return;
                if (mode < 0)
                {
                    index = BloodArsenalUtils.next(currentIndex, INVENTORY_SIZE);
                    currentItemStack = getItemStackInSlot(itemStack, index);

                    while (currentItemStack.isEmpty())
                    {
                        index = BloodArsenalUtils.next(currentIndex, INVENTORY_SIZE);
                        currentItemStack = getItemStackInSlot(itemStack, index);
                    }
                }
                else
                {
                    index = BloodArsenalUtils.prev(currentIndex, INVENTORY_SIZE);
                    currentItemStack = getItemStackInSlot(itemStack, index);

                    while (currentItemStack.isEmpty())
                    {
                        index = BloodArsenalUtils.prev(currentIndex, INVENTORY_SIZE);
                        currentItemStack = getItemStackInSlot(itemStack, index);
                    }
                }
            }

            itemStack.getTagCompound().setInteger(Constants.NBT.CURRENT_SIGIL, index);
        }
    }
}
