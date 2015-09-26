package com.arc.bloodarsenal.common.items;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import com.arc.bloodarsenal.common.BloodArsenal;
import com.arc.bloodarsenal.common.BloodArsenalConfig;
import com.arc.bloodarsenal.common.block.ModBlocks;
import com.arc.bloodarsenal.common.items.armor.GlassArmor;
import com.arc.bloodarsenal.common.items.armor.LifeImbuedArmor;
import com.arc.bloodarsenal.common.items.armor.VampireCostume;
import com.arc.bloodarsenal.common.items.bauble.*;
import com.arc.bloodarsenal.common.items.orb.TransparentOrb;
import com.arc.bloodarsenal.common.items.sigil.*;
import com.arc.bloodarsenal.common.items.sigil.holding.SigilAugmentedHolding;
import com.arc.bloodarsenal.common.items.tool.*;
import com.arc.bloodarsenal.common.thaumcraft.*;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.StaffRod;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;

import java.util.ArrayList;
import java.util.Arrays;

public class ModItems
{
    public static Item bound_bow;
    public static Item blood_orange;
    public static Item blood_infused_pickaxe_wood;
    public static Item blood_infused_axe_wood;
    public static Item blood_infused_shovel_wood;
    public static Item blood_infused_sword_wood;
    public static Item amorphic_catalyst;
    public static Item blood_infused_stick;
    public static Item bound_sickle;
    public static Item blood_infused_pickaxe_iron;
    public static Item blood_infused_axe_iron;
    public static Item blood_infused_shovel_iron;
    public static Item blood_infused_sword_iron;
    public static Item blood_infused_iron;
    public static Item bound_shears;
    public static Item blood_diamond;
    public static Item blood_infused_diamond_unactive;
    public static Item blood_infused_diamond_active;
    public static Item blood_infused_diamond_bound;
    public static Item blood_infused_pickaxe_diamond;
    public static Item blood_infused_axe_diamond;
    public static Item blood_infused_shovel_diamond;
    public static Item blood_infused_sword_diamond;
    public static Item heart;
    public static Item soul_fragment;
    public static Item bound_igniter;
    public static Item soul_booster;
    public static Item soul_nullifier;
    public static Item blood_cookie;
    public static Item orange_juice;
    public static Item juice_and_cookies;
    public static Item item_blood_cake;
    public static Item vampire_cape;
    public static Item vampire_greaves;
    public static Item vampire_boots;
    public static Item vampire_ring;
    public static Item sigil_of_swimming;
    public static Item sigil_of_ender;
    public static Item blood_infused_glowstone_dust;
    public static Item self_sacrifice_amulet;
    public static Item sacrifice_amulet;
    public static Item sigil_of_divinity;
    public static Item wolf_hide;
    public static Item blood_money;
    public static Item blood_burned_string;
    public static Item glass_shard;
    public static Item empowered_sacrifice_amulet;
    public static Item empowered_self_sacrifice_amulet;
    public static Item sigil_of_augmented_holding;
    public static Item sigil_of_lightning;
    public static Item glass_sacrificial_dagger;
    public static Item glass_dagger_of_sacrifice;
    public static Item glass_helmet;
    public static Item glass_chestplate;
    public static Item glass_leggings;
    public static Item glass_boots;
    public static Item transparent_orb;
    public static Item life_imbued_helmet;
    public static Item life_imbued_chestplate;
    public static Item life_imbued_leggings;
    public static Item life_imbued_boots;
    public static Item bound_diamond_shard;
    public static Item wandCore;
    public static Item wandCap;
    public static WandRod WAND_ROD_BLOOD_INFUSED_WOOD;
    public static StaffRod STAFF_ROD_BLOOD_INFUSED_WOOD;
    public static WandCap WAND_CAP_BLOOD_INFUSED_IRON;
    public static Item life_imbued_helmet_water;
    public static Item life_imbued_chestplate_fire;
    public static Item life_imbued_leggings_earth;
    public static Item life_imbued_boots_air;

    public static ArrayList<String> itemsNotToBeRegistered = new ArrayList<String>();

