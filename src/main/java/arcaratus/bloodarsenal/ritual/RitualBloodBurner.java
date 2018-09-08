package arcaratus.bloodarsenal.ritual;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.tile.TileBloodTank;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalBlocks;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.item.types.EnumBaseTypes;
import arcaratus.bloodarsenal.tile.TileStasisPlate;
import arcaratus.bloodarsenal.util.TriFunction;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@RitualRegister("blood_burner")
public class RitualBloodBurner extends RitualBloodArsenal
{
    private static final Set<BlockPos> FIRE_POS = Sets.newHashSet(new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(1, 0, 1), new BlockPos(-1, 0, 1), new BlockPos(1, 0, -1), new BlockPos(-1, 0, -1));
    private static final Set<BlockPos> GLOWSTONE_POS = Sets.newHashSet(new BlockPos(2, 1, 0), new BlockPos(-2, 1, 0), new BlockPos(0, 1, 2), new BlockPos(0, 1, -2), new BlockPos(2, 1, 1), new BlockPos(2, 1, -1), new BlockPos(-2, 1, 1), new BlockPos(-2, 1, -1), new BlockPos(1, 1, 2), new BlockPos(1, 1, -2), new BlockPos(-1, 1, 2), new BlockPos(-1, 1, -2), new BlockPos(2, 1, 2), new BlockPos(2, 1, -2), new BlockPos(-2, 1, 2), new BlockPos(-2, 1, -2));
    private static final Set<BlockPos> LAVA_POS = Sets.newHashSet(new BlockPos(3, 1, 1), new BlockPos(3, 1, -1), new BlockPos(-3, 1, 1), new BlockPos(-3, 1, -1), new BlockPos(3, 1, 2), new BlockPos(-3, 1, 2), new BlockPos(3, 1, -2), new BlockPos(-3, 1, -2), new BlockPos(1, 1, 3), new BlockPos(-1, 1, 3), new BlockPos(1, 1, -3), new BlockPos(-1, 1, -3), new BlockPos(2, 1, 3), new BlockPos(-2, 1, 3), new BlockPos(2, 1, -3), new BlockPos(-2, 1, -3));
    private static final Set<BlockPos> LIFE_ESSENCE_POS = Sets.newHashSet(new BlockPos(4, 1, 0), new BlockPos(-4, 1, 0), new BlockPos(0, 1, 4), new BlockPos(0, 1, -4), new BlockPos(4, 1, 2), new BlockPos(-4, 1, 2), new BlockPos(4, 1, -2), new BlockPos(-4, 1, -2), new BlockPos(2, 1, 4), new BlockPos(-2, 1, 4), new BlockPos(2, 1, -4), new BlockPos(-2, 1, -4), new BlockPos(4, 1, 4), new BlockPos(-4, 1, 4), new BlockPos(4, 1, -4), new BlockPos(-4, 1, -4));

    private static final Set<Item> IGNITERS = Sets.newHashSet(Items.FLINT_AND_STEEL, RegistrarBloodArsenalItems.BOUND_IGNITER);

    private static final BiFunction<Integer, Integer, BiFunction<Double, Integer, Integer>> TOTAL_RF = (a, g) -> (u, l) -> (int) (u * ((double) a * (g + 1D) - 0.8 * l));
    private static final TriFunction<Integer, Integer, Integer, Integer> TIME = (a, g, l) -> (int) (20D * (3D * a * (g + 8D)) / (1322D * Math.pow(1.00036, l) + 16425.5 - 0.68756 * l));

    private boolean active;
    private int secondsLeft;
    private int rateRF;

