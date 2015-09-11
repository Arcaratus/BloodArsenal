/**
 *  Taken from SpitefulFox's ForbiddenMagic
 *  https://github.com/SpitefulFox/ForbiddenMagic
 */

package com.arc.bloodarsenal.common.thaumcraft;

import com.arc.bloodarsenal.common.block.ModBlocks;
import com.arc.bloodarsenal.common.items.ModItems;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.StaffRod;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;

public class BloodArsenalThaumcraft
{
    public static void addResearch()
    {
        final String FORBIDDEN = "FORBIDDEN";

        WandRod bloodRod = WandRod.rods.get("blood");
        WandCap alchemicalCap = WandCap.caps.get("alchemical");
        WandRod bloodStaff = StaffRod.rods.get("blood");

        InfusionRecipe rod_blood_wood = ThaumcraftApi.addInfusionCraftingRecipe("ROD_blood_wood", new ItemStack(ModItems.wandCore, 1, 0), 3, (new AspectList()).add(Aspect.LIFE, 32).add(Aspect.MAGIC, 24).add(Aspect.TOOL, 16), bloodRod.getItem(), new ItemStack[] {new ItemStack(ModItems.amorphic_catalyst), new ItemStack(ModItems.amorphic_catalyst), new ItemStack(ModBlocks.blood_infused_wood), new ItemStack(ModBlocks.blood_infused_wood), new ItemStack(ModBlocks.blood_infused_wood), new ItemStack(ModItems.blood_infused_glowstone_dust)});
        new BloodArsenalResearchItem("ROD_blood_wood", FORBIDDEN, (new AspectList()).add(Aspect.LIFE, 10).add(Aspect.MAGIC, 8).add(Aspect.TOOL, 4), 4, -5, 2, new ItemStack(ModItems.wandCore, 1, 0)).setPages(new ResearchPage("blood_arsenal.research_page.ROD_blood_wood.1"), new ResearchPage("blood_arsenal.research_page.ROD_blood_wood.2"), new ResearchPage(rod_blood_wood)).setParents("ROD_blood").registerResearchItem();
        ThaumcraftApi.addWarpToResearch("ROD_blood_wood", 2);
        ThaumcraftApi.addWarpToItem(new ItemStack(ModItems.wandCore, 1, 0), 2);
        InfusionRecipe cap_blood_iron = ThaumcraftApi.addInfusionCraftingRecipe("CAP_blood_iron", new ItemStack(ModItems.wandCap, 1, 0), 3, (new AspectList()).add(Aspect.LIFE, 24).add(Aspect.WATER, 24).add(Aspect.MAGIC, 20), alchemicalCap.getItem(), new ItemStack[] { new ItemStack(ModItems.amorphic_catalyst), new ItemStack(ModItems.amorphic_catalyst), new ItemStack(ModItems.blood_infused_iron), new ItemStack(ModItems.blood_infused_iron), new ItemStack(ModItems.blood_infused_iron), new ItemStack(ModItems.blood_infused_glowstone_dust), new ItemStack(ModItems.blood_infused_glowstone_dust) });
        new BloodArsenalResearchItem("CAP_blood_iron", FORBIDDEN, (new AspectList()).add(Aspect.LIFE, 6).add(Aspect.TOOL, 2), 2, -5, 2, new ItemStack(ModItems.wandCap, 1, 0)).setPages(new ResearchPage("blood_arsenal.research_page.CAP_blood_iron.1"), new ResearchPage(cap_blood_iron)).setParents("ROD_blood_wood", "CAP_alchemical").registerResearchItem();
        ThaumcraftApi.addWarpToResearch("CAP_blood_iron", 1);
        ThaumcraftApi.addWarpToItem(new ItemStack(ModItems.wandCap), 1);
        InfusionRecipe staff_blood_wood = ThaumcraftApi.addInfusionCraftingRecipe("ROD_blood_wood_staff", new ItemStack(ModItems.wandCore, 1, 1), 6, (new AspectList()).add(Aspect.LIFE, 64).add(Aspect.MAGIC, 48).add(Aspect.WATER, 32).add(Aspect.TOOL, 24), bloodStaff.getItem(), new ItemStack[] {new ItemStack(ModItems.wandCore, 1, 0), new ItemStack(ModItems.blood_infused_glowstone_dust), new ItemStack(ModItems.amorphic_catalyst), new ItemStack(ModBlocks.blood_infused_wood), new ItemStack(ModBlocks.blood_infused_wood), new ItemStack(ModItems.amorphic_catalyst), new ItemStack(ModItems.blood_infused_glowstone_dust), new ItemStack(ModItems.wandCore, 1, 0), new ItemStack(ModItems.blood_infused_glowstone_dust), new ItemStack(ModItems.amorphic_catalyst), new ItemStack(ModBlocks.blood_infused_wood), new ItemStack(ModBlocks.blood_infused_wood), new ItemStack(ModItems.amorphic_catalyst), new ItemStack(ModItems.blood_infused_glowstone_dust)});
        new BloodArsenalResearchItem("ROD_blood_wood_staff", FORBIDDEN, (new AspectList()).add(Aspect.LIFE, 16).add(Aspect.MAGIC, 12).add(Aspect.TOOL, 8), 5, -3, 3, new ItemStack(ModItems.wandCore, 1, 1)).setPages(new ResearchPage("blood_arsenal.research_page.ROD_blood_wood_staff.1"), new ResearchPage(staff_blood_wood)).setParents("ROD_blood_wood", "ROD_blood_staff").setHidden().setSpecial().registerResearchItem();
        ThaumcraftApi.addWarpToResearch("ROD_blood_wood_staff", 3);
        ThaumcraftApi.addWarpToItem(new ItemStack(ModItems.wandCore, 1, 1), 4);
    }
}
