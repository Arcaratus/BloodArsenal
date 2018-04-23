package arcaratus.bloodarsenal.registry;

import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.entity.projectile.EntitySummonedTool;
import arcaratus.bloodarsenal.entity.projectile.EntityWarpBlade;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities
{
    public static void init()
    {
        int id = 0;
        EntityRegistry.registerModEntity(new ResourceLocation(BloodArsenal.MOD_ID, "SummonedTool"), EntitySummonedTool.class, "SummonedTool", ++id, BloodArsenal.INSTANCE, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(BloodArsenal.MOD_ID, "WarpBlade"), EntityWarpBlade.class, "WarpBlade", ++id, BloodArsenal.INSTANCE, 128, 1, true);
    }
}
