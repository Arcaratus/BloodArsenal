package arc.bloodarsenal.client.gui.config;

import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig
{
    public ConfigGui(GuiScreen parentScreen)
    {
        super(parentScreen, getConfigElements(parentScreen), BloodArsenal.MOD_ID, false, false, "Blood Arsenal Configuration");
    }

    @SuppressWarnings("rawtypes")
    private static List<IConfigElement> getConfigElements(GuiScreen parent)
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        // adds sections declared in ConfigHandler. toLowerCase() is used
        // because the configuration class automatically does this, so must we.
        list.add(new ConfigElement(ConfigHandler.getConfig().getCategory("Item/Block Blacklisting".toLowerCase())));
        list.add(new ConfigElement(ConfigHandler.getConfig().getCategory("Block Configs".toLowerCase())));
        list.add(new ConfigElement(ConfigHandler.getConfig().getCategory("Item Configs".toLowerCase())));
        list.add(new ConfigElement(ConfigHandler.getConfig().getCategory("Miscellaneous".toLowerCase())));

        return list;
    }
}
