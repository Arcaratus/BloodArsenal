package arcaratus.bloodarsenal.ritual;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.ritual.Ritual;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Locale;
import java.util.UUID;

public abstract class RitualBloodArsenal extends Ritual
{
    private int refreshTime;
    private int refreshCost;

    public RitualBloodArsenal(String name, int crystalLevel, int activationCost, int refreshTime, int refreshCost)
    {
        super("ritual" + name, crystalLevel, activationCost, "ritual." + BloodArsenal.MOD_ID + "." + name.toLowerCase(Locale.ENGLISH) + "_ritual");
        this.refreshTime = refreshTime;
        this.refreshCost = refreshCost;
    }

    public RitualBloodArsenal(String name, int crystalLevel, int activationCost)
    {
        this(name, crystalLevel, activationCost, 0, 0);
    }

    @Override
    public boolean activateRitual(IMasterRitualStone masterRitualStone, EntityPlayer player, UUID owner)
    {
        World world = masterRitualStone.getWorldObj();
        SoulNetwork soulNetwork = masterRitualStone.getOwnerNetwork();
        return activateRitual(masterRitualStone, player, world, soulNetwork);
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone)
    {
        World world = masterRitualStone.getWorldObj();
        SoulNetwork soulNetwork = masterRitualStone.getOwnerNetwork();
        performRitual(masterRitualStone, world, soulNetwork);
    }

    public boolean activateRitual(IMasterRitualStone masterRitualStone, EntityPlayer player, World world, SoulNetwork network) { return false; }

    /**
     * This is for rituals that stay active
     *
     * @param masterRitualStone
     * @param world
     */
    public void performRitual(IMasterRitualStone masterRitualStone, World world, SoulNetwork network) {}

    @Override
    public int getRefreshTime()
    {
        return refreshTime;
    }

    @Override
    public int getRefreshCost()
    {
        return refreshCost;
    }
}
