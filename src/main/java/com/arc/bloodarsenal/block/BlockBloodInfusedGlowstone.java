package com.arc.bloodarsenal.block;

import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.items.ModItems;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockBloodInfusedGlowstone extends BlockGlowstone
{
    public BlockBloodInfusedGlowstone()
    {
        super(Material.glass);
        setBlockName("blood_infused_glowstone");
        setBlockTextureName("BloodArsenal:blood_infused_glowstone");
        setCreativeTab(BloodArsenal.BA_TAB);
        setHardness(0.5F);
        setResistance(0.75F);
        setStepSound(soundTypeGlass);
        setLightLevel(1.0F);
    }

    public Item getItemDropped(int par1, Random random, int par3) {return ModItems.blood_infused_glowstone_dust;}
}
