package arcaratus.bloodarsenal.util;

import WayofTime.bloodmagic.item.sigil.ItemSigilToggleable;
import WayofTime.bloodmagic.util.helper.NBTHelper;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.item.IProfilable;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.tile.TileInventory;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

import static arcaratus.bloodarsenal.registry.Constants.Item.*;

public class BloodArsenalUtils
{
    /**
     * @see #insertItemIntoTile(EntityPlayer, int, TileInventory, int)
     */
    public static boolean insertItemIntoTile(EntityPlayer player, TileInventory tile)
    {
        return insertItemIntoTile(player, 1, tile, 0);
    }

    /**
     * @see #insertItemIntoTile(EntityPlayer, int, TileInventory, int)
     */
    public static boolean insertItemIntoTile(EntityPlayer player, int amount, TileInventory tile)
    {
        return insertItemIntoTile(player, amount, tile, 0);
    }

    /**
     * Used for inserting an ItemStack with a stacksize of 1 to a tile's
     * inventory at slot 0
     * <p/>
     * EG: Block Altar
     *
     * @param player - The player to take the item from.
     * @param tile   - The {@link TileInventory} to input the item to
     * @param slot   - The slot to attempt to insert to
     * @return {@code true} if the ItemStack is inserted, {@code false}
     * otherwise
     */
    public static boolean insertItemIntoTile(EntityPlayer player, int amount, TileInventory tile, int slot)
    {
        if (tile.getStackInSlot(slot).isEmpty() && !player.getHeldItemMainhand().isEmpty())
        {
            ItemStack input = player.getHeldItemMainhand().copy();
            input.setCount(amount);
            player.getHeldItemMainhand().shrink(amount);
            tile.setInventorySlotContents(slot, input);
            return true;
        }
        else if (!tile.getStackInSlot(slot).isEmpty() && player.getHeldItemMainhand().isEmpty())
        {
            if (!tile.getWorld().isRemote)
            {
                EntityItem invItem = new EntityItem(tile.getWorld(), player.posX, player.posY + 0.25, player.posZ, tile.getStackInSlot(slot));
                tile.getWorld().spawnEntity(invItem);
            }
            tile.clear();
            return false;
        }

        return false;
    }

    public static RayTraceResult rayTrace(World world, EntityPlayer player, boolean useLiquids)
    {
        if (world == null || player == null)
            return null;

        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        double d0 = player.posX;
        double d1 = player.posY + (double) player.getEyeHeight();
        double d2 = player.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double range = ConfigHandler.misc.rayTraceRange;
        Vec3d vec3d1 = vec3d.add((double) f6 * range, (double) f5 * range, (double) f7 * range);

        return world.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
    }

    public static void writePosToNBT(NBTTagCompound tag, int x, int y, int z)
    {
        writePosToNBT(tag, new BlockPos(x, y, z));
    }

    public static void writePosToNBT(NBTTagCompound tag, BlockPos pos)
    {
        tag.setLong(Constants.NBT.POS, pos.toLong());
    }

    public static void writePosToStack(ItemStack stack, BlockPos pos)
    {
        writePosToNBT(NBTHelper.checkNBT(stack).getTagCompound(), pos);
    }

    public static BlockPos getPosFromNBT(NBTTagCompound tag)
    {
        return tag.hasKey(Constants.NBT.POS) ? BlockPos.fromLong(tag.getLong(Constants.NBT.POS)) : BlockPos.ORIGIN;
    }

    public static BlockPos getPosFromStack(ItemStack stack)
    {
        return getPosFromNBT(NBTHelper.checkNBT(stack).getTagCompound());
    }

    public static TileEntity getAdjacentTileEntity(World world, BlockPos pos, EnumFacing dir)
    {
        pos = pos.offset(dir);
        return world == null || !world.isBlockLoaded(pos) ? null : world.getTileEntity(pos);
    }

    public static TileEntity getAdjacentTileEntity(TileEntity refTile, EnumFacing dir)
    {
        return refTile == null ? null : getAdjacentTileEntity(refTile.getWorld(), refTile.getPos(), dir);
    }

