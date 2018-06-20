package arcaratus.bloodarsenal.core;

import WayofTime.bloodmagic.altar.ComponentType;
import WayofTime.bloodmagic.api.*;
import WayofTime.bloodmagic.api.impl.BloodMagicRecipeRegistrar;

@BloodMagicPlugin
public class BloodArsenalCorePlugin implements IBloodMagicPlugin
{
    @Override
    public void register(IBloodMagicAPI apiInterface)
    {
        apiInterface.registerAltarComponent(RegistrarBloodArsenalBlocks.BLOOD_INFUSED_GLOWSTONE.getDefaultState(), ComponentType.GLOWSTONE.name());
    }

    @Override
    public void registerRecipes(IBloodMagicRecipeRegistrar recipeRegistrar)
    {
        RegistrarBloodArsenalRecipes.registerAltarRecipes(recipeRegistrar);
//        RegistrarBloodArsenalRecipes.registerAlchemyTableRecipes((BloodMagicRecipeRegistrar) recipeRegistrar);
        RegistrarBloodArsenalRecipes.registerTartaricForgeRecipes((BloodMagicRecipeRegistrar) recipeRegistrar);
        RegistrarBloodArsenalRecipes.registerAlchemyArrayRecipes((BloodMagicRecipeRegistrar) recipeRegistrar);
    }
}
