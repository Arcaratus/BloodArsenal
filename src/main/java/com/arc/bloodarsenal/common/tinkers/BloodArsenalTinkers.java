package com.arc.bloodarsenal.common.tinkers;

import com.arc.bloodarsenal.common.BloodArsenal;
import com.arc.bloodarsenal.common.block.ModBlocks;
import com.arc.bloodarsenal.common.items.ModItems;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.*;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.weaponry.TinkerWeaponry;

import java.util.ListIterator;

public class BloodArsenalTinkers
{
    public static PartHelper ARROWHEAD;
    public static PartHelper AXE_HEAD;
    public static PartHelper BATTLE_SIGN_HEAD;
    public static PartHelper BINDING;
    public static PartHelper BOLT;
    public static PartHelper BOW_LIMB;
    public static PartHelper CHISEL_HEAD;
    public static PartHelper CHUNK;
    public static PartHelper CROSSBAR;
    public static PartHelper CROSSBOW_BODY;
    public static PartHelper CROSSBOW_LIMB;
    public static PartHelper EXCAVATOR_HEAD;
    public static PartHelper FISHING_ROD;
    public static PartHelper FRYPAN_HEAD;
    public static PartHelper FULL_GUARD;
    public static PartHelper HAMMER_HEAD;
    public static PartHelper KNIFE_BLADE;
    public static PartHelper LARGE_GUARD;
    public static PartHelper LARGE_SWORD_BLADE;
    public static PartHelper LARGEPLATE;
    public static PartHelper LUMBERAXE_HEAD;
    public static PartHelper MEDIUM_GUARD;
    public static PartHelper PICKAXE_HEAD;
    public static PartHelper SCYTHE_HEAD;
    public static PartHelper SHOVEL_HEAD;
    public static PartHelper SHURIKEN;
    public static PartHelper SWORD_BLADE;
    public static PartHelper TOOLROD;
    public static PartHelper TOUGHBIND;
    public static PartHelper TOUGHROD;

    public static Fluid bloodInfusedIron = new Fluid("molten.bloodInfusedIron").setDensity(3000).setViscosity(6000).setTemperature(1300);

    public static void init()
    {
//        addMaterials();

        addParts();
        addLifeInfusedIronMaterial();
        addLiquids();

        MinecraftForge.EVENT_BUS.register(new TConEvents());
    }

    public static void addLifeInfusedIronMaterial()
    {
        if (ModItems.itemsNotToBeRegistered.contains(ModItems.blood_infused_iron) && ModBlocks.blocksNotToBeRegistered.contains(ModBlocks.blood_infused_iron_block))
        {
            return;
        }

        final int id = 250;



        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("Id", id);

        final ToolMaterial material = new ToolMaterial("blood_infused_iron", "material.blood_infused_iron", 550, 1800, 3, 3, 1.5F, 0, 0.0F, EnumChatFormatting.DARK_RED.toString(), 16777215);

/*        tag.setString("Name", "Blood Infused Iron");
        tag.setString("localizationString", "material.blood_infused_iron");
        tag.setInteger("Durability", 550);
        tag.setInteger("MiningSpeed", 1800);
        tag.setInteger("HarvestLevel", 3);
        tag.setInteger("Attack", 3);
        tag.setFloat("HandleModifier", 1.5F);
        tag.setInteger("Reinforced", 0);
        tag.setFloat("Bow_ProjectileSpeed", 3.0F);
        tag.setInteger("Bow_DrawSpeed", 40);
        tag.setFloat("Projectile_Mass", 4.7F);
        tag.setFloat("Projectile_Fragility", 0.2F);
        tag.setString("Style", EnumChatFormatting.BLACK.toString());
        tag.setInteger("Color", 16777215);
        FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

        FluidRegistry.registerFluid(bloodInfusedIron);

        FluidType.registerFluidType(bloodInfusedIron.getName(), ModBlocks.blood_infused_iron_block, 0, 850, bloodInfusedIron, true);
        Smeltery.addMelting(new ItemStack(ModBlocks.blood_infused_iron_block, 1), ModBlocks.blood_infused_iron_block, 0, 850, new FluidStack(bloodInfusedIron, 1296));
        Smeltery.addMelting(new ItemStack(ModItems.blood_infused_iron, 1, 0), ModBlocks.blood_infused_iron_block, 0, 850, new FluidStack(bloodInfusedIron, 144));

        ItemStack ingotcast = new ItemStack(TinkerSmeltery.metalPattern, 1, 0);
        TConstructRegistry.getBasinCasting().addCastingRecipe(new ItemStack(ModBlocks.blood_infused_iron_block, 1), new FluidStack(bloodInfusedIron, 1296), null, true, 100);
        TConstructRegistry.getTableCasting().addCastingRecipe(new ItemStack(ModItems.blood_infused_iron, 1), new FluidStack(bloodInfusedIron, 144), ingotcast, false, 50);

        tag = new NBTTagCompound();
        tag.setString("FluidName", bloodInfusedIron.getName());
        tag.setInteger("MaterialId", id);
        FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);

        tag = new NBTTagCompound();
        tag.setInteger("MaterialId", id);
        tag.setTag("Item", new ItemStack(ModItems.blood_infused_iron, 1, 0).writeToNBT(new NBTTagCompound()));
        tag.setInteger("Value", 2);
        FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);
 */
        TConstructRegistry.addtoolMaterial(id, material);
        TConstructRegistry.addDefaultToolPartMaterial(id);
        TConstructRegistry.addBowMaterial(id, 40, 1.2F);
        TConstructRegistry.addArrowMaterial(id, 4.7F, 0.2F);

        BloodArsenal.proxy.executeClientCode(new IClientCode()
        {
            @SideOnly(Side.CLIENT)
            public void executeClientCode()
            {
                if (FMLCommonHandler.instance().getSide().isClient()) {
                    TConstructClientRegistry.addMaterialRenderMapping(id, "tinker", material.name().toLowerCase(), true);
                }
            }
        });

        FluidRegistry.registerFluid(bloodInfusedIron);

        FluidType.registerFluidType(bloodInfusedIron.getName(), ModBlocks.blood_infused_iron_block, 0, 850, bloodInfusedIron, true);
        Smeltery.addMelting(new ItemStack(ModBlocks.blood_infused_iron_block, 1), ModBlocks.blood_infused_iron_block, 0, 850, new FluidStack(bloodInfusedIron, 1296));
        Smeltery.addMelting(new ItemStack(ModItems.blood_infused_iron, 1, 0), ModBlocks.blood_infused_iron_block, 0, 850, new FluidStack(bloodInfusedIron, 144));

        ItemStack ingotcast = new ItemStack(TinkerSmeltery.metalPattern, 1, 0);
        TConstructRegistry.getBasinCasting().addCastingRecipe(new ItemStack(ModBlocks.blood_infused_iron_block, 1), new FluidStack(bloodInfusedIron, 1296), null, true, 100);
        TConstructRegistry.getTableCasting().addCastingRecipe(new ItemStack(ModItems.blood_infused_iron, 1), new FluidStack(bloodInfusedIron, 144), ingotcast, false, 50);

        tag = new NBTTagCompound();
        tag.setString("FluidName", bloodInfusedIron.getName());
        tag.setInteger("MaterialId", id);
        FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);

        tag = new NBTTagCompound();
        tag.setInteger("MaterialId", id);
        tag.setTag("Item", new ItemStack(ModItems.blood_infused_iron, 1, 0).writeToNBT(new NBTTagCompound()));
        tag.setInteger("Value", 2);
        FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

