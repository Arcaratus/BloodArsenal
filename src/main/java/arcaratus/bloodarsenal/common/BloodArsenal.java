package arcaratus.bloodarsenal.common;

import arcaratus.bloodarsenal.client.ClientProxy;
import arcaratus.bloodarsenal.common.block.ModBlocks;
import arcaratus.bloodarsenal.common.core.IProxy;
import arcaratus.bloodarsenal.common.item.ModItems;
import arcaratus.bloodarsenal.data.DataGenerators;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
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

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(DataGenerators::gatherData);
//        modBus.addGenericListener(EntityType.class, ModEntities::registerEntities);
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
//        modBus.addGenericListener(Block.class, ModBlocks::registerBlocks);
//        modBus.addGenericListener(Item.class, ModBlocks::registerItemBlocks);
//        modBus.addGenericListener(TileEntityType.class, ModTiles::registerTiles);
    }
}