    public static void init()
    {
        bound_bow = registerItem(new BoundBow(), "bound_bow");
        blood_orange = registerItem(new ItemBloodOrange(), "blood_orange");
        blood_infused_pickaxe_wood = registerItem(new InfusedWoodPickaxe(), "blood_infused_pickaxe_wood");
        blood_infused_axe_wood = registerItem(new InfusedWoodAxe(), "blood_infused_axe_wood");
        blood_infused_shovel_wood = registerItem(new InfusedWoodShovel(), "blood_infused_shovel_wood");
        blood_infused_sword_wood = registerItem(new InfusedWoodSword(), "blood_infused_sword_wood");
        amorphic_catalyst = registerItem(new ItemBloodArsenal(), "amorphic_catalyst");
        blood_infused_stick = registerItem(new ItemBloodArsenal(), "blood_infused_stick");
        bound_sickle = registerItem(new BoundSickle(AlchemicalWizardry.bloodBoundToolMaterial), "bound_sickle");
        blood_infused_pickaxe_iron = registerItem(new InfusedIronPickaxe(), "blood_infused_pickaxe_iron");
        blood_infused_axe_iron = registerItem(new InfusedIronAxe(), "blood_infused_axe_iron");
        blood_infused_shovel_iron = registerItem(new InfusedIronShovel(), "blood_infused_shovel_iron");
        blood_infused_sword_iron = registerItem(new InfusedIronSword(), "blood_infused_sword_iron");
        blood_infused_iron = registerItem(new ItemBloodArsenal(), "blood_infused_iron");
        bound_shears = registerItem(new BoundShears(), "bound_shears");
        blood_diamond = registerItem(new ItemBloodArsenal(), "blood_diamond");
        blood_infused_diamond_unactive = registerItem(new InfusedDiamond(), "blood_infused_diamond_unactive");
        blood_infused_diamond_active = registerItem(new InfusedDiamond(), "blood_infused_diamond_active");
        blood_infused_diamond_bound = registerItem(new InfusedDiamond(), "blood_infused_diamond_bound");
        blood_infused_pickaxe_diamond = registerItem(new InfusedDiamondPickaxe(), "blood_infused_pickaxe_diamond");
        blood_infused_axe_diamond = registerItem(new InfusedDiamondAxe(), "blood_infused_axe_diamond");
        blood_infused_shovel_diamond = registerItem(new InfusedDiamondShovel(), "blood_infused_shovel_diamond");
        blood_infused_sword_diamond = registerItem(new InfusedDiamondSword(), "blood_infused_sword_diamond");
        heart = registerItem(new ItemBloodArsenal(), "heart");
        soul_fragment = registerItem(new ItemBloodArsenal(), "soul_fragment");
        bound_igniter = registerItem(new BoundIgniter(), "bound_igniter");
        soul_booster = registerItem(new ItemSoulBooster(), "soul_booster");
        soul_nullifier = registerItem(new ItemSoulNullifier(), "soul_nullifier");
        blood_cookie = registerItem(new ItemBloodCookie(), "blood_cookie");
        orange_juice = registerItem(new ItemBloodArsenal(), "orange_juice");
        juice_and_cookies = registerItem(new ItemJuiceAndCookies(), "juice_and_cookies");
        item_blood_cake = registerItem(new ItemReed(ModBlocks.blood_cake), "item_blood_cake");
        vampire_cape = registerItem(new VampireCostume(1), "vampire_cape");
        vampire_greaves = registerItem(new VampireCostume(2), "vampire_greaves");
        vampire_boots = registerItem(new VampireCostume(3), "vampire_boots");
        sigil_of_swimming = registerItem(new SigilSwimming(), "sigil_of_swimming");
        sigil_of_ender = registerItem(new SigilEnder(), "sigil_of_ender");
        blood_infused_glowstone_dust = registerItem(new ItemBloodArsenal(), "blood_infused_glowstone_dust");
        sigil_of_divinity = registerItem(new SigilDivinity(), "sigil_of_divinity");
        wolf_hide = registerItem(new ItemBloodArsenal(), "wolf_hide");
        blood_money = registerItem(new ItemBloodMoney(), "blood_money");
        blood_burned_string = registerItem(new ItemReed(ModBlocks.block_burned_string), "blood_burned_string");
        glass_shard = registerItem(new ItemBloodArsenal(), "glass_shard");
        sigil_of_augmented_holding = registerItem(new SigilAugmentedHolding(), "sigil_of_augmented_holding");
        sigil_of_lightning = registerItem(new SigilLightning(), "sigil_of_lightning");
        glass_sacrificial_dagger = registerItem(new GlassSacrificialDagger(), "glass_sacrificial_dagger");
        glass_dagger_of_sacrifice = registerItem(new GlassDaggerOfSacrifice(), "glass_dagger_of_sacrifice");
        glass_helmet = registerItem(new GlassArmor(0), "glass_helmet");
        glass_chestplate = registerItem(new GlassArmor(1), "glass_chestplate");
        glass_leggings = registerItem(new GlassArmor(2), "glass_leggings");
        glass_boots = registerItem(new GlassArmor(3), "glass_boots");
        transparent_orb = registerItem(new TransparentOrb(30000000), "transparent_orb");
        life_imbued_helmet = registerItem(new LifeImbuedArmor(0), "life_imbued_helmet");
        life_imbued_chestplate = registerItem(new LifeImbuedArmor(1), "life_imbued_chestplate");
        life_imbued_leggings = registerItem(new LifeImbuedArmor(2), "life_imbued_leggings");
        life_imbued_boots = registerItem(new LifeImbuedArmor(3), "life_imbued_boots");
        bound_diamond_shard = registerItem(new ItemBoundDiamondShards(), "bound_diamond_shard");
    }

