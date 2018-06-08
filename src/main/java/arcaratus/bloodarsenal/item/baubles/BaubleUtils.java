package arcaratus.bloodarsenal.item.baubles;

import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BaubleUtils
{
    public static ItemStack getBaubleStackInPlayer(EntityPlayer player, IBauble baubleThing)
    {
        if (player == null || baubleThing == null)
            return null;

        IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);

        for (int i = 0; i < baubles.getSlots(); i++)
        {
            ItemStack bauble = baubles.getStackInSlot(i);
            if (bauble.getItem() == baubleThing)
                return bauble;
        }

        return ItemStack.EMPTY;
    }
}
