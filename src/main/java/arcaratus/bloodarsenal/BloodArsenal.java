package arcaratus.bloodarsenal;

import WayofTime.bloodmagic.iface.IActivatable;
import arcaratus.bloodarsenal.client.gui.GuiHandler;
import arcaratus.bloodarsenal.compat.CompatibilityPlugin;
import arcaratus.bloodarsenal.compat.ICompatibilityPlugin;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.network.BloodArsenalPacketHandler;
import arcaratus.bloodarsenal.proxy.CommonProxy;
import arcaratus.bloodarsenal.registry.BloodArsenalSounds;
import arcaratus.bloodarsenal.registry.ModModifiers;
import arcaratus.bloodarsenal.registry.ModRecipes;
import com.google.common.collect.Sets;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;
import java.util.Locale;
import java.util.Set;

@Mod(modid = BloodArsenal.MOD_ID, version = BloodArsenal.VERSION, name = BloodArsenal.NAME, dependencies = "required-after:bloodmagic@[1.12.2-2.3.0,);required-after:baubles;after:guideapi;after:tconstruct")
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

    public static final Set<ICompatibilityPlugin> COMPAT_PLUGINS = Sets.newHashSet();

    public final static CreativeTabs TAB_BLOOD_ARSENAL = new CreativeTabs(BloodArsenal.MOD_ID + ".creative_tab")
    {
        @Override
        public ItemStack createIcon()
        {
            ItemStack sword = new ItemStack(RegistrarBloodArsenalItems.STASIS_SWORD);
            IActivatable activatable = (IActivatable) sword.getItem();
            activatable.setActivatedState(sword, true);
            return sword;
        }
    };

    public static File configDir;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        COMPAT_PLUGINS.addAll(CompatibilityPlugin.Gather.gather(event.getAsmData()));
        configDir = new File(event.getModConfigurationDirectory(), "bloodarsenal");

        for (ICompatibilityPlugin plugin : COMPAT_PLUGINS)
            plugin.preInit();

        ModModifiers.init();
        PROXY.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        BloodArsenalPacketHandler.init();

        ModRecipes.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(BloodArsenal.INSTANCE, new GuiHandler());

        for (ICompatibilityPlugin plugin : COMPAT_PLUGINS)
            plugin.init();

        RegistryShenanigans.init();
        BloodArsenalSounds.init();
        PROXY.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        for (ICompatibilityPlugin plugin : COMPAT_PLUGINS)
            plugin.postInit();

        PROXY.postInit();
    }
}
