package arcaratus.bloodarsenal.common.item;

import arcaratus.bloodarsenal.common.BloodArsenal;
import arcaratus.bloodarsenal.common.block.ModBlocks;
import arcaratus.bloodarsenal.common.core.BloodArsenalCreativeTab;
import arcaratus.bloodarsenal.common.item.sigil.DivinitySigilItem;
import arcaratus.bloodarsenal.common.item.sigil.EnderSigilItem;
import arcaratus.bloodarsenal.common.item.sigil.LightningSigilItem;
import arcaratus.bloodarsenal.common.item.tool.*;
import arcaratus.bloodarsenal.common.item.tool.iron.*;
import arcaratus.bloodarsenal.common.item.tool.wood.*;
import net.minecraft.item.Food;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BloodArsenal.MOD_ID);

    public static final Food FOOD_BLOOD_ORANGE = new Food.Builder().hunger(4).saturation(2).effect(new EffectInstance(Effects.REGENERATION, 80), 0.5F).setAlwaysEdible().build();

    public static final RegistryObject<Item> GLASS_SHARD = registerItem("glass_shard", new BloodArsenalItem(defaultBuilder(), true));
    public static final RegistryObject<Item> BLOOD_ORANGE = registerItem("blood_orange", new BloodArsenalItem(defaultBuilder().food(FOOD_BLOOD_ORANGE)));
    public static final RegistryObject<Item> BLOOD_INFUSED_STICK = registerItem("blood_infused_stick", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> BLOOD_INFUSED_GLOWSTONE_DUST = registerItem("blood_infused_glowstone_dust", new BloodArsenalItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INERT_BLOOD_INFUSED_IRON_INGOT = registerItem("inert_blood_infused_iron_ingot", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> BLOOD_INFUSED_IRON_INGOT = registerItem("blood_infused_iron_ingot", new BloodArsenalItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BLOOD_DIAMOND = registerItem("blood_diamond", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> INERT_BLOOD_DIAMOND = registerItem("inert_blood_diamond", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> INFUSED_BLOOD_DIAMOND = registerItem("infused_blood_diamond", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> BOUND_BLOOD_DIAMOND = registerItem("bound_blood_diamond", new BloodArsenalItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> STASIS_PLATE = registerItem("stasis_plate", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> REAGENT_SWIMMING = registerItem("reagent_swimming", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> REAGENT_ENDER = registerItem("reagent_ender", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> REAGENT_LIGHTNING = registerItem("reagent_lightning", new BloodArsenalItem(defaultBuilder().rarity(Rarity.RARE), true, true));
    public static final RegistryObject<Item> REAGENT_DIVINITY = registerItem("reagent_divinity", new BloodArsenalItem(defaultBuilder().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> GLASS_SACRIFICIAL_DAGGER = registerItem("glass_sacrificial_dagger", new GlassSacrificialDaggerItem(unstackable()));
    public static final RegistryObject<Item> GLASS_DAGGER_OF_SACRIFICE = registerItem("glass_dagger_of_sacrifice", new GlassDaggerOfSacrificeItem(unstackable()));

    public static final RegistryObject<Item> BLOOD_INFUSED_WOODEN_PICKAXE = registerItem("blood_infused_wooden_pickaxe", new BloodInfusedWoodenPickaxeItem(unstackable()));
    public static final RegistryObject<Item> BLOOD_INFUSED_WOODEN_SHOVEL = registerItem("blood_infused_wooden_shovel", new BloodInfusedWoodenShovelItem(unstackable()));
    public static final RegistryObject<Item> BLOOD_INFUSED_WOODEN_AXE = registerItem("blood_infused_wooden_axe", new BloodInfusedWoodenAxeItem(unstackable()));
    public static final RegistryObject<Item> BLOOD_INFUSED_WOODEN_SWORD = registerItem("blood_infused_wooden_sword", new BloodInfusedWoodenSwordItem(unstackable()));
    public static final RegistryObject<Item> BLOOD_INFUSED_IRON_PICKAXE = registerItem("blood_infused_iron_pickaxe", new BloodInfusedIronPickaxeItem(unstackable()));
    public static final RegistryObject<Item> BLOOD_INFUSED_IRON_SHOVEL = registerItem("blood_infused_iron_shovel", new BloodInfusedIronShovelItem(unstackable()));
    public static final RegistryObject<Item> BLOOD_INFUSED_IRON_AXE = registerItem("blood_infused_iron_axe", new BloodInfusedIronAxeItem(unstackable()));
    public static final RegistryObject<Item> BLOOD_INFUSED_IRON_SWORD = registerItem("blood_infused_iron_sword", new BloodInfusedIronSwordItem(unstackable()));

    public static final RegistryObject<Item> ENDER_SIGIL = registerItem("ender_sigil", new EnderSigilItem(unstackable()));
    public static final RegistryObject<Item> LIGHTNING_SIGIL = registerItem("lightning_sigil", new LightningSigilItem(unstackable().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DIVINITY_SIGIL = registerItem("divinity_sigil", new DivinitySigilItem(unstackable()));

    public static Item.Properties defaultBuilder()
    {
        return new Item.Properties().group(BloodArsenalCreativeTab.INSTANCE);
    }

    private static Item.Properties unstackable()
    {
        return defaultBuilder().maxStackSize(1);
    }

    public static RegistryObject<Item> registerItem(String name, Item item)
    {
        return ITEMS.register(name, () -> item);
    }

    public enum ItemTier implements IItemTier
    {
        BLOOD_INFUSED_WOOD(180, 5.5F, 1, 1, 25, () -> ModBlocks.BLOOD_INFUSED_PLANKS.get().asItem()),
        BLOOD_INFUSED_IRON(900, 7.5F, 2.7F, 3, 30, ModItems.BLOOD_INFUSED_IRON_INGOT),
        ;

        private final int maxUses;
        private final float efficiency;
        private final float attackDamage;
        private final int harvestLevel;
        private final int enchantability;
        private final Supplier<Item> repairItem;

        ItemTier(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Item> repairItem)
        {
            this.maxUses = maxUses;
            this.efficiency = efficiency;
            this.attackDamage = attackDamage;
            this.harvestLevel = harvestLevel;
            this.enchantability = enchantability;
            this.repairItem = repairItem;
        }

        @Override
        public int getMaxUses()
        {
            return maxUses;
        }

        @Override
        public float getEfficiency()
        {
            return efficiency;
        }

        @Override
        public float getAttackDamage()
        {
            return attackDamage;
        }

        @Override
        public int getHarvestLevel()
        {
            return harvestLevel;
        }

        @Override
        public int getEnchantability()
        {
            return enchantability;
        }

        @Override
        public Ingredient getRepairMaterial()
        {
            return Ingredient.fromItems(repairItem.get());
        }
    }
}
