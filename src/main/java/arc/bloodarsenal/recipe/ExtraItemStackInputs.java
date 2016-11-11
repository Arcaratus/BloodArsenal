package arc.bloodarsenal.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;

import java.util.*;

public class ExtraItemStackInputs
{
    private final ImmutableList<ItemStack> itemStacks;

    public ExtraItemStackInputs(ItemStack... itemStacks)
    {
        List<ItemStack> dummyList = new ArrayList<>();

        for (ItemStack itemStack : itemStacks)
            dummyList.addAll(splitStack(itemStack));

        this.itemStacks = ImmutableList.copyOf(dummyList);
    }

    public ImmutableList<ItemStack> getItemStacks()
    {
        return itemStacks;
    }

    /**
     * Takes an ItemStack of whatever-the-hell size and splits it into multiple stacks of max size 64
     */
    public static List<ItemStack> splitStack(ItemStack itemStack)
    {
        if (itemStack.getCount() <= 64)
            return Collections.singletonList(itemStack);

        List<ItemStack> splitStack = new ArrayList<>();
        ItemStack copyStack = itemStack.copy();
        int itemsRemaining = itemStack.getCount();

        copyStack.setCount(itemStack.getCount() % 64);
        splitStack.add(copyStack);
        itemsRemaining -= copyStack.getCount();
        int quotient = itemsRemaining / 64;

        if (quotient == 0)
            return splitStack;

        copyStack.setCount(64);
        for (int i = 0; i < quotient; i++)
            splitStack.add(copyStack);

        return splitStack;
    }
}
