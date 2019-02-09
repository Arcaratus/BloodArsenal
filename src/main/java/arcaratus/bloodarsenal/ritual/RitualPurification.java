package arcaratus.bloodarsenal.ritual;

import WayofTime.bloodmagic.block.BlockLifeEssence;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.tile.TileBloodTank;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.block.BlockStasisPlate;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import arcaratus.bloodarsenal.tile.TileStasisPlate;
import com.google.common.collect.Sets;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;
import java.util.function.Consumer;

import static arcaratus.bloodarsenal.registry.ModRecipes.PURIFICATION_1;

@RitualRegister("purification")
public class RitualPurification extends RitualBloodArsenal
{
    private static final Set<BlockPos> STASIS_PLATE_POS = Sets.newHashSet(new BlockPos(2, 1, 0), new BlockPos(-2, 1, 0), new BlockPos(0, 1, 2), new BlockPos(0, 1, -2));
    private static final BlockPos INPUT_TANK_POS = new BlockPos(0, 3, 0);
    private static final BlockPos OUTPUT_TANK_POS = new BlockPos(0, 2, 0);

    private boolean active;
    private int fluidLeft;

    public RitualPurification()
    {
        super("purification", 0, ConfigHandler.rituals.purificationtRitualActivationCost, 1, ConfigHandler.rituals.purificationRitualRefreshCost);
        active = false;
        fluidLeft = 0;
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone, World world, SoulNetwork network)
    {
        BlockPos pos = masterRitualStone.getBlockPos();

        if (active && world.isRemote)
            world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D + world.rand.nextGaussian() / 8, pos.getY() + 1.5D + world.rand.nextGaussian() / 8, pos.getZ() + 0.5D + world.rand.nextGaussian() / 8, 0, 0, 0, 0);

