package arcaratus.bloodarsenal.common.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Utils
{
    public static void lightningBolt(World world, double x, double y, double z, boolean effectOnly)
    {
        LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.setPosition(x, y, z);
        lightning.setEffectOnly(effectOnly);
        world.addEntity(lightning);
    }

    // Modified version of the PistonBlock#canPush to allow tiles
    public static boolean canPush(BlockState blockStateIn, World worldIn, BlockPos pos, Direction facing, boolean destroyBlocks, Direction direction)
    {
        if (pos.getY() >= 0 && pos.getY() <= worldIn.getHeight() - 1 && worldIn.getWorldBorder().contains(pos))
        {
            if (blockStateIn.isAir())
            {
                return true;
            }
            else if (!blockStateIn.isIn(Blocks.OBSIDIAN) && !blockStateIn.isIn(Blocks.CRYING_OBSIDIAN) && !blockStateIn.isIn(Blocks.RESPAWN_ANCHOR))
            {
                if (facing == Direction.DOWN && pos.getY() == 0)
                {
                    return false;
                }
                else if (facing == Direction.UP && pos.getY() == worldIn.getHeight() - 1)
                {
                    return false;
                }
                else
                {
                    if (!blockStateIn.isIn(Blocks.PISTON) && !blockStateIn.isIn(Blocks.STICKY_PISTON))
                    {
                        if (blockStateIn.getBlockHardness(worldIn, pos) == -1.0F)
                        {
                            return false;
                        }

                        switch (blockStateIn.getPushReaction())
                        {
                            case BLOCK:
                                return false;
                            case DESTROY:
                                return destroyBlocks;
                            case PUSH_ONLY:
                                return facing == direction;
                        }
                    }

                    return true;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}
