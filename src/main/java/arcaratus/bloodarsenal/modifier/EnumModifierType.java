package arcaratus.bloodarsenal.modifier;

public enum EnumModifierType
{
    HEAD(3),
    CORE(2),
    HANDLE(2),
    ABILITY(1)
    ;

    private final int max;

    EnumModifierType(int max)
    {
        this.max = max;
    }

    public int getMax()
    {
        return max;
    }
}
