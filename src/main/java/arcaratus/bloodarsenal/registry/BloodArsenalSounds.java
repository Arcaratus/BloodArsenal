package arcaratus.bloodarsenal.registry;

import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BloodArsenalSounds
{
    public static SoundEvent SOUND_CRIT;

    public static void init()
    {
        SOUND_CRIT = registerSound("crit");
    }

    private static SoundEvent registerSound(String name)
    {
        SoundEvent soundEvent = new SoundEvent(new ResourceLocation(BloodArsenal.MOD_ID, name));
        soundEvent.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(soundEvent);
        return soundEvent;
    }
}
