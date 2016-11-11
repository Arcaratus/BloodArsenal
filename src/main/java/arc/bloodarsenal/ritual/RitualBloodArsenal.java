package arc.bloodarsenal.ritual;

import WayofTime.bloodmagic.api.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.api.ritual.Ritual;
import WayofTime.bloodmagic.api.ritual.RitualComponent;
import WayofTime.bloodmagic.api.saving.SoulNetwork;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import arc.bloodarsenal.BloodArsenal;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Locale;

public abstract class RitualBloodArsenal extends Ritual
{
    private int refreshTime;
    private int refreshCost;

    public RitualBloodArsenal(String name, int crystalLevel, int activationCost, int refreshTime, int refreshCost)
    {
        super("ritual" + name, crystalLevel, activationCost, "ritual." + BloodArsenal.MOD_ID + "." + name.toLowerCase(Locale.ENGLISH) + "Ritual");
        this.refreshTime = refreshTime;
        this.refreshCost = refreshCost;
    }

    public RitualBloodArsenal(String name, int crystalLevel, int activationCost)
    {
        this(name, crystalLevel, activationCost, 0, 0);
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone)
    {
        World world = masterRitualStone.getWorldObj();
        SoulNetwork network = NetworkHelper.getSoulNetwork(masterRitualStone.getOwner());
        int currentEssence = network.getCurrentEssence();

        performRitual(masterRitualStone, world, network, currentEssence);
    }

    /**
     * This is for rituals that stay active
     *
     * @param masterRitualStone
     * @param world
     * @param network
     * @param currentEssence
     */
    public void performRitual(IMasterRitualStone masterRitualStone, World world, SoulNetwork network, int currentEssence)
    {

    }

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

    @Override
    public ArrayList<RitualComponent> getComponents()
    {
        ArrayList<RitualComponent> components = new ArrayList<>();
        return addComponents(components);
    }

    public ArrayList<RitualComponent> addComponents(ArrayList<RitualComponent> components)
    {
        return components;
    }
}
