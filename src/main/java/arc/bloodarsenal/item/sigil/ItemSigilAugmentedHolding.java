package arc.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.item.inventory.InventoryHolding;
import WayofTime.bloodmagic.item.sigil.ItemSigilHolding;
import WayofTime.bloodmagic.util.handler.BMKeyBinding;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemSigilAugmentedHolding extends ItemSigilHolding
{
    public ItemSigilAugmentedHolding()
    {
        setUnlocalizedName(BloodArsenal.MOD_ID + ".sigil.augmentedHolding");
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        inventorySize = 9;
    }

    @Override
    public void onKeyPressed(ItemStack stack, EntityPlayer player, BMKeyBinding.Key key, boolean showInChat)
    {
        if (stack == player.getHeldItemMainhand() && stack.getItem() instanceof ItemSigilHolding && key.equals(BMKeyBinding.Key.OPEN_SIGIL_HOLDING))
        {
            InventoryHolding.setUUID(stack);
            player.openGui(BloodArsenal.INSTANCE, Constants.Gui.SIGIL_AUGMENTED_HOLDING_GUI, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }
}
