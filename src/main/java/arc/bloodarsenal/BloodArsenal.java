package arc.bloodarsenal;

import WayofTime.bloodmagic.api.util.helper.LogHelper;
import arc.bloodarsenal.proxy.CommonProxy;
import arc.bloodarsenal.registry.ModBlocks;
import arc.bloodarsenal.registry.ModItems;
import arc.bloodarsenal.registry.ModPotions;
import arc.bloodarsenal.registry.ModRecipes;
import arc.bloodarsenal.util.DamageSourceBleeding;
import arc.bloodarsenal.util.DamageSourceGlass;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.Locale;

@Mod(modid = BloodArsenal.MOD_ID, version = BloodArsenal.VERSION, name = "Blood Arsenal", dependencies = "required-after:BloodMagic;after:Waila", guiFactory = "arc.bloodarsenal.client.gui.config.ConfigGuiFactory", acceptedMinecraftVersions = "[1.9]")
public class BloodArsenal
{
    public final static String MOD_ID = "BloodArsenal";
    public final static String VERSION = "@VERSION@";
    public final static String DOMAIN = MOD_ID.toLowerCase(Locale.ENGLISH) + ":";

    @SidedProxy(serverSide = "arc.bloodarsenal.proxy.CommonProxy", clientSide = "arc.bloodarsenal.proxy.ClientProxy")
    public static CommonProxy PROXY;

    @Mod.Instance(BloodArsenal.MOD_ID)
    public static BloodArsenal INSTANCE;

    public static CreativeTabs tabBloodArsenal = new CreativeTabs(BloodArsenal.MOD_ID + ".creativeTab")
    {
        @Override
        public Item getTabIconItem()
        {
            return Items.baked_potato;
        }
    };

    private LogHelper logger = new LogHelper(BloodArsenal.MOD_ID);
    private File configDir;

    private static boolean isDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public LogHelper getLogger()
    {
        return this.logger;
    }
    public File getConfigDir()
    {
        return this.configDir;
    }
    public static boolean isDev()
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

        ModItems.init();
        ModBlocks.init();
        ModPotions.init();

        PROXY.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        ModRecipes.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
