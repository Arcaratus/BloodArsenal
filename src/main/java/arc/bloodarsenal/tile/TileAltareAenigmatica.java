package arc.bloodarsenal.tile;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.altar.IBloodAltar;
import WayofTime.bloodmagic.api.iface.IBindable;
import WayofTime.bloodmagic.api.orb.IBloodOrb;
import WayofTime.bloodmagic.api.registry.AltarRecipeRegistry;
import WayofTime.bloodmagic.tile.TileInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.ArrayList;
import java.util.List;

public class TileAltareAenigmatica extends TileInventory implements //ISidedInventory,
        ITickable
{
    public static final int ORB_SLOT = 9;

    public List<Integer> blockedSlots = new ArrayList<>();
    private BlockPos altarPos = BlockPos.ORIGIN;

    private String linkedOrbOwner = "";

    public TileAltareAenigmatica()
    {
        super(10, "altareAenigmatica");
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
//
//        isSlave = tag.getBoolean("isSlave");
        altarPos = new BlockPos(tag.getInteger(Constants.NBT.X_COORD), tag.getInteger(Constants.NBT.Y_COORD), tag.getInteger(Constants.NBT.Z_COORD));
        linkedOrbOwner = tag.getString(Constants.NBT.OWNER_UUID);
//        burnTime = tag.getInteger("burnTime");
//        ticksRequired = tag.getInteger("ticksRequired");
//
//        blockedSlots.clear();
//        int[] blockedSlotArray = tag.getIntArray("blockedSlots");
//        for (int blocked : blockedSlotArray)
//        {
//            blockedSlots.add(blocked);
//        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

//        tag.setBoolean("isSlave", isSlave);
        tag.setInteger(Constants.NBT.X_COORD, altarPos.getX());
        tag.setInteger(Constants.NBT.Y_COORD, altarPos.getY());
        tag.setInteger(Constants.NBT.Z_COORD, altarPos.getZ());
        tag.setString(Constants.NBT.OWNER_UUID, linkedOrbOwner);
//
//        tag.setInteger("burnTime", burnTime);
//        tag.setInteger("ticksRequired", ticksRequired);
//
//        int[] blockedSlotArray = new int[blockedSlots.size()];
//        for (int i = 0; i < blockedSlots.size(); i++)
//        {
//            blockedSlotArray[i] = blockedSlots.get(i);
//        }
//
//        tag.setIntArray("blockedSlots", blockedSlotArray);
        return tag;
    }

    public void toggleInputSlotAccessible(int slot)
    {
        if (slot < 6 && slot >= 0)
        {
            if (blockedSlots.contains(slot))
            {
                blockedSlots.remove(slot);
            } else
            {
                blockedSlots.add(slot);
            }
        }
    }

//    @Override
//    public int[] getSlotsForFace(EnumFacing side)
//    {
//        switch (side)
//        {
//            case DOWN:
//                return new int[] { outputSlot };
//            case UP:
//                return new int[] { ORB_SLOT, toolSlot };
//            default:
//                return new int[] { 0, 1, 2, 3, 4, 5 };
//        }
//    }
//
//    @Override
//    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction)
//    {
//        switch (direction)
//        {
//            case DOWN:
//                return index != outputSlot && index != ORB_SLOT && index != toolSlot;
//            case UP:
//                if (index == ORB_SLOT && stack != null && stack.getItem() instanceof IBloodOrb)
//                {
//                    return true;
//                } else if (index == toolSlot)
//                {
//                    return false; //TODO:
//                } else
//                {
//                    return true;
//                }
//            default:
//                return getAccessibleInputSlots(direction).contains(index);
//        }
//    }
//
//    @Override
//    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
//    {
//        switch (direction)
//        {
//            case DOWN:
//                return index == outputSlot;
//            case UP:
//                if (index == ORB_SLOT && stack != null && stack.getItem() instanceof IBloodOrb)
//                {
//                    return true;
//                } else if (index == toolSlot)
//                {
//                    return true; //TODO:
//                } else
//                {
//                    return true;
//                }
//            default:
//                return getAccessibleInputSlots(direction).contains(index);
//        }
//    }

    @Override
    public void update()
    {
        if (altarPos != null && altarPos != BlockPos.ORIGIN)
        {
            TileInventory tile = (TileInventory) worldObj.getTileEntity(altarPos);
            if (tile instanceof IBloodAltar)
            {
                if (tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN) instanceof InvWrapper)
                {
                    ItemStack orbStack = getStackInSlot(ORB_SLOT);
//                    if (orbStack != null && orbStack.getItem() instanceof IBloodOrb && orbStack.getItem() instanceof IBindable)
                    {
//                        IBloodOrb orb = (IBloodOrb) orbStack.getItem();
//                        IBindable sameOrb = (IBindable) orb;

//                        if (!Strings.isNullOrEmpty(sameOrb.getOwnerName(orbStack)) && sameOrb.getOwnerName(orbStack).equals(linkedOrbOwner))
                        {
                            IBloodAltar altar = (IBloodAltar) tile;
                            InvWrapper altarInventory = (InvWrapper) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
                            InvWrapper thisInventory = (InvWrapper) getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
                            int altarEssence = altar.getCurrentBlood();

                            if (canInsertIntoAltar(altarInventory))
                            {
                                ItemStack stackInSlot = null;

                                int slot = 0;
                                for (int i = 0; i < ORB_SLOT; i++)
                                {
                                    if (getStackInSlot(i) != null)
                                    {
                                        stackInSlot = getStackInSlot(i);
                                        slot = i;
                                        break;
                                    }
                                }

                                if (stackInSlot != null)
                                {
                                    for (AltarRecipeRegistry.AltarRecipe altarRecipe : AltarRecipeRegistry.getRecipes().values())
                                    {
                                        if (altarRecipe.doesRequiredItemMatch(stackInSlot, altar.getTier()))
                                        {
                                            if (checkOrb(altarInventory.getStackInSlot(0))) //Check for Blood Orb in Altar
                                            {
                                                if (((IBindable) altarInventory.getStackInSlot(0).getItem()).getOwnerName(altarInventory.getStackInSlot(0)).equals(linkedOrbOwner))
                                                {
                                                    thisInventory.setStackInSlot(ORB_SLOT, altarInventory.getStackInSlot(0).copy());
                                                    altarInventory.setStackInSlot(0, null);
                                                }
                                            }

                                            if (altarRecipe.getSyphon() * stackInSlot.stackSize <= altarEssence// && NetworkHelper.canSyphonFromContainer(orbStack, stackInSlot.stackSize * 200)
                                                    )
                                            {
                                                altarInventory.insertItem(0, stackInSlot.copy(), false);
                                                thisInventory.setStackInSlot(slot, null);
//                                                NetworkHelper.syphonFromContainer(orbStack, stackInSlot.stackSize * 200);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    if (checkOrb(orbStack) && altarInventory.getStackInSlot(0) == null)
                                    {
                                        altarInventory.insertItem(0, orbStack.copy(), false);
                                        thisInventory.setStackInSlot(ORB_SLOT, null);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean canInsertIntoAltar(InvWrapper altarInventory)
    {
        return altarInventory.getStackInSlot(0) == null || checkOrb(altarInventory.getStackInSlot(0));
    }

    private boolean checkOrb(ItemStack orbStack)
    {
        return orbStack != null && orbStack.getItem() instanceof IBloodOrb && orbStack.getItem() instanceof IBindable && ((IBindable) orbStack.getItem()).getOwnerName(orbStack).equals(linkedOrbOwner);
    }

    public BlockPos getAltarPos()
    {
        return this.altarPos;
    }

    public void setAltarPos(BlockPos pos)
    {
        this.altarPos = pos;
    }

    public String getLinkedOrbOwner()
    {
        return linkedOrbOwner;
    }

    public boolean setLinkedOrbOwner(String linkedOrbOwner)
    {
        if (this.linkedOrbOwner.equals(linkedOrbOwner))
            return false;
        else
            this.linkedOrbOwner = linkedOrbOwner;

        return true;
    }
}
