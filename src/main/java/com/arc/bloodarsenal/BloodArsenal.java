package com.arc.bloodarsenal;

import com.arc.bloodarsenal.entity.ModEntities;
import com.arc.bloodarsenal.entity.ModLivingDropsEvent;
import com.arc.bloodarsenal.gui.GuiHandler;
import com.arc.bloodarsenal.items.ModItems;
import com.arc.bloodarsenal.items.sigil.SigilUtils;
import com.arc.bloodarsenal.items.sigil.holding.AHPacketHandler;
import com.arc.bloodarsenal.items.tinkers.BloodArsenalModifiers;
import com.arc.bloodarsenal.items.tinkers.BloodArsenalTinkers;
import com.arc.bloodarsenal.items.tinkers.RecipeHelper;
import com.arc.bloodarsenal.misc.CommandDownloadMod;
import com.arc.bloodarsenal.potion.PotionBloodArsenal;
import com.arc.bloodarsenal.rituals.RitualRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import com.arc.bloodarsenal.block.ModBlocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Calendar;

@Mod(modid = BloodArsenal.MODID, version = BloodArsenal.VERSION, name = "Blood Arsenal", dependencies = "required-after:AWWayofTime;after:NotEnoughItems;after:Baubles;after:TConstruct", guiFactory = "com.arc.bloodarsenal.gui.ConfigGuiFactory")
public class BloodArsenal
{
    public final static String MODID = "BloodArsenal";
    public final static String VERSION = "1.1-7";

    @SidedProxy(clientSide = "com.arc.bloodarsenal.ClientProxy", serverSide = "com.arc.bloodarsenal.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance("BloodArsenal")
    public static BloodArsenal instance;

    public static Potion vampiricAura;
    public static Potion bleeding;
    public static Potion swimming;

    public static Item.ToolMaterial infusedWood = EnumHelper.addToolMaterial("InfusedWood", 1, 0, 5.0F, 1.0F, 0);
    public static Item.ToolMaterial infusedIron = EnumHelper.addToolMaterial("InfusedIron", 4, 0, 11.0F, 4.0F, 0);
    public static Item.ToolMaterial infusedDiamond = EnumHelper.addToolMaterial("InfusedDiamond", 6, 0, 17.0F, 9.0F, 0);
    public static Item.ToolMaterial infusedNetherium = EnumHelper.addToolMaterial("InfusedNetherium", 9, 0, 31.0F, 18.0F, 0);

    public static ItemArmor.ArmorMaterial vampireArmor = EnumHelper.addArmorMaterial("VampireArmor", 0, new int[]{2, 7, 4, 2}, 0);
    public static ItemArmor.ArmorMaterial lifeImbuedArmor = EnumHelper.addArmorMaterial("ImbuedArmor", 0, new int[]{4, 10, 8, 4}, 7);

	public static Logger logger = LogManager.getLogger(MODID);
    public static CreativeTabs BA_TAB = new CreativeTabs("BA_TAB")
    {
        @Override
        public Item getTabIconItem()
        {
            return ModItems.bound_bow;
        }
    };

    public static final AHPacketHandler packetPipeline = new AHPacketHandler();
    public static DamageSource deathFromBlood = (new DamageSource("deathFromBlood")).setDamageBypassesArmor();

    public static boolean isHalloween()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) == Calendar.OCTOBER;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
	    BloodArsenalConfig.init(new File(event.getModConfigurationDirectory(), "BloodArsenal.cfg"));

	    ModBlocks.init();
        ModBlocks.registerTileEntities();

	    ModItems.init();

        ModEntities.init();

	    Potion[] potionTypes;

	    for (Field f : Potion.class.getDeclaredFields())
        {
	        f.setAccessible(true);

	        try
            {
	            if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a"))
                {
	                Field modfield = Field.class.getDeclaredField("modifiers");
	                modfield.setAccessible(true);
	                modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
	                potionTypes = (Potion[]) f.get(null);
	                final Potion[] newPotionTypes = new Potion[256];
	                System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
	                f.set(null, newPotionTypes);
                }
            }
            catch (Exception e)
            {
	            logger.error("ポケモン！");
	            logger.error(e);
            }
        }

        Object dropsEvent = new ModLivingDropsEvent();
        MinecraftForge.EVENT_BUS.register(dropsEvent);

        Object eventHook = new BloodArsenalEventHooks();
        FMLCommonHandler.instance().bus().register(eventHook);
        MinecraftForge.EVENT_BUS.register(eventHook);

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        AHPacketHandler.init();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        BloodArsenalRecipes.registerBindingRecipes();
        BloodArsenalRecipes.registerAltarRecipes();
        BloodArsenalRecipes.registerRecipes();
        RitualRegistry.initRituals();

        proxy.init();

        vampiricAura = new PotionBloodArsenal(BloodArsenalConfig.vampiricAuraID, false, 0).setIconIndex(0, 0).setPotionName("Vampiric Aura");
        bleeding = new PotionBloodArsenal(BloodArsenalConfig.bleedingID, true, 0).setIconIndex(1, 0).setPotionName("Bleeding");
        swimming = new PotionBloodArsenal(BloodArsenalConfig.swimmingID, false, 0).setIconIndex(2, 0).setPotionName("Swimming");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (Loader.isModLoaded("Baubles"))
        {
            if (BloodArsenalConfig.baublesIntegration)
            {
                logger.info("Loaded Baubles integration");
                ModItems.registerBaubles();
                BloodArsenalRecipes.addBaublesRecipe();
            }
        }

        if (Loader.isModLoaded("TConstruct"))
        {
            if (BloodArsenalConfig.tinkersIntegration)
            {
                logger.info("Loaded Tinker's Construct integration");

                BloodArsenalTinkers.init();
                BloodArsenalModifiers.init();
                RecipeHelper.addRecipes();
            }
        }
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandDownloadMod());
    }
}
