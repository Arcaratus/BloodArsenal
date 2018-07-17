package arcaratus.bloodarsenal.client.hud;

import WayofTime.bloodmagic.client.hud.ElementRegistry;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.client.hud.element.ElementAugmentedHolding;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Vector2f;

public class BloodArsenalElements
{
    public static void registerElements()
    {
        ElementRegistry.registerHandler(
                new ResourceLocation(BloodArsenal.MOD_ID, "augmented_holding"),
                new ElementAugmentedHolding(),
                new Vector2f(0.72F, 1.0F)
        );

        ElementRegistry.readConfig();
    }
}
