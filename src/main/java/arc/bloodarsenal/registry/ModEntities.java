package arc.bloodarsenal.registry;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.entity.projectile.EntitySummonedTool;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities
{
    public static void init()
    {
        int id = 0;
        EntityRegistry.registerModEntity(new ResourceLocation(BloodArsenal.MOD_ID, "SummonedTool"), EntitySummonedTool.class, "SummonedTool", ++id, BloodArsenal.INSTANCE, 64, 3, true);
    }
}
