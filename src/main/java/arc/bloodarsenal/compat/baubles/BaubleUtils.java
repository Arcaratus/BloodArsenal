package arc.bloodarsenal.compat.baubles;

import baubles.api.BaublesApi;
import baubles.api.IBauble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class BaubleUtils
{
    public static ItemStack getBaubleStackInPlayer(EntityPlayer player, IBauble baubleThing)
    {
        if (player == null || baubleThing == null)
            return null;

        IInventory baublesInventory = BaublesApi.getBaubles(player);
        ItemStack bauble;

        for (int i = 0; i < baublesInventory.getSizeInventory(); i++)
        {
            bauble = baublesInventory.getStackInSlot(i);
            if (bauble.getItem() == baubleThing)
                return bauble;
        }

        return null;
    }
}
