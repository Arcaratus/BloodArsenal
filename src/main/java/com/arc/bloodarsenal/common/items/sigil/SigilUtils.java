package com.arc.bloodarsenal.common.items.sigil;

import com.arc.bloodarsenal.common.items.sigil.holding.AHPacketHandler;
import com.arc.bloodarsenal.common.items.sigil.holding.AHPacketProcessor;
import com.arc.bloodarsenal.common.items.sigil.holding.SigilAugmentedHolding;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;

public class SigilUtils
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

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(MouseEvent event)
    {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;

        if (event.dwheel != 0 && player != null && player.isSneaking())
        {
            ItemStack stack = player.getCurrentEquippedItem();

            if (stack != null)
            {
                Item item = stack.getItem();

                if (item instanceof SigilAugmentedHolding)
                {
                    cycleSigil(stack, player, event.dwheel);
                    event.setCanceled(true);
                }
            }
        }
    }

    private void cycleSigil(ItemStack stack, EntityPlayer player, int dWheel)
    {
        int mode = SigilAugmentedHolding.getCurrentItem(stack);
        mode = dWheel < 0 ? SigilAugmentedHolding.next(mode) : SigilAugmentedHolding.prev(mode);
        SigilAugmentedHolding.cycleSigil(stack, mode);
        AHPacketHandler.INSTANCE.sendToServer(new AHPacketProcessor(player.inventory.currentItem, mode));
    }
}
