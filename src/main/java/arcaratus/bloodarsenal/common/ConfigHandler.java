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
        }
    }
}
