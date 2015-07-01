package com.arc.bloodarsenal.common.items;

import com.arc.bloodarsenal.common.BloodArsenal;
import com.arc.bloodarsenal.common.entity.projectile.EntityBloodBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBloodBall extends Item
{
    public ItemBloodBall()
    {
        setUnlocalizedName("blood_ball");
        setTextureName("BloodArsenal:blood_ball");
        setMaxStackSize(16);
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --itemStack.stackSize;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new EntityBloodBall(world, player));
        }

        return itemStack;
    }
}
