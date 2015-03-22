package com.arc.bloodarsenal.items.sigil;

import net.minecraft.entity.Entity;
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
        double var21 = range;
        Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);

        return world.func_147447_a(var13, var23, par3, !par3, false);
    }

    public static MovingObjectPosition getTargetBlock(World world, Entity entity, boolean par3)
    {
        float var4 = 1.0F;
        float var5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * var4;

        float var6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * var4;

        double var7 = entity.prevPosX + (entity.posX - entity.prevPosX) * var4;

        double var9 = entity.prevPosY + (entity.posY - entity.prevPosY) * var4 + 1.62D - entity.getYOffset();//field_70129_M

        double var11 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * var4;

        Vec3 var13 = Vec3.createVectorHelper(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.141593F);
        float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.141593F);
        float var16 = -MathHelper.cos(-var5 * 0.01745329F);
        float var17 = MathHelper.sin(-var5 * 0.01745329F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = 10.0D;
        Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);

        return world.func_147447_a(var13, var23, par3, !par3, false);
    }
}
