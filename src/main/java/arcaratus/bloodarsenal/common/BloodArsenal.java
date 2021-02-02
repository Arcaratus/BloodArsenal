package arcaratus.bloodarsenal.common;

import arcaratus.bloodarsenal.client.ClientProxy;
import arcaratus.bloodarsenal.common.block.ModBlocks;
import arcaratus.bloodarsenal.common.core.IProxy;
import arcaratus.bloodarsenal.common.item.ModItems;
import arcaratus.bloodarsenal.common.potion.ModEffects;
import arcaratus.bloodarsenal.data.DataGenerators;
import arcaratus.bloodarsenal.common.integration.BloodMagicIntegration;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BloodArsenal.MOD_ID)
public class BloodArsenal
{
    public static final String MOD_ID = "bloodarsenal";

    public static final Logger LOGGER = LogManager.getLogger();

    public static IProxy proxy = new IProxy() {};

    public BloodArsenal()
    {
        DistExecutor.callWhenOn(Dist.CLIENT, () -> () -> proxy = new ClientProxy());
        proxy.registerHandlers();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(DataGenerators::gatherData);
//        modBus.addGenericListener(EntityType.class, ModEntities::registerEntities);
        ModEffects.EFFECTS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
//        modBus.addGenericListener(Block.class, ModBlocks::registerBlocks);
//        modBus.addGenericListener(Item.class, ModBlocks::registerItemBlocks);
//        modBus.addGenericListener(TileEntityType.class, ModTiles::registerTiles);
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        BloodMagicIntegration.integrate();
    }

    public static ResourceLocation rl(String path)
    {
        return new ResourceLocation(MOD_ID, path);
    }
}
