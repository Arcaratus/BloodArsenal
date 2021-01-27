package arcaratus.bloodarsenal.common.item;

import arcaratus.bloodarsenal.common.BloodArsenal;
import arcaratus.bloodarsenal.common.core.BloodArsenalCreativeTab;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BloodArsenal.MOD_ID);

    public static final RegistryObject<Item> GLASS_SHARD = registerItem("glass_shard", new BloodArsenalItem(defaultBuilder(), "glass_shard.info"));
    public static final RegistryObject<Item> BLOOD_INFUSED_STICK = registerItem("blood_infused_stick", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> BLOOD_INFUSED_GLOWSTONE_DUST = registerItem("blood_infused_glowstone_dust", new BloodArsenalItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INERT_BLOOD_INFUSED_IRON_INGOT = registerItem("inert_blood_infused_iron_ingot", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> BLOOD_INFUSED_IRON_INGOT = registerItem("blood_infused_iron_ingot", new BloodArsenalItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> STASIS_PLATE = registerItem("stasis_plate", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> REAGENT_SWIMMING = registerItem("reagent_swimming", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> REAGENT_ENDER = registerItem("reagent_ender", new BloodArsenalItem(defaultBuilder()));
    public static final RegistryObject<Item> REAGENT_LIGHTNING = registerItem("reagent_lightning", new BloodArsenalItem(defaultBuilder(), "reagent_lightning.info", true));
    public static final RegistryObject<Item> REAGENT_DIVINITY = registerItem("reagent_divinity", new BloodArsenalItem(defaultBuilder().rarity(Rarity.EPIC)));

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
}
