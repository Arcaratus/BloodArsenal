package arcaratus.bloodarsenal.data;

import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGenerators
{
    public static void gatherData(GatherDataEvent event)
    {
        if (event.includeServer())
        {
            event.getGenerator().addProvider(new GeneratorLootTable(event.getGenerator()));
        }

        if (event.includeClient())
        {
            event.getGenerator().addProvider(new GeneratorBlock(event.getGenerator(), event.getExistingFileHelper()));
            event.getGenerator().addProvider(new GeneratorItem(event.getGenerator(), event.getExistingFileHelper()));
        }
    }
}
