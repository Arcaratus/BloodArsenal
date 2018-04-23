package arcaratus.bloodarsenal.ritual.imperfect;

import WayofTime.bloodmagic.api.BlockStack;
import WayofTime.bloodmagic.api.ritual.imperfect.IImperfectRitualStone;
import WayofTime.bloodmagic.api.ritual.imperfect.ImperfectRitual;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ImperfectRitualLightning extends ImperfectRitual
{
    public ImperfectRitualLightning()
    {
        super("lightning", new BlockStack(Blocks.IRON_BLOCK), 10000, "ritual." + BloodArsenal.MOD_ID + ".imperfect.lightning");
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

    @Override
    public boolean isLightshow()
    {
        return false;
    }
}
