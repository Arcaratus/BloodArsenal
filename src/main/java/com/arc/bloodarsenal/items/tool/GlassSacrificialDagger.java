package com.arc.bloodarsenal.items.tool;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.event.SacrificeKnifeUsedEvent;
import WayofTime.alchemicalWizardry.api.sacrifice.PlayerSacrificeHandler;
import WayofTime.alchemicalWizardry.api.tile.IBloodAltar;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.BloodArsenalConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;

import java.util.List;
import java.util.Random;

public class GlassSacrificialDagger extends Item
{
    public GlassSacrificialDagger()
    {
        super();
        setMaxStackSize(1);
        setFull3D();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        if (AlchemicalWizardry.wimpySettings)
        {
            this.itemIcon = iconRegister.registerIcon("BloodArsenal:glass_sacrificial_orb");
        } else
        {
            this.itemIcon = iconRegister.registerIcon("BloodArsenal:glass_sacrificial_dagger");
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(StatCollector.translateToLocal("tooltip.tool.glassSacrificeDagger"));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (this.canUseForSacrifice(stack))
        {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            return stack;
        }

        if (!player.capabilities.isCreativeMode)
        {
            SacrificeKnifeUsedEvent evt = new SacrificeKnifeUsedEvent(player, true, true, 2);
            if(MinecraftForge.EVENT_BUS.post(evt))
            {
                return stack;
            }

            if(evt.shouldDrainHealth)
            {
                player.setHealth(player.getHealth() - 2);
            }

            if(!evt.shouldFillAltar)
            {
                return stack;
            }
        }

        if (player instanceof FakePlayer)
        {
            return stack;
        }

        double posX = player.posX;
        double posY = player.posY;
        double posZ = player.posZ;
        world.playSoundEffect((double) ((float) posX + 0.5F), (double) ((float) posY + 0.5F), (double) ((float) posZ + 0.5F), "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
        float f = 1.0F;
        float f1 = f * 0.6F + 0.4F;
        float f2 = f * f * 0.7F - 0.5F;
        float f3 = f * f * 0.6F - 0.7F;

        for (int l = 0; l < 8; ++l)
        {
            world.spawnParticle("reddust", posX + Math.random() - Math.random(), posY + Math.random() - Math.random(), posZ + Math.random() - Math.random(), f1, f2, f3);
        }

        if (!world.isRemote && SpellHelper.isFakePlayer(world, player))
        {
            return stack;
        }

        if (player.isPotionActive(AlchemicalWizardry.customPotionSoulFray))
        {
            findAndFillAltar(world, player, 20);
        } else
        {
            findAndFillAltar(world, player, 200);
        }

        if (player.getHealth() <= 0.001f)
        {
            player.onDeath(BloodArsenal.deathFromBlood);
        }

        Random random = new Random();
        if (random.nextInt() + 18 <= 22)
        {
            player.addPotionEffect(new PotionEffect(BloodArsenalConfig.bleedingID, random.nextInt(5) * 20, 0));
        }
        return stack;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount)
    {
        PlayerSacrificeHandler.sacrificePlayerHealth(player);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    public void findAndFillAltar(World world, EntityPlayer player, int amount)
    {
        int posX = (int) Math.round(player.posX - 0.5f);
        int posY = (int) player.posY;
        int posZ = (int) Math.round(player.posZ - 0.5f);
        IBloodAltar altarEntity = getAltar(world, posX, posY, posZ);

        if (altarEntity == null)
        {
            return;
        }

        altarEntity.sacrificialDaggerCall(amount, false);
        altarEntity.startCycle();
    }

    public IBloodAltar getAltar(World world, int x, int y, int z)
    {
        TileEntity tileEntity;

        for (int i = -2; i <= 2; i++)
        {
            for (int j = -2; j <= 2; j++)
            {
                for (int k = -2; k <= 1; k++)
                {
                    tileEntity = world.getTileEntity(i + x, k + y, j + z);

                    if ((tileEntity instanceof IBloodAltar))
                    {
                        return (IBloodAltar) tileEntity;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        if (!world.isRemote && entity instanceof EntityPlayer)
        {
            this.setUseForSacrifice(stack, this.isPlayerPreparedForSacrifice(world, (EntityPlayer)entity));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (AlchemicalWizardry.wimpySettings)
        {
            return "Glass Sacrificial Orb";
        }
        return super.getItemStackDisplayName(stack);
    }

    public boolean isPlayerPreparedForSacrifice(World world, EntityPlayer player)
    {
        return !world.isRemote && (PlayerSacrificeHandler.getPlayerIncense(player) > 0);
    }

    public boolean canUseForSacrifice(ItemStack stack)
    {
        NBTTagCompound tag = stack.getTagCompound();

        return tag != null && tag.getBoolean("sacrifice");
    }

    public void setUseForSacrifice(ItemStack stack, boolean sacrifice)
    {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null)
        {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }

        tag.setBoolean("sacrifice", sacrifice);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass)
    {
        return this.canUseForSacrifice(stack) || super.hasEffect(stack, pass);
    }
}