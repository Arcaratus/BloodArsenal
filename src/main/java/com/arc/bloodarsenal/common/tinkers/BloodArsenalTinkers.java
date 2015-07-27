package com.arc.bloodarsenal.common.tinkers;

import com.arc.bloodarsenal.common.BloodArsenal;
import com.arc.bloodarsenal.common.BloodArsenalConfig;
import com.arc.bloodarsenal.common.block.ModBlocks;
import com.arc.bloodarsenal.common.items.ModItems;
import cpw.mods.fml.common.event.FMLInterModComms;
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
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.TinkerSmeltery;

public class BloodArsenalTinkers
{
    public final static BloodArsenalTinkers INSTANCE = new BloodArsenalTinkers();

    public static Fluid bloodInfusedIronFluid;

    public BloodArsenalTinkers() {}

    public void init()
    {
        addBloodInfusedWoodMaterial();
        addBloodInfusedIronMaterial();
        BloodArsenalModifiers.initModifiers();
    }

    public void addBloodInfusedWoodMaterial()
    {
        if (ModBlocks.blood_infused_planks != null)
        {
            int id = BloodArsenalConfig.bloodInfusedWoodID;

            if (id > 0)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setInteger("Id", id);
                tag.setString("Name", "BloodInfusedWood");
                tag.setString("localizationString", "material.bloodarsenal.blood_infused_wood");
                tag.setInteger("Durability", 123);
                tag.setInteger("MiningSpeed", 450);
                tag.setInteger("HarvestLevel", 1);
                tag.setInteger("Attack", 1);
                tag.setFloat("HandleModifier", 1.25F);
                tag.setInteger("Reinforced", 0);
                tag.setFloat("Bow_ProjectileSpeed", 3.2F);
                tag.setInteger("Bow_DrawSpeed", 15);
                tag.setFloat("Projectile_Mass", 0.62F);
                tag.setFloat("Projectile_Fragility", 0.5F);
                tag.setString("Style", EnumChatFormatting.RED.toString());
                tag.setInteger("Color", 13178385);
                FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
                ItemStack itemstack = new ItemStack(ModBlocks.blood_infused_planks);
                tag = new NBTTagCompound();
                tag.setInteger("MaterialId", id);
                NBTTagCompound item = new NBTTagCompound();
                itemstack.writeToNBT(item);
                tag.setTag("Item", item);
                tag.setInteger("Value", 2);
                FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
                tag = new NBTTagCompound();
                tag.setInteger("MaterialId", id);
                tag.setInteger("Value", 2);
                item = new NBTTagCompound();
                itemstack.writeToNBT(item);
                tag.setTag("Item", item);
                FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);
            }
        }
    }

    public void addBloodInfusedIronMaterial()
    {
        if (ModBlocks.blood_infused_iron_block != null && ModItems.blood_infused_iron != null)
        {
            int id = BloodArsenalConfig.bloodInfusedIronID;

            if (id > 0)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setInteger("Id", id);
                tag.setString("Name", "BloodInfusedIron");
                tag.setString("localizationString", "material.bloodarsenal.blood_infused_iron");
                tag.setInteger("Durability", 459);
                tag.setInteger("MiningSpeed", 700);
                tag.setInteger("HarvestLevel", 3);
                tag.setInteger("Attack", 3);
                tag.setFloat("HandleModifier", 1.4F);
                tag.setInteger("Reinforced", 0);
                tag.setFloat("Bow_ProjectileSpeed", 5.1F);
                tag.setInteger("Bow_DrawSpeed", 52);
                tag.setFloat("Projectile_Mass", 3.1F);
                tag.setFloat("Projectile_Fragility", 0.7F);
                tag.setString("Style", EnumChatFormatting.DARK_RED.toString());
                tag.setInteger("Color", 16777215);
                FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
                FluidRegistry.registerFluid(bloodInfusedIronFluid);
                FluidType.registerFluidType(bloodInfusedIronFluid.getName(), ModBlocks.blood_infused_iron_block, 0, 850, bloodInfusedIronFluid, true);
                Smeltery.addMelting(new ItemStack(ModBlocks.blood_infused_iron_block, 1), ModBlocks.blood_infused_iron_block, 0, 850, new FluidStack(bloodInfusedIronFluid, 1296));
                Smeltery.addMelting(new ItemStack(ModItems.blood_infused_iron, 1, 0), ModBlocks.blood_infused_iron_block, 0, 850, new FluidStack(bloodInfusedIronFluid, 144));
                ItemStack ingotcast = new ItemStack(TinkerSmeltery.metalPattern, 1, 0);
                TConstructRegistry.getBasinCasting().addCastingRecipe(new ItemStack(ModBlocks.blood_infused_iron_block, 1), new FluidStack(bloodInfusedIronFluid, 1296), null, true, 100);
                TConstructRegistry.getTableCasting().addCastingRecipe(new ItemStack(ModItems.blood_infused_iron, 1), new FluidStack(bloodInfusedIronFluid, 144), ingotcast, false, 50);
                tag = new NBTTagCompound();
                tag.setString("FluidName", bloodInfusedIronFluid.getName());
                tag.setInteger("MaterialId", id);
                FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);
                tag = new NBTTagCompound();
                tag.setInteger("MaterialId", id);
                tag.setTag("Item", (new ItemStack(ModItems.blood_infused_iron, 1, 0)).writeToNBT(new NBTTagCompound()));
                tag.setInteger("Value", 2);
                FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);
                BloodArsenal.proxy.executeClientCode(new IClientCode()
                {
                    @SideOnly(Side.CLIENT)
                    public void executeClientCode()
                    {
                        (new TextureResourcePackBloodInfusedIron("BloodInfusedIron")).register();
                    }
                });
            }
        }
    }

    static
    {
        MinecraftForge.EVENT_BUS.register(new TConEvents());
        bloodInfusedIronFluid = new Fluid("molten.blood_infused_iron").setDensity(3000).setViscosity(6000).setTemperature(1400);
    }
}
