/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Sep 1, 2015, 5:32:15 PM (GMT)]
 */
package com.arc.bloodarsenal.common.block;

import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MultipartHandler
{
    public MultipartHandler()
    {
        registerAllMultiparts(ModBlocks.blood_stone);
        registerAllMultiparts(ModBlocks.blood_stained_glass);
        registerAllMultiparts(ModBlocks.blood_infused_wood);
        registerAllMultiparts(ModBlocks.blood_infused_planks);
        registerAllMultiparts(ModBlocks.blood_stained_ice);
        registerAllMultiparts(ModBlocks.blood_stained_ice_packed);
        registerAllMultiparts(ModBlocks.blood_infused_iron_block);
        registerAllMultiparts(ModBlocks.blood_infused_glowstone);
        registerAllMultiparts(ModBlocks.blood_lamp);
        registerAllMultiparts(ModBlocks.blood_infused_diamond_block);
    }

    private static void registerAllMultiparts(Block block)
    {
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        Item item = Item.getItemFromBlock(block);
        block.getSubBlocks(item, block.getCreativeTabToDisplayOn(), stacks);

        for (ItemStack stack : stacks)
        {
            if (stack.getItem() == item)
            {
                registerMultipart(block, stack.getItemDamage());
            }
        }
    }

    private static void registerMultipartMetadataLine(Block block, int maxMeta)
    {
        for (int i = 0; i < maxMeta; i++)
        {
            registerMultipart(block, i);
        }
    }

    private static void registerMultipart(Block block, int meta)
    {
        MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(block, meta), block.getUnlocalizedName() + (meta == 0 ? "" : "_" + meta));
    }
}
