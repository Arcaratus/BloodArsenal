package arcaratus.bloodarsenal.registry;

import arcaratus.bloodarsenal.BloodArsenal;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import java.util.Set;
import java.util.UUID;

public class Constants
{
    public static final int HUNDRED = 100;
    public static final int ONE_K = 1000;
    public static final int TEN_K = 10000;
    public static final int HUNDRED_K = 100000;
    public static final int MILLION = 1000000;

    public static class NBT
    {
        public static final String OWNER_UUID = "ownerUUID";
        public static final String OWNER_NAME = "ownerNAME";
        public static final String ACTIVATED = "activated";
        public static final String POS = "pos";
        public static final String ITEMS = "Items";
        public static final String SLOT = "Slot";
        public static final String ITEM_INVENTORY = "itemInventory";

        public static final String LEVEL = "Level";
        public static final String KEY = "Key";
        public static final String COUNTER = "Counter";
        public static final String READY_TO_UPGRADE = "ReadyToUpgrade";

        public static final String TICKS_REMAINING = "ticksRemaining";
        public static final String PROJECTILE_TICKS_IN_AIR = "projectileTicksInAir";
        public static final String PROJECTILE_MAX_TICKS_IN_AIR = "projectileMaxTicksInAir";

        public static final String CURRENT_SIGIL = "currentSigil";
        public static final String MOST_SIG = "mostSig";
        public static final String LEAST_SIG = "leastSig";
        public static final String COLOR = "color";

        public static final String DELAY = "delay";

        public static final String STASIS_MODIFIERS = "stasisModifiers";
        public static final String MODIFIERS = "modifiers";
        public static final String MODIFIER = "modifier";

        public static final String MULTIPLIER = "multiplier";

        public static final String ITEMSTACK = "itemStack";
        public static final String ITEMSTACK_NAME = "itemStackName";

        public static final String CURRENT_PROFILE = "currentProfile";

        // TODO Do all the resources and stuff
        // ALL functional things should be finished!
    }

    public static class Item
    {
        public static final Set<Block> AXE_EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE);
        public static final Set<Block> PICKAXE_EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);
        public static final Set<Block> SHOVEL_EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER);
        public static final Set<Block> SICKLE_EFFECTIVE_ON = Sets.newHashSet(Blocks.BEETROOTS, Blocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.CACTUS, Blocks.CARROTS, Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT, Blocks.COCOA, Blocks.DEADBUSH, Blocks.DOUBLE_PLANT, Blocks.HAY_BLOCK, Blocks.LEAVES, Blocks.LEAVES2, Blocks.PUMPKIN_STEM, Blocks.RED_FLOWER, Blocks.RED_MUSHROOM, Blocks.RED_MUSHROOM_BLOCK, Blocks.REEDS, Blocks.SAPLING, Blocks.SPONGE, Blocks.TALLGRASS, Blocks.VINE, Blocks.WATERLILY, Blocks.WHEAT, Blocks.YELLOW_FLOWER);

        public static final Set<Material> AXE_MATERIALS_EFFECTIVE_ON = Sets.newHashSet(Material.WOOD, Material.CACTUS, Material.GOURD);
        public static final Set<Material> PICKAXE_MATERIALS_EFFECTIVE_ON = Sets.newHashSet(Material.ANVIL, Material.CLAY, Material.IRON, Material.PISTON, Material.ROCK);
        public static final Set<Material> SHOVEL_MATERIALS_EFFECTIVE_ON = Sets.newHashSet(Material.CRAFTED_SNOW, Material.GROUND, Material.SAND, Material.SNOW);
        public static final Set<Material> SICKLE_MAERIALS_EFFECTIVE_ON = Sets.newHashSet(Material.CACTUS, Material.CORAL, Material.LEAVES, Material.PLANTS, Material.GRASS, Material.VINE);
    }

    public static class Modifiers
    {
        public static final String BAD_POTION = "bad_potion";
        public static final String BLOODLUST = "bloodlust";
        public static final String FLAME = "flame";
        public static final String SHARPNESS = "sharpness";

        public static final String FORTUNATE = "fortunate";
        public static final String LOOTING = "looting";
        public static final String SILKY = "silky";
        public static final String SMELTING = "smelting";
        public static final String XPERIENCED = "xperienced";

        public static final String BENEFICIAL_POTION = "beneficial_potion";
        public static final String QUICK_DRAW = "quick_draw";
        public static final String SHADOW_TOOL = "shadow_tool";

        public static final String AOD = "aod";
        public static final String SIGIL = "sigil";

        public static final int[] BAD_POTION_COUNTER = new int[] { 0, 64, 256, 1024 };
        public static final int[] BLOODLUST_COUNTER = new int[] { 0, 2, 128, 256, 512, 1024, 2048 };
        public static final int[] FLAME_COUNTER = new int[] { 0, 40, 80, 160, 320, 640, 1280 };
        public static final int[] SHARPNESS_COUNTER = new int[] { 0, 4, 128, 256, 512, 1024, 2048 };

        public static final int[] FORTUNATE_COUNTER = new int[] { 0, 64, 256, 1024, 4096 };
        public static final int[] LOOTING_COUNTER = new int[] { 0, 36, 72, 144, 288 };
        public static final int[] SMELTING_COUNTER = new int[] { 0, 256, 1024 };
        public static final int[] XPERIENCED_COUNTER = new int[] { 0, 64, 256, 1024, 2048, 4096 };

        public static final int[] BENEFICIAL_POTION_COUNTER = new int[] { 0, 64, 256, 1024 };
        public static final int[] QUICK_DRAW_COUNTER = new int[] { 0, 500, 1000 };
        public static final int[] SHADOW_TOOL_COUNTER = new int[] { 0, 256, 1024, 16384 };

        public static final int[] AOD_COUNTER = new int[] { 0, 64, 256, 512, 1024, 2048 };
    }

    public static class Gui
    {
        public static final int SIGIL_AUGMENTED_HOLDING_GUI = 0;
        public static final int ALTARE_AENIGMATICA_GUI = 1;
    }

    public static class Compat
    {
        public static final String JEI_CATEGORY_SANGUINE_INFUSION = BloodArsenal.MOD_ID + ":sanguineInfusion";

        public static final String WAILA_CONFIG_STASIS_PLATE = BloodArsenal.MOD_ID + ".stasis_plate";
        public static final String WAILA_CONFIG_BLOOD_CAPACITOR = BloodArsenal.MOD_ID + ".blood_capacitor";
    }

    public static class Misc
    {
        public static final int PLAYER_INVENTORY_ROWS = 3;
        public static final int PLAYER_INVENTORY_COLUMNS = 9;
        public static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");

        public static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    }
}
