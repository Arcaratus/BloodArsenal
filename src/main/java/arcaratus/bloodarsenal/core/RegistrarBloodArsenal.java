package arcaratus.bloodarsenal.core;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.entity.projectile.EntitySummonedTool;
import arcaratus.bloodarsenal.entity.projectile.EntityWarpBlade;
import arcaratus.bloodarsenal.potion.PotionBloodArsenal;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.*;

@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
@GameRegistry.ObjectHolder(BloodArsenal.MOD_ID)
public class RegistrarBloodArsenal
{
    public static final Potion BLEEDING = MobEffects.HASTE;

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().registerAll(
                new PotionBloodArsenal("Bleeding", "bleeding", true, 0xFF0000, 1, 0)
        );
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        int entities = 0;
        final int chunk = 16;
        event.getRegistry().registerAll(
                EntityEntryBuilder.create().id("summoned_tool", ++entities).entity(EntitySummonedTool.class).name("summoned_tool").tracker(chunk * 4, 3, true).build(),
                EntityEntryBuilder.create().id("warp_blade", ++entities).entity(EntityWarpBlade.class).name("warp_blade").tracker(chunk * 4, 3, true).build()
        );
    }
}
