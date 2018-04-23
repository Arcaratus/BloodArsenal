package arcaratus.bloodarsenal.recipe;

import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

/**
 * Wrapper class for a Predicate
 * Tests an ItemStack if it matches it
 *
 * Mainly here so I can use fancy lambdas
 */
public class RecipeFilter
{
    private final Predicate<? super ItemStack> predicate;

    public RecipeFilter(Predicate<? super ItemStack> predicate)
    {
        this.predicate = predicate;
    }

    public boolean matches(ItemStack itemStack)
    {
        return predicate.test(itemStack);
    }
}
