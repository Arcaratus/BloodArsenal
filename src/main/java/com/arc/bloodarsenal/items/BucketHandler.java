package com.arc.bloodarsenal.items;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class BucketHandler
{
    private static BucketHandler INSTANCE = new BucketHandler();
    private static HashMap<Block, ItemStack> FILLED_BUCKETS = new HashMap();

    public static BucketHandler getInstance()
    {
        return INSTANCE;
    }

    public static void addBuckets()
    {
        MoltenBucket bucket = new MoltenBucket(null);
        GameRegistry.registerItem(bucket, "extra.bucket");

        int bucketVol = 1000;
        ItemStack emptyBucketStack = FluidContainerRegistry.EMPTY_BUCKET.copy();



        FluidStack fluidStack = FluidRegistry.getFluidStack("molten.blood.infused.iron", bucketVol);

        if (fluidStack != null)
        {
            int bucketId = 1;

            bucket.addTextureMapping(bucketId, fluidStack.getFluid().getName(), "bucket_fill_blood_infused_iron");

            ItemStack bucketStack = new ItemStack(bucket, 1, bucketId);

            FILLED_BUCKETS.put(fluidStack.getFluid().getBlock(), bucketStack);

            FluidContainerRegistry.registerFluidContainer(fluidStack, bucketStack, emptyBucketStack);
        }
    }

    public void fillEvent(FillBucketEvent event)
    {
        World world = event.world;
        MovingObjectPosition pos = event.target;

        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

        ItemStack bucketStack = getFilledBucket(block);

        if (bucketStack == null)
        {
            return;
        }

        world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);

        event.result = bucketStack;
        event.setResult(Event.Result.ALLOW);
    }

    public static HashMap<Block, ItemStack> getAllBuckets()
    {
        return FILLED_BUCKETS;
    }

    public static ItemStack getFilledBucket(Block fluidBlock)
    {
        return FILLED_BUCKETS.get(fluidBlock);
    }

    public static ItemStack getFilledBucket(String fluidName)
    {
        Block fluidBlock = FluidRegistry.getFluid(fluidName).getBlock();
        return FILLED_BUCKETS.get(fluidBlock).copy();
    }

    public static boolean isAvailableBucket(int fluidBlockId)
    {
        return FILLED_BUCKETS.containsKey(Integer.valueOf(fluidBlockId));
    }

    public static boolean isAvailableBucket(String fluidName)
    {
        Block fluidBlock = FluidRegistry.getFluid(fluidName).getBlock();
        return FILLED_BUCKETS.containsKey(fluidBlock);
    }

    public static Block getBlock(ItemStack bucketStack)
    {
        Block block = Blocks.air;
        for (Map.Entry<Block, ItemStack> entry : FILLED_BUCKETS.entrySet())
        {
            ItemStack stack = entry.getValue();
            if (stack.isItemEqual(bucketStack))
            {
                block = entry.getKey();
                break;
            }
        }
        return block;
    }
}
