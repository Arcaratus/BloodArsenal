package arc.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.entity.projectile.EntitySummonedTool;
import arc.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSigilSentience extends ItemSigilBase
{
    public ItemSigilSentience()
    {
        super("sentience", ConfigHandler.sigilSentienceBaseCost);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        if (hand.equals(EnumHand.OFF_HAND))
        {
            ItemStack heldStack = player.getHeldItemMainhand();
            if (heldStack != null && (heldStack.getItem() instanceof ItemSword || heldStack.getItem() instanceof ItemTool))
            {
                ItemStack toolStack = heldStack.copy();
                if (!player.capabilities.isCreativeMode)
                {
                    toolStack.damageItem(2, player);
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, toolStack);
                }

                EntitySummonedTool summonedTool = new EntitySummonedTool(world, player, heldStack);
                player.getCooldownTracker().setCooldown(this, 20);

                world.spawnEntityInWorld(summonedTool);

                if (!world.isRemote)
                    syphonCosts(player, heldStack);
            }
        }

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return oldStack.getItem() != newStack.getItem();
    }

    private void syphonCosts(EntityPlayer player, ItemStack summonedTool)
    {
        float cost = 500;

        if (summonedTool.getItem() instanceof ItemTool)
        {
            String toolType = BloodArsenalUtils.getToolType(summonedTool.getItem());
            float damage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            cost *= 1 + (summonedTool.getItem().getHarvestLevel(summonedTool, toolType, player, null) / 3);
            cost *= 1 + ((damage / 3) / 3);
        }
        else if (summonedTool.getItem() instanceof ItemSword)
        {
            float damage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            cost *= 1 + (damage / 4);
        }

        // HANDLE THE ENCHANTMENTS
        int lvl;
        for (Enchantment enchantment : EnchantmentHelper.getEnchantments(summonedTool).keySet())
        {
            lvl = EnchantmentHelper.getEnchantmentLevel(enchantment, summonedTool);
            cost += 200 * lvl;
        }

        NetworkHelper.syphonAndDamage(NetworkHelper.getSoulNetwork(player), player, (int) cost);
    }
}
