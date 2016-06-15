package arc.bloodarsenal.util;

import WayofTime.bloodmagic.api.impl.ItemSigilToggleable;
import arc.bloodarsenal.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BloodArsenalUtils
{
    public static RayTraceResult rayTrace(World world, EntityPlayer player, boolean useLiquids)
    {
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        double d0 = player.posX;
        double d1 = player.posY + (double) player.getEyeHeight();
        double d2 = player.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double range = ConfigHandler.rayTraceRange;
        Vec3d vec3d1 = vec3d.addVector((double) f6 * range, (double) f5 * range, (double) f7 * range);

        return world.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
    }

    public static boolean isSigilInInvAndActive(EntityPlayer player, Item item)
    {
        if (item instanceof ItemSigilToggleable)
            for (ItemStack itemStack : player.inventoryContainer.getInventory())
                if (itemStack != null && itemStack.getItem() == item)
                    return ((ItemSigilToggleable) itemStack.getItem()).getActivated(itemStack);

        return false;
    }
}
