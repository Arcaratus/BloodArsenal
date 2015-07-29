package com.arc.bloodarsenal.common.rituals;

import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.FishingHooks;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RitualEffectFishing extends RitualEffect
{
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

        final int terraeDrain = 20;
        final int orbisTerraeDrain = 20;
        final int potentiaDrain = 20;

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
            boolean hasTerrae = canDrainReagent(ritualStone, ReagentRegistry.terraeReagent, terraeDrain, true);
            boolean hasOrbisTerrae = canDrainReagent(ritualStone, ReagentRegistry.orbisTerraeReagent, orbisTerraeDrain, true);
            boolean hasPotentia = canDrainReagent(ritualStone, ReagentRegistry.potentiaReagent, potentiaDrain, true);

            int i = hasTerrae ? -5 : -1;
            int j = i;

            for (; hasTerrae ? i <= 5 : i <= 1; i++)
            {
                for (; j <= 1; j++)
                {
                    for (int k = 1; k <= 5; k++)
                    {
                        Block block = world.getBlock(x + i, y - k, z + j);
                        Random rand = new Random();
                        float chance = hasOrbisTerrae ? 0.8F : rand.nextInt(2);

                        if (block.isAssociatedBlock(Blocks.water) || block.isAssociatedBlock(Blocks.flowing_water))
                        {
                            if (rand.nextInt(9) >= (hasPotentia ? 6 : 8))
                            {
                                ItemStack fishStack = FishingHooks.getRandomFishable(rand, chance, hasOrbisTerrae ? rand.nextInt(90) : -1, hasPotentia ? 2 : -1);
                                if (fishStack != null)
                                {
                                    SpellHelper.insertStackIntoInventory(fishStack, tileEntity, ForgeDirection.DOWN);
                                    SoulNetworkHandler.syphonFromNetwork(owner, 500);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getCostPerRefresh()
    {
        return 1000;
    }

    @Override
    public List<RitualComponent> getRitualComponentList()
    {
        ArrayList<RitualComponent> fishingRitual = new ArrayList<RitualComponent>();
        fishingRitual.add(new RitualComponent(1, 0, 0, RitualComponent.WATER));
        fishingRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.WATER));
        fishingRitual.add(new RitualComponent(0, 0, 1, RitualComponent.WATER));
        fishingRitual.add(new RitualComponent(0, 0, -1, RitualComponent.WATER));
        fishingRitual.add(new RitualComponent(-1, 0, 1, RitualComponent.EARTH));
        fishingRitual.add(new RitualComponent(1, 0, 1, RitualComponent.EARTH));
        fishingRitual.add(new RitualComponent(-1, 0, -1, RitualComponent.EARTH));
        fishingRitual.add(new RitualComponent(1, 0, -1, RitualComponent.EARTH));
        fishingRitual.add(new RitualComponent(1, 1, 0, RitualComponent.AIR));
        fishingRitual.add(new RitualComponent(-1, 1, 0, RitualComponent.AIR));
        fishingRitual.add(new RitualComponent(0, 1, 1, RitualComponent.AIR));
        fishingRitual.add(new RitualComponent(0, 1, -1, RitualComponent.AIR));
        fishingRitual.add(new RitualComponent(2, 0, 2, RitualComponent.WATER));
        fishingRitual.add(new RitualComponent(2, 0, -2, RitualComponent.WATER));
        fishingRitual.add(new RitualComponent(-2, 0, 2, RitualComponent.WATER));
        fishingRitual.add(new RitualComponent(-2, 0, -2, RitualComponent.WATER));
        fishingRitual.add(new RitualComponent(2, 0, 0, RitualComponent.BLANK));
        fishingRitual.add(new RitualComponent(-2, 0, 0, RitualComponent.BLANK));
        fishingRitual.add(new RitualComponent(0, 0, 2, RitualComponent.BLANK));
        fishingRitual.add(new RitualComponent(0, 0, -2, RitualComponent.BLANK));
        return fishingRitual;
    }
}
