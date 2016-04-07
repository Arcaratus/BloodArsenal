package arc.bloodarsenal.registry;

import WayofTime.bloodmagic.api.altar.EnumAltarTier;
import WayofTime.bloodmagic.api.recipe.ShapedBloodOrbRecipe;
import WayofTime.bloodmagic.api.recipe.ShapelessBloodOrbRecipe;
import WayofTime.bloodmagic.api.registry.AltarRecipeRegistry;
import WayofTime.bloodmagic.api.registry.OrbRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;

public class ModRecipes
{
    public static void init()
    {
        addCraftingRecipes();
        addAltarRecipes();
        addSoulForgeRecipes();
    }

    public static void addCraftingRecipes()
    {
        ItemStack weakOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbWeak);
        ItemStack apprenticeOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbApprentice);
        ItemStack magicianOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbMagician);
        ItemStack masterOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbMaster);
        ItemStack archmageOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbArchmage);
        ItemStack transcendentOrb = OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbTranscendent);

        //SHAPED
        addOreDictRecipe(new ItemStack(ModItems.bloodInfusedStick, 2), "a", "a", 'a', ModBlocks.bloodInfusedWoodenPlanks);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenSlab, 6), "aaa", 'a', ModBlocks.bloodInfusedWoodenPlanks);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenStairs, 4), "a  ", "aa ", "aaa", 'a', ModBlocks.bloodInfusedWoodenPlanks);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenFence, 3), "aba", "aba", 'a', ModBlocks.bloodInfusedWoodenPlanks, 'b', ModItems.bloodInfusedStick);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenPlanks), "a", "a", 'a',  ModBlocks.bloodInfusedWoodenSlab);
        addOreDictRecipe(new ItemStack(ModBlocks.bloodStainedGlassPane, 16), "aaa", "aaa", 'a', ModBlocks.bloodStainedGlass);

        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.bloodStainedGlass, 8), "aaa", "aba", "aaa", 'a', "blockGlass", 'b', weakOrb);
        addOreDictBloodOrbRecipe(new ItemStack(ModBlocks.bloodStainedGlassPane, 8), "aaa", "aba", "aaa", 'a', "glassPane", 'b', weakOrb);

        //SHAPELESS
        addShapelessOreDictRecipe(new ItemStack(ModBlocks.bloodInfusedWoodenPlanks, 2), ModBlocks.bloodInfusedWoodenLog);
        addShapelessOreDictRecipe(new ItemStack(Blocks.glass), ModBlocks.bloodStainedGlass);
        addShapelessOreDictRecipe(new ItemStack(Blocks.glass_pane), ModBlocks.bloodStainedGlassPane);

        addShapelessBloodOrbRecipe(new ItemStack(ModBlocks.bloodStainedGlass), "blockGlass", weakOrb);
        addShapelessOreDictRecipe(new ItemStack(ModBlocks.bloodStainedGlassPane), "glassPane", weakOrb);
    }

    public static void addAltarRecipes()
    {
        //ONE

        //TWO
        addAltarRecipe("logWood", new ItemStack(ModBlocks.bloodInfusedWoodenLog), EnumAltarTier.TWO, 5000, 5, 5);

        //THREE

        //FOUR

        //FIVE

        //SIX
    }

    public static void addSoulForgeRecipes()
    {

    }

    public static void addOreDictRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
    }

    public static void addOreDictBloodOrbRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedBloodOrbRecipe(output, recipe));
    }

    public static void addShapelessOreDictRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
    }

    public static void addShapelessBloodOrbRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessBloodOrbRecipe(output, recipe));
    }

    public static void addAltarRecipe(List<ItemStack> input, ItemStack output, EnumAltarTier minTier, int syphon, int consumeRate, int drainRate)
    {
        AltarRecipeRegistry.registerRecipe(new AltarRecipeRegistry.AltarRecipe(input, output, minTier, syphon, consumeRate, drainRate));
    }

    public static void addAltarRecipe(ItemStack input, ItemStack output, EnumAltarTier minTier, int syphon, int consumeRate, int drainRate)
    {
        AltarRecipeRegistry.registerRecipe(new AltarRecipeRegistry.AltarRecipe(input, output, minTier, syphon, consumeRate, drainRate));
    }

    public static void addAltarRecipe(String oreDictInput, ItemStack output, EnumAltarTier minTier, int syphon, int consumeRate, int drainRate)
    {
        AltarRecipeRegistry.registerRecipe(new AltarRecipeRegistry.AltarRecipe(oreDictInput, output, minTier, syphon, consumeRate, drainRate));
    }
}
