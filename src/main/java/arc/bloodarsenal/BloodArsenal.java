package arc.bloodarsenal;

import WayofTime.bloodmagic.api.util.helper.LogHelper;
import arc.bloodarsenal.client.gui.GuiHandler;
import arc.bloodarsenal.command.CommandBloodArsenal;
import arc.bloodarsenal.compat.ICompatibility;
import arc.bloodarsenal.network.BloodArsenalPacketHandler;
import arc.bloodarsenal.proxy.CommonProxy;
import arc.bloodarsenal.registry.*;
import arc.bloodarsenal.util.DamageSourceBleeding;
import arc.bloodarsenal.util.DamageSourceGlass;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;
import java.util.Locale;

@Mod(modid = BloodArsenal.MOD_ID, version = BloodArsenal.VERSION, name = "Blood Arsenal", dependencies = "required-after:bloodmagic;after:baubles;after:guideapi", guiFactory = "arc.bloodarsenal.client.gui.config.ConfigGuiFactory")
public class BloodArsenal
{
    public final static String MOD_ID = "bloodarsenal";
    public final static String VERSION = "@VERSION@";
    public final static String DOMAIN = MOD_ID.toLowerCase(Locale.ENGLISH) + ":";

    @SidedProxy(serverSide = "arc.bloodarsenal.proxy.CommonProxy", clientSide = "arc.bloodarsenal.proxy.ClientProxy")
    public static CommonProxy PROXY;

    @Mod.Instance(BloodArsenal.MOD_ID)
    public static BloodArsenal INSTANCE;

    public final static CreativeTabs TAB_BLOOD_ARSENAL = new CreativeTabs(BloodArsenal.MOD_ID + ".creativeTab")
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.BLOOD_ORANGE);
        }
    };

    private LogHelper logger = new LogHelper(BloodArsenal.MOD_ID);
    private File configDir;
    private static boolean isDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public LogHelper getLogger()
    {
        return logger;
    }
    public File getConfigDir()
    {
        return configDir;
    }
    public boolean isDev()
    {
        return isDev;
    }

    public static DamageSourceGlass getDamageSourceGlass()
    {
        return new DamageSourceGlass();
    }
    public static DamageSourceBleeding getDamageSourceBleeding()
    {
        return new DamageSourceBleeding();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        configDir = new File(event.getModConfigurationDirectory(), "BloodArsenal");
        ConfigHandler.init(new File(getConfigDir(), "BloodArsenal.cfg"));

        ModBlocks.initTiles();
        ModBlocks.addOreDictBlocks();
        ModItems.addOreDictItems();
        ModPotions.init();
        ModEntities.init();
        ModCompat.registerModCompat();
        ModCompat.loadCompat(ICompatibility.InitializationPhase.PRE_INIT);

        PROXY.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        BloodArsenalPacketHandler.init();

        ModRecipes.init();
        ModRituals.overrideRituals();
        ModModifiers.init();
        ModCompat.loadCompat(ICompatibility.InitializationPhase.INIT);
        NetworkRegistry.INSTANCE.registerGuiHandler(BloodArsenal.INSTANCE, new GuiHandler());

        PROXY.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        ModCompat.loadCompat(ICompatibility.InitializationPhase.POST_INIT);

        PROXY.postInit();
    }

    @Mod.EventHandler
    public void modMapping(FMLModIdMappingEvent event)
    {
        ModCompat.loadCompat(ICompatibility.InitializationPhase.MAPPING);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandBloodArsenal());
    }
}
