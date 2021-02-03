package arcaratus.bloodarsenal.common.util.helper;

import net.minecraft.client.resources.I18n;

import java.util.TreeMap;

/**
 * Stolen from Blood Magic
 * https://github.com/WayofTime/BloodMagic
 */
public class TextHelper
{
    private static final TreeMap<Integer, String> romanNumerals = new TreeMap<>();

    static
    {
        romanNumerals.put(1000, "M");
        romanNumerals.put(900, "CM");
        romanNumerals.put(500, "D");
        romanNumerals.put(400, "CD");
        romanNumerals.put(100, "C");
        romanNumerals.put(90, "XC");
        romanNumerals.put(50, "L");
        romanNumerals.put(40, "XL");
        romanNumerals.put(10, "X");
        romanNumerals.put(9, "IX");
        romanNumerals.put(5, "V");
        romanNumerals.put(4, "IV");
        romanNumerals.put(1, "I");
    }

    public static String getFormattedText(String string)
    {
        return string.replaceAll("&", "§");
    }

    public static String localize(String input, Object... format)
    {
        return I18n.format(input, format);
    }

    public static String localizeEffect(String input, Object... format)
    {
        return getFormattedText(localize(input, format));
    }

    public static String toRoman(int arabic)
    {
        int convert = romanNumerals.floorKey(arabic);
        return arabic == convert ? romanNumerals.get(convert) : romanNumerals.get(convert) + toRoman(arabic - convert);
    }
}
