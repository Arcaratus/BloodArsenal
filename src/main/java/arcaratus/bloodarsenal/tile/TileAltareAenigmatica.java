package arcaratus.bloodarsenal.tile;

import WayofTime.bloodmagic.altar.IBloodAltar;
import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.api.impl.recipe.RecipeBloodAltar;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.iface.IBindable;
import WayofTime.bloodmagic.orb.IBloodOrb;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.block.BlockAltareAenigmatica;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import com.google.common.base.Strings;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
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
        super(10, "altare_aenigmatica");
    }

    @Override
    public void deserialize(NBTTagCompound tag)
    {
        super.deserialize(tag);
        altarPos = BloodArsenalUtils.getPosFromNBT(tag);
        linkedOrbOwner = tag.getString(Constants.NBT.OWNER_UUID);
    }

    @Override
    public NBTTagCompound serialize(NBTTagCompound tag)
    {
        super.serialize(tag);
        BloodArsenalUtils.writePosToNBT(tag, altarPos.getX(), altarPos.getY(), altarPos.getZ());
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
            TileEntity tile = getWorld().getTileEntity(altarPos);
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
        return orbStack.getItem() instanceof IBloodOrb && orbStack.getItem() instanceof IBindable && ((IBindable) orbStack.getItem()).getBinding(orbStack) != null && ((IBindable) orbStack.getItem()).getBinding(orbStack).getOwnerName().equals(linkedOrbOwner);
    }

    private void manageAltar(IItemHandler altarInventory, ItemStack orbStack, IBloodAltar altar)
    {
        IBloodOrb orb = (IBloodOrb) orbStack.getItem();
        IBindable sameOrb = (IBindable) orb;

        if (sameOrb.getBinding(orbStack) != null)
        {
//            if (checkOrb(altarInventory.getStackInSlot(0)))
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
                    RecipeBloodAltar altarRecipe = BloodMagicAPI.INSTANCE.getRecipeRegistrar().getBloodAltar(stackInSlot);
                    if (altarRecipe != null && altarRecipe.getInput().apply(stackInSlot) && altar.getTier().toInt() >= altarRecipe.getMinimumTier().toInt())
                    {
                        if (checkOrb(altarInventory.getStackInSlot(0))) //Check for Blood Orb in Altar and remove if found
                        {
                            ItemStack copyStack = altarInventory.getStackInSlot(0).copy();
                            altarInventory.extractItem(0, 1, false);
                            setInventorySlotContents(ORB_SLOT, copyStack);

                            altarInventory.insertItem(0, stackInSlot.copy(), false);
                            setInventorySlotContents(slot, ItemStack.EMPTY);
                            NetworkHelper.syphonFromContainer(orbStack, SoulTicket.block(world, pos, stackInSlot.getCount() * ConfigHandler.values.altareAenigmaticaMoveMultiplier));
                        }
                        else if (altarRecipe.getSyphon() * stackInSlot.getCount() <= altarEssence && NetworkHelper.canSyphonFromContainer(orbStack, stackInSlot.getCount() * ConfigHandler.values.altareAenigmaticaMoveMultiplier)) //Move items into the Altar after checking LP levels
                        {
                            altarInventory.insertItem(0, stackInSlot.copy(), false);
                            setInventorySlotContents(slot, ItemStack.EMPTY);
                            NetworkHelper.syphonFromContainer(orbStack, SoulTicket.block(world, pos, stackInSlot.getCount() * ConfigHandler.values.altareAenigmaticaMoveMultiplier));
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
        Binding binding = ((IBindable) player.getHeldItemMainhand().getItem()).getBinding(player.getHeldItemMainhand());
        String probablyOwnerName = binding == null ? "" : binding.getOwnerName();
        if (Strings.isNullOrEmpty(probablyOwnerName))
        {
            player.sendStatusMessage(new TextComponentString(TextHelper.localize("chat.bloodarsenal.noOwner")), true);
            return false;
        }
        else if (linkedOrbOwner.equals(probablyOwnerName))
        {
            player.sendStatusMessage(new TextComponentString(TextHelper.localize("chat.bloodarsenal.alreadyOwner")), true);
            return false;
        }
        else
        {
            linkedOrbOwner = probablyOwnerName;
            player.sendStatusMessage(new TextComponentString(TextHelper.localize("chat.bloodarsenal.setOwner", player.getName())), true);
            return true;
        }
    }
}
