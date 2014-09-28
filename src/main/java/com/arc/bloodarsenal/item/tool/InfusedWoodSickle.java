package com.arc.bloodarsenal.item.tool;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.arc.bloodarsenal.BloodArsenal;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import java.util.List;
import java.util.Set;

public class InfusedWoodSickle extends ItemTool implements IBindable
{
    private int energyUsed;

    private int radiusLeaf = 1;
    private int radiusCrop = 2;

    private static final Set toolBlocks = Sets.newHashSet(new Block[]{Blocks.leaves, Blocks.leaves2, Blocks.wheat, Blocks.potatoes, Blocks.carrots, Blocks.nether_wart, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.reeds, Blocks.tallgrass, Blocks.vine, Blocks.waterlily, Blocks.red_flower, Blocks.yellow_flower});

    public InfusedWoodSickle(ToolMaterial toolMaterial)
    {
        super(1.0F, toolMaterial, toolBlocks);
        setUnlocalizedName("blood_infused_sickle_wood");
        setTextureName("BloodArsenal:blood_infused_sickle_wood");
        setMaxStackSize(1);
        setMaxDamage(0);
        setFull3D();
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3Block, int x, int y, int z, EntityLivingBase par7EntityLivingBase)
    {
        boolean use = false;

        if (!(par7EntityLivingBase instanceof EntityPlayer)) return false;
        EntityPlayer player = (EntityPlayer) par7EntityLivingBase;

        if (!player.capabilities.isCreativeMode)
        {
            EnergyItems.syphonBatteries(par1ItemStack, player, 50);
        }

        if ((par3Block !=null) && (par3Block.isLeaves(par2World, x, y, z)))
        {
            for (int i = -radiusLeaf; i <= radiusLeaf; i++)
            {
                for (int j = -radiusLeaf; j <= radiusLeaf; j++)
                {
                    for (int k = -radiusLeaf; k <= radiusLeaf; k++)
                    {
                        Block blockToCheck = par2World.getBlock(x + i, y + j, z + k);
                        int meta = par2World.getBlockMetadata(x + i, y + j, z + k);

                        if ((blockToCheck !=null) && (blockToCheck.isLeaves(par2World, x + i, y + j, z + k)))
                        {
                            if (blockToCheck.canHarvestBlock(player, meta))
                            {
                                blockToCheck.harvestBlock(par2World, player, x + i, y + j, z + k, meta);
                            }
                            par2World.setBlock(x + i, y + j, z + k, Blocks.air);
                            use = true;
                        }
                    }
                }
            }
        }

        for (int i = -radiusCrop; i <= radiusCrop; i++)
        {
            for (int j = -radiusCrop; j <= radiusCrop; j++)
            {
                Block blockToCheck = par2World.getBlock(x + i, y, z + j);
                int meta = par2World.getBlockMetadata(x + i, y, z + j);

                if (blockToCheck !=null)
                {
                    if(blockToCheck instanceof BlockBush)
                    {
                        if (blockToCheck.canHarvestBlock(player, meta))
                        {
                            blockToCheck.harvestBlock(par2World, player, x + i, y, z + j, meta);
                        }
                        par2World.setBlock(x + i, y, z + j, Blocks.air);
                        use = true;
                    }
                }
            }
        }
        return use;
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        if (!par2EntityPlayer.capabilities.isCreativeMode)
        {
            EnergyItems.syphonBatteries(par1ItemStack, par2EntityPlayer, 50);
        }

        if (!par2EntityPlayer.canPlayerEdit(x, y, z, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            UseHoeEvent event = new UseHoeEvent(par2EntityPlayer, par1ItemStack, par3World, x, y, z);
            if (MinecraftForge.EVENT_BUS.post(event))
            {
                return false;
            }

            if (event.getResult() == Event.Result.ALLOW)
            {
                return true;
            }

            Block block = par3World.getBlock(x, y, z);

            if (par7 != 0 && par3World.getBlock(x, y + 1, z).isAir(par3World, x, y + 1, z) && (block == Blocks.grass || block == Blocks.dirt))
            {
                Block block1 = Blocks.farmland;
                par3World.playSoundEffect((double)((float) x + 0.5F), (double)((float) y + 0.5F), (double)((float) z + 0.5F), block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);

                if (par3World.isRemote)
                {
                    return true;
                }
                else
                {
                    par3World.setBlock(x, y, z, block1);
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer , List par3List, boolean par4)
    {
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

        return par1ItemStack;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
}
