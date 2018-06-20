package arcaratus.bloodarsenal.compat.tconstruct;

import WayofTime.bloodmagic.util.helper.NetworkHelper;
import arcaratus.bloodarsenal.ConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitLiving extends AbstractTraitLeveled
{
    public TraitLiving(int level)
    {
        super("living", 0x7A1E0E, 3, level);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (!world.isRemote && tool.isItemDamaged() && entity instanceof EntityPlayer && random.nextBoolean())
        {
            ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, name));
            int time = 0;
            int cost = 0;
            switch (data.level)
            {
                case 1:
                    time = ConfigHandler.values.bloodInfusedWoodenToolsRepairUpdate;
                    cost = 2 * ConfigHandler.values.bloodInfusedWoodenToolsRepairCost;
                    break;
                case 2:
                    time = ConfigHandler.values.bloodInfusedIronToolsRepairUpdate;
                    cost = 2 * ConfigHandler.values.bloodInfusedIronToolsRepairCost;
                    break;
            }

            if (world.getWorldTime() % time == 0)
            {
                if (NetworkHelper.syphonFromContainer(tool, cost))
                    ToolHelper.healTool(tool, 1, (EntityLivingBase) entity);
            }
        }
    }
}
