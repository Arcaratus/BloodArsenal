package arcaratus.bloodarsenal.compat.jei;

import WayofTime.bloodmagic.core.RegistrarBloodMagicBlocks;
import WayofTime.bloodmagic.iface.IActivatable;
import WayofTime.bloodmagic.iface.ISigil;
import arcaratus.bloodarsenal.compat.jei.infusion.SanguineInfusionCategory;
import arcaratus.bloodarsenal.compat.jei.infusion.SanguineInfusionRecipeJEI;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.item.types.EnumBaseTypes;
import arcaratus.bloodarsenal.modifier.IModifiableItem;
import arcaratus.bloodarsenal.recipe.RecipeSanguineInfusion;
import arcaratus.bloodarsenal.recipe.SanguineInfusionRecipeRegistry;
import arcaratus.bloodarsenal.registry.Constants;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.*;
import net.minecraft.potion.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class BloodArsenalPlugin implements IModPlugin
{
    public static IJeiHelpers jeiHelper;
    public static List<ItemStack> modifiables = new ArrayList<>();
    public static List<ItemStack> sigils = new ArrayList<>();
    public static List<ItemStack> badPotions = new ArrayList<>();
    public static List<ItemStack> beneficialPotions = new ArrayList<>();

    @Override
    public void register(@Nonnull IModRegistry registry)
    {
        jeiHelper = registry.getJeiHelpers();

        registry.addRecipes(SanguineInfusionRecipeRegistry.getInfusionRecipes(), Constants.Compat.JEI_CATEGORY_SANGUINE_INFUSION);

        registry.handleRecipes(RecipeSanguineInfusion.class, SanguineInfusionRecipeJEI::new, Constants.Compat.JEI_CATEGORY_SANGUINE_INFUSION);

        registry.addRecipeCatalyst(new ItemStack(RegistrarBloodMagicBlocks.RITUAL_CONTROLLER), Constants.Compat.JEI_CATEGORY_SANGUINE_INFUSION);

        for (ItemStack itemStack : registry.getIngredientRegistry().getAllIngredients(ItemStack.class))
        {
            if (!itemStack.isEmpty())
            {
                if (itemStack.getItem() instanceof IModifiableItem)
                {
                    modifiables.add(itemStack);
                }
                else if (itemStack.getItem() instanceof ISigil && itemStack.getItem() instanceof IActivatable)
                {
                    sigils.add(itemStack);
                }
                else if (itemStack.getItem() instanceof ItemPotion)
                {
                    List<PotionEffect> potionEffects = PotionUtils.getEffectsFromStack(itemStack);

                    if (!potionEffects.isEmpty())
                    {
                        Potion potion = potionEffects.get(0).getPotion();

                        if (itemStack.getItem() instanceof ItemSplashPotion && potion.isBadEffect())
                        {
                            badPotions.add(itemStack);
                        }
                        else if (potion.isBeneficial() && !(itemStack.getItem() instanceof ItemSplashPotion))
                        {
                            beneficialPotions.add(itemStack);
                        }
                    }
                }
            }
        }
//
//        jeiHelper.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.BLOOD_BURNED_STRING));
//
//        for (Map.Entry<String, Integer> entry : ModifierHandler.modifierMaxLevelMap.entrySet())
//        {
//            String key = entry.getKey();
//            int maxLevel = entry.getValue();
//            for (int i = 0; i < maxLevel - 1; i++)
//            {
//                ItemStack stack = new ItemStack(ModItems.MODIFIER_TOME);
//                ModifierHelper.setKey(stack, key);
//                ModifierHelper.setLevel(stack, i);
//                jeiHelper.getIngredientBlacklist().addIngredientToBlacklist(stack);
//            }
//        }
//
        registry.addIngredientInfo(EnumBaseTypes.GLASS_SHARD.getStack(), ItemStack.class, "jei.bloodarsenal.desc.glass_shard");
        registry.addIngredientInfo(EnumBaseTypes.REAGENT_LIGHTNING.getStack(), ItemStack.class, "jei.bloodarsenal.desc.reagent_lightning");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry)
    {
        subtypeRegistry.useNbtForSubtypes(RegistrarBloodArsenalItems.MODIFIER_TOME);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        if (jeiHelper == null)
            jeiHelper = registry.getJeiHelpers();

        registry.addRecipeCategories(
                new SanguineInfusionCategory()
        );
    }
}
