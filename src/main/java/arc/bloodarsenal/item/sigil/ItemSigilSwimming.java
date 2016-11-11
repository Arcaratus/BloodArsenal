package arc.bloodarsenal.item.sigil;

import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemSigilSwimming extends ItemSigilBaseToggleable
{
    public ItemSigilSwimming()
    {
        super("swimming", ConfigHandler.sigilSwimmingCost);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();

            if (BloodArsenalUtils.isSigilInInvAndActive(player, this) && (player.isInWater() || player.isInsideOfMaterial(Material.LAVA)))
            {
                player.setAir(300);

                if (player.getEntityWorld().isRemote)
                    GlStateManager.setFogDensity(0.01F);

                player.motionX *= 1.2D;

                if (player.motionY > 0.0D)
                    player.motionY *= 1.2D;

                player.motionZ *= 1.2D;

                double maxSpeed = 0.2D;

                if (player.motionX > maxSpeed)
                    player.motionX = maxSpeed;
                else if (player.motionX < -maxSpeed)
                    player.motionX = -maxSpeed;

                if (player.motionY > maxSpeed)
                    player.motionY = maxSpeed;

                if (player.motionZ > maxSpeed)
                    player.motionZ = maxSpeed;
                else if (player.motionZ < -maxSpeed)
                    player.motionZ = -maxSpeed;
            }
        }
    }

    @SubscribeEvent
    public void breakSpeed(PlayerEvent.BreakSpeed event)
    {
        EntityLivingBase entityLiving = event.getEntityLiving();

        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityLiving;

            if (BloodArsenalUtils.isSigilInInvAndActive(player, this)&& (entityLiving.isInWater() || entityLiving.isInsideOfMaterial(Material.LAVA)))
                event.setNewSpeed(event.getOriginalSpeed() * 3F);
        }
    }
}
