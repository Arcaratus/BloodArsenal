package com.arc.bloodarsenal;

import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;
import com.arc.bloodarsenal.item.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {

    }

    public static class BloodArsenalRecipes extends BindingRegistry
    {
        public static void initBindingRecipes()
        {
            BindingRegistry.registerRecipe(new ItemStack(ModItems.bound_bow), new ItemStack(Items.bow));
        }
    }
}