        if (checkStructure(world, pos))
        {
            TileBloodTank inputTank = (TileBloodTank) world.getTileEntity(pos.add(INPUT_TANK_POS));
            TileBloodTank outputTank = (TileBloodTank) world.getTileEntity(pos.add(OUTPUT_TANK_POS));

            if (active)
            {
                if (fluidLeft > 0)
                {
                    tickRitual(world, pos, network, inputTank, outputTank);
                }
                else if (fluidLeft == 0)
                {
                    if (hasInputs(world, pos, PURIFICATION_1) && !(inputTank.getTank().getFluidAmount() == 0 || inputTank.getTank().getFluid().getFluid() != BlockLifeEssence.getLifeEssence() || inputTank.getTank().getFluidAmount() < ConfigHandler.rituals.purificationRitualMinLP))
                    {
                        fluidLeft = ConfigHandler.rituals.purificationRitualMinLP;
                        shrinkItemStackInputs(world, pos, PURIFICATION_1);
                    }
                    else
                    {
                        endRitual(world, pos, masterRitualStone);
                    }
                }

                return;
            }

            if (outputTank.getTank().getFluidAmount() != 0 && outputTank.getTank().getFluid().getFluid() != RegistrarBloodArsenalBlocks.FLUID_REFINED_LIFE_ESSENCE)
            {
                endRitual(world, pos, masterRitualStone);
                return;
            }

            if (inputTank.getTank().getFluidAmount() == 0 || inputTank.getTank().getFluid().getFluid() != BlockLifeEssence.getLifeEssence() || inputTank.getTank().getFluidAmount() < ConfigHandler.rituals.purificationRitualMinLP)
            {
                endRitual(world, pos, masterRitualStone);
                return;
            }

            if (!hasInputs(world, pos, PURIFICATION_1))
            {
                endRitual(world, pos, masterRitualStone);
                return;
            }

            shrinkItemStackInputs(world, pos, PURIFICATION_1);
            active = true;
            fluidLeft = ConfigHandler.rituals.purificationRitualMinLP;
        }
        else
        {
            endRitual(world, pos, masterRitualStone);
        }
    }

    private void tickRitual(World world, BlockPos pos, SoulNetwork network, TileBloodTank inputTank, TileBloodTank outputTank)
    {
        if (!active)
            return;

        int conversion = ConfigHandler.rituals.refinedLifeEssenceConversion;
        inputTank.getTank().drain(conversion, true);
        outputTank.getTank().fill(new FluidStack(RegistrarBloodArsenalBlocks.FLUID_REFINED_LIFE_ESSENCE, 1), true);

        world.notifyBlockUpdate(pos.add(OUTPUT_TANK_POS), world.getBlockState(pos.add(OUTPUT_TANK_POS)), world.getBlockState(pos.add(OUTPUT_TANK_POS)), 3);
        world.notifyBlockUpdate(pos.add(OUTPUT_TANK_POS), world.getBlockState(pos.add(OUTPUT_TANK_POS)), world.getBlockState(pos.add(OUTPUT_TANK_POS)), 3);

        fluidLeft -= conversion;
        network.syphon(SoulTicket.block(world, pos, getRefreshCost()));
    }

    private void endRitual(World world, BlockPos pos, IMasterRitualStone mrs)
    {
        List<TileStasisPlate> stasisPlates = getStasisPlates(world, pos);
        setStasisPlates(world, stasisPlates, false);
        mrs.setActive(false);
    }

    private boolean checkStructure(World world, BlockPos pos)
    {
        if (!(world.getTileEntity(pos.add(OUTPUT_TANK_POS)) instanceof TileBloodTank) || !(world.getTileEntity(pos.add(INPUT_TANK_POS)) instanceof TileBloodTank))
            return false;

        for (BlockPos stasisPlatePos : STASIS_PLATE_POS)
            if (!(world.getBlockState(pos.add(stasisPlatePos)).getBlock() instanceof BlockStasisPlate))
                return false;

        return true;
    }

    private List<TileStasisPlate> getStasisPlates(World world, BlockPos pos)
    {
        List<TileStasisPlate> stasisPlates = new ArrayList<>();
        for (BlockPos stasisPlatePos : STASIS_PLATE_POS)
            if (world.getTileEntity(pos.add(stasisPlatePos)) instanceof TileStasisPlate)
                stasisPlates.add((TileStasisPlate) world.getTileEntity(pos.add(stasisPlatePos)));

        return stasisPlates;
    }

    private void setStasisPlates(World world, List<TileStasisPlate> stasisPlates, boolean stasis)
    {
        for (TileStasisPlate plate : stasisPlates)
        {
            plate.setStasis(stasis);
            world.notifyBlockUpdate(plate.getPos(), world.getBlockState(plate.getPos()), world.getBlockState(plate.getPos()), 3);
        }
    }

    private List<ItemStack> getItemStackInputs(World world, BlockPos pos)
    {
        List<ItemStack> stackList = new ArrayList<>();
        for (BlockPos stasisPlatePos : STASIS_PLATE_POS)
        {
            BlockPos actualPos = pos.add(stasisPlatePos);
            if (world.getTileEntity(actualPos) instanceof TileStasisPlate)
            {
                TileStasisPlate plate = (TileStasisPlate) world.getTileEntity(actualPos);
                if (!plate.getStackInSlot(0).isEmpty())
                    stackList.add(plate.getStackInSlot(0));
            }
        }

        return stackList;
    }

    private boolean hasInputs(World world, BlockPos pos, Collection<ItemStack> ingredients)
    {
        List<ItemStack> inputs = getItemStackInputs(world, pos);
        for (ItemStack ing : ingredients)
        {
            boolean found = false;
            for (ItemStack inputStack : inputs)
            {
                if (ItemStack.areItemsEqual(ing, inputStack))
                {
                    found = true;
                    break;
                }
            }

            if (!found)
                return false;
        }

        return true;
    }

    private void shrinkItemStackInputs(World world, BlockPos pos, Collection<ItemStack> recipeInputs)
    {
        for (BlockPos stasisPlatePos : STASIS_PLATE_POS)
        {
            BlockPos platePosition = pos.add(stasisPlatePos);
            if (world.getTileEntity(platePosition) instanceof TileStasisPlate)
            {
                TileStasisPlate plate = (TileStasisPlate) world.getTileEntity(platePosition);
                ItemStack plateStack = plate.getStackInSlot(0);
                if (!plateStack.isEmpty())
                {
                    for (ItemStack recipeInput : recipeInputs)
                    {
                        if (ItemStack.areItemsEqual(recipeInput, plateStack))
                        {
                            plateStack.shrink(recipeInput.getCount());
                            plate.setInventorySlotContents(0, plateStack);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void gatherComponents(Consumer<RitualComponent> components)
    {
        addParallelRunes(components, 1, 0, EnumRuneType.BLANK);
        addCornerRunes(components, 1, 0, EnumRuneType.EARTH);
        addParallelRunes(components, 2, 0, EnumRuneType.FIRE);
        addOffsetRunes(components, 2, 1, 0, EnumRuneType.AIR);
        addCornerRunes(components, 2, 0, EnumRuneType.EARTH);
        addParallelRunes(components, 3, 0, EnumRuneType.BLANK);
        addOffsetRunes(components, 3, 1, 0, EnumRuneType.AIR);
        addOffsetRunes(components, 3, 2, 0, EnumRuneType.WATER);

        addCornerRunes(components, 4, 0, EnumRuneType.EARTH);
        addCornerRunes(components, 4, 1, EnumRuneType.EARTH);
        addCornerRunes(components, 4, 2, EnumRuneType.FIRE);
        addCornerRunes(components, 4, 3, EnumRuneType.FIRE);
        addCornerRunes(components, 4, 4, EnumRuneType.DUSK);
    }

    @Override
    public Ritual getNewCopy()
    {
        return new RitualPurification();
    }
}
