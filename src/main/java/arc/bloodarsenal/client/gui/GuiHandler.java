package arc.bloodarsenal.client.gui;

import arc.bloodarsenal.item.inventory.ContainerAugmentedHolding;
import arc.bloodarsenal.item.inventory.InventoryAugmentedHolding;
import arc.bloodarsenal.registry.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (id)
        {
            case Constants.Gui.SIGIL_AUGMENTED_HOLDING_GUI:
                return new ContainerAugmentedHolding(player, new InventoryAugmentedHolding(player.getHeldItemMainhand()));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (id)
        {
            case Constants.Gui.SIGIL_AUGMENTED_HOLDING_GUI:
                return new GuiAugmentedHolding(player, new InventoryAugmentedHolding(player.getHeldItemMainhand()));
        }

        return null;
    }
}
