package com.arc.bloodarsenal.block;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import com.arc.bloodarsenal.BloodArsenalConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBloodCake extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon cakeTop;
    @SideOnly(Side.CLIENT)
    private IIcon cakeBottom;

    public BlockBloodCake()
    {
        super(Material.cake);
        setBlockName("blood_cake");
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        float f1 = (float) (1 + l) / 12.0F;
        this.setBlockBounds(f1, 0.0F, f, 1.0F - f, 0.5F, 1.0F - f);
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        float f = 0.0625F;
        float f1 = 0.5F;
        this.setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        float f1 = (float) (1 + l) / 12.0F;
        float f2 = 0.5F;
        return AxisAlignedBB.getBoundingBox((double)((float)x + f1), (double)y, (double)((float)z + f), (double)((float)(x + 1) - f), (double)((float)y + f2 - f), (double)((float)(z + 1) - f));
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        float f1 = (float) (1 + l) / 12.0F;
        float f2 = 0.5F;
        return AxisAlignedBB.getBoundingBox((double)((float)x + f1), (double)y, (double)((float)z + f), (double)((float)(x + 1) - f), (double)((float)y + f2), (double)((float)(z + 1) - f));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int x)
    {
        return par1 == 1 ? cakeTop : (par1 == 0 ? cakeBottom : (x > 0 && par1 == 4 ? null : blockIcon));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("BloodArsenal:blood_cake_side");
        cakeTop = iconRegister.registerIcon("BloodArsenal:blood_cake_top");
        cakeBottom = iconRegister.registerIcon("BloodArsenal:blood_cake_bottom");
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        eatCake(player);
        return true;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        eatCake(player);
    }

    public void eatCake(EntityPlayer player)
    {
        String playerEating = player.getCommandSenderName();

        if (player.canEat(false))
        {
            player.getFoodStats().addStats(2, 1.5F);
            SoulNetworkHandler.syphonFromNetwork(playerEating, 200);

            if (BloodArsenalConfig.cakeIsLie)
            {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cake.lie")));
            }
            else
            {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cake.eating")));
            }
        }
        else if (player.canEat(true))
        {
            if (BloodArsenalConfig.cakeIsLie)
            {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cake.lie")));
            }
            else
            {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cake.tooFull")));
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (!this.canBlockStay(world, x, y, z))
        {
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return world.getBlock(x, y - 1, z).getMaterial().isSolid();
    }

    @Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    @Override
    public Item getItem(World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        return new ItemStack(this);
    }
}
