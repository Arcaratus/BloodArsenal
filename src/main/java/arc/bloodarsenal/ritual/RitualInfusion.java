package arc.bloodarsenal.ritual;

import WayofTime.bloodmagic.api.ritual.EnumRuneType;
import WayofTime.bloodmagic.api.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.api.ritual.Ritual;
import WayofTime.bloodmagic.api.ritual.RitualComponent;
import net.minecraft.world.World;

import java.util.ArrayList;

public class RitualInfusion extends RitualBloodArsenal
{
    public RitualInfusion()
    {
        super("infusion", 0, 10000);
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone)
    {
        World world = masterRitualStone.getWorldObj();

        if (world.isRemote)
            return;
//
//        world.spawnEntityInWorld(new EntityLightningBolt(world, masterRitualStone.getBlockPos().getX(), masterRitualStone.getBlockPos().getY(), masterRitualStone.getBlockPos().getZ(), true));
//
//        masterRitualStone.setActive(false);
    }

    public ArrayList<RitualComponent> addComponents(ArrayList<RitualComponent> components)
    {
        addCornerRunes(components, 3, 0, EnumRuneType.DUSK);
        return components;
    }

    @Override
    public Ritual getNewCopy()
    {
        return new RitualInfusion();
    }
}
