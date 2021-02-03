package arcaratus.bloodarsenal.common.util;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.world.World;

public class Utils
{
    public static void lightningBolt(World world, double x, double y, double z, boolean effectOnly)
    {
        LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.setPosition(x, y, z);
        lightning.setEffectOnly(effectOnly);
        world.addEntity(lightning);
    }
}