        BloodArsenal.proxy.executeClientCode(new IClientCode()
        {
            @SideOnly(Side.CLIENT)
            public void executeClientCode()
            {
                new TextureResourcePackBloodInfusedIron("blood_infused_iron").register();
            }
        });
    }

    public static void addLiquids()
    {
        LiquidCasting tb = TConstructRegistry.getTableCasting();
        for (ListIterator<CastingRecipe> iterator = tb.getCastingRecipes().listIterator(); iterator.hasNext();)
        {
            CastingRecipe castingRecipe = iterator.next();

            if ((castingRecipe != null) && (castingRecipe.getClass() == CastingRecipe.class) && (castingRecipe.output != null) && (castingRecipe.output.getItem() == TinkerWeaponry.partBolt) && (castingRecipe.cast != null) && (castingRecipe.cast.getItem() == TinkerTools.toolRod))
            {
                int materialID = ToolBuilder.instance.getMaterialID(castingRecipe.cast);

                if (materialID > 0)
                {
                    if (materialID == 250)
                    {
                        iterator.set(new TConCastingRecipe(castingRecipe));
                    }
                }
            }
        }
    }

    public static void addMaterials()
    {
        TConstructRegistry.addToolMaterial(250, "Blood Infused Iron", "blood_infused_iron", 3, 550, 1800, 3, 1.5F, 0, 0.0F, "Blood Infused Iron", 0xab0000);

        TConstructClientRegistry.addMaterialRenderMapping(250, "BloodArsenal", "Blood Infused Iron", true);

        TConstructRegistry.addBowMaterial(250, 40, 1.2F);

        TConstructRegistry.addArrowMaterial(250, 4.7F, 0.2F);
    }

    public static void addParts()
    {
        initParts();

//        addMaterialToToolParts(250, "blood.infused.iron", "blood_infused_iron");

        registerParts();
    }

    private static void initParts()
    {
        ARROWHEAD = new PartHelper("arrowhead", 0, "");
        AXE_HEAD = new PartHelper("axe.head", 0, "");
        BATTLE_SIGN_HEAD = new PartHelper("battle.sign", 0, "");
        BINDING = new PartHelper("binding", 0, "");
        BOLT = new PartHelper("bolt", 0, "");
        BOW_LIMB = new PartHelper("bow.limb", 0, "");
        CHISEL_HEAD = new PartHelper("chisel.head", 0, "");
        CHUNK = new PartHelper("chunk", 0, "");
        CROSSBAR = new PartHelper("crossbar", 0, "");
        CROSSBOW_BODY = new PartHelper("crossbow.body", 0, "");
        CROSSBOW_LIMB = new PartHelper("crossbow.limb", 0, "");
        EXCAVATOR_HEAD = new PartHelper("excavator.head", 0, "");
        FISHING_ROD = new PartHelper("fishing.rod", 0, "");
        FRYPAN_HEAD = new PartHelper("frypan.head", 0, "");
        FULL_GUARD = new PartHelper("full.guard", 0, "");
        HAMMER_HEAD = new PartHelper("hammer.head", 0, "");
        KNIFE_BLADE = new PartHelper("knife.blade", 0, "");
        LARGE_GUARD = new PartHelper("large.guard", 0, "");
        LARGE_SWORD_BLADE = new PartHelper("large.sword.blade", 0, "");
        LARGEPLATE = new PartHelper("largeplate", 0, "");
        LUMBERAXE_HEAD = new PartHelper("lumberaxe.head", 0, "");
        MEDIUM_GUARD = new PartHelper("medium.guard", 0, "");
        PICKAXE_HEAD = new PartHelper("pickaxe.head", 0, "");
        SCYTHE_HEAD = new PartHelper("scythe.head", 0, "");
        SHOVEL_HEAD = new PartHelper("shovel.head", 0, "");
        SHURIKEN = new PartHelper("shuriken", 0, "");
        SWORD_BLADE = new PartHelper("sword.blade", 0, "");
        TOOLROD = new PartHelper("toolrod", 0, "");
        TOUGHBIND = new PartHelper("toughbind", 0, "");
        TOUGHROD = new PartHelper("toughrod", 0, "");
    }

    private static void addMaterialToToolParts(int materialID, String unlocalizedName, String texture)
    {
        ARROWHEAD.addMaterial(materialID, unlocalizedName, texture + "_arrowhead");
        AXE_HEAD.addMaterial(materialID, unlocalizedName, texture + "_axe_head");
        BATTLE_SIGN_HEAD.addMaterial(materialID, unlocalizedName, texture + "_battlesign_head");
        BINDING.addMaterial(materialID, unlocalizedName, texture + "_binding");
        BOLT.addMaterial(materialID, unlocalizedName, texture + "_bolt");
        BOW_LIMB.addMaterial(materialID, unlocalizedName, texture + "_bow_limb");
        CHISEL_HEAD.addMaterial(materialID, unlocalizedName, texture + "_chisel_head");
        CHUNK.addMaterial(materialID, unlocalizedName, texture + "_chunk");
        CROSSBAR.addMaterial(materialID, unlocalizedName, texture + "_crossbar");
        CROSSBOW_BODY.addMaterial(materialID, unlocalizedName, texture + "_crossbow_body");
        CROSSBOW_LIMB.addMaterial(materialID, unlocalizedName, texture + "_crossbow_limb");
        EXCAVATOR_HEAD.addMaterial(materialID, unlocalizedName, texture + "_excavator_head");
        FISHING_ROD.addMaterial(materialID, unlocalizedName, texture + "_fishingrod");
        FRYPAN_HEAD.addMaterial(materialID, unlocalizedName, texture + "_frypan_head");
        FULL_GUARD.addMaterial(materialID, unlocalizedName, texture + "_full_guard");
        HAMMER_HEAD.addMaterial(materialID, unlocalizedName, texture + "_hammer_head");
        KNIFE_BLADE.addMaterial(materialID, unlocalizedName, texture + "_knife_blade");
        LARGE_GUARD.addMaterial(materialID, unlocalizedName, texture + "_large_guard");
        LARGE_SWORD_BLADE.addMaterial(materialID, unlocalizedName, texture + "_large_sword_blade");
        LARGEPLATE.addMaterial(materialID, unlocalizedName, texture + "_largeplate");
        LUMBERAXE_HEAD.addMaterial(materialID, unlocalizedName, texture + "_lumberaxe_head");
        MEDIUM_GUARD.addMaterial(materialID, unlocalizedName, texture + "_medium_guard");
        PICKAXE_HEAD.addMaterial(materialID, unlocalizedName, texture + "_pickaxe_head");
        SCYTHE_HEAD.addMaterial(materialID, unlocalizedName, texture + "_scythe_head");
        SHOVEL_HEAD.addMaterial(materialID, unlocalizedName, texture + "_shovel_head");
        SHURIKEN.addMaterial(materialID, unlocalizedName, texture + "_shuriken");
        SWORD_BLADE.addMaterial(materialID, unlocalizedName, texture + "_sword_blade");
        TOOLROD.addMaterial(materialID, unlocalizedName, texture + "_rod");
        TOUGHBIND.addMaterial(materialID, unlocalizedName, texture + "_toughbind");
        TOUGHROD.addMaterial(materialID, unlocalizedName, texture + "_toughrod");
    }

    private static void registerParts()
    {
        GameRegistry.registerItem(ARROWHEAD, "arrowhead");
        GameRegistry.registerItem(AXE_HEAD, "axeHead");
        GameRegistry.registerItem(BATTLE_SIGN_HEAD, "battleSign");
        GameRegistry.registerItem(BINDING, "binding");
        GameRegistry.registerItem(BOLT, "bolt");
        GameRegistry.registerItem(BOW_LIMB, "bowLimb");
        GameRegistry.registerItem(CHISEL_HEAD, "chiselHead");
        GameRegistry.registerItem(CHUNK, "chunk");
        GameRegistry.registerItem(CROSSBAR, "crossbar");
        GameRegistry.registerItem(CROSSBOW_BODY, "crossbowBody");
        GameRegistry.registerItem(CROSSBOW_LIMB, "crossbowLimb");
        GameRegistry.registerItem(EXCAVATOR_HEAD, "excavatorHead");
//        GameRegistry.registerItem(FISHING_ROD, "fishingRod");
        GameRegistry.registerItem(FRYPAN_HEAD, "frypanHead");
        GameRegistry.registerItem(FULL_GUARD, "fullGuard");
        GameRegistry.registerItem(HAMMER_HEAD, "hammerHead");
        GameRegistry.registerItem(KNIFE_BLADE, "knifeBlade");
        GameRegistry.registerItem(LARGE_GUARD, "largeGuard");
        GameRegistry.registerItem(LARGE_SWORD_BLADE, "largeSwordBlade");
        GameRegistry.registerItem(LARGEPLATE, "largeplate");
        GameRegistry.registerItem(LUMBERAXE_HEAD, "lumberaxeHead");
        GameRegistry.registerItem(MEDIUM_GUARD, "mediumGuard");
        GameRegistry.registerItem(PICKAXE_HEAD, "pickaxeHead");
        GameRegistry.registerItem(SCYTHE_HEAD, "scytheHead");
        GameRegistry.registerItem(SHOVEL_HEAD, "shovelHead");
        GameRegistry.registerItem(SHURIKEN, "shuriken");
        GameRegistry.registerItem(SWORD_BLADE, "swordBlade");
        GameRegistry.registerItem(TOOLROD, "toolrod");
        GameRegistry.registerItem(TOUGHBIND, "toughbind");
        GameRegistry.registerItem(TOUGHROD, "toughrod");
        BloodArsenal.logger.info("SOMETHING WORKED NOW DIDN'T IT?");
    }
}
