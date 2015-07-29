package com.arc.bloodarsenal.common.rituals;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RitualEffectMobDisable extends RitualEffect
{
    @Override
    public void performEffect(IMasterRitualStone ritualStone)
    {
        String owner = ritualStone.getOwner();

        int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();

        final int potentiaDrain = 10;
        final int offensaDrain = 20;

        boolean hasPotentia = this.canDrainReagent(ritualStone, ReagentRegistry.potentiaReagent, potentiaDrain, false);
        boolean hasOffensa = this.canDrainReagent(ritualStone, ReagentRegistry.offensaReagent, offensaDrain, false);

        int d0 = 10;
        int vertRange = hasPotentia ? 20 : 10;
        AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox((double) x, (double) y, (double) z, (double) (x + 1), (double) (y + 1), (double) (z + 1)).expand(d0, vertRange, d0);
        List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

        if (currentEssence < this.getCostPerRefresh() * list.size())
        {
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        }
        else
        {
            for (EntityLivingBase livingEntity : list)
            {
                if (!livingEntity.isEntityAlive() || livingEntity instanceof EntityPlayer || AlchemicalWizardry.wellBlacklist.contains(livingEntity.getClass()))
                {
                    continue;
                }

                Collection<PotionEffect> potionEffects = livingEntity.getActivePotionEffects();
                if (potionEffects.isEmpty())
                {
                    if (hasOffensa) livingEntity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5, 1));
                    livingEntity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5, 9));
                    livingEntity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5, 4));
                }
                else
                {
                    if (hasOffensa)
                    {
                        if (!potionEffects.contains(new PotionEffect(Potion.weakness.id, 5, 1))) livingEntity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5, 1));
                    }

                    if (!potionEffects.contains(new PotionEffect(Potion.moveSlowdown.id, 5, 9))) livingEntity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5, 9));
                    if (!potionEffects.contains(new PotionEffect(Potion.digSlowdown.id, 5, 4))) livingEntity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5, 4));
                }

                if (livingEntity instanceof EntityEnderman)
                {
                    if (!potionEffects.contains(new PotionEffect(AlchemicalWizardry.customPotionPlanarBindingID, 5, 0))) livingEntity.addPotionEffect(new PotionEffect(AlchemicalWizardry.customPotionPlanarBindingID, 5, 0));
                }
                else if (livingEntity instanceof EntityCreeper)
                {
                    if (((EntityCreeper) livingEntity).getCreeperState() > -1)
                    {
                        ((EntityCreeper) livingEntity).setCreeperState(-1);
                    }
                }
            }
        }
    }

    @Override
    public int getCostPerRefresh()
    {
        return 2000;
    }

    @Override
    public List<RitualComponent> getRitualComponentList()
    {
        ArrayList<RitualComponent> mobDisableRitual = new ArrayList<RitualComponent>();
        mobDisableRitual.add(new RitualComponent(1, 0, 0, RitualComponent.EARTH));
        mobDisableRitual.add(new RitualComponent(0, 0, 1, RitualComponent.EARTH));
        mobDisableRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.EARTH));
        mobDisableRitual.add(new RitualComponent(0, 0, -1, RitualComponent.EARTH));
        mobDisableRitual.add(new RitualComponent(1, 0, 1, RitualComponent.DUSK));
        mobDisableRitual.add(new RitualComponent(-1, 0, 1, RitualComponent.DUSK));
        mobDisableRitual.add(new RitualComponent(-1, 0, -1, RitualComponent.DUSK));
        mobDisableRitual.add(new RitualComponent(1, 0, -1, RitualComponent.DUSK));
        mobDisableRitual.add(new RitualComponent(2, 0, 0, RitualComponent.WATER));
        mobDisableRitual.add(new RitualComponent(0, 0, 2, RitualComponent.WATER));
        mobDisableRitual.add(new RitualComponent(-2, 0, 0, RitualComponent.WATER));
        mobDisableRitual.add(new RitualComponent(0, 0, -2, RitualComponent.WATER));
        mobDisableRitual.add(new RitualComponent(2, 0, 1, RitualComponent.BLANK));
        mobDisableRitual.add(new RitualComponent(2, 0, -1, RitualComponent.BLANK));
        mobDisableRitual.add(new RitualComponent(1, 0, 2, RitualComponent.BLANK));
        mobDisableRitual.add(new RitualComponent(-1, 0, 2, RitualComponent.BLANK));
        mobDisableRitual.add(new RitualComponent(-2, 0, 1, RitualComponent.BLANK));
        mobDisableRitual.add(new RitualComponent(-2, 0, -1, RitualComponent.BLANK));
        mobDisableRitual.add(new RitualComponent(1, 0, -2, RitualComponent.BLANK));
        mobDisableRitual.add(new RitualComponent(-1, 0, -2, RitualComponent.BLANK));
        mobDisableRitual.add(new RitualComponent(3, 0, 3, RitualComponent.AIR));
        mobDisableRitual.add(new RitualComponent(3, 0, -3, RitualComponent.AIR));
        mobDisableRitual.add(new RitualComponent(-3, 0, 3, RitualComponent.AIR));
        mobDisableRitual.add(new RitualComponent(-3, 0, -3, RitualComponent.AIR));
        return mobDisableRitual;
    }
}
