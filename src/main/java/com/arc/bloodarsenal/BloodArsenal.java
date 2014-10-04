package com.arc.bloodarsenal;

import com.arc.bloodarsenal.entity.ModLivingDropsEvent;
import com.arc.bloodarsenal.items.ModItems;
import com.arc.bloodarsenal.rituals.RitualRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import com.arc.bloodarsenal.blocks.ModBlocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

@Mod(modid="BloodArsenal", version="1.0.0", name="Blood Arsenal", dependencies="required-after:AWWayofTime")
public class BloodArsenal
{
    public static String MODID = "BloodArsenal";
    public static String VERSION = "1.0.0";
    public static String NAME = "Blood Arsenal";

    @SidedProxy(clientSide = "com.arc.bloodarsenal.ClientProxy", serverSide = "com.arc.bloodarsenal.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance("BloodArsenal")
    public static BloodArsenal instance;

    public static Item.ToolMaterial infusedWood = EnumHelper.addToolMaterial("InfusedWood", 1, 0, 4.0F, 1.0F, 0);
    public static Item.ToolMaterial infusedIron = EnumHelper.addToolMaterial("InfusedIron", 3, 0, 10.0F, 4.0F, 0);
    public static Item.ToolMaterial infusedDiamond = EnumHelper.addToolMaterial("InfusedDiamond", 4, 0, 16.0F, 9.0F, 0);
    public static Item.ToolMaterial infusedNetherium = EnumHelper.addToolMaterial("InfusedNetherium", 5, 0, 21.0F, 11.0F, 0);

    public static CreativeTabs BA_TAB = new CreativeTabs("BA_TAB")
    {
        @Override
        public Item getTabIconItem()
        {
            return ModItems.bound_bow;
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
        ModBlocks.registerBlocksInPre();

        ModItems.init();
        ModItems.registerItems();

        Object dropsEvent = new ModLivingDropsEvent();
        MinecraftForge.EVENT_BUS.register(dropsEvent);
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        BloodArsenalRecipes.registerBindingRecipes();
        BloodArsenalRecipes.registerAltarRecipes();
        BloodArsenalRecipes.registerOrbRecipes();
        BloodArsenalRecipes.registerRecipes();
        RitualRegistry.initRituals();
    }
}
