package arcaratus.bloodarsenal.common.item.tool.wood;

import arcaratus.bloodarsenal.common.ConfigHandler;
import arcaratus.bloodarsenal.common.item.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wayoftime.bloodmagic.common.item.IBindable;
import wayoftime.bloodmagic.core.data.Binding;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import java.util.List;

public class BloodInfusedWoodenShovelItem extends ShovelItem implements IBindable
{
    public BloodInfusedWoodenShovelItem(Properties properties)
    {
        this(ModItems.ItemTier.BLOOD_INFUSED_WOOD, properties);
    }

    public BloodInfusedWoodenShovelItem(IItemTier material, Properties properties)
    {
        super(material, 1.5F, -3, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int slot, boolean selected)
    {
        if (!world.isRemote && world.getGameTime() % ConfigHandler.COMMON.bloodInfusedWoodenToolsRepairUpdate.get() == 0)
        {
            if (player instanceof PlayerEntity && stack.getDamage() > 0 && NetworkHelper.syphonFromContainer(stack, SoulTicket.item(stack, world, player, ConfigHandler.COMMON.bloodInfusedWoodenToolsRepairCost.get())))
            {
                stack.setDamage(stack.getDamage() - 1);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        if (!stack.hasTag())
            return;

        Binding binding = getBinding(stack);
        if (binding != null)
            tooltip.add(new TranslationTextComponent("tooltip.bloodmagic.currentOwner", binding.getOwnerName()).mergeStyle(TextFormatting.GRAY));
    }
}
