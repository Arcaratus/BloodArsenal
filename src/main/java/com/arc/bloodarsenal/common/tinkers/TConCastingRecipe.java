package com.arc.bloodarsenal.common.tinkers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.client.FluidRenderProperties;
import tconstruct.library.crafting.CastingRecipe;

public class TConCastingRecipe extends CastingRecipe
{
    public TConCastingRecipe(ItemStack replacement, FluidStack metal, ItemStack cast, boolean consume, int delay, FluidRenderProperties props)
    {
        super(replacement, metal, cast, consume, delay, props);
    }

    public TConCastingRecipe(CastingRecipe recipe)
    {
        super(recipe.output.copy(), recipe.castingMetal.copy(), recipe.cast.copy(), recipe.consumeCast, recipe.coolTime, recipe.fluidRenderProperties);
    }

    @Override
    public boolean matches(FluidStack metal, ItemStack inputCast)
    {
        return (this.castingMetal != null) && (this.castingMetal.isFluidEqual(metal)) && (inputCast != null) && (this.cast != null) && (inputCast.getItem() == this.cast.getItem()) && ((this.cast.getItemDamage() == Short.MAX_VALUE));
    }
}
