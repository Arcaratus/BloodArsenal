package arcaratus.bloodarsenal.item.block;

import WayofTime.bloodmagic.util.helper.TextHelper;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlockBloodCapacitor extends ItemBlock
{
    public ItemBlockBloodCapacitor(Block block)
    {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.energy", getEnergyStored(stack)));
    }

    public int getEnergyStored(ItemStack stack)
    {
        int energy = 0;
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Stored"))
            energy = stack.getTagCompound().getInteger("Stored");

        return energy;
    }
}
