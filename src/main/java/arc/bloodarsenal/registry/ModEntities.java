package arc.bloodarsenal.registry;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.entity.projectile.EntitySummonedSword;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities
{
    public static void init()
    {
        int id = 0;

        EntityRegistry.registerModEntity(EntitySummonedSword.class, "SummonedSword", id++, BloodArsenal.INSTANCE, 64, 20, true);
    }
}
