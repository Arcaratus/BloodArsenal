package arcaratus.bloodarsenal.ritual.imperfect;

import WayofTime.bloodmagic.ritual.imperfect.IImperfectRitualStone;
import WayofTime.bloodmagic.ritual.imperfect.ImperfectRitual;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.ConfigHandler;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ImperfectRitualLightning extends ImperfectRitual
{
    public ImperfectRitualLightning()
    {
        super("lightning", s -> s.getBlock() == Blocks.IRON_BLOCK, ConfigHandler.rituals.imperfect.imperfectLightningActivationCost, false, "ritual." + BloodArsenal.MOD_ID + ".imperfect.lightning");
    }

    @Override
    public boolean onActivate(IImperfectRitualStone imperfectRitualStone, EntityPlayer player)
    {
        World world = imperfectRitualStone.getRitualWorld();
        if (world.isRemote)
            return false;

        BlockPos pos = imperfectRitualStone.getRitualPos();
        world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY() + 2, pos.getZ(), false));

        return true;
    }
}
