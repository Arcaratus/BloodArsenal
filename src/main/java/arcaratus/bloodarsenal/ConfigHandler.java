package arcaratus.bloodarsenal;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        @Comment({ "Amount of damage the Glass Shards block deals when an entity touches it." })
        @RangeDouble(min = 0, max = 1000)
        public double glassShardsDamage = 2;
        @Comment({ "The multiplier (per item) of LP drained when the Altare Aenigmatica moves an item to the altar." })
        @RangeInt(min = 0, max = 1000)
        public int altareAenigmaticaMoveMultiplier = 50;
        @Comment({ "Amount of LP the Glass Sacrificial Dagger (approximately) gives per use." })
        @RangeInt(min = 0, max = 10000)
        public int glassSacrificialDaggerLP = 500;
        @Comment({ "Amount of damage the Glass Sacrificial Dagger deals per use." })
        @RangeInt(min = 1, max = 10000)
        public double glassSacrificialDaggerHealth = 2;
        @Comment({ "The multiplier of LP for the Glass Dagger of Sacrifice." })
        @RangeDouble(min = 1, max = 1000)
        public double glassDaggerOfSacrificeLPMultiplier = 2;
        @Comment({ "Amount of ticks for a repair to occur for Blood Infused Wooden Tools." })
        @RangeInt(min = 0, max = 1000)
        public int bloodInfusedWoodenToolsRepairUpdate = 100;
        @Comment({ "Amount of LP required for a repair for Blood Infused Wooden Tools." })
        @RangeInt(min = 0, max = 1000)
        public int bloodInfusedWoodenToolsRepairCost = 20;
        @Comment({ "Amount of ticks for a repair to occur for Blood Infused Iron Tools." })
        @RangeInt(min = 0, max = 1000)
        public int bloodInfusedIronToolsRepairUpdate = 40;
        @Comment({ "Amount of LP required for a repair for Blood Infused Iron Tools." })
        @RangeInt(min = 0, max = 1000)
        public int bloodInfusedIronToolsRepairCost = 50;
        @Comment({ "Amount of LP per update for the Sigil of Swimming." })
        @RangeInt(min = 0, max = 10000)
        public int sigilSwimmingCost = 100;
        @Comment({ "Amount of LP required to open the Ender Sigil." })
        @RangeInt(min = 0, max = 10000)
        public int sigilEnderOpenCost = 500;
        @Comment({ "Multiplier of LP for teleporting with the Ender Sigil" })
        @RangeDouble(min = 0, max = 10000)
        public double sigilEnderTeleportMultiplier = 200;
        @Comment({ "Multiplier of LP (per lightning bolt) for zapping things with the Lightning Sigil" })
        @RangeDouble(min = 0, max = 10000)
        public double sigilLightningMultiplier = 800;
        @Comment({ "Amount of LP per update for the Sigil of Divinity." })
        @RangeInt(min = 0)
        public int sigilDivinityCost = 100000;
        @Comment({ "Amount of LP per usage for the Sigil of Sentience." })
        @RangeInt(min = 0, max = 10000)
        public int sigilSentienceBaseCost = 1000;
    }

    public static class ConfigRituals
    {
        public boolean infusionRitual = true;

        public ConfigImperfectRituals imperfect = new ConfigImperfectRituals();
    }

    public static class ConfigImperfectRituals
    {
        public boolean imperfectLightning = true;
        public boolean imperfectEnchantReset = true;
        public boolean imperfectIce = true;
        public boolean imperfectSnow = true;
    }

    public static class ConfigMisc
    {
        @Comment({ "Should be set to false when another mod adds in its own Ore-Dict glass shards." })
        public boolean doGlassShardsDrop = true;
        @Comment({ "The max range for anything that uses raytracing (Ender Sigil, Lightning Sigil, etc.)" })
        @RangeDouble(min = 1, max = 256)
        public double rayTraceRange;
        @Comment({ "Provides a temporary recipe for the Crystal Cluster blocks in vanilla Blood Magic for those who want access to Tier 6." })
        public boolean crystalClusterEnabled;
    }

    public static class ConfigCompat
    {
        public boolean baublesCompatEnabled = true;
        public boolean tconstructCompatEnabled = true;

        @Comment({ "Base conversion for the Sacrifice Amulet = (damageDealt * sacrificeAmuletConversion)" })
        @RangeDouble(min = 0, max = 10000)
        public double sacrificeAmuletConversion = 20;
        @Comment({ "Base conversion for the Self Sacrifice Amulet = (damageDealt * selfSacrificeAmuletConversion)" })
        @RangeDouble(min = 0, max = 10000)
        public double selfSacrificeAmuletConversion = 2;
        @Comment({ "Health syphoned from the Ring of Vampirism = (damageDealt * ringOfVampirismHealthSyphon)" })
        @RangeDouble(min = 0, max = 10000)
        public double ringOfVampirismHealthSyphon = 0.5;
    }
}
