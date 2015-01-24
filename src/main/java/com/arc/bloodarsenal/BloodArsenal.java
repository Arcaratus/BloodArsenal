package com.arc.bloodarsenal;

import com.arc.bloodarsenal.entity.ModLivingDropsEvent;
import com.arc.bloodarsenal.gui.GuiHandler;
import com.arc.bloodarsenal.items.ModItems;
import com.arc.bloodarsenal.items.bauble.SacrificeAmulet;
import com.arc.bloodarsenal.items.bauble.SelfSacrificeAmulet;
import com.arc.bloodarsenal.items.bauble.VampireRing;
import com.arc.bloodarsenal.items.tinkers.BloodArsenalTinkers;
import com.arc.bloodarsenal.items.tinkers.RecipeHelper;
import com.arc.bloodarsenal.potion.PotionVampiricAura;
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
import cpw.mods.fml.common.registry.GameRegistry;
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

@Mod(modid="BloodArsenal", version="1.1.3", name="Blood Arsenal", dependencies="required-after:AWWayofTime;after:NotEnoughItems;after:Baubles;after:TConstruct", guiFactory = "com.arc.bloodarsenal.gui.ConfigGuiFactory")
public class BloodArsenal
{
    public static String MODID = "BloodArsenal";

    @SidedProxy(clientSide = "com.arc.bloodarsenal.ClientProxy", serverSide = "com.arc.bloodarsenal.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance("BloodArsenal")
    public static BloodArsenal instance;

    public static Potion vampiricAura;
    public static Potion bleeding;
    public static Potion swimming;

    public static Item.ToolMaterial infusedWood = EnumHelper.addToolMaterial("InfusedWood", 1, 0, 4.0F, 1.0F, 0);
    public static Item.ToolMaterial infusedIron = EnumHelper.addToolMaterial("InfusedIron", 4, 0, 10.0F, 4.0F, 0);
    public static Item.ToolMaterial infusedDiamond = EnumHelper.addToolMaterial("InfusedDiamond", 6, 0, 16.0F, 9.0F, 0);
    public static Item.ToolMaterial infusedNetherium = EnumHelper.addToolMaterial("InfusedNetherium", 9, 0, 31.0F, 18.0F, 0);

    public static ItemArmor.ArmorMaterial vampireArmor = EnumHelper.addArmorMaterial("VampireArmor", 0, new int[]{3, 8, 6, 3}, 0);

    public static boolean isBaublesLoaded;
    public static boolean isTinkersConstructLoaded;

	public static Logger logger = LogManager.getLogger(MODID);
    public static CreativeTabs BA_TAB = new CreativeTabs("BA_TAB")
    {
        @Override
        public Item getTabIconItem()
        {
            return ModItems.bound_bow;
        }
    };

    public static DamageSource deathFromBlood = (new DamageSource("deathFromBlood")).setDamageBypassesArmor();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
	    BloodArsenalConfig.init(new File(event.getModConfigurationDirectory(), "BloodArsenal.cfg"));

	    ModBlocks.init();
	    ModBlocks.registerBlocksInPre();
        ModBlocks.registerTileEntities();

	    ModItems.init();
	    ModItems.registerItems();

	    Potion[] potionTypes = null;

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
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        BloodArsenalRecipes.registerBindingRecipes();
        BloodArsenalRecipes.registerAltarRecipes();
        BloodArsenalRecipes.registerRecipes();
        RitualRegistry.initRituals();

        proxy.registerRenders();
        proxy.registerEvents();

        vampiricAura = new PotionVampiricAura(BloodArsenalConfig.vampiricAuraID, false, 0).setIconIndex(0, 0).setPotionName("Vampiric Aura");
        bleeding = new PotionVampiricAura(BloodArsenalConfig.bleedingID, true, 0).setIconIndex(1, 0).setPotionName("Bleeding");
        swimming = new PotionVampiricAura(BloodArsenalConfig.swimmingID, false, 0).setIconIndex(2, 0).setPotionName("Swimming");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (Loader.isModLoaded("Baubles"))
        {
            isBaublesLoaded = true;
            logger.info("Loaded Baubles integration");

            ModItems.vampire_ring = new VampireRing();
            ModItems.self_sacrifice_amulet = new SelfSacrificeAmulet();
            ModItems.sacrifice_amulet = new SacrificeAmulet();

            GameRegistry.registerItem(ModItems.vampire_ring, "vampire_ring");
            GameRegistry.registerItem(ModItems.self_sacrifice_amulet, "self_sacrifice_amulet");
            GameRegistry.registerItem(ModItems.sacrifice_amulet, "sacrifice_amulet");
        }
        else
        {
            isBaublesLoaded = false;
        }

        if (Loader.isModLoaded("TConstruct"))
        {
            isTinkersConstructLoaded = true;
            logger.info("Loaded Tinker's Construct integration　ほし！");

            BloodArsenalTinkers.addMaterials();
            BloodArsenalTinkers.addParts();
            RecipeHelper.addRecipes();
        }
        else
        {
            isTinkersConstructLoaded = false;
        }
    }
}
