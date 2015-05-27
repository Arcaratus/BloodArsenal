package com.arc.bloodarsenal.items;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.block.ModBlocks;
import com.arc.bloodarsenal.items.armor.VampireCostume;
import com.arc.bloodarsenal.items.bauble.*;
import com.arc.bloodarsenal.items.sigil.SigilDivinity;
import com.arc.bloodarsenal.items.sigil.SigilEnder;
import com.arc.bloodarsenal.items.sigil.SigilSwimming;
import com.arc.bloodarsenal.items.tool.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemReed;

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
    public static Item energy_gatling;
    public static Item sigil_of_swimming;
    public static Item sigil_of_ender;
    public static Item blood_infused_glowstone_dust;
    public static Item self_sacrifice_amulet;
    public static Item sacrifice_amulet;
    public static Item sigil_of_divinity;
    public static Item blood_ball;
    public static Item wolf_hide;
    public static Item blood_money;
    public static Item blood_burned_string;
    public static Item glass_shard;
    public static Item empowered_sacrifice_amulet;
    public static Item empowered_self_sacrifice_amulet;

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
        bound_igniter = registerItem(new BoundIgniter(), "bound_flint_and_steel");
        soul_booster = registerItem(new ItemSoulBooster(), "soul_booster");
        soul_nullifier = registerItem(new ItemSoulNullifier(), "soul_nullifier");
        blood_cookie = registerItem(new ItemBloodCookie(), "blood_cookie");
        orange_juice = registerItem(new ItemBloodArsenal(), "orange_juice");
        juice_and_cookies = registerItem(new ItemJuiceAndCookies(), "juice_and_cookies");
        item_blood_cake = registerItem(new ItemReed(ModBlocks.blood_cake), "item_blood_cake");
        vampire_cape = registerItem(new VampireCostume(1), "vampire_cape");
        vampire_greaves = registerItem(new VampireCostume(2), "vampire_greaves");
        vampire_boots = registerItem(new VampireCostume(3), "vampire_boots");
        energy_gatling = registerItem(new EnergyGatling(), "energy_gatling");
        sigil_of_swimming = registerItem(new SigilSwimming(), "sigil_of_swimming");
        sigil_of_ender = registerItem(new SigilEnder(), "sigil_of_ender");
        blood_infused_glowstone_dust = registerItem(new ItemBloodArsenal(), "blood_infused_glowstone_dust");
        sigil_of_divinity = registerItem(new SigilDivinity(), "sigil_of_divinity");
        blood_ball = registerItem(new ItemBloodBall(), "blood_ball");
        wolf_hide = registerItem(new ItemBloodArsenal(), "wolf_hide");
        blood_money = registerItem(new ItemBloodMoney(), "blood_money");
        blood_burned_string = registerItem(new ItemReed(ModBlocks.block_burned_string), "blood_burned_string");
        glass_shard = registerItem(new ItemBloodArsenal(), "glass_shard");

        vampire_ring = new VampireRing();
        self_sacrifice_amulet = new SelfSacrificeAmulet();
        sacrifice_amulet = new SacrificeAmulet();
        empowered_sacrifice_amulet = new EmpoweredSacrificeAmulet();
        empowered_self_sacrifice_amulet = new EmpoweredSelfSacrificeAmulet();
    }

    public static void registerBaubles()
    {
        GameRegistry.registerItem(vampire_ring, "vampire_ring");
        GameRegistry.registerItem(self_sacrifice_amulet, "self_sacrifice_amulet");
        GameRegistry.registerItem(sacrifice_amulet, "sacrifice_amulet");
        GameRegistry.registerItem(empowered_self_sacrifice_amulet, "empowered_self_sacrifice_amulet");
        GameRegistry.registerItem(empowered_sacrifice_amulet, "empowered_sacrifice_amulet");
    }

    public static Item registerItem(Item item, String unlocalizedName)
    {
        item.setUnlocalizedName(unlocalizedName);
        item.setTextureName(BloodArsenal.MODID + ":" + unlocalizedName);
        item.setCreativeTab(BloodArsenal.BA_TAB);
        GameRegistry.registerItem(item, unlocalizedName);
        return item;
    }
}
