package arcaratus.bloodarsenal;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static arcaratus.bloodarsenal.registry.Constants.*;

@Config(modid = BloodArsenal.MOD_ID, name = BloodArsenal.MOD_ID + "/" + BloodArsenal.MOD_ID, category = "")
@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
public class ConfigHandler
{
    @Comment({ "Blacklist options for various features" })
    public static ConfigBlacklist blacklist = new ConfigBlacklist();
    @Comment({ "Value modifiers for various features" })
    public static ConfigValues values = new ConfigValues();
    @Comment({ "Toggles for all rituals" })
    public static ConfigRituals rituals = new ConfigRituals();
    @Comment({ "Miscellaneous options" })
    public static ConfigMisc misc = new ConfigMisc();
    @Comment({ "Options for different mod compatibilities" })
    public static ConfigCompat compat = new ConfigCompat();

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(BloodArsenal.MOD_ID))
        {
            ConfigManager.sync(event.getModID(), Config.Type.INSTANCE); // Sync config values
        }
    }

    public static class ConfigBlacklist
    {
        @Comment({ "Blacklists the loading of listed items.", "Use the registry name of the item. Vanilla items do not require the modid." })
        public String[] itemBlacklist = {};
        @Comment({ "Blacklists the loading of listed blocks.", "Use the registry name of the block. Vanilla blocks do not require the modid." })
        public String[] blockBlacklist = {};
    }

    public static class ConfigValues
    {
        @Comment({ "Amount of damage the Glass Shards block deals when an entity touches it" })
        @RangeDouble(min = 0, max = ONE_K)
        public double glassShardsDamage = 2;
        @Comment({ "The multiplier (per item) of LP drained when the Altare Aenigmatica moves an item to the altar" })
        @RangeInt(min = 0, max = ONE_K)
        public int altareAenigmaticaMoveMultiplier = 50;
        @Comment({ "Amount of energy the Blood Capacitor can store" })
        @RangeInt(min = 0, max = ONE_K * MILLION)
        public int bloodCapacitorStorage = 10 * MILLION;
        @Comment({ "Amount of energy the Blood Capacitor can transfer per tick" })
        @RangeInt(min = 0, max = MILLION)
        public int bloodCapacitorTransfer = TEN_K;
        @Comment({ "Amount of LP the Glass Sacrificial Dagger (approximately) gives per use" })
        @RangeInt(min = 0, max = TEN_K)
        public int glassSacrificialDaggerLP = 5 * HUNDRED;
        @Comment({ "Amount of damage the Glass Sacrificial Dagger deals per use" })
        @RangeInt(min = 1, max = TEN_K)
        public double glassSacrificialDaggerHealth = 2;
        @Comment({ "The multiplier of LP for the Glass Dagger of Sacrifice" })
        @RangeDouble(min = 1, max = ONE_K)
        public double glassDaggerOfSacrificeLPMultiplier = 2;
        @Comment({ "Amount of ticks for a repair to occur for Blood Infused Wooden Tools" })
        @RangeInt(min = 0, max = ONE_K)
        public int bloodInfusedWoodenToolsRepairUpdate = HUNDRED;
        @Comment({ "Amount of LP required for a repair for Blood Infused Wooden Tools" })
        @RangeInt(min = 0, max = ONE_K)
        public int bloodInfusedWoodenToolsRepairCost = 20;
        @Comment({ "Amount of ticks for a repair to occur for Blood Infused Iron Tools" })
        @RangeInt(min = 0, max = ONE_K)
        public int bloodInfusedIronToolsRepairUpdate = 40;
        @Comment({ "Amount of LP required for a repair for Blood Infused Iron Tools" })
        @RangeInt(min = 0, max = ONE_K)
        public int bloodInfusedIronToolsRepairCost = 50;
        @Comment({ "Amount of LP required per usage of the Bound Igniter" })
        @RangeInt(min = 0, max = TEN_K)
        public int boundIgniterCost = 2 * HUNDRED;
        @Comment({ "Amount of LP required per usage of the Bound Shears" })
        @RangeInt(min = 0, max = TEN_K)
        public int boundShearsCost = 3 * HUNDRED;
        @Comment({ "Amount of LP per update for the Sigil of Swimming" })
        @RangeInt(min = 0, max = TEN_K)
        public int sigilSwimmingCost = HUNDRED;
        @Comment({ "Amount of LP required to open the Ender Sigil" })
        @RangeInt(min = 0, max = TEN_K)
        public int sigilEnderOpenCost = 5 * HUNDRED;
        @Comment({ "Multiplier of LP for teleporting with the Ender Sigil" })
        @RangeDouble(min = 0, max = TEN_K)
        public double sigilEnderTeleportMultiplier = 2 * HUNDRED;
        @Comment({ "Time (in ticks) of the delay before teleportation for the Ender Sigil" })
        @RangeInt(min = 0, max = 60)
        public int sigilEnderTeleportationDelay = 10;
        @Comment({ "Time (in ticks) of the cooldown after teleportation for the Ender Sigil" })
        @RangeInt(min = 0, max = 200)
        public int sigilEnderTeleportationCooldown = 40;
        @Comment({ "Multiplier of LP (per lightning bolt) for zapping things with the Lightning Sigil" })
        @RangeDouble(min = 0, max = TEN_K)
        public double sigilLightningMultiplier = 8 * HUNDRED;
        @Comment({ "Amount of LP per update for the Sigil of Divinity" })
        @RangeInt(min = 0)
        public int sigilDivinityCost = HUNDRED_K;
        @Comment({ "Amount of LP per usage for the Sigil of Sentience" })
        @RangeInt(min = 0, max = TEN_K)
        public int sigilSentienceBaseCost = ONE_K;
        @Comment({ "Fraction of the damage that is converted to health for the Vampire Ring: health = (damageDealt * vampireRingSyphon)" })
        @RangeDouble(min = 0, max = HUNDRED)
        public double vampireRingMultiplier = 0.5;
        @Comment({ "Multiplier of LP for the Sacrifice Amulet: LP = (damageDone * sacrificeAmuletMultiplier" })
        @RangeDouble(min = 0, max = HUNDRED)
        public double sacrificeAmuletMultiplier = 20;
        @Comment({ "Multiplier of LP for the Self Sacrifice Amulet: LP = (damageDone * sacrificeAmuletMultiplier" })
        @RangeDouble(min = 0, max = HUNDRED)
        public double selfSacrificeAmuletMultiplier = 20;
    }

    // TODO: Port this to the automagic stuff as seen in RitualManager
    public static class ConfigRituals
    {
        public boolean infusionRitual = true;
        public boolean purificationRitual = true;
        public boolean bloodBurnerRitual = true;

        @Comment({ "Activation cost of the Infusion de Sanguine (infusionRitual)" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int infusionRitualActivationCost = TEN_K;
        @Comment({ "Refresh cost of the Infusion de Sanguine (infusionRitual)" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int infusionRitualRefreshCost = HUNDRED;
        @Comment({ "Activation cost of the Ritual of Purification (purificationRitual)" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int purificationtRitualActivationCost = TEN_K;
        @Comment({ "Refresh cost of the Ritual of Purification (purificationRitual)" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int purificationRitualRefreshCost = 20;
        @Comment({ "Minimum amount of Life Essence (in mB) required to start the Ritual of Purification" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int purificationRitualMinLP = TEN_K;
        @Comment({ "Amount of Life Essence (in mB) per mB of Refined Life Essence" })
        @RangeInt(min = 1, max = TEN_K)
        public int refinedLifeEssenceConversion = 10;
        @Comment({ "Activation cost of the Ritual of Purification (purificationRitual)" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int bloodBurnerRitualActivationCost = TEN_K;
        @Comment({ "Refresh cost of the Ritual of Purification (purificationRitual)" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int bloodBurnerRitualRefreshCost = 2 * HUNDRED;

        public ConfigImperfectRituals imperfect = new ConfigImperfectRituals();
    }

    public static class ConfigImperfectRituals
    {
        public boolean imperfectLightning = true;
        public boolean imperfectEnchantReset = true;
        public boolean imperfectIce = true;
        public boolean imperfectSnow = true;

        @Comment({ "Activation cost of the Imperfect Lightning Ritual" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int imperfectLightningActivationCost = 5 * ONE_K;
        @Comment({ "Activation cost of the Enchant Reset Ritual" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int imperfectEnchantResetActivationCost = 5 * ONE_K;
        @Comment({ "Activation cost of the Imperfect Lightning Ritual" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int imperfectIceActivationCost = 5 * HUNDRED;
        @Comment({ "Activation cost of the Imperfect Lightning Ritual" })
        @RangeInt(min = 0, max = HUNDRED_K)
        public int imperfectSnowActivationCost = 5 * HUNDRED;
    }

    public static class ConfigMisc
    {
        @Comment({ "Should be set to false when another mod adds in its own Ore-Dict glass shards" })
        public boolean doGlassShardsDrop = true;
        @Comment({ "Determines if there is a chance dying to a glass sacrificial dagger or bleeding has the drops disappear." })
        public boolean glassDeathCanRemoveDrops = true;
        @Comment({ "The max range for anything that uses raytracing (Ender Sigil, Lightning Sigil, etc.)" })
        @RangeDouble(min = 1, max = 256)
        public double rayTraceRange = 64;
    }

    public static class ConfigCompat
    {
        public boolean tconstructCompatEnabled = true;


    }
}
