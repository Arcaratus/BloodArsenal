package arcaratus.bloodarsenal.ritual.imperfect;

import WayofTime.bloodmagic.ritual.RitualRegister;
import WayofTime.bloodmagic.ritual.imperfect.IImperfectRitualStone;
import WayofTime.bloodmagic.ritual.imperfect.ImperfectRitual;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

@RitualRegister.Imperfect("enchant_reset")
public class ImperfectRitualEnchantReset extends ImperfectRitual
{
    public ImperfectRitualEnchantReset()
    {
        super("enchant_reset", s -> s.getBlock() == Blocks.BOOKSHELF, ConfigHandler.rituals.imperfect.imperfectEnchantResetActivationCost, true, "ritual." + BloodArsenal.MOD_ID + ".imperfect.enchant_reset");
    }

    @Override
    public boolean onActivate(IImperfectRitualStone imperfectRitualStone, EntityPlayer player)
    {
        World world = imperfectRitualStone.getRitualWorld();
        if (world.isRemote)
            return false;

        player.addExperienceLevel(1);
        player.addExperienceLevel(-1);

        return true;
    }
}
