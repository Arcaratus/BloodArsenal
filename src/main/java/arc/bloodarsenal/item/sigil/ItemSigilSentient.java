package arc.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import arc.bloodarsenal.entity.projectile.EntitySummonedSword;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSigilSentient extends ItemSigilBase
{
    public ItemSigilSentient()
    {
        super("sentient");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
//        if (getCooldownRemainder(stack) > 0)
//            return super.onItemRightClick(stack, world, player, hand);

//        if (!world.isRemote)
        {
            if (hand.equals(EnumHand.OFF_HAND))
            {
                if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemSword)
                {
                    ItemStack swordStack = player.getHeldItemMainhand().copy();
                    if (!player.capabilities.isCreativeMode)
                    {
                        swordStack.damageItem(2, player);
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, swordStack);
                    }

                    EntitySummonedSword summonedSword = new EntitySummonedSword(world, player);
                    player.getCooldownTracker().setCooldown(this, 20);

//                    if (world.isRemote)
                    summonedSword.setStack(player.getHeldItemMainhand());
                    world.spawnEntityInWorld(summonedSword);
                }
            }
//            NetworkHelper.syphonAndDamage(NetworkHelper.getSoulNetwork(player), player, getLpUsed());
        }

        resetCooldown(stack);

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return oldStack.getItem() != newStack.getItem();
    }

    public int getCooldownRemainder(ItemStack stack)
    {
        return NBTHelper.checkNBT(stack).getTagCompound().getInteger(Constants.NBT.TICKS_REMAINING);
    }

    public void reduceCooldown(ItemStack stack)
    {
        NBTHelper.checkNBT(stack).getTagCompound().setInteger(Constants.NBT.TICKS_REMAINING, getCooldownRemainder(stack) - 1);
    }

    public void resetCooldown(ItemStack stack)
    {
        NBTHelper.checkNBT(stack).getTagCompound().setInteger(Constants.NBT.TICKS_REMAINING, 10);
    }
}