    public RitualBloodBurner()
    {
        super("blood_burner", 0, ConfigHandler.rituals.bloodBurnerRitualActivationCost, 1, ConfigHandler.rituals.bloodBurnerRitualRefreshCost);
        active = false;
        secondsLeft = 0;
        rateRF = 0;
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone, World world, SoulNetwork network)
    {
        BlockPos pos = masterRitualStone.getBlockPos();

        if (checkStructure(world, pos))
        {
            List<TileBloodTank> lavaTanks = getTanks(world, pos, LAVA_POS);
            List<TileBloodTank> lifeEssenceTanks = getTanks(world, pos, LIFE_ESSENCE_POS);

            int lavaAmount = getFluidNumber(lavaTanks, true);
            int lifeEssenceAmount = getFluidNumber(lifeEssenceTanks, false);

            if (active)
            {
                if (secondsLeft > 0 && rateRF > 0)
                {
                    TileEntity energyHandler = world.getTileEntity(pos.add(0, 2, 0));
                    if (energyHandler != null && energyHandler.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN))
                    {
                        world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5, pos.getY() + 4.5, + pos.getZ() + 0.5, 0, 0, 0, 0);
                        network.syphon(SoulTicket.block(world, pos, rateRF / 100));
                        energyHandler.getCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN).receiveEnergy(rateRF, false);
                        secondsLeft--;
                    }
                }
                else if (secondsLeft == 0)
                {
                    if (lifeEssenceAmount >= Fluid.BUCKET_VOLUME)
                    {
                        startBurn(world, pos, lavaAmount, lifeEssenceAmount, lavaTanks, lifeEssenceTanks);
                    }

                    endRitual(world, pos, masterRitualStone);
                }
            }
            else if (lifeEssenceAmount >= Fluid.BUCKET_VOLUME)
            {
                startBurn(world, pos, lavaAmount, lifeEssenceAmount, lavaTanks, lifeEssenceTanks);
            }
            else
            {
                endRitual(world, pos, masterRitualStone);
            }
        }
        else
        {
            endRitual(world, pos, masterRitualStone);
        }
    }

    private void startBurn(World world, BlockPos pos, int lavaAmount, int lifeEssenceAmount, List<TileBloodTank> lavaTanks, List<TileBloodTank> lifeEssenceTanks)
    {
        TileStasisPlate stasisPlate = (TileStasisPlate) world.getTileEntity(pos.add(0, 1, 0));
        ItemStack igniter = stasisPlate.getStackInSlot(0);
        double u = igniter.getItem() == RegistrarBloodArsenalItems.BOUND_IGNITER ? 1D : 0.5;
        int g = (int) Math.pow((double) getTotalDustAmount(getStasisPlates(world, pos)) / 2D, 2);

        int time = TIME.apply(lifeEssenceAmount, g, lavaAmount);
        int totalRF = TOTAL_RF.apply(lifeEssenceAmount, g).apply(u, lavaAmount);
        int rate = totalRF / time;

        active = true;
        secondsLeft = time;
        rateRF = rate;

        for (TileBloodTank tank : Iterables.concat(lifeEssenceTanks, lavaTanks))
            tank.getTank().drain(Fluid.BUCKET_VOLUME, true);

        shrinkInputs(world, pos);
        world.spawnEntity(new EntityLightningBolt(world, pos.getX() + 6, pos.getY() + 5, pos.getZ() + 6, false));
        world.spawnEntity(new EntityLightningBolt(world, pos.getX() + 6, pos.getY() + 5, pos.getZ() - 6, false));
        world.spawnEntity(new EntityLightningBolt(world, pos.getX() - 6, pos.getY() + 5, pos.getZ() + 6, false));
        world.spawnEntity(new EntityLightningBolt(world, pos.getX() - 6, pos.getY() + 5, pos.getZ() - 6, false));
    }

    private void endRitual(World world, BlockPos pos, IMasterRitualStone mrs)
    {
        world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0, false);
        mrs.setActive(false);
    }

    private boolean checkStructure(World world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos.add(0, 2, 0));
        if (tile == null || !tile.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN))
            return false;

        tile = world.getTileEntity(pos.add(0, 1, 0));
        if (!(tile instanceof TileStasisPlate))
            return false;

        ItemStack igniter = ((TileStasisPlate) tile).getStackInSlot(0);
        if (igniter.isEmpty() || !IGNITERS.contains(igniter.getItem()))
            return false;

        for (BlockPos firePos : FIRE_POS)
            if (!(world.getBlockState(pos.add(firePos)).getBlock() instanceof BlockFire))
                return false;

        return true;
    }

    private List<TileBloodTank> getTanks(World world, BlockPos pos, Collection<BlockPos> tankPositions)
    {
        List<TileBloodTank> tanks = new ArrayList<>();
        for (BlockPos tankPos : tankPositions)
        {
            BlockPos actualPos = pos.add(tankPos);
            TileEntity tank = world.getTileEntity(actualPos);
            if (tank instanceof TileBloodTank)
            {
                world.markAndNotifyBlock(actualPos, world.getChunkFromBlockCoords(actualPos), world.getBlockState(actualPos), world.getBlockState(actualPos), 3);

                if (((TileBloodTank) tank).getTank().getFluidAmount() >= Fluid.BUCKET_VOLUME)
                {
                    tanks.add((TileBloodTank) tank);
                }
            }
        }

        return tanks;
    }

    private int getFluidNumber(List<TileBloodTank> tanks, boolean lava)
    {
        if (lava)
            return Fluid.BUCKET_VOLUME * tanks.stream().mapToInt(t -> (Math.min(t.getTank().getFluidAmount(), Fluid.BUCKET_VOLUME) / Fluid.BUCKET_VOLUME)).sum();
        else
            return Fluid.BUCKET_VOLUME * tanks.stream().mapToInt(t -> (t.getTank().getFluid().getFluid() == RegistrarBloodArsenalBlocks.FLUID_REFINED_LIFE_ESSENCE ? 2 : 1) * (Math.min(t.getTank().getFluidAmount(), Fluid.BUCKET_VOLUME) / Fluid.BUCKET_VOLUME)).sum() / 2;
    }

    private List<TileStasisPlate> getStasisPlates(World world, BlockPos pos)
    {
        List<TileStasisPlate> plates = new ArrayList<>();
        for (BlockPos platePos : GLOWSTONE_POS)
            if (world.getTileEntity(pos.add(platePos)) instanceof TileStasisPlate)
                plates.add((TileStasisPlate) world.getTileEntity(pos.add(platePos)));

        return plates;
    }

    private int getTotalDustAmount(List<TileStasisPlate> stasisPlates)
    {
        int amount = 0;
        for (TileStasisPlate stasisPlate : stasisPlates)
            if (!stasisPlate.getStackInSlot(0).isEmpty())
                if (stasisPlate.getStackInSlot(0).getItem() == Items.GLOWSTONE_DUST)
                    amount++;
                else if (stasisPlate.getStackInSlot(0).getItem() == EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getItem())
                    amount += 2;

        return amount;
    }

    private void shrinkInputs(World world, BlockPos pos)
    {
        for (BlockPos stasisPos : GLOWSTONE_POS)
        {
            if (world.getTileEntity(pos.add(stasisPos)) instanceof TileStasisPlate)
            {
                TileStasisPlate stasisPlate = (TileStasisPlate) world.getTileEntity(pos.add(stasisPos));
                ItemStack plateStack = stasisPlate.getStackInSlot(0);
                if (!plateStack.isEmpty() && (ItemStack.areItemsEqual(plateStack, new ItemStack(Items.GLOWSTONE_DUST)) || ItemStack.areItemsEqual(plateStack, EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getStack())))
                {
                    plateStack.shrink(1);
                    stasisPlate.setInventorySlotContents(0, plateStack);
                }
            }
        }
    }

    @Override
    public void gatherComponents(Consumer<RitualComponent> components)
    {
        addRune(components, 0, -1, 0, EnumRuneType.FIRE);
        addParallelRunes(components, 2, -1, EnumRuneType.FIRE);
        addOffsetRunes(components, 1, 2, -1, EnumRuneType.FIRE);
        addCornerRunes(components, 2, -1, EnumRuneType.BLANK);
        addParallelRunes(components, 2, 0, EnumRuneType.AIR);
        addOffsetRunes(components, 1, 2, 0, EnumRuneType.AIR);
        addCornerRunes(components, 2, 0, EnumRuneType.AIR);
        addParallelRunes(components, 3, 0, EnumRuneType.DUSK);
        addOffsetRunes(components, 3, 1, 0, EnumRuneType.FIRE);
        addOffsetRunes(components, 3, 2, 0, EnumRuneType.FIRE);
        addCornerRunes(components, 3, 0, EnumRuneType.BLANK);
        addParallelRunes(components, 4, 0, EnumRuneType.WATER);
        addOffsetRunes(components, 4, 1, 0, EnumRuneType.EARTH);
        addOffsetRunes(components, 4, 2, 0, EnumRuneType.WATER);
        addOffsetRunes(components, 4, 3, 0, EnumRuneType.EARTH);
        addCornerRunes(components, 4, 0, EnumRuneType.WATER);

        addOffsetRunes(components, 6, 5, -1, EnumRuneType.DUSK);
        addCornerRunes(components, 6, -1, EnumRuneType.DUSK);
        addCornerRunes(components, 5, -1, EnumRuneType.DUSK);

        for (int y = 0; y < 6; y++)
        {
            if (y % 2 == 0)
                addOffsetRunes(components, 6, 5, y, EnumRuneType.FIRE);

            addCornerRunes(components, 6, y, EnumRuneType.FIRE);
        }
    }

    @Override
    public Ritual getNewCopy()
    {
        return new RitualBloodBurner();
    }
}
