package com.arc.bloodarsenal.common.rituals;

import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectWithering extends RitualEffect
{
    public static final int reductusDrain = 15;
    public static final int virtusDrain = 15;
    public static final int praesidiumDrain = 5;

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
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();

        int timeDelay = 50;

        if (world.getWorldTime() % timeDelay != 0)
        {
            return;
        }

        boolean hasPraesidium = canDrainReagent(ritualStone, ReagentRegistry.praesidiumReagent, praesidiumDrain, false);

        int range = 15 * (hasPraesidium ? 3 : 1);
        int vertRange = 15 * (hasPraesidium ? 3 : 1);

        List<EntityLivingBase> list = SpellHelper.getLivingEntitiesInRange(world, x + 0.5, y + 0.5, z + 0.5, range, vertRange);
        int entityCount = 0;

        for(EntityLivingBase livingEntity : list)
        {
            if (livingEntity instanceof EntityPlayer)
            {
                entityCount += 10;
            }
            else
            {
                entityCount++;
            }
        }

        boolean hasVirtus = canDrainReagent(ritualStone, ReagentRegistry.virtusReagent, virtusDrain, false);

        int cost = getCostPerRefresh() * (hasVirtus ? 3 : 1);
        int potency = hasVirtus ? 1 : 0;

        if (currentEssence < cost * entityCount)
        {
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        }
        else
        {
            entityCount = 0;

            boolean hasReductus = canDrainReagent(ritualStone, ReagentRegistry.reductusReagent, reductusDrain, false);

            for (EntityLivingBase livingEntity : list)
            {
                hasReductus = hasReductus && canDrainReagent(ritualStone, ReagentRegistry.reductusReagent, reductusDrain, false);
                if (hasReductus && !(livingEntity instanceof EntityPlayer))
                {
                    continue;
                }

                PotionEffect effect = livingEntity.getActivePotionEffect(Potion.wither);
                if (effect == null || (effect != null && effect.getAmplifier() <= potency && effect.getDuration() <= timeDelay))
                {
                    if (!hasVirtus || (canDrainReagent(ritualStone, ReagentRegistry.virtusReagent, virtusDrain, false)))
                    {
                        livingEntity.addPotionEffect(new PotionEffect(Potion.wither.id, timeDelay + 2, potency));
                        if (hasReductus)
                        {
                            canDrainReagent(ritualStone, ReagentRegistry.reductusReagent, reductusDrain, true);
                        }
                        if (hasVirtus)
                        {
                            canDrainReagent(ritualStone, ReagentRegistry.virtusReagent, virtusDrain, true);
                        }

                        if (livingEntity instanceof EntityPlayer)
                        {
                            entityCount += 10;
                        }
                        else
                        {
                            entityCount++;
                        }
                    }
                }
            }

            if(entityCount > 0)
            {
                if (hasPraesidium)
                {
                    canDrainReagent(ritualStone, ReagentRegistry.praesidiumReagent, praesidiumDrain, true);
                }
                data.currentEssence = currentEssence - cost * entityCount;
                data.markDirty();
            }
        }
    }

    @Override
    public int getCostPerRefresh()
    {
        return 30;
    }

    @Override
    public List<RitualComponent> getRitualComponentList()
    {
        ArrayList<RitualComponent> witherRitual = new ArrayList();
        witherRitual.add(new RitualComponent(1, 0, -1, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(-1, 0, -1, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(-1, 0, 1, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(1, 0, 1, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(0, 0, -2, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(0, 0, 2, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(2, 0, 0, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(-2, 0, 0, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(1, 0, -3, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(1, 0, 3, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(3, 0, 1, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(3, 0, -1, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(-3, 0, -1, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(-3, 0, 1, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(-1, 0, -3, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(-1, 0, 3, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(1, 3, 0, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(-1, 3, 0, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(0, 3, -1, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(-2, 3, 2, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(-2, 3, -2, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(2, 3, 2, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(2, 3, -2, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(0, 3, 1, RitualComponent.FIRE));
        witherRitual.add(new RitualComponent(4, 1, 0, RitualComponent.AIR));
        witherRitual.add(new RitualComponent(0, 1, -4, RitualComponent.AIR));
        witherRitual.add(new RitualComponent(-4, 1, 0, RitualComponent.AIR));
        witherRitual.add(new RitualComponent(0, 1, 4, RitualComponent.AIR));
        witherRitual.add(new RitualComponent(4, 0, -4, RitualComponent.EARTH));
        witherRitual.add(new RitualComponent(-4, 0, -4, RitualComponent.EARTH));
        witherRitual.add(new RitualComponent(4, 0, 4, RitualComponent.EARTH));
        witherRitual.add(new RitualComponent(-4, 0, 4, RitualComponent.EARTH));
        witherRitual.add(new RitualComponent(-3, 1, 3, RitualComponent.AIR));
        witherRitual.add(new RitualComponent(3, 1, -3, RitualComponent.AIR));
        witherRitual.add(new RitualComponent(3, 1, 3, RitualComponent.AIR));
        witherRitual.add(new RitualComponent(-3, 1, -3, RitualComponent.AIR));
        witherRitual.add(new RitualComponent(3, 2, 0, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(-3, 2, 0, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(0, 2, -3, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(0, 2, 3, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(3, 2, -3, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(3, 2, 3, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(-3, 2, -3, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(-3, 2, 3, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(3, 0, -4, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(3, 0, 4, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(4, 0, -3, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(4, 0, 3, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(-3, 0, 4, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(-3, 0, -4, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(-4, 0, 3, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(-4, 0, -3, RitualComponent.WATER));
        witherRitual.add(new RitualComponent(-5, 0, 0, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(0, 0, 5, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(0, 0, -5, RitualComponent.DUSK));
        witherRitual.add(new RitualComponent(5, 0, 0, RitualComponent.DUSK));
        return witherRitual;
    }
}
