package arcaratus.bloodarsenal.item.baubles;

import WayofTime.bloodmagic.client.IMeshProvider;
import WayofTime.bloodmagic.iface.IMultiWillTool;
import WayofTime.bloodmagic.soul.*;
import WayofTime.bloodmagic.util.Constants;
import WayofTime.bloodmagic.util.helper.*;
import arcaratus.bloodarsenal.client.mesh.CustomMeshDefinitionSoulPendant;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class ItemSoulPendant extends ItemBauble implements IDemonWillGem, IMeshProvider, IMultiWillTool
{
    public static String[] names = { "petty", "lesser", "common", "greater", "grand" };

    public ItemSoulPendant(String name)
    {
        super(name, BaubleType.AMULET);

        setHasSubtypes(true);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event)
    {
        ItemStack stack = event.getItem().getItem();
        if (stack.getItem() instanceof IDemonWill)
        {
            EntityPlayer player = event.getEntityPlayer();
            EnumDemonWillType pickupType = ((IDemonWill) stack.getItem()).getType(stack);
            ItemStack remainder = addDemonWill(player, stack);

            if (remainder.isEmpty() || ((IDemonWill) stack.getItem()).getWill(pickupType, stack) < 0.0001 || isDemonWillFull(pickupType, player))
            {
                stack.setCount(0);
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    public static boolean isDemonWillFull(EnumDemonWillType type, EntityPlayer player)
    {
        NonNullList<ItemStack> inventory = NonNullList.create();

        IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
        for (int i = 0; i < baubles.getSlots(); i++)
            inventory.add(baubles.getStackInSlot(i));

        inventory.addAll(player.inventory.mainInventory);

        boolean hasGem = false;
        for (ItemStack stack : inventory)
        {
            if (stack.getItem() instanceof IDemonWillGem)
            {
                hasGem = true;
                if (((IDemonWillGem) stack.getItem()).getWill(type, stack) < ((IDemonWillGem) stack.getItem()).getMaxWill(type, stack))
                    return false;
            }
        }

        return hasGem;
    }

    /**
     * Modified version of {@link PlayerDemonWillHandler#addDemonWill}
     * We need to override it to special case for baubles
     *
     * @param player    - The player to add will to
     * @param willStack - ItemStack that contains an IDemonWill to be added
     * @return - The modified willStack
     */
    public static ItemStack addDemonWill(EntityPlayer player, ItemStack willStack)
    {
        if (willStack.isEmpty())
            return ItemStack.EMPTY;

        NonNullList<ItemStack> inventory = NonNullList.create();

        IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
        for (int i = 0; i < baubles.getSlots(); i++)
            inventory.add(baubles.getStackInSlot(i));

        inventory.addAll(player.inventory.mainInventory);

        for (ItemStack stack : inventory)
        {
            if (stack.getItem() instanceof IDemonWillGem)
            {
                ItemStack newStack = ((IDemonWillGem) stack.getItem()).fillDemonWillGem(stack, willStack);
                if (newStack.isEmpty())
                    return ItemStack.EMPTY;
            }
        }

        return willStack;
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return super.getTranslationKey(stack) + "." + names[stack.getItemDamage()];
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
            return super.onItemRightClick(world, player, hand);

        EnumDemonWillType type = getCurrentType(stack);
        double drain = Math.min(getWill(type, stack), getMaxWill(type, stack) / 10);

        double filled = PlayerDemonWillHandler.addDemonWill(type, player, drain, stack);
        drainWill(type, stack, filled, true);

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return new CustomMeshDefinitionSoulPendant("soul_pendant");
    }

    @Override
    public void gatherVariants(Consumer<String> variants)
    {
        for (EnumDemonWillType type : EnumDemonWillType.values())
        {
            variants.accept("type=petty_" + type.getName().toLowerCase());
            variants.accept("type=lesser_" + type.getName().toLowerCase());
            variants.accept("type=common_" + type.getName().toLowerCase());
            variants.accept("type=greater_" + type.getName().toLowerCase());
            variants.accept("type=grand_" + type.getName().toLowerCase());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> list)
    {
        if (!isInCreativeTab(creativeTab))
            return;

        for (int i = 0; i < names.length; i++)
        {
            ItemStack emptyStack = new ItemStack(this, 1, i);
            list.add(emptyStack);
        }

        for (EnumDemonWillType type : EnumDemonWillType.values())
        {
            for (int i = 0; i < names.length; i++)
            {
                ItemStack fullStack = new ItemStack(this, 1, i);
                setWill(type, fullStack, getMaxWill(EnumDemonWillType.DEFAULT, fullStack));
                list.add(fullStack);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (!stack.hasTagCompound())
            return;

        EnumDemonWillType type = getCurrentType(stack);
        tooltip.add(TextHelper.localize("tooltip.bloodarsenal.soul_pendant." + names[stack.getItemDamage()]));
        tooltip.add(TextHelper.localize("tooltip.bloodmagic.will", getWill(type, stack)));
        tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.currentType." + getCurrentType(stack).getName().toLowerCase()));

        super.addInformation(stack, world, tooltip, flag);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        EnumDemonWillType type = getCurrentType(stack);
        double maxWill = getMaxWill(type, stack);
        if (maxWill <= 0)
            return 1;

        return 1.0 - (getWill(type, stack) / maxWill);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        EnumDemonWillType type = getCurrentType(stack);
        double maxWill = getMaxWill(type, stack);
        if (maxWill <= 0)
            return 1;

        return MathHelper.hsvToRGB(Math.max(0.0F, (float) (getWill(type, stack)) / (float) maxWill) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public ItemStack fillDemonWillGem(ItemStack soulGemStack, ItemStack soulStack)
    {
        if (soulStack.getItem() instanceof IDemonWill)
        {
            EnumDemonWillType thisType = getCurrentType(soulGemStack);
            if (thisType != ((IDemonWill) soulStack.getItem()).getType(soulStack))
                return soulStack;

            IDemonWill soul = (IDemonWill) soulStack.getItem();
            double soulsLeft = getWill(thisType, soulGemStack);

            if (soulsLeft < getMaxWill(thisType, soulGemStack))
            {
                double newSoulsLeft = Math.min(soulsLeft + soul.getWill(thisType, soulStack), getMaxWill(thisType, soulGemStack));
                soul.drainWill(thisType, soulStack, newSoulsLeft - soulsLeft);

                setWill(thisType, soulGemStack, newSoulsLeft);
                if (soul.getWill(thisType, soulStack) <= 0)
                    return ItemStack.EMPTY;
            }
        }

        return soulStack;
    }

    @Override
    public double getWill(EnumDemonWillType type, ItemStack soulGemStack)
    {
        if (!type.equals(getCurrentType(soulGemStack)))
            return 0;

        NBTTagCompound tag = soulGemStack.getTagCompound();

        return tag.getDouble(Constants.NBT.SOULS);
    }

    @Override
    public void setWill(EnumDemonWillType type, ItemStack soulGemStack, double souls)
    {
        setCurrentType(type, soulGemStack);

        NBTTagCompound tag = soulGemStack.getTagCompound();

        tag.setDouble(Constants.NBT.SOULS, souls);
    }

    @Override
    public double drainWill(EnumDemonWillType type, ItemStack soulGemStack, double drainAmount, boolean doDrain)
    {
        EnumDemonWillType currentType = getCurrentType(soulGemStack);
        if (currentType != type)
            return 0;

        double souls = getWill(type, soulGemStack);

        double soulsDrained = Math.min(drainAmount, souls);
        setWill(type, soulGemStack, souls - soulsDrained);

        return soulsDrained;
    }

    @Override
    public int getMaxWill(EnumDemonWillType type, ItemStack soulGemStack)
    {
        EnumDemonWillType currentType = getCurrentType(soulGemStack);
        if (!type.equals(currentType) && currentType != EnumDemonWillType.DEFAULT)
            return 0;

        switch (soulGemStack.getMetadata())
        {
            case 0:
                return 64;
            case 1:
                return 256;
            case 2:
                return 1024;
            case 3:
                return 4096;
            case 4:
                return 16384;
        }

        return 64;
    }

    @Override
    public EnumDemonWillType getCurrentType(ItemStack soulGemStack)
    {
        NBTHelper.checkNBT(soulGemStack);

        NBTTagCompound tag = soulGemStack.getTagCompound();

        if (!tag.hasKey(Constants.NBT.WILL_TYPE))
            return EnumDemonWillType.DEFAULT;

        return EnumDemonWillType.valueOf(tag.getString(Constants.NBT.WILL_TYPE).toUpperCase(Locale.ENGLISH));
    }

    public void setCurrentType(EnumDemonWillType type, ItemStack soulGemStack)
    {
        NBTHelper.checkNBT(soulGemStack);

        NBTTagCompound tag = soulGemStack.getTagCompound();

        if (type == EnumDemonWillType.DEFAULT)
        {
            if (tag.hasKey(Constants.NBT.WILL_TYPE))
                tag.removeTag(Constants.NBT.WILL_TYPE);

            return;
        }

        tag.setString(Constants.NBT.WILL_TYPE, type.toString());
    }

    @Override
    public double fillWill(EnumDemonWillType type, ItemStack stack, double fillAmount, boolean doFill)
    {
        if (!type.equals(getCurrentType(stack)) && getWill(getCurrentType(stack), stack) > 0)
            return 0;

        double current = getWill(type, stack);
        double maxWill = getMaxWill(type, stack);

        double filled = Math.min(fillAmount, maxWill - current);

        if (filled > 0)
        {
            setWill(type, stack, filled + current);
            return filled;
        }

        return 0;
    }
}