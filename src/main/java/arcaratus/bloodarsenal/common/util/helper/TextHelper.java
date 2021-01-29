package arcaratus.bloodarsenal.common.util.helper;

import net.minecraft.client.resources.I18n;

/**
 * Stolen from Blood Magic
 * https://github.com/WayofTime/BloodMagic
 */
public class TextHelper
{
    public static String getFormattedText(String string) {
        return string.replaceAll("&", "§");
    }

    public static String localize(String input, Object... format) {
        return I18n.format(input, format);
    }

    public static String localizeEffect(String input, Object... format) {
        return getFormattedText(localize(input, format));
    }
}
