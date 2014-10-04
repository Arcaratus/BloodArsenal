package com.arc.bloodarsenal.blocks;

import com.arc.bloodarsenal.items.block.BloodStoneBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks
{
    public static Block blood_stone;
    public static Block blood_tnt;
    public static Block blood_stained_glass;
    public static Block blood_infused_wood;
    public static Block blood_infused_planks;
    public static Block blood_stained_ice;
    public static Block blood_stained_ice_packed;
    public static Block blood_infused_iron_block;
    public static Block blood_door_wood;

    public static void init()
    {
        blood_stone = new BlockBloodStone();
        blood_tnt = new BlockBloodTNT();
        blood_stained_glass = new BlockBloodStainedGlass();
        blood_infused_wood = new BlockBloodInfusedWood();
        blood_infused_planks = new BlockBloodInfusedPlanks();
        blood_stained_ice = new BlockBloodStainedIce();
        blood_stained_ice_packed = new BlockBloodStainedPackedIce();
        blood_infused_iron_block = new BlockInfusedIron();
        blood_door_wood = new BlockInfusedWoodenDoor();
    }

    public static void registerBlocksInPre()
    {
        GameRegistry.registerBlock(ModBlocks.blood_stone, BloodStoneBlock.class, "BloodArsenal" + (ModBlocks.blood_stone.getUnlocalizedName().substring(5)));
        GameRegistry.registerBlock(ModBlocks.blood_tnt, "blood_tnt");
        GameRegistry.registerBlock(ModBlocks.blood_stained_glass, "blood_stained_glass");
        GameRegistry.registerBlock(ModBlocks.blood_infused_wood, "blood_infused_wood");
        GameRegistry.registerBlock(ModBlocks.blood_infused_planks, "blood_infused_planks");
        GameRegistry.registerBlock(ModBlocks.blood_stained_ice, "blood_stained_ice");
        GameRegistry.registerBlock(ModBlocks.blood_stained_ice_packed, "blood_stained_ice_packed");
        GameRegistry.registerBlock(ModBlocks.blood_infused_iron_block, "blood_infused_iron_block");
        GameRegistry.registerBlock(ModBlocks.blood_door_wood, "blood_door_wood");
    }
}