    @Optional.Method(modid = "Baubles")
    public static void registerBaubles()
    {
        vampire_ring = registerItem(new VampireRing(), "vampire_ring");
        self_sacrifice_amulet = registerItem(new SelfSacrificeAmulet(), "self_sacrifice_amulet");
        sacrifice_amulet = registerItem(new SacrificeAmulet(), "sacrifice_amulet");
        empowered_sacrifice_amulet = registerItem(new EmpoweredSacrificeAmulet(), "empowered_sacrifice_amulet");
        empowered_self_sacrifice_amulet = registerItem(new EmpoweredSelfSacrificeAmulet(), "empowered_self_sacrifice_amulet");
    }

    @Optional.Method(modid = "Thaumcraft")
    public static void registerThaumcraftItems()
    {
        wandCore = (new ItemWandCores()).setUnlocalizedName("wand_cores");
        GameRegistry.registerItem(wandCore, "wand_cores");
        wandCap = (new ItemWandCaps()).setUnlocalizedName("wand_caps");
        GameRegistry.registerItem(wandCap, "wand_caps");
        WAND_ROD_BLOOD_INFUSED_WOOD = new WandRod("blood_wood", 100, new ItemStack(wandCore, 1, 0), 16, new BloodWoodWandUpdate(), new ResourceLocation("bloodarsenal", "models/wand_rod_blood_wood.png"));
        STAFF_ROD_BLOOD_INFUSED_WOOD = new StaffRod("blood_wood", 60, new ItemStack(wandCore, 1, 1), 27, new BloodWoodStaffUpdate(), new ResourceLocation("bloodarsenal", "models/wand_rod_blood_wood.png"));
        WAND_CAP_BLOOD_INFUSED_IRON = new BloodyWandCap("blood_iron", 0.85F, Arrays.asList(new Aspect[]{Aspect.WATER}), 0.80F, new ItemStack(wandCap, 1, 0), 5, new ResourceLocation("bloodarsenal", "models/wand_cap_blood_iron.png"));
    }

    public static Item registerItem(Item item, String unlocalizedName)
    {
        item.setUnlocalizedName(unlocalizedName);
        item.setTextureName(BloodArsenal.MODID + ":" + unlocalizedName);
        item.setCreativeTab(BloodArsenal.BA_TAB);
        itemsNotToBeRegistered.clear();

        for (String unlocName : BloodArsenalConfig.itemsToBeDisabled)
        {
            if (unlocName.equals(unlocalizedName))
            {
                itemsNotToBeRegistered.add(unlocName);
            }
        }

        if (!itemsNotToBeRegistered.contains(unlocalizedName))
        {
            GameRegistry.registerItem(item, unlocalizedName);
        }

        return item;
    }
}
