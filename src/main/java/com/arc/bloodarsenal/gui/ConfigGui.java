package com.arc.bloodarsenal.gui;

import com.arc.bloodarsenal.BloodArsenal;
import cpw.mods.fml.client.config.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.arc.bloodarsenal.BloodArsenalConfig.*;

public class ConfigGui extends GuiConfig
{
	public ConfigGui(GuiScreen parentScreen)
	{
		super(parentScreen, getConfigElements(parentScreen), BloodArsenal.MODID, false, false, "BloodArsenal Configuration");
	}

	@SuppressWarnings("rawtypes")
	private static List<IConfigElement> getConfigElements(GuiScreen parent)
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		// Adds sections declared in BloodArsenalConfig. toLowerCase() is used because the configuration class automatically does this, so must we.
		list.add(new ConfigElement<ConfigCategory>(config.getCategory(potionId.toLowerCase())));
		list.add(new ConfigElement<ConfigCategory>(config.getCategory(ritualBlacklist.toLowerCase())));
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(blockSettings.toLowerCase())));
		list.add(new ConfigElement<ConfigCategory>(config.getCategory(itemSettings.toLowerCase())));
		list.add(new ConfigElement<ConfigCategory>(config.getCategory(modSettings.toLowerCase())));
		list.add(new ConfigElement<ConfigCategory>(config.getCategory(lpSettings.toLowerCase())));
		list.add(new ConfigElement<ConfigCategory>(config.getCategory(misc.toLowerCase())));

		return list;
	}
}