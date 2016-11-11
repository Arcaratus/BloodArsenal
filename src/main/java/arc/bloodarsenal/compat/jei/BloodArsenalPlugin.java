package arc.bloodarsenal.compat.jei;

import WayofTime.bloodmagic.api.iface.IActivatable;
import WayofTime.bloodmagic.api.iface.ISigil;
import arc.bloodarsenal.compat.jei.infusion.SanguineInfusionCategory;
import arc.bloodarsenal.compat.jei.infusion.SanguineInfusionHandler;
import arc.bloodarsenal.modifier.*;
import arc.bloodarsenal.recipe.SanguineInfusionRecipeRegistry;
import arc.bloodarsenal.registry.ModBlocks;
import arc.bloodarsenal.registry.ModItems;
import mezz.jei.api.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;

import javax.annotation.Nonnull;
import java.util.*;

@JEIPlugin
public class BloodArsenalPlugin extends BlankModPlugin
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

        registry.addRecipeCategories(new SanguineInfusionCategory(jeiHelper.getGuiHelper()));

        registry.addRecipeHandlers(new SanguineInfusionHandler());

        registry.addRecipes(SanguineInfusionRecipeRegistry.getInfusionRecipes());

        registry.addRecipeCategoryCraftingItem(new ItemStack(WayofTime.bloodmagic.registry.ModBlocks.RITUAL_CONTROLLER), SanguineInfusionCategory.UID);

        for (ItemStack itemStack : registry.getIngredientRegistry().getIngredients(ItemStack.class))
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

        jeiHelper.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.BLOOD_BURNED_STRING));

        for (Map.Entry<String, Integer> entry : ModifierHandler.modifierMaxLevelMap.entrySet())
        {
            String key = entry.getKey();
            int maxLevel = entry.getValue();
            for (int i = 0; i < maxLevel - 1; i++)
            {
                ItemStack stack = new ItemStack(ModItems.MODIFIER_TOME);
                ModifierHelper.setKey(stack, key);
                ModifierHelper.setLevel(stack, i);
                jeiHelper.getIngredientBlacklist().addIngredientToBlacklist(stack);
            }
        }

        registry.addDescription(new ItemStack(ModItems.GLASS_SHARD), "jei.bloodarsenal.desc.glassShard");
        registry.addDescription(new ItemStack(ModItems.REAGENT_LIGHTNING), "jei.bloodarsenal.desc.reagentLightning");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry)
    {
        subtypeRegistry.useNbtForSubtypes(ModItems.MODIFIER_TOME);
    }
}
