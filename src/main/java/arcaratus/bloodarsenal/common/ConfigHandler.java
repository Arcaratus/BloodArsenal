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


        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("values");
            glassSacrificialDaggerLP = builder
                    .comment("Amount of LP the Glass Sacrificial Dagger (approximately) gives per use. Default: 500")
                    .defineInRange("glassSacrificialDaggerLP", 500, 0, Integer.MAX_VALUE);
            glassSacrificialDaggerDamage = builder
                    .comment("Amount of damage the Glass Sacrificial Dagger deals per use. Default: 2")
                    .defineInRange("glassSacrificialDaggerDamage", 2D, 0, 10000);
            glassDaggerOfSacrificeMultiplier = builder
                    .comment("The multiplier of LP for the Glass Dagger of Sacrifice. Default: 2")
                    .defineInRange("glassDaggerOfSacrifice", 2D, 0, 10000);
        }
    }
}
