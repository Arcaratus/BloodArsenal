package arc.bloodarsenal.item.tool;

import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.registry.ModItems;
import net.minecraft.block.Block;

import java.util.Set;

public class ItemBloodInfusedWoodenTool extends ItemBloodInfusedTool
{
    public ItemBloodInfusedWoodenTool(String name, float damage, Set<Block> effectiveBlocks)
    {
        super(Constants.Item.WOODEN, ModItems.bloodInfusedWoodMaterial, name, damage, effectiveBlocks, 18, Constants.Item.WOODEN_REPAIR_UPDATE, Constants.Item.WOODEN_REPAIR_COST);
    }
}
