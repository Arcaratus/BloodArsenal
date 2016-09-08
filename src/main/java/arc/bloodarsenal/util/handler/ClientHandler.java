package arc.bloodarsenal.util.handler;

import WayofTime.bloodmagic.ConfigHandler;
import arc.bloodarsenal.item.sigil.ItemSigilAugmentedHolding;
import arc.bloodarsenal.network.BloodArsenalPacketHandler;
import arc.bloodarsenal.network.SigilAugmentedHoldingPacketProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        if (event.getDwheel() != 0 && player != null && player.isSneaking())
        {
            ItemStack stack = player.getHeldItemMainhand();

            if (stack != null)
            {
                Item item = stack.getItem();

                if (item instanceof ItemSigilAugmentedHolding)
                {
                    cycleSigil(stack, player, event.getDwheel());
                    event.setCanceled(true);
                }
            }
        }
    }

    private void cycleSigil(ItemStack stack, EntityPlayer player, int dWheel)
    {
        int mode = dWheel;
        if (!ConfigHandler.sigilHoldingSkipsEmptySlots)
        {
            mode = ItemSigilAugmentedHolding.getCurrentItemOrdinal(stack);
            mode = dWheel < 0 ? ItemSigilAugmentedHolding.next(mode) : ItemSigilAugmentedHolding.prev(mode);
        }

        ItemSigilAugmentedHolding.cycleToNextSigil(stack, mode);
        BloodArsenalPacketHandler.INSTANCE.sendToServer(new SigilAugmentedHoldingPacketProcessor(player.inventory.currentItem, mode));
        ItemStack newStack = ItemSigilAugmentedHolding.getItemStackInSlot(stack, ItemSigilAugmentedHolding.getCurrentItemOrdinal(stack));
        if (newStack != null)
            Minecraft.getMinecraft().ingameGUI.setRecordPlaying(newStack.getDisplayName(), false);
    }
}
