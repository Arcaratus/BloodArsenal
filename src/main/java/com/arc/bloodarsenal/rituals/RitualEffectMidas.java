package com.arc.bloodarsenal.rituals;

import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RitualEffectMidas extends RitualEffect
{
    public static double rand;

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

        if (currentEssence < getCostPerRefresh())
        {
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        }
        else
        {
            if (ritualStone.getVar1() == 0)
            {
                int d0 = 0;
                AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox((double) x, (double) y + 1, (double) z, (double) (x + 1), (double) (y + 2), (double) (z + 1)).expand(d0, d0, d0);
                List list = world.getEntitiesWithinAABB(EntityItem.class, axisalignedbb);
                Iterator iterator = list.iterator();
                EntityItem item;

                while (iterator.hasNext())
                {
                    item = (EntityItem) iterator.next();
                    ItemStack itemStack = item.getEntityItem();

                    if (itemStack == null)
                    {
                        continue;
                    }

                    if (itemStack.getItem() == Items.iron_ingot)
                    {
                        item.setDead();
                        ritualStone.setVar1(+1);
                        world.addWeatherEffect(new EntityLightningBolt(world, x, y + 1, z));
                        ritualStone.setCooldown(ritualStone.getCooldown() - 1);
                        break;
                    }

                    if (world.rand.nextInt(10) == 0)
                    {
                        SpellHelper.sendIndexedParticleToAllAround(world, x, y, z, 20, world.provider.dimensionId, 1, x, y, z);
                    }
                }
                data.currentEssence = currentEssence - getCostPerRefresh();
                data.markDirty();
            }
            else
            {
                ritualStone.setCooldown(ritualStone.getCooldown() - 1);

                if (world.rand.nextInt(20) == 0)
                {
                    int lightningPoint = world.rand.nextInt(4);

                    switch (lightningPoint)
                    {
                        case 0:
                            world.addWeatherEffect(new EntityLightningBolt(world, x + 3, y + 6, z + 3));
                            break;

                        case 1:
                            world.addWeatherEffect(new EntityLightningBolt(world, x + 3, y + 6, z - 3));
                            break;

                        case 2:
                            world.addWeatherEffect(new EntityLightningBolt(world, x - 3, y + 6, z + 3));
                            break;

                        case 3:
                            world.addWeatherEffect(new EntityLightningBolt(world, x - 3, y + 6, z - 3));
                            break;
                    }
                }

                if (ritualStone.getCooldown() <= 0)
                {
                    ItemStack spawnedItem = new ItemStack(Items.gold_ingot);
                    rand = Math.random();

                    if (rand + 0.5F > 0.6F)
                    {
                        if (spawnedItem != null)
                        {
                            EntityItem newItem = new EntityItem(world, x + 0.5, y + 1, z + 0.5, spawnedItem.copy());
                            world.spawnEntityInWorld(newItem);
                        }
                    }
                    ritualStone.setActive(false);
                }
            }
        }
    }

    @Override
    public int getCostPerRefresh()
    {
        return 0;
    }

    @Override
    public int getInitialCooldown()
    {
        return 100;
    }

    @Override
    public List<RitualComponent> getRitualComponentList()
    {
        ArrayList<RitualComponent> midasRitual = new ArrayList();
        midasRitual.add(new RitualComponent(1, 0, 0, RitualComponent.EARTH));
        midasRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.EARTH));
        midasRitual.add(new RitualComponent(0, 0, 1, RitualComponent.EARTH));
        midasRitual.add(new RitualComponent(0, 0, -1, RitualComponent.EARTH));
        midasRitual.add(new RitualComponent(1, 0, 1, RitualComponent.AIR));
        midasRitual.add(new RitualComponent(1, 0, -1, RitualComponent.AIR));
        midasRitual.add(new RitualComponent(-1, 0, 1, RitualComponent.AIR));
        midasRitual.add(new RitualComponent(-1, 0, -1, RitualComponent.AIR));
        midasRitual.add(new RitualComponent(1, 3, 1, RitualComponent.FIRE));
        midasRitual.add(new RitualComponent(1, 3, -1, RitualComponent.FIRE));
        midasRitual.add(new RitualComponent(-1, 3, 1, RitualComponent.FIRE));
        midasRitual.add(new RitualComponent(-1, 3, -1, RitualComponent.FIRE));
        midasRitual.add(new RitualComponent(2, 1, 2, RitualComponent.WATER));
        midasRitual.add(new RitualComponent(2, 1, -2, RitualComponent.WATER));
        midasRitual.add(new RitualComponent(-2, 1, 2, RitualComponent.WATER));
        midasRitual.add(new RitualComponent(-2, 1, -2, RitualComponent.WATER));
        midasRitual.add(new RitualComponent(2, 2, 2, RitualComponent.EARTH));
        midasRitual.add(new RitualComponent(2, 2, -2, RitualComponent.EARTH));
        midasRitual.add(new RitualComponent(-2, 2, 2, RitualComponent.EARTH));
        midasRitual.add(new RitualComponent(-2, 2, -2, RitualComponent.EARTH));
        midasRitual.add(new RitualComponent(3, 4, 3, RitualComponent.DUSK));
        midasRitual.add(new RitualComponent(3, 4, -3, RitualComponent.DUSK));
        midasRitual.add(new RitualComponent(-3, 4, 3, RitualComponent.DUSK));
        midasRitual.add(new RitualComponent(-3, 4, -3, RitualComponent.DUSK));
        midasRitual.add(new RitualComponent(3, 5, 3, RitualComponent.DUSK));
        midasRitual.add(new RitualComponent(3, 5, -3, RitualComponent.DUSK));
        midasRitual.add(new RitualComponent(-3, 5, 3, RitualComponent.DUSK));
        midasRitual.add(new RitualComponent(-3, 5, -3, RitualComponent.DUSK));
        return midasRitual;
    }
}
