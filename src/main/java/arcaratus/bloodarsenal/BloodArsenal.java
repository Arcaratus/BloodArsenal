package arcaratus.bloodarsenal;

import arcaratus.bloodarsenal.client.gui.GuiHandler;
import arcaratus.bloodarsenal.compat.ICompatibility;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.network.BloodArsenalPacketHandler;
import arcaratus.bloodarsenal.proxy.CommonProxy;
import arcaratus.bloodarsenal.registry.*;
import arcaratus.bloodarsenal.util.DamageSourceBleeding;
import arcaratus.bloodarsenal.util.DamageSourceGlass;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;
import java.util.Locale;

@Mod(modid = BloodArsenal.MOD_ID, version = BloodArsenal.VERSION, name = BloodArsenal.NAME, dependencies = "required-after:bloodmagic;required-after:baubles;after:guideapi;after:tconstruct")
public class BloodArsenal
{
    public static final String MOD_ID = "bloodarsenal";
    public static final String NAME = "Blood Arsenal";
    public static final String VERSION = "@VERSION@";
    public static final String DOMAIN = MOD_ID.toLowerCase(Locale.ENGLISH) + ":";

    public static final boolean IS_DEV = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    @SidedProxy(serverSide = "arcaratus.bloodarsenal.proxy.CommonProxy", clientSide = "arcaratus.bloodarsenal.proxy.ClientProxy")
    public static CommonProxy PROXY;

    @Mod.Instance(BloodArsenal.MOD_ID)
    public static BloodArsenal INSTANCE;

    public final static CreativeTabs TAB_BLOOD_ARSENAL = new CreativeTabs(BloodArsenal.MOD_ID + ".creativeTab")
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(RegistrarBloodArsenalItems.BLOOD_ORANGE);
        }
    };

    public static File configDir;

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
        configDir = new File(event.getModConfigurationDirectory(), "bloodarsenal");

        ModCompat.registerModCompat();
        ModCompat.loadCompat(ICompatibility.InitializationPhase.PRE_INIT);

        PROXY.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        BloodArsenalPacketHandler.init();

        ModRituals.initImperfectRituals();
        ModModifiers.init();
        ModRecipes.init();
        ModRituals.overrideRituals();
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
//        ModCompat.loadCompat(ICompatibility.InitializationPhase.MAPPING);
    }

//    @Mod.EventHandler
//    public void serverStarting(FMLServerStartingEvent event)
//    {
//        event.registerServerCommand(new CommandBloodArsenal());
//    }
}
