package arc.bloodarsenal.tile;

import WayofTime.bloodmagic.api.altar.IBloodAltar;
import WayofTime.bloodmagic.api.iface.IBindable;
import WayofTime.bloodmagic.api.orb.IBloodOrb;
import WayofTime.bloodmagic.api.registry.AltarRecipeRegistry;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.ChatUtil;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.block.BlockAltareAenigmatica;
import arc.bloodarsenal.registry.Constants;
import com.google.common.base.Strings;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileAltareAenigmatica extends TileInventory implements ISidedInventory, ITickable
{
    public static final int ORB_SLOT = 9;

    private BlockPos altarPos = BlockPos.ORIGIN;

    private String linkedOrbOwner = "";

    public TileAltareAenigmatica()
    {
        super(10, "altareAenigmatica");
    }

    @Override
    public void deserialize(NBTTagCompound tag)
    {
        super.deserialize(tag);
        altarPos = new BlockPos(tag.getInteger(Constants.NBT.X_COORD), tag.getInteger(Constants.NBT.Y_COORD), tag.getInteger(Constants.NBT.Z_COORD));
        linkedOrbOwner = tag.getString(Constants.NBT.OWNER_UUID);
    }

    @Override
    public NBTTagCompound serialize(NBTTagCompound tag)
    {
        super.serialize(tag);
        tag.setInteger(Constants.NBT.X_COORD, altarPos.getX());
        tag.setInteger(Constants.NBT.Y_COORD, altarPos.getY());
        tag.setInteger(Constants.NBT.Z_COORD, altarPos.getZ());
        tag.setString(Constants.NBT.OWNER_UUID, linkedOrbOwner);

        return tag;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        IBlockState state = getWorld().getBlockState(pos);
        if (state.getBlock() instanceof BlockAltareAenigmatica)
        {
            BlockAltareAenigmatica aenigmatica = (BlockAltareAenigmatica) state.getBlock();
            if (EnumFacing.values()[aenigmatica.getMetaFromState(state)] == side)
            {
                return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
            }
        }

        return new int[]{};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction)
    {
        return true;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return true;
    }

    @Override
    public void update()
    {
        if (altarPos != BlockPos.ORIGIN)
        {
            TileInventory tile = (TileInventory) getWorld().getTileEntity(altarPos);
            if (tile instanceof IBloodAltar)
            {
                if (tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN) instanceof InvWrapper)
                {
                    IBloodAltar altar = (IBloodAltar) tile;
                    ItemStack orbStack = getStackInSlot(ORB_SLOT);
                    IItemHandler altarInventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

                    boolean inThis = checkOrb(orbStack);
                    boolean inAltar = checkOrb(altarInventory.getStackInSlot(0));

                    if (inThis)
                        manageAltar(altarInventory, orbStack, altar);
                    else if (inAltar)
                        manageAltar(altarInventory, altarInventory.getStackInSlot(0), altar);
                }
            }
        }
    }

    /**
     * @param altarInventory - The Altar inventory
     * @return - If the Aenigmatica can insert the recipe-item into the Altar
     */
    private boolean canInsertIntoAltar(IItemHandler altarInventory)
    {
        return altarInventory.getStackInSlot(0).isEmpty() || checkOrb(altarInventory.getStackInSlot(0));
    }

    private boolean checkOrb(ItemStack orbStack)
    {
        return orbStack.getItem() instanceof IBloodOrb && orbStack.getItem() instanceof IBindable && ((IBindable) orbStack.getItem()).getOwnerName(orbStack).equals(linkedOrbOwner);
    }

    private void manageAltar(IItemHandler altarInventory, ItemStack orbStack, IBloodAltar altar)
    {
        IBloodOrb orb = (IBloodOrb) orbStack.getItem();
        IBindable sameOrb = (IBindable) orb;

        if (!Strings.isNullOrEmpty(sameOrb.getOwnerName(orbStack)))
        {
            if (canInsertIntoAltar(altarInventory))
            {
                ItemStack stackInSlot = ItemStack.EMPTY;

                int slot = -1;
                for (int i = 0; i < ORB_SLOT; i++)
                {
                    if (!getStackInSlot(i).isEmpty())
                    {
                        stackInSlot = getStackInSlot(i);
                        slot = i;
                        break;
                    }
                }

                int altarEssence = altar.getCurrentBlood();

                if (!stackInSlot.isEmpty() && slot > -1)
                {
                    AltarRecipeRegistry.AltarRecipe altarRecipe = AltarRecipeRegistry.getRecipeForInput(stackInSlot);
                    if (altarRecipe != null && altarRecipe.doesRequiredItemMatch(stackInSlot, altar.getTier()))
                    {
                        if (checkOrb(altarInventory.getStackInSlot(0))) //Check for Blood Orb in Altar and remove if found
                        {
                            ItemStack copyStack = altarInventory.getStackInSlot(0).copy();
                            altarInventory.extractItem(0, 1, false);
                            setInventorySlotContents(ORB_SLOT, copyStack);
                        }
                        else if (altarRecipe.getSyphon() * stackInSlot.getCount() <= altarEssence && NetworkHelper.canSyphonFromContainer(orbStack, stackInSlot.getCount() * ConfigHandler.altareAenigmaticaMoveMultiplier)) //Move items into the Altar after checking LP levels
                        {
                            altarInventory.insertItem(0, stackInSlot.copy(), false);
                            setInventorySlotContents(slot, ItemStack.EMPTY);
                            NetworkHelper.syphonFromContainer(orbStack, stackInSlot.getCount() * ConfigHandler.altareAenigmaticaMoveMultiplier);
                        }
                    }
                    else
                    {
                        shoveOrbIntoAltar(altarInventory, orbStack);
                    }
                }
                else if (altarInventory.getStackInSlot(0).isEmpty()) //Put orb back in if possible
                {
                    shoveOrbIntoAltar(altarInventory, orbStack);
                }
            }
        }
    }

    private void shoveOrbIntoAltar(IItemHandler altarInventory, ItemStack orbStack)
    {
        ItemStack copyStack = orbStack.copy();
        setInventorySlotContents(ORB_SLOT, ItemStack.EMPTY);
        altarInventory.insertItem(0, copyStack, false);
    }

    public BlockPos getAltarPos()
    {
        return this.altarPos;
    }

    public void setAltarPos(BlockPos pos)
    {
        this.altarPos = pos;
    }

    public boolean setLinkedOrbOwner(EntityPlayer player)
    {
        String dontKnowWhatToCallThis = ((IBindable) player.getHeldItemMainhand().getItem()).getOwnerName(player.getHeldItemMainhand());
        if (this.linkedOrbOwner.equals(dontKnowWhatToCallThis))
        {
            ChatUtil.sendNoSpamClient(TextHelper.localize("chat.BloodArsenal.alreadyOwner"));
            return false;
        }
        else
        {
            this.linkedOrbOwner = dontKnowWhatToCallThis;
            ChatUtil.sendNoSpamClient(TextHelper.localize("chat.BloodArsenal.setOwner", player.getName()));
            return true;
        }
    }
}
