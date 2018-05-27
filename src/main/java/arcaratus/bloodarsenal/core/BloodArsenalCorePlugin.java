package arcaratus.bloodarsenal.core;

import WayofTime.bloodmagic.api.*;
import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.api.impl.BloodMagicRecipeRegistrar;

@BloodMagicPlugin
public class BloodArsenalCorePlugin implements IBloodMagicPlugin
{
    @Override
    public void register(IBloodMagicAPI apiInterface)
    {
        BloodMagicAPI api = (BloodMagicAPI) apiInterface;
    }

    @Override
    public void registerRecipes(IBloodMagicRecipeRegistrar recipeRegistrar)
    {
        RegistrarBloodArsenalRecipes.registerAltarRecipes((BloodMagicRecipeRegistrar) recipeRegistrar);
//        RegistrarBloodArsenalRecipes.registerAlchemyTableRecipes((BloodMagicRecipeRegistrar) recipeRegistrar);
        RegistrarBloodArsenalRecipes.registerTartaricForgeRecipes((BloodMagicRecipeRegistrar) recipeRegistrar);
        RegistrarBloodArsenalRecipes.registerAlchemyArrayRecipes((BloodMagicRecipeRegistrar) recipeRegistrar);
    }
}
