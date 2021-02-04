package arcaratus.bloodarsenal.common;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHandler
{
    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static
    {
        final Pair<Client, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientPair.getRight();
        CLIENT = clientPair.getLeft();

        final Pair<Common, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonPair.getRight();
        COMMON = commonPair.getLeft();
    }

    public static class Client
    {
        public Client(ForgeConfigSpec.Builder builder)
        {

        }
    }

    public static class Common
    {
        public final ForgeConfigSpec.IntValue glassSacrificialDaggerLP;
        public final ForgeConfigSpec.DoubleValue glassSacrificialDaggerDamage;
        public final ForgeConfigSpec.DoubleValue glassDaggerOfSacrificeMultiplier;
        public final ForgeConfigSpec.IntValue bloodInfusedWoodenToolsRepairUpdate;
        public final ForgeConfigSpec.IntValue bloodInfusedWoodenToolsRepairCost;
        public final ForgeConfigSpec.IntValue bloodInfusedIronToolsRepairUpdate;
        public final ForgeConfigSpec.IntValue bloodInfusedIronToolsRepairCost;
        public final ForgeConfigSpec.IntValue enderSigilCost;
        public final ForgeConfigSpec.IntValue enderSigilTeleportMultiplier;
        public final ForgeConfigSpec.IntValue enderSigilTeleportCooldown;
        public final ForgeConfigSpec.IntValue lightningSigilMaxLevel;
        public final ForgeConfigSpec.IntValue lightningSigilCost;
        public final ForgeConfigSpec.IntValue lightningSigilCooldown;
        public final ForgeConfigSpec.IntValue divinitySigilCost;

        public final ForgeConfigSpec.DoubleValue rayTraceRange;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("values");
            glassSacrificialDaggerLP = builder
                    .comment("The amount of LP the Glass Sacrificial Dagger (approximately) gives per use. Default: 500")
                    .defineInRange("glassSacrificialDaggerLP", 500, 0, Integer.MAX_VALUE);
            glassSacrificialDaggerDamage = builder
                    .comment("The amount of damage the Glass Sacrificial Dagger deals per use. Default: 2")
                    .defineInRange("glassSacrificialDaggerDamage", 2D, 0, 10000);
            glassDaggerOfSacrificeMultiplier = builder
                    .comment("The multiplier of LP for the Glass Dagger of Sacrifice. Default: 2")
                    .defineInRange("glassDaggerOfSacrifice", 2D, 0, 10000);
            bloodInfusedWoodenToolsRepairUpdate = builder
                    .comment("The amount of ticks for a repair to occur for Blood Infused Wooden Tools. Default: 100")
                    .defineInRange("bloodInfusedWoodenToolsRepairUpdate", 100, 1, Integer.MAX_VALUE);
            bloodInfusedWoodenToolsRepairCost = builder
                    .comment("The amount of LP required for a repair for Blood Infused Wooden Tools. Default: 20")
                    .defineInRange("bloodInfusedWoodenToolsRepairUpdate", 20, 0, Integer.MAX_VALUE);
            bloodInfusedIronToolsRepairUpdate = builder
                    .comment("The amount of ticks for a repair to occur for Blood Infused Iron Tools. Default: 20")
                    .defineInRange("bloodInfusedWoodenToolsRepairUpdate", 20, 1, Integer.MAX_VALUE);
            bloodInfusedIronToolsRepairCost = builder
                    .comment("The amount of LP required for a repair for Blood Infused Iron Tools. Default: 50")
                    .defineInRange("bloodInfusedWoodenToolsRepairUpdate", 50, 0, Integer.MAX_VALUE);
            enderSigilCost = builder
                    .comment("The amount of LP used to use the Ender Sigil. Default: 100")
                    .defineInRange("enderSigilCost", 100, 0, Integer.MAX_VALUE);
            enderSigilTeleportMultiplier = builder
                    .comment("Multiplier LP per block needed to teleport with the Ender Sigil. Default: 100")
                    .defineInRange("enderSigilTeleportMultiplier", 100, 0, Integer.MAX_VALUE);
            enderSigilTeleportCooldown = builder
                    .comment("The cooldown (in ticks) after teleporting with the Ender Sigil. Default: 20")
                    .defineInRange("enderSigilTeleportCooldown", 20, 0, Integer.MAX_VALUE);
            lightningSigilMaxLevel = builder
                    .comment("The maximum level allowed for the Lightning Sigil (good for conscious server owners). Default: 5 (6 levels total)")
                    .defineInRange("lightningSigilMaxLevel", 5, 0, 5);
            lightningSigilCost = builder
                    .comment("The amount of LP required per lightning bolt summoned by the Lightning Sigil. Default: 1000")
                    .defineInRange("lightningSigilCost", 1000, 0, Integer.MAX_VALUE);
            lightningSigilCooldown = builder
                    .comment("The cooldown (in ticks) after using the Lightning Sigil. Default: 20")
                    .defineInRange("lightningSigilCooldown", 20, 0, Integer.MAX_VALUE);
            divinitySigilCost = builder
                    .comment("The amount of LP used every 5 seconds by the Divinity Sigil. Default: 10000")
                    .defineInRange("divinitySigilCost", 10000, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("misc");
            rayTraceRange = builder
                    .comment("Maximum raytracing distance (used by the Ender Sigil and Lightning Sigil) for certain mechanics. Default: 128")
                    .defineInRange("rayTraceRange", 128, 1D, 264);
        }
    }
}
