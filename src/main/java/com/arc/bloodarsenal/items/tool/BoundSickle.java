package com.arc.bloodarsenal.items.tool;

import WayofTime.alchemicalWizardry.common.items.EnergyItems;
import com.google.common.collect.Sets;
import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import java.util.List;
import java.util.Set;

public class BoundSickle extends ItemTool implements IBindable
{
    private int energyUsed;

    private int radiusLeaf = 3;
    private int radiusCrop = 4;

    private static IIcon active;
    private static IIcon passive;

    private static final Set toolBlocks = Sets.newHashSet(new Block[] { Blocks.leaves, Blocks.leaves2, Blocks.wheat, Blocks.potatoes, Blocks.carrots, Blocks.nether_wart, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.reeds, Blocks.tallgrass, Blocks.vine, Blocks.waterlily, Blocks.red_flower, Blocks.yellow_flower });

    public BoundSickle(ToolMaterial toolMaterial)
    {
        super(1.0F, toolMaterial, toolBlocks);
        setUnlocalizedName("bound_sickle");
        setMaxStackSize(1);
        setMaxDamage(0);
        setFull3D();
        setCreativeTab(BloodArsenal.BA_TAB);
        setEnergyUsed(50);
    }

    public void setEnergyUsed(int i)
    {
        energyUsed = i;
    }

    public int getEnergyUsed()
    {
        return energyUsed;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3Block, int x, int y, int z, EntityLivingBase par7EntityLivingBase)
    {
        NBTTagCompound tag = par1ItemStack.stackTagCompound;

        boolean use = false;

        if (tag.getBoolean("isActive"))
        {
            if (!(par7EntityLivingBase instanceof EntityPlayer))
            {
                return false;
            }

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
        }
        return use;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(StatCollector.translateToLocal("tooltip.tool.bound_sickle1"));
        par3List.add(StatCollector.translateToLocal("tooltip.tool.bound_sickle2"));

        if (!(par1ItemStack.stackTagCompound == null))
        {
            if (par1ItemStack.stackTagCompound.getBoolean("isActive"))
            {
                par3List.add("Activated");
            }
            else
            {
                par3List.add("Deactivated");
            }

            if (!par1ItemStack.stackTagCompound.getString("ownerName").equals(""))
            {
                par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
            }
        }
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        NBTTagCompound tag = par1ItemStack.stackTagCompound;

        if (tag.getBoolean("isActive"))
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
        else
        {
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("BloodArsenal:bound_sickle");
        active = iconRegister.registerIcon("BloodArsenal:bound_sickle");
        passive = iconRegister.registerIcon("AlchemicalWizardry:SheathedItem");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if (stack.stackTagCompound == null)
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = stack.stackTagCompound;

        if (tag.getBoolean("isActive"))
        {
            return active;
        }
        else
        {
            return passive;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        if (par3EntityPlayer.isSneaking())
        {
            setActivated(par1ItemStack, !getActivated(par1ItemStack));
            par1ItemStack.stackTagCompound.setInteger("worldTimeDelay", (int) (par2World.getWorldTime() - 1) % 100);
            return par1ItemStack;
        }

        if (!getActivated(par1ItemStack))
        {
            return par1ItemStack;
        }

        return par1ItemStack;
    }

    public void setActivated(ItemStack par1ItemStack, boolean newActivated)
    {
        NBTTagCompound itemTag = par1ItemStack.stackTagCompound;

        if (itemTag == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        itemTag.setBoolean("isActive", newActivated);
    }

    public boolean getActivated(ItemStack par1ItemStack)
    {
        NBTTagCompound itemTag = par1ItemStack.stackTagCompound;

        if (itemTag == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        return itemTag.getBoolean("isActive");
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (!(par3Entity instanceof EntityPlayer))
        {
            return;
        }

        EntityPlayer par3EntityPlayer = (EntityPlayer) par3Entity;

        if (par1ItemStack.stackTagCompound == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        if (par2World.getWorldTime() % 200 == par1ItemStack.stackTagCompound.getInteger("worldTimeDelay") && par1ItemStack.stackTagCompound.getBoolean("isActive"))
        {
            if (!par3EntityPlayer.capabilities.isCreativeMode)
            {
                EnergyItems.syphonBatteries(par1ItemStack, par3EntityPlayer, 50);
            }
        }

        par1ItemStack.setItemDamage(0);
        return;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
}
