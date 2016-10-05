package arc.bloodarsenal.registry;

import arc.bloodarsenal.potion.PotionBloodArsenal;
import arc.bloodarsenal.potion.PotionEventHandlers;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class ModPotions
{
    public static final Potion BLEEDING;

    static
    {
        BLEEDING = registerPotion("Bleeding", new ResourceLocation("BLEEDING"), true, 0xFF0000, 1, 0);
    }

    public static void init()
    {
        MinecraftForge.EVENT_BUS.register(new PotionEventHandlers());
    }

    protected static Potion registerPotion(String name, ResourceLocation location, boolean badEffect, int potionColour, int x, int y)
    {
        Potion potion = new PotionBloodArsenal(name, badEffect, potionColour, x, y);

        Potion.REGISTRY.register(-1, location, potion);

        return potion;
    }
}
