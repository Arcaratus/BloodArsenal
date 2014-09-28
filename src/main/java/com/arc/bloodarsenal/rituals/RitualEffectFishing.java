package com.arc.bloodarsenal.rituals;

import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RitualEffectFishing extends RitualEffect
{
    private static final int terraeDrain = 20;
    private static final int orbisTerraeDrain = 20;

    protected Random random = null;
    protected float next = Float.NaN;

    @Override
    public void performEffect(IMasterRitualStone ritualStone)
    {
        String owner = ritualStone.getOwner();
        World worldSave = MinecraftServer.getServer().worldServers[0];
        LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);

        if (data == null)
        {
            data = new LifeEssenceNetwork(owner);
            worldSave.setItemData(owner, data);
        }

        int currentEssence = data.currentEssence;
        World world = ritualStone.getWorld();

        if (world.getWorldTime() % 10 != 5)
        {
            return;
        }

        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        TileEntity tile = world.getTileEntity(x, y + 1, z);
        IInventory tileEntity;

        if (tile instanceof IInventory)
        {
            tileEntity = (IInventory) tile;
        }
        else
        {
            return;
        }

        if (tileEntity.getSizeInventory() <= 0)
        {
            return;
        }

        boolean hasRoom = false;
        for (int i=0; i<tileEntity.getSizeInventory(); i++)
        {
            if (tileEntity.getStackInSlot(i) == null)
            {
                hasRoom = true;
                break;
            }
        }

        if (!hasRoom)
        {
            return;
        }

        if (currentEssence < getCostPerRefresh())
        {
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        }
        else
        {
            boolean hasTerrae = canDrainReagent(ritualStone, ReagentRegistry.terraeReagent, terraeDrain, false);
            boolean hasOrbisTerrae = canDrainReagent(ritualStone, ReagentRegistry.orbisTerraeReagent, orbisTerraeDrain, false);

            int speed = getSpeedForReagents(hasTerrae, hasOrbisTerrae);
            if (world.getWorldTime() % speed != 0)
            {
                return;
            }

            int flag = 0;

            for (int i = -1; i <= 1; i++)
            {
                for (int j = -1; j <= 1; j++)
                {
                    Block block = world.getBlock(x + i, y - 1, z + j);

                    {
                        if (block.isAssociatedBlock(Blocks.water) || block.isAssociatedBlock(Blocks.flowing_water))
                        {

                        }
                    }
                }
            }
        }
    }

    @Override
    public int getCostPerRefresh()
    {
        return 50;
    }

    @Override
    public List<RitualComponent> getRitualComponentList()
    {
        ArrayList<RitualComponent> fishingRitual = new ArrayList();
        fishingRitual.add(new RitualComponent(1, 0, 0, 1));
        fishingRitual.add(new RitualComponent(-1, 0, 0, 1));
        fishingRitual.add(new RitualComponent(0, 0, 1, 1));
        fishingRitual.add(new RitualComponent(1, 0, -1, 1));
        fishingRitual.add(new RitualComponent(-1, 0, 1, 3));
        fishingRitual.add(new RitualComponent(1, 0, 1, 3));
        fishingRitual.add(new RitualComponent(-1, 0, -1, 3));
        fishingRitual.add(new RitualComponent(1, 0, -1, 3));
        fishingRitual.add(new RitualComponent(1, 1, 0, 4));
        fishingRitual.add(new RitualComponent(-1, 1, 0, 4));
        fishingRitual.add(new RitualComponent(0, 1, 1, 4));
        fishingRitual.add(new RitualComponent(0, 1, -1, 4));
        return fishingRitual;
    }

    public int getSpeedForReagents(boolean hasTerrae, boolean hasOrbisTerrae)
    {
        if (hasOrbisTerrae)
        {
            if (hasTerrae)
            {
                return 10;
            }
            else
            {
                return 15;
            }
        }
        else
        {
            if (hasTerrae)
            {
                return 20;
            }
            else
            {
                return 30;
            }
        }
    }
}
