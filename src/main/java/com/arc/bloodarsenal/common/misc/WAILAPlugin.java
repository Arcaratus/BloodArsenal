package com.arc.bloodarsenal.common.misc;

import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import WayofTime.alchemicalWizardry.common.items.sigil.SigilDivination;
import WayofTime.alchemicalWizardry.common.items.sigil.SigilSeer;
import WayofTime.alchemicalWizardry.common.items.sigil.holding.SigilOfHolding;
import com.arc.bloodarsenal.common.block.BlockCompactedMRS;
import com.arc.bloodarsenal.common.block.BlockLPMaterializer;
import com.arc.bloodarsenal.common.block.BlockLifeInfuser;
import com.arc.bloodarsenal.common.block.BlockPortableAltar;
import com.arc.bloodarsenal.common.items.sigil.holding.SigilAugmentedHolding;
import com.arc.bloodarsenal.common.tileentity.TileCompactedMRS;
import com.arc.bloodarsenal.common.tileentity.TileLPMaterializer;
import com.arc.bloodarsenal.common.tileentity.TileLifeInfuser;
import com.arc.bloodarsenal.common.tileentity.TilePortableAltar;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class WAILAPlugin implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor data, IWailaConfigHandler cfg)
    {
        return data.getStack();
    }

    @Override
    public List<String> getWailaHead(ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg)
    {
        return tip;
    }

    @Override
    public List<String> getWailaBody(ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg)
    {
        if (data.getTileEntity() != null)
        {
            if (data.getTileEntity() instanceof TilePortableAltar)
            {
                EntityPlayer player = data.getPlayer();
                NBTTagCompound tagCompound = data.getNBTData();
                EnumChatFormatting red = EnumChatFormatting.RED;
                //Basic
                int currentEssence = tagCompound.getInteger("currentEssence");
                int tier = tagCompound.getInteger("upgradeLevel");
                int capacity = tagCompound.getInteger("capacity");
                //Extended
                TilePortableAltar altar = (TilePortableAltar) data.getTileEntity();

                if (player.getCurrentEquippedItem() != null)
                {
                    if (player.getCurrentEquippedItem().getItem() instanceof SigilDivination || player.getCurrentEquippedItem().getItem() instanceof SigilSeer)
                    {
                        tip.add(StatCollector.translateToLocal("tooltip.currentEssence") + " " + red + currentEssence);
                        tip.add(StatCollector.translateToLocal("tooltip.altarTier") + " " + red + tier);
                        tip.add(StatCollector.translateToLocal("tooltip.capacity") + " " + red + capacity);

                        addTonsOfInfo(player, altar, tip, cfg);
                    }
                    else if (player.getCurrentEquippedItem().getItem() instanceof SigilOfHolding)
                    {
                        if (SigilOfHolding.getCurrentSigil(player.getCurrentEquippedItem()).getItem() instanceof SigilDivination || SigilOfHolding.getCurrentSigil(player.getCurrentEquippedItem()).getItem() instanceof SigilSeer)
                        {
                            tip.add(StatCollector.translateToLocal("tooltip.currentEssence") + " " + red + currentEssence);
                            tip.add(StatCollector.translateToLocal("tooltip.altarTier") + " " + red + tier);
                            tip.add(StatCollector.translateToLocal("tooltip.capacity") + " " + red + capacity);
                        }

                        addTonsOfInfo(player, altar, tip, cfg);
                    }
                    else if (player.getCurrentEquippedItem().getItem() instanceof SigilAugmentedHolding)
                    {
                        if (SigilAugmentedHolding.getCurrentSigil(player.getCurrentEquippedItem()).getItem() instanceof SigilDivination || SigilAugmentedHolding.getCurrentSigil(player.getCurrentEquippedItem()).getItem() instanceof SigilSeer)
                        {
                            tip.add(StatCollector.translateToLocal("tooltip.currentEssence") + " " + red + currentEssence);
                            tip.add(StatCollector.translateToLocal("tooltip.altarTier") + " " + red + tier);
                            tip.add(StatCollector.translateToLocal("tooltip.capacity") + " " + red + capacity);
                        }

                        addTonsOfInfo(player, altar, tip, cfg);
                    }
                }
            }
            else if (data.getTileEntity() instanceof TileCompactedMRS)
            {
                EnumChatFormatting red = EnumChatFormatting.RED;
                NBTTagCompound tagCompound = data.getNBTData();

                boolean isRunning = tagCompound.getBoolean("isRunning");
                String ritualID = tagCompound.getString("ritualName");
                String ritualName = Rituals.getNameOfRitual(ritualID);
                int direction = tagCompound.getInteger("direction");

                tip.add(StatCollector.translateToLocal("tooltip.ritualName") + " " + red + ritualName);
                tip.add(StatCollector.translateToLocal("tooltip.running") + " " + red + (isRunning ? StatCollector.translateToLocal("tooltip.yes") : StatCollector.translateToLocal("tooltip.no")));
                tip.add(StatCollector.translateToLocal("tooltip.direction") + " " + red + direction);
            }
            else if (data.getTileEntity() instanceof TileLifeInfuser)
            {
                EnumChatFormatting red = EnumChatFormatting.RED;
//                NBTTagCompound tagCompound = data.getNBTData();

//                boolean isRunning = tagCompound.getBoolean("isRunning");
                int currentEssence = ((TileLifeInfuser) data.getTileEntity()).getFluidAmount();

                tip.add(StatCollector.translateToLocal("message.tile.contains") + " " + red + currentEssence);
//                tip.add(StatCollector.translateToLocal("tooltip.running") + " " + red + (isRunning ? StatCollector.translateToLocal("tooltip.yes") : StatCollector.translateToLocal("tooltip.no")));
            }
            else if (data.getTileEntity() instanceof TileLPMaterializer)
            {
                EnumChatFormatting red = EnumChatFormatting.RED;

                int currentEssence = ((TileLPMaterializer) data.getTileEntity()).getFluidAmount();

                tip.add(StatCollector.translateToLocal("message.tile.contains") + " " + red + currentEssence);
            }
        }

        return tip;
    }

    protected void addTonsOfInfo(EntityPlayer player, TilePortableAltar altar, List<String> tip, IWailaConfigHandler cfg)
    {
        int speedUpgrades = altar.speedUpgrades;
        int efficiencyUpgrades = altar.efficiencyUpgrades;
        int sacrificeUpgrades = altar.sacrificeUpgrades;
        int selfSacrificeUpgrades = altar.selfSacrificeUpgrades;
        int displacementUpgrades = altar.displacementUpgrades;
        int altarCapacitiveUpgrades = altar.altarCapacitiveUpgrades;
        int orbCapacitiveUpgrades = altar.orbCapacitiveUpgrades;
        int betterCapacitiveUpgrades = altar.betterCapacitiveUpgrades;
        int accelerationUpgrades = altar.accelerationUpgrades;

        if (cfg.getConfig(showExtendedTooltip) && player.isSneaking())
        {
            tip.add(StatCollector.translateToLocal("tooltip.speedUpgrades") + " " + speedUpgrades);
            tip.add(StatCollector.translateToLocal("tooltip.efficiencyUpgrades") + " " + efficiencyUpgrades);
            tip.add(StatCollector.translateToLocal("tooltip.sacrificeUpgrades") + " " + sacrificeUpgrades);
            tip.add(StatCollector.translateToLocal("tooltip.selfSacrificeUpgrades") + " " + selfSacrificeUpgrades);
            tip.add(StatCollector.translateToLocal("tooltip.displacementUpgrades") + " " + displacementUpgrades);
            tip.add(StatCollector.translateToLocal("tooltip.altarCapacitiveUpgrades") + " " + altarCapacitiveUpgrades);
            tip.add(StatCollector.translateToLocal("tooltip.orbCapacitiveUpgrades") + " " + orbCapacitiveUpgrades);
            tip.add(StatCollector.translateToLocal("tooltip.betterCapacitiveUpgrades") + " " + betterCapacitiveUpgrades);
            tip.add(StatCollector.translateToLocal("tooltip.accelerationUpgrades") + " " + accelerationUpgrades);
        }
    }

    @Override
    public List<String> getWailaTail(ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg)
    {
        return tip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tileEntity, NBTTagCompound tag, World world, int x, int y, int z)
    {
        if (tileEntity != null)
        {
            tileEntity.writeToNBT(tag);
        }

        return tag;
    }

    private static String showExtendedTooltip = "bloodarsenal.showExtendedAltarTooltip";

    public static void registerAddon(IWailaRegistrar register)
    {
        WAILAPlugin dataProvider = new WAILAPlugin();

        register.addConfig("BloodArsenal", showExtendedTooltip, false);
        register.registerBodyProvider(dataProvider, BlockPortableAltar.class);
        register.registerNBTProvider(dataProvider, BlockPortableAltar.class);

        register.registerBodyProvider(dataProvider, BlockCompactedMRS.class);
        register.registerNBTProvider(dataProvider, BlockCompactedMRS.class);

        register.registerBodyProvider(dataProvider, BlockLifeInfuser.class);
        register.registerNBTProvider(dataProvider, BlockLifeInfuser.class);

        register.registerBodyProvider(dataProvider, BlockLPMaterializer.class);
        register.registerBodyProvider(dataProvider, BlockLPMaterializer.class);
    }
}
