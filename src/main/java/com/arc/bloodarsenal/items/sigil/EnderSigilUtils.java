package com.arc.bloodarsenal.items.sigil;

import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EnderSigilUtils
{
    public static MovingObjectPosition getTargetBlock(World world, double x, double y, double z, float yaw, float pitch, boolean par3, double range)
    {
        Vec3 var13 = Vec3.createVectorHelper(x, y, z);
        float var14 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
        float var15 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
        float var16 = -MathHelper.cos(-pitch * 0.01745329F);
        float var17 = MathHelper.sin(-pitch * 0.01745329F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        Vec3 var23 = var13.addVector(var18 * range, var17 * range, var20 * range);

        return world.func_147447_a(var13, var23, par3, !par3, false);
    }
}
