package com.arc.bloodarsenal.block;

import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import com.arc.bloodarsenal.items.block.BloodStoneBlock;
import com.arc.bloodarsenal.items.block.CompactedMRSBlock;
import com.arc.bloodarsenal.items.block.PortableAltarBlock;
import com.arc.bloodarsenal.tileentity.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

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
    public static Block blood_door_iron;
    public static Block blood_cake;
    public static Block blood_torch;
    public static Block blood_infused_glowstone;
    public static Block blood_lamp;
    public static Block blood_infused_diamond_block;
    public static BlockPortableAltar portable_altar;
    public static Block life_infuser;
    public static Block compacter;
    public static Block lp_materializer;
    public static Block compacted_mrs;

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
        blood_door_iron = new BlockInfusedIronDoor();
        blood_cake = new BlockBloodCake();
        blood_torch = new BlockBloodTorch();
        blood_infused_glowstone = new BlockBloodInfusedGlowstone();
        blood_lamp = new BlockBloodLamp();
        blood_infused_diamond_block = new BlockBloodInfusedDiamond();
        portable_altar = new BlockPortableAltar();
        life_infuser = new BlockLifeInfuser();
        compacter = new BlockCompacter();
        lp_materializer = new BlockLPMaterializer();
        compacted_mrs = new BlockCompactedMRS();
    }

    public static void registerBlocksInPre()
    {
        GameRegistry.registerBlock(blood_stone, BloodStoneBlock.class, "BloodArsenal" + ModBlocks.blood_stone.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(blood_tnt, "blood_tnt");
        GameRegistry.registerBlock(blood_stained_glass, "blood_stained_glass");
        GameRegistry.registerBlock(blood_infused_wood, "blood_infused_wood");
        GameRegistry.registerBlock(blood_infused_planks, "blood_infused_planks");
        GameRegistry.registerBlock(blood_stained_ice, "blood_stained_ice");
        GameRegistry.registerBlock(blood_stained_ice_packed, "blood_stained_ice_packed");
        GameRegistry.registerBlock(blood_infused_iron_block, "blood_infused_iron_block");
        GameRegistry.registerBlock(blood_door_wood, "blood_door_wood");
        GameRegistry.registerBlock(blood_door_iron, "blood_door_iron");
        GameRegistry.registerBlock(blood_cake, "blood_cake");
        GameRegistry.registerBlock(blood_torch, "blood_torch");
        GameRegistry.registerBlock(blood_infused_glowstone, "blood_infused_glowstone");
        GameRegistry.registerBlock(blood_lamp, "blood_lamp");
        GameRegistry.registerBlock(blood_infused_diamond_block, "blood_infused_diamond_block");
        GameRegistry.registerBlock(portable_altar, PortableAltarBlock.class, "BloodArsenal" + ModBlocks.portable_altar.getUnlocalizedName());
        GameRegistry.registerBlock(life_infuser, "life_infuser");
        GameRegistry.registerBlock(compacter, "compacter");
        GameRegistry.registerBlock(lp_materializer, "lp_materializer");
        GameRegistry.registerBlock(compacted_mrs, CompactedMRSBlock.class, "BloodArsenal" + ModBlocks.compacted_mrs.getUnlocalizedName());
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileOwned.class, "blood_infused_door");
        GameRegistry.registerTileEntity(TilePortableAltar.class, "portable_altar");
        GameRegistry.registerTileEntity(TileLifeInfuser.class, "life_infuser");
        GameRegistry.registerTileEntity(TileCompacter.class, "compacter");
        GameRegistry.registerTileEntity(TileLPMaterializer.class, "lp_materializer");
        GameRegistry.registerTileEntity(TileCompactedMRS.class, "compacted_mrs");
    }
}
