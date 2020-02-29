package arcaratus.bloodarsenal.client.gui;

import arcaratus.bloodarsenal.item.inventory.ContainerAugmentedHolding;
import arcaratus.bloodarsenal.item.inventory.InventoryAugmentedHolding;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.tile.TileAltareAenigmatica;
import arcaratus.bloodarsenal.tile.container.ContainerAltareAenigmatica;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);

        switch (id)
        {
            case Constants.Gui.SIGIL_AUGMENTED_HOLDING_GUI:
                return new ContainerAugmentedHolding(player, new InventoryAugmentedHolding(player.getHeldItemMainhand()), player.getHeldItemMainhand());
            case Constants.Gui.ALTARE_AENIGMATICA_GUI:
                return new ContainerAltareAenigmatica(player.inventory, (TileAltareAenigmatica) world.getTileEntity(pos));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (world instanceof WorldClient)
        {
            BlockPos pos = new BlockPos(x, y, z);

            switch (id)
            {
                case Constants.Gui.SIGIL_AUGMENTED_HOLDING_GUI:
                    return new GuiAugmentedHolding(player, new InventoryAugmentedHolding(player.getHeldItemMainhand()));
                case Constants.Gui.ALTARE_AENIGMATICA_GUI:
                    return new GuiAltareAenigmatica(player.inventory, (TileAltareAenigmatica) world.getTileEntity(pos));
            }
        }

        return null;
    }
}
