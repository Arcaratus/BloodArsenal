package com.arc.bloodarsenal.items.tool;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class InfusedDiamondAxe extends ItemAxe implements IBindable
{
    private int energyUsed;

    public static ArrayList<List> oreDictLogs = new ArrayList();

    public InfusedDiamondAxe()
    {
        super(BloodArsenal.infusedIron);
        setMaxStackSize(1);
        setUnlocalizedName("blood_infused_axe_diamond");
        setTextureName("BloodArsenal:blood_infused_axe_diamond");
        setCreativeTab(BloodArsenal.BA_TAB);
        setFull3D();
        setHasSubtypes(true);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer , List par3List, boolean par4)
    {
        par3List.add("Demonic deforestation!");

        if (par1ItemStack.getItemDamage() == 0)
        {
            par3List.add("Normal Mode");
        }
        else if (par1ItemStack.getItemDamage() == 1)
        {
            par3List.add("Area Mode");
        }
        else if (par1ItemStack.getItemDamage() == 2)
        {
            par3List.add("Tree Mode");
        }

        if (!(par1ItemStack.stackTagCompound == null))
        {
            if (!par1ItemStack.stackTagCompound.getString("ownerName").equals(""))
            {
                par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        if (par3EntityPlayer.isSneaking())
        {
            ToolCapabilities.changeMode(par1ItemStack);
        }

        return par1ItemStack;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player)
    {
        World world = player.worldObj;
        Material mat = world.getBlock(x, y, z).getMaterial();
        if (!ToolCapabilities.isRightMaterial(mat, ToolCapabilities.materialsAxe))
        {
            return false;
        }
        MovingObjectPosition block = ToolCapabilities.raytraceFromEntity(world, player, true, 4.5);
        if (block == null)
        {
            return false;
        }
        ForgeDirection direction = ForgeDirection.getOrientation(block.sideHit);

        switch (ToolCapabilities.getMode(stack))
        {
            case 0:
            {
                if (!player.capabilities.isCreativeMode)
                {
                    EnergyItems.syphonBatteries(stack, player, 100);
                }

                break;
            }
            case 1:
            {
                if (!player.capabilities.isCreativeMode)
                {
                    EnergyItems.syphonBatteries(stack, player, 1000);
                }

                boolean doX = direction.offsetX == 0;
                boolean doY = direction.offsetY == 0;
                boolean doZ = direction.offsetZ == 0;

                ToolCapabilities.removeBlocksInIteration(player, world, x, y, z, doX ? -2 : 0, doY ? -1 : 0, doZ ? -2 : 0, doX ? 3 : 1, doY ? 4 : 1, doZ ? 3 : 1, null, ToolCapabilities.materialsAxe);
                break;
            }
            case 2:
            {
                Block blck = world.getBlock(x, y, z);
                if (ToolCapabilities.isWoodLog(world, x, y, z))
                {
                    while (blck != Blocks.air)
                    {
                        ToolCapabilities.breakFurthestBlock(world, x, y, z, blck, player);
                        blck = world.getBlock(x, y, z);

                        if (!player.capabilities.isCreativeMode)
                        {
                            EnergyItems.syphonBatteries(stack, player, 3000);
                        }
                    }

                    List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x - 5, y - 1, z - 5, x + 5, y + 64, z + 5));
                    for (EntityItem item : items)
                    {
                        item.setPosition(x + 0.5, y + 0.5, z + 0.5);
                        item.ticksExisted += 20;
                    }
                }
                break;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.rare;
    }
}