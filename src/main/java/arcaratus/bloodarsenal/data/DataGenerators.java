package arcaratus.bloodarsenal.data;

import arcaratus.bloodarsenal.data.recipe.GeneratorRecipe;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGenerators
{
    public static void gatherData(GatherDataEvent event)
    {
        if (event.includeServer())
        {
            event.getGenerator().addProvider(new GeneratorLootTable(event.getGenerator()));
            event.getGenerator().addProvider(new GeneratorRecipe(event.getGenerator()));
        }

        if (event.includeClient())
        {
            event.getGenerator().addProvider(new GeneratorBlock(event.getGenerator(), event.getExistingFileHelper()));
            event.getGenerator().addProvider(new GeneratorItem(event.getGenerator(), event.getExistingFileHelper()));
        }
    }
}
