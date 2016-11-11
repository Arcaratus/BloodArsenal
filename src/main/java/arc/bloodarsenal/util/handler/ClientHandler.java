package arc.bloodarsenal.util.handler;

import WayofTime.bloodmagic.ConfigHandler;
import arc.bloodarsenal.item.IProfilable;
import arc.bloodarsenal.item.sigil.ItemSigilAugmentedHolding;
import arc.bloodarsenal.network.*;
import arc.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientHandler
{
    @SubscribeEvent
    public void onMouseEvent(MouseEvent event)
    {
        EntityPlayerSP player = Minecraft.getMinecraft().player;

        if (event.getDwheel() != 0 && player != null && player.isSneaking())
        {
            ItemStack stack = player.getHeldItemMainhand();

            if (stack.getItem() instanceof ItemSigilAugmentedHolding)
            {
                cycleSigil(stack, player, event.getDwheel());
                event.setCanceled(true);
            }
//            else if (stack.getItem() instanceof IProfilable)
//            {
//                cycleProfile(stack, player, event.getDwheel());
//                event.setCanceled(true);
//            }
        }
    }

    private void cycleSigil(ItemStack stack, EntityPlayer player, int dWheel)
    {
        int mode = dWheel;
        if (!ConfigHandler.sigilHoldingSkipsEmptySlots)
        {
            mode = ItemSigilAugmentedHolding.getCurrentItemOrdinal(stack);
            mode = dWheel < 0 ? BloodArsenalUtils.next(mode, ItemSigilAugmentedHolding.INVENTORY_SIZE) : BloodArsenalUtils.prev(mode, ItemSigilAugmentedHolding.INVENTORY_SIZE);
        }

        ItemSigilAugmentedHolding.cycleToNextSigil(stack, mode);
        BloodArsenalPacketHandler.INSTANCE.sendToServer(new SigilAugmentedHoldingPacketProcessor(player.inventory.currentItem, mode));
        ItemStack newStack = ItemSigilAugmentedHolding.getItemStackInSlot(stack, ItemSigilAugmentedHolding.getCurrentItemOrdinal(stack));
        player.sendStatusMessage(newStack.isEmpty() ? new TextComponentString("") : newStack.getTextComponent(), true);
    }

    private void cycleProfile(ItemStack itemStack, EntityPlayer player, int dWheel)
    {
        int mode;

        IProfilable profilable = BloodArsenalUtils.Profilable.getProfilable(itemStack);
        int maxProfiles = BloodArsenalUtils.Profilable.getMaxProfiles(itemStack);
        mode = BloodArsenalUtils.Profilable.getProfileIndex(itemStack);
        mode = dWheel < 0 ? BloodArsenalUtils.next(mode, maxProfiles) : BloodArsenalUtils.prev(mode, maxProfiles);

        if (profilable != null)
            BloodArsenalUtils.Profilable.setProfileIndex(itemStack, mode);

        BloodArsenalPacketHandler.INSTANCE.sendToServer(new ProfilablePacketProcessor(player.inventory.currentItem, mode));
    }
}