    public static int insertEnergyIntoAdjacentEnergyReceiver(TileEntity tile, EnumFacing side, int energy, boolean simulate)
    {
        TileEntity handler = getAdjacentTileEntity(tile, side);

        if (handler != null && handler.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite()))
        {
            return handler.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).receiveEnergy(energy, simulate);
        }

        return 0;
    }

    public static boolean isSigilInInvAndActive(EntityPlayer player, Item item)
    {
        if (item instanceof ItemSigilToggleable)
            for (ItemStack itemStack : player.inventoryContainer.getInventory())
                if (itemStack.getItem() == item)
                    return ((ItemSigilToggleable) itemStack.getItem()).getActivated(itemStack);

        return false;
    }

    public static boolean canItemBreakBlock(EntityPlayer player, ItemStack itemStack, IBlockState blockState)
    {
        if (blockState.getBlock() != Blocks.AIR && blockState.getBlock() != Blocks.BEDROCK && blockState.getBlock() != Blocks.BARRIER)
        {
            Item item = itemStack.getItem();

            if (item instanceof ItemSword)
                return item.getDestroySpeed(itemStack, blockState) > 1F || item.canHarvestBlock(blockState);

            String toolClass = getToolType(item);

            return (toolClass.equals(blockState.getBlock().getHarvestTool(blockState)) && item.getHarvestLevel(itemStack, toolClass, player, blockState) >= blockState.getBlock().getHarvestLevel(blockState)) || (item.getDestroySpeed(itemStack, blockState) > 1F && item.canHarvestBlock(blockState));
        }

        return false;
    }

    public static String getToolType(Item tool)
    {
        String toolClass = "";

        if (tool instanceof ItemPickaxe)
            toolClass = "pickaxe";
        else if (tool instanceof ItemAxe)
            toolClass = "axe";
        else if (tool instanceof ItemSpade)
            toolClass = "shovel";

        return toolClass;
    }

    public static List<BlockPos> getBlocksInRegion(World world, BlockPos pos, AxisAlignedBB bb)
    {
        return getBlocksInRegion(world, pos, bb, Blocks.AIR);
    }

    /**
     * @param world      - The world
     * @param pos        - Start position
     * @param bb         - Bounding box in which to check in (added onto pos)
     * @param checkBlock - Blocks in question
     * @return - Returns a List of all BlockPos's containing the specified blocks
     */
    public static List<BlockPos> getBlocksInRegion(World world, BlockPos pos, AxisAlignedBB bb, Block checkBlock)
    {
        List<BlockPos> blocks = new ArrayList<>();

        if (world.getBlockState(pos) != Blocks.AIR)
        {
            bb = bb.offset(pos);
            for (int i = (int) bb.minX; i < bb.maxX; i++)
            {
                for (int j = (int) bb.minY; j < bb.maxY; j++)
                {
                    for (int k = (int) bb.minZ; k < bb.maxZ; k++)
                    {
                        BlockPos checkPos = new BlockPos(i, j, k);
                        if (world.getBlockState(checkPos) != Blocks.AIR && (checkBlock == Blocks.AIR || world.getBlockState(checkPos).getBlock() == checkBlock))
                            blocks.add(checkPos);
                    }
                }
            }
        }

        return blocks;
    }

    public static void dropStacks(NonNullList<ItemStack> drops, World world, BlockPos posToDrop)
    {
        for (ItemStack itemStack : drops)
        {
            int count = itemStack.getCount();
            int maxStackSize = itemStack.getItem().getItemStackLimit(itemStack);

            while (count >= maxStackSize)
            {
                ItemStack s = ItemHandlerHelper.copyStackWithSize(itemStack, maxStackSize);
                world.spawnEntity(new EntityItem(world, posToDrop.getX(), posToDrop.getY(), posToDrop.getZ(), s));
                count -= maxStackSize;
            }

            if (count > 0)
            {
                ItemStack s = ItemHandlerHelper.copyStackWithSize(itemStack, count);
                world.spawnEntity(new EntityItem(world, posToDrop.getX(), posToDrop.getY(), posToDrop.getZ(), s));
            }
        }
    }

    public static void tillBlock(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, IBlockState state)
    {
        world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (!world.isRemote)
        {
            world.setBlockState(pos, state, 11);
            itemStack.damageItem(1, player);
        }
    }

    public static boolean hasFullSet(EntityPlayer player, Class<? extends ItemArmor> armor)
    {
        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values())
        {
            if (slot.getSlotType() != EntityEquipmentSlot.Type.ARMOR)
                continue;

            ItemStack itemStack = player.getItemStackFromSlot(slot);
            if (itemStack.isEmpty() || !itemStack.getItem().getClass().isInstance(armor)) // Sketchiness intensifies
                return false;
        }

        return true;
    }

    public static Set<Block> getEffectiveBlocksForTool(String name)
    {
        switch (name)
        {
            case "axe":
                return AXE_EFFECTIVE_ON;
            case "pickaxe":
                return PICKAXE_EFFECTIVE_ON;
            case "shovel":
                return SHOVEL_EFFECTIVE_ON;
            case "sickle":
                return SICKLE_EFFECTIVE_ON;
            default:
                return Sets.newHashSet();
        }
    }

    public static Set<Material> getEffectiveMaterialsForTool(String name)
    {
        switch (name)
        {
            case "axe":
                return AXE_MATERIALS_EFFECTIVE_ON;
            case "pickaxe":
                return PICKAXE_MATERIALS_EFFECTIVE_ON;
            case "shovel":
                return SHOVEL_MATERIALS_EFFECTIVE_ON;
            case "sickle":
                return SICKLE_MAERIALS_EFFECTIVE_ON;
            default:
                return Sets.newHashSet();
        }
    }

    /**
     * Adds the given Enchantment to the ItemStack
     *
     * @param itemStack   - ItemStack to be enchanted
     * @param enchantment - Enchantment to be applied
     * @param level       - Level of enchantment
     */
    public static void writeNBTEnchantment(ItemStack itemStack, Enchantment enchantment, int level)
    {
        NBTTagCompound tag = itemStack.getTagCompound();
        NBTTagList enchantments = tag.getTagList("ench", 10);
        if (enchantments == null)
            enchantments = new NBTTagList();

        NBTTagCompound enchTag = new NBTTagCompound();
        enchTag.setShort("id", (short) Enchantment.getEnchantmentID(enchantment));
        enchTag.setShort("lvl", (short) level);
        enchantments.appendTag(enchTag);
        tag.setTag("ench", enchantments);
    }

    public static void removeNBTEnchantment(ItemStack itemStack, Enchantment enchantment)
    {
        NBTTagCompound tag = itemStack.getTagCompound();
        NBTTagList enchantments = itemStack.getEnchantmentTagList();
        if (enchantments == null)
            return;

        for (int i = 0; i < enchantments.tagCount(); i++)
        {
            NBTTagCompound compound = enchantments.getCompoundTagAt(i);
            int id = compound.getShort("id");
            if (id == Enchantment.getEnchantmentID(enchantment))
                enchantments.removeTag(i);
        }

        if (enchantments.tagCount() == 0)
            tag.removeTag("ench");
    }

    // OreDict helper methods

    /**
     * Checks if the first Object is equivalent to the ItemStack
     * Does not take stack size into account.
     */
    public static boolean areStacksEqual(Object oreStack, ItemStack compare)
    {
        if (oreStack instanceof String)
        {
            if (!OreDictionary.doesOreNameExist((String) oreStack))
                return false;

            int[] ids = OreDictionary.getOreIDs(compare);
            for (int id : ids)
                if (OreDictionary.getOreName(id).equals(oreStack))
                    return true;
        }
        else if (oreStack instanceof ItemStack)
        {
            return OreDictionary.itemMatches(compare, (ItemStack) oreStack, false);
        }
        else if (oreStack instanceof Item)
        {
            return areStacksEqual(new ItemStack((Item) oreStack), compare);
        }
        else if (oreStack instanceof Block)
        {
            return areStacksEqual(new ItemStack((Block) oreStack), compare);
        }

        return false;
    }

    public static ItemStack findFirstOreMatch(String oreName)
    {
        if (!OreDictionary.doesOreNameExist(oreName))
            return ItemStack.EMPTY;

        List<ItemStack> stacks = OreDictionary.getOres(oreName);

        if (stacks.size() == 0)
            return ItemStack.EMPTY;
        else
            return stacks.get(0);
    }

    public static ItemStack resolveObject(Object obj)
    {
        if (obj instanceof String)
            return findFirstOreMatch((String) obj);
        else if (obj instanceof ItemStack)
            return (ItemStack) obj;
        else if (obj instanceof Item)
            return new ItemStack((Item) obj);
        else if (obj instanceof Block)
            return new ItemStack((Block) obj);

        return ItemStack.EMPTY;
    }

    public static int next(int mode, int max)
    {
        int index = mode + 1;
        if (index >= max)
            index = 0;

        return index;
    }

    public static int prev(int mode, int max)
    {
        int index = mode - 1;

        if (index < 0)
            index = max;

        return index;
    }

    public static class Profilable
    {
        public static IProfilable getProfilable(ItemStack itemStack)
        {
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof IProfilable)
                return (IProfilable) itemStack.getItem();

            return null;
        }

        public static int getProfileIndex(ItemStack itemStack)
        {
            int index = 0;
            IProfilable profilable = getProfilable(itemStack);

            if (profilable != null)
                index = profilable.getProfileIndex(itemStack);

            return index;
        }

        public static int getMaxProfiles(ItemStack itemStack)
        {
            int max = 0;
            IProfilable profilable = getProfilable(itemStack);

            if (profilable != null)
                max = profilable.getMaxProfiles(itemStack);

            return max;
        }

        public static void setProfileIndex(ItemStack itemStack, int index)
        {
            IProfilable profilable = getProfilable(itemStack);

            if (profilable != null)
                profilable.setProfileIndex(itemStack, index);
        }

        public static NBTTagCompound getProfileNBT(ItemStack itemStack)
        {
            NBTTagCompound tag = new NBTTagCompound();
            IProfilable profilable = getProfilable(itemStack);

            if (profilable != null)
            {
                NBTHelper.checkNBT(itemStack);
                tag = profilable.getProfiles(itemStack).getCompoundTagAt(profilable.getProfileIndex(itemStack));
            }

            return tag;
        }

        public static void setProfileNBT(ItemStack itemStack, NBTTagCompound tag)
        {
            IProfilable profilable = getProfilable(itemStack);

            if (profilable != null)
            {
                NBTHelper.checkNBT(itemStack);
                profilable.getProfiles(itemStack).getCompoundTagAt(profilable.getProfileIndex(itemStack)).merge(tag);
            }
        }
    }
}
