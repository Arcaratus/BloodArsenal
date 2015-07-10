package com.arc.bloodarsenal.common.block;

import com.arc.bloodarsenal.common.BloodArsenal;
import com.arc.bloodarsenal.common.BloodArsenalConfig;
import com.arc.bloodarsenal.common.items.block.BloodStoneBlock;
import com.arc.bloodarsenal.common.items.block.CompactedMRSBlock;
import com.arc.bloodarsenal.common.items.block.PortableAltarBlock;
import com.arc.bloodarsenal.common.tileentity.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import java.util.ArrayList;

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
    public static Block block_burned_string;

    public static ArrayList<String> blocksNotToBeRegistered = new ArrayList();

    public static void init()
    {
        blood_stone = registerBlock(new BlockBloodStone(), BloodStoneBlock.class, "blood_stone");
        blood_tnt = registerBlock(new BlockBloodTNT(), "blood_tnt");
        blood_stained_glass = registerBlock(new BlockBloodStainedGlass(), "blood_stained_glass");
        blood_infused_wood = registerBlock(new BlockBloodInfusedWood(), "blood_infused_wood");
        blood_infused_planks = registerBlock(new BlockBloodInfusedPlanks(), "blood_infused_planks");
        blood_stained_ice = registerBlock(new BlockBloodStainedIce(), "blood_stained_ice");
        blood_stained_ice_packed = registerBlock(new BlockBloodStainedPackedIce(), "blood_stained_ice_packed");
        blood_infused_iron_block = registerBlock(new BlockInfusedIron(), "blood_infused_iron_block");
        blood_cake = registerBlock(new BlockBloodCake(), "blood_cake");
        blood_torch = registerBlock(new BlockBloodTorch(), "blood_torch");
        blood_infused_glowstone = registerBlock(new BlockBloodInfusedGlowstone(), "blood_infused_glowstone");
        blood_lamp = registerBlock(new BlockBloodLamp(), "blood_lamp");
        blood_infused_diamond_block = registerBlock(new BlockBloodInfusedDiamond(), "blood_infused_diamond_block");
        portable_altar = (BlockPortableAltar) registerBlock(new BlockPortableAltar(), PortableAltarBlock.class, "portable_altar");
        life_infuser = registerBlock(new BlockLifeInfuser(), "life_infuser");
        compacter = registerBlock(new BlockCompacter(), "compacter");
        lp_materializer = registerBlock(new BlockLPMaterializer(), "lp_materializer");
        compacted_mrs = registerBlock(new BlockCompactedMRS(), CompactedMRSBlock.class, "compacted_mrs");
        block_burned_string = registerBlock(new BlockBurnedString(), "block_burned_string");
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TilePortableAltar.class, "portable_altar");
        GameRegistry.registerTileEntity(TileLifeInfuser.class, "life_infuser");
        GameRegistry.registerTileEntity(TileCompacter.class, "compacter");
        GameRegistry.registerTileEntity(TileLPMaterializer.class, "lp_materializer");
        GameRegistry.registerTileEntity(TileCompactedMRS.class, "compacted_mrs");
    }

    public static Block registerBlock(Block block, String unlocalizedName)
    {
        block.setBlockName(unlocalizedName);
        block.setBlockTextureName(BloodArsenal.MODID + ":" + unlocalizedName);

        if (!(block instanceof BlockBloodCake || block instanceof BlockBurnedString))
        {
            block.setCreativeTab(BloodArsenal.BA_TAB);
        }

        blocksNotToBeRegistered.clear();
        for (String unlocName : BloodArsenalConfig.blocksToBeDisabled)
        {
            if (unlocName.equals(unlocalizedName))
            {
                blocksNotToBeRegistered.add(unlocName);
            }
        }
        if (!blocksNotToBeRegistered.contains(unlocalizedName))
        {
            GameRegistry.registerBlock(block, unlocalizedName);
        }

        return block;
    }

    public static Block registerBlock(Block block, Class<? extends ItemBlock> itemBlockClass, String unlocalizedName)
    {
        block.setCreativeTab(BloodArsenal.BA_TAB);

        for (String unlocName : BloodArsenalConfig.blocksToBeDisabled)
        {
            System.out.println(unlocName);
            if (unlocName.equals(unlocalizedName))
            {
                blocksNotToBeRegistered.add(unlocName);
            }
        }
        if (!blocksNotToBeRegistered.contains(unlocalizedName))
        {
            GameRegistry.registerBlock(block, itemBlockClass, unlocalizedName);
        }

        return block;
    }
}
