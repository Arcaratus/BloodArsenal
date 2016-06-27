package arc.bloodarsenal.util;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.recipe.TartaricForgeRecipe;
import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import WayofTime.bloodmagic.item.sigil.ItemSigilHolding;
import arc.bloodarsenal.registry.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TartaricForgeSigilHoldingRecipe extends TartaricForgeRecipe
{
    public TartaricForgeSigilHoldingRecipe(ItemStack sigilAugmentedHolding, double minSouls, double drain, Object... recipe)
    {
        super(sigilAugmentedHolding, minSouls, drain, recipe);
    }

    public ItemStack getRecipeOutput()
    {
        if (input != null && input.contains(ModRecipes.getBMItem(Constants.BloodMagicItem.SIGIL_HOLDING)) && input.get(input.indexOf(ModRecipes.getBMItem(Constants.BloodMagicItem.SIGIL_HOLDING))) instanceof ItemStack)
        {
            System.out.println("STAGE ONE");
            ItemStack sigilHolding = (ItemStack) input.get(input.indexOf(ModRecipes.getBMItem(Constants.BloodMagicItem.SIGIL_HOLDING)));
            if (sigilHolding.getItem() instanceof ItemSigilHolding && sigilHolding.getTagCompound() != null)
            {
                System.out.println("STAGE TWO");
                NBTTagList tagList = sigilHolding.getTagCompound().getTagList(Constants.NBT.ITEMS, 10);

                if (tagList == null)
                    return output.copy();

                ItemStack[] inv = new ItemStack[5];

                for (int i = 0; i < tagList.tagCount(); i++)
                {
                    NBTTagCompound data = tagList.getCompoundTagAt(i);
                    byte j = data.getByte(Constants.NBT.SLOT);

                    if (j >= 0 && j < inv.length)
                    {
                        inv[j] = ItemStack.loadItemStackFromNBT(data);
                    }
                }

                NBTTagList itemList = new NBTTagList();

                for (int i = 0; i < 5; i++)
                {
                    if (inv[i] != null)
                    {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setByte(Constants.NBT.SLOT, (byte) i);
                        inv[i].writeToNBT(tag);
                        itemList.appendTag(tag);
                    }
                }

                output = NBTHelper.checkNBT(output);
                output.getTagCompound().setTag(Constants.NBT.ITEMS, itemList);
            }
        }

        return output.copy();
    }
}

