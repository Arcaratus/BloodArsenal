package arcaratus.bloodarsenal.block.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum EnumSlate implements IStringSerializable
{
    BLANK,
    REINFORCED,
    IMBUED,
    DEMONIC,
    ETHEREAL;

    @Override
    public String toString()
    {
        return name().toLowerCase(Locale.ENGLISH);
    }

    @Override
    public String getName()
    {
        return toString();
    }
}
