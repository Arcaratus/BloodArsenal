package arc.bloodarsenal.ritual.imperfect;

import WayofTime.bloodmagic.api.BlockStack;
import WayofTime.bloodmagic.api.ritual.imperfect.IImperfectRitualStone;
import WayofTime.bloodmagic.api.ritual.imperfect.ImperfectRitual;
import WayofTime.bloodmagic.registry.ModBlocks;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ImperfectRitualSnow extends ImperfectRitual
{
    public ImperfectRitualSnow()
    {
        super("snow", new BlockStack(Blocks.SNOW), 500, "ritual." + BloodArsenal.MOD_ID + ".imperfect.snow");
    }

    @Override
    public boolean onActivate(IImperfectRitualStone imperfectRitualStone, EntityPlayer player)
    {
        World world = imperfectRitualStone.getRitualWorld();
        if (world.isRemote)
            return false;

        BlockPos ritualPos = imperfectRitualStone.getRitualPos();
        for (BlockPos blockPos : BloodArsenalUtils.getBlocksInRegion(world, ritualPos, new AxisAlignedBB(-1, -1, -1, 2, 1, 2), Blocks.WATER))
            if (world.getBlockState(blockPos).getBlock() != ModBlocks.RITUAL_CONTROLLER)
                world.setBlockState(blockPos, Blocks.SNOW.getDefaultState());

        return true;
    }

    @Override
    public boolean isLightshow()
    {
        return true;
    }
}
