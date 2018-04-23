package arcaratus.bloodarsenal.ritual.imperfect;

import WayofTime.bloodmagic.api.BlockStack;
import WayofTime.bloodmagic.api.ritual.imperfect.IImperfectRitualStone;
import WayofTime.bloodmagic.api.ritual.imperfect.ImperfectRitual;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ImperfectRitualEnchantReset extends ImperfectRitual
{
    public ImperfectRitualEnchantReset()
    {
        super("enchant_reset", new BlockStack(Blocks.BOOKSHELF), 5000, "ritual." + BloodArsenal.MOD_ID + ".imperfect.enchant_reset");
    }

    @Override
    public boolean onActivate(IImperfectRitualStone imperfectRitualStone, EntityPlayer player)
    {
        World world = imperfectRitualStone.getRitualWorld();
        if (world.isRemote)
            return false;

        player.addExperienceLevel(1);
        player.removeExperienceLevel(1);

        return true;
    }

    @Override
    public boolean isLightshow()
    {
        return true;
    }
}
