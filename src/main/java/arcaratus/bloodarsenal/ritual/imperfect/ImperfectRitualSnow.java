package arcaratus.bloodarsenal.ritual.imperfect;

import WayofTime.bloodmagic.core.RegistrarBloodMagicBlocks;
import WayofTime.bloodmagic.ritual.imperfect.IImperfectRitualStone;
import WayofTime.bloodmagic.ritual.imperfect.ImperfectRitual;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ImperfectRitualSnow extends ImperfectRitual
{
    public ImperfectRitualSnow()
    {
        super("snow", s -> s.getBlock() == Blocks.SNOW, ConfigHandler.rituals.imperfect.imperfectSnowActivationCost, true, "ritual." + BloodArsenal.MOD_ID + ".imperfect.snow");
    }

    @Override
    public boolean onActivate(IImperfectRitualStone imperfectRitualStone, EntityPlayer player)
    {
        World world = imperfectRitualStone.getRitualWorld();
        if (world.isRemote)
            return false;

        BlockPos ritualPos = imperfectRitualStone.getRitualPos();
        for (BlockPos blockPos : BloodArsenalUtils.getBlocksInRegion(world, ritualPos, new AxisAlignedBB(-1, -1, -1, 2, 1, 2), Blocks.WATER))
            if (world.getBlockState(blockPos).getBlock() != RegistrarBloodMagicBlocks.RITUAL_CONTROLLER)
                world.setBlockState(blockPos, Blocks.SNOW.getDefaultState());

        return true;
    }
}
