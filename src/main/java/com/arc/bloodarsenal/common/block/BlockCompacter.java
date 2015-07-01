package com.arc.bloodarsenal.common.block;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBloodOrb;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.block.BlockAltar;
import WayofTime.alchemicalWizardry.common.block.BlockMasterStone;
import WayofTime.alchemicalWizardry.common.bloodAltarUpgrade.AltarComponent;
import WayofTime.alchemicalWizardry.common.bloodAltarUpgrade.AltarUpgradeComponent;
import WayofTime.alchemicalWizardry.common.bloodAltarUpgrade.UpgradedAltars;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import WayofTime.alchemicalWizardry.common.tileEntity.TEMasterStone;
import com.arc.bloodarsenal.common.tileentity.TileCompactedMRS;
import com.arc.bloodarsenal.common.tileentity.TileCompacter;
import com.arc.bloodarsenal.common.tileentity.TilePortableAltar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

import java.util.List;

public class BlockCompacter extends BlockContainer
{
    private String ritualName;
    private int direction;

    private int tier;
    private int lpAmount;
    private int capacity;
    private float consumptionMultiplier;
    private float efficiencyMultiplier;
    private float sacrificeEfficiencyMultiplier;
    private float selfSacrificeEfficiencyMultiplier;
    private float capacityMultiplier;
    private float orbCapacityMultiplier;
    private float dislocationMultiplier;
    private int accelerationUpgrades;
    private int bufferCapacity;

    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;

    public BlockCompacter()
    {
        super(Material.iron);
        setHardness(8.0F);
        setResistance(5.0F);
        setStepSound(soundTypeMetal);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are)
    {
        world.markBlockForUpdate(x, y, z);
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity != null && !player.isSneaking())
        {
            ItemStack playerItem = player.getCurrentEquippedItem();

            if (playerItem == null)
            {
                return false;
            }
            else
            {
                Item item = playerItem.getItem();

                if (!(item instanceof IBloodOrb))
                {
                    return false;
                }
                else
                {
                    Block block = world.getBlock(x, y + 1, z);

                    if (block != null)
                    {
                        TileEntity tile;

                        world.markBlockForUpdate(x, y, z);
                        world.markBlockForUpdate(x, y + 1, z);

                        String owner = player.getCommandSenderName();
                        int currentEssence = SoulNetworkHandler.getCurrentEssence(owner);

                        if (block instanceof BlockMasterStone)
                        {
                            tile = world.getTileEntity(x, y + 1, z);

                            checkRitual(tile);

                            List<RitualComponent> ritualList = Rituals.getRitualList(ritualName);

                            if (ritualList == null)
                            {
                                return false;
                            }
                            else
                            {
                                float multiplier = 1;

                                for (RitualComponent ritualComponent : ritualList)
                                {
                                    int stoneType = ritualComponent.getStoneType();

                                    switch (stoneType)
                                    {
                                        case 0:
                                            break;

                                        case 1:
                                            multiplier = multiplier * 1.05F;
                                            break;

                                        case 2:
                                            multiplier = multiplier * 1.05F;
                                            break;

                                        case 3:
                                            multiplier = multiplier * 1.05F;
                                            break;

                                        case 4:
                                            multiplier = multiplier * 1.05F;
                                            break;

                                        case 5:
                                            multiplier = multiplier * 1.075F;
                                            break;
                                    }
                                }

                                int activationCost = Rituals.getCostForActivation(ritualName);
                                int cost = (activationCost * (int) multiplier) / 2; //Divided by 2 since this fires twice apparently and also because I'm nice

                                if (cost <= currentEssence)
                                {
                                    compactRitual(tile);
                                    SoulNetworkHandler.syphonFromNetwork(owner, cost);
                                    --player.inventory.getCurrentItem().stackSize;
                                }
                                else
                                {
                                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.compacter.notEnoughLP")));
                                    return true;
                                }
                            }

                            return true;
                        }
                        else if (block instanceof BlockAltar && world.getTileEntity(x, y + 1, z) != null)
                        {
                            tile = world.getTileEntity(x, y + 1, z);

                            TEAltar altar = (TEAltar) tile;
                            checkAltar(altar);

                            if (tier == 0)
                            {
                                return false;
                            }
                            else if (altar.getStackInSlot(0) != null)
                            {
                                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.compacter.itemInAltar")));
                            }
                            else
                            {
                                float multiplier = 1;

                                AltarUpgradeComponent upgradeComponent = UpgradedAltars.getUpgrades(world, x, y + 1, z, tier);

                                int speedUpgrades = upgradeComponent.getSpeedUpgrades();
                                int efficiencyUpgrades = upgradeComponent.getEfficiencyUpgrades();
                                int sacrificeUpgrades = upgradeComponent.getSacrificeUpgrades();
                                int selfSacrificeUpgrades = upgradeComponent.getSelfSacrificeUpgrades();
                                int displacementUpgrades = upgradeComponent.getDisplacementUpgrades();
                                int altarCapacitiveUpgrades = upgradeComponent.getAltarCapacitiveUpgrades();
                                int orbCapacitiveUpgrades = upgradeComponent.getOrbCapacitiveUpgrades();
                                int betterCapacitiveUpgrades = upgradeComponent.getBetterCapacitiveUpgrades();
                                int accelerationUpgrades = upgradeComponent.getAccelerationUpgrades();

                                multiplier = multiplier * ((speedUpgrades + 4) / 4);
                                multiplier = multiplier * ((efficiencyUpgrades + 3) / 3);
                                multiplier = multiplier * ((sacrificeUpgrades + 3) / 3);
                                multiplier = multiplier * ((selfSacrificeUpgrades + 3) / 3);
                                multiplier = multiplier * ((displacementUpgrades + 3) / 3);
                                multiplier = multiplier * ((altarCapacitiveUpgrades + 3) / 3);
                                multiplier = multiplier * ((orbCapacitiveUpgrades + 3) / 3);
                                multiplier = multiplier * ((betterCapacitiveUpgrades + 2) / 2);
                                multiplier = multiplier * ((accelerationUpgrades + 3) / 3);

                                IBloodOrb bloodOrb = (IBloodOrb) item;

                                if (bloodOrb.getOrbLevel() >= tier)
                                {
                                    int cost = ((tier * 10) * (int) (multiplier * 5)) + capacity;

                                    if (cost <= currentEssence)
                                    {
                                        compactAltar(tile);
                                        SoulNetworkHandler.syphonFromNetwork(owner, cost);
                                        --player.inventory.getCurrentItem().stackSize;
                                    }
                                    else
                                    {
                                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.compacter.notEnoughLP")));
                                    }
                                }
                                else
                                {
                                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.compacter.wrongOrbTier")));
                                }
                            }

                            return true;
                        }
                    }

                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 1 ? this.topIcon : (side == 0 ? this.blockIcon : this.sideIcon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
        this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.sideIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileCompacter();
    }

    private void checkRitual(TileEntity tileEntity)
    {
        TEMasterStone masterStone = (TEMasterStone) tileEntity;
        World world = masterStone.getWorld();
        int x = masterStone.xCoord;
        int y = masterStone.yCoord;
        int z = masterStone.zCoord;

        ritualName = Rituals.checkValidRitual(world, x, y, z);

        if (ritualName.equals(""))
        {
            return;
        }
        else
        {
            direction = Rituals.getDirectionOfRitual(world, x, y, z, ritualName);
        }

        world.markBlockForUpdate(x, y, z);
        world.markBlockForUpdate(x, y + 1, z);
    }

    private void compactRitual(TileEntity tileEntity)
    {
        TEMasterStone masterStone = (TEMasterStone) tileEntity;
        World world = masterStone.getWorld();
        int x = masterStone.xCoord;
        int y = masterStone.yCoord;
        int z = masterStone.zCoord;

        List<RitualComponent> ritualList = Rituals.getRitualList(ritualName);

        if (ritualList == null)
        {
            return;
        }

        for (RitualComponent ritualComponent : ritualList)
        {
            world.setBlockToAir(x + ritualComponent.getX(direction), y + ritualComponent.getY(), z + ritualComponent.getZ(direction));
            world.setBlockToAir(x, y, z);
            world.markBlockForUpdate(x + ritualComponent.getX(direction), y + ritualComponent.getY(), z + ritualComponent.getZ(direction));
        }

        world.setBlock(x, y, z, ModBlocks.compacted_mrs);
        Block block = world.getBlock(x, y, z);
        configureMRS(world, x, y, z, block);
        world.markBlockForUpdate(x, y, z);
    }

    private void configureMRS(World world, int x, int y, int z, Block block)
    {
        if (block instanceof BlockCompactedMRS)
        {
            if (world.getBlock(x, y, z) == block)
            {
                TileCompactedMRS tile = (TileCompactedMRS) world.getTileEntity(x, y, z);

                tile.setRitualName(ritualName);
                world.markBlockForUpdate(x, y, z);
                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z));
            }
        }
    }

    private void checkAltar(TEAltar altar)
    {
        World world = altar.getWorldObj();
        int x = altar.xCoord;
        int y = altar.yCoord;
        int z = altar.zCoord;

        tier = altar.getTier();
        if (tier == 0)
        {
            return;
        }

        AltarUpgradeComponent upgrades = UpgradedAltars.getUpgrades(world, x, y, z, tier);

        if (upgrades == null)
        {
            this.consumptionMultiplier = 0;
            this.efficiencyMultiplier = 1;
            this.sacrificeEfficiencyMultiplier = 0;
            this.selfSacrificeEfficiencyMultiplier = 0;
            this.capacityMultiplier = 1;
            this.orbCapacityMultiplier = 1;
            this.dislocationMultiplier = 1;
            this.accelerationUpgrades = 0;
            return;
        }

        lpAmount = altar.getFluidAmount();
        consumptionMultiplier = (float) (0.20 * upgrades.getSpeedUpgrades());
        efficiencyMultiplier = (float) Math.pow(0.85, upgrades.getSpeedUpgrades());
        sacrificeEfficiencyMultiplier = (float) (0.10 * upgrades.getSacrificeUpgrades());
        selfSacrificeEfficiencyMultiplier = (float) (0.10 * upgrades.getSelfSacrificeUpgrades());
        capacityMultiplier = (float) ((1 * Math.pow(1.10, upgrades.getBetterCapacitiveUpgrades()) + 0.20 * upgrades.getAltarCapacitiveUpgrades()));
        dislocationMultiplier = (float) (Math.pow(1.2, upgrades.getDisplacementUpgrades()));
        orbCapacityMultiplier = (float) (1 + 0.02 * upgrades.getOrbCapacitiveUpgrades());
        capacity = (int) (FluidContainerRegistry.BUCKET_VOLUME * 10 * capacityMultiplier);
        bufferCapacity = (int) (FluidContainerRegistry.BUCKET_VOLUME * 1 * capacityMultiplier);
        accelerationUpgrades = upgrades.getAccelerationUpgrades();

        world.markBlockForUpdate(x, y, z);
        world.markBlockForUpdate(x, y - 1, z);
    }

    private void compactAltar(TileEntity tileEntity)
    {
        TEAltar altar = (TEAltar) tileEntity;
        World world = altar.getWorldObj();
        int x = altar.xCoord;
        int y = altar.yCoord;
        int z = altar.zCoord;

        List<AltarComponent> altarComponents = UpgradedAltars.getAltarUpgradeListForTier(tier);

        if (altarComponents != null && !world.isRemote)
        {
            for (AltarComponent altarComponent : altarComponents)
            {
                world.setBlockToAir(x + altarComponent.getX(), y + altarComponent.getY(), z + altarComponent.getZ());
                world.setBlockToAir(x, y, z);
                world.markBlockForUpdate(x + altarComponent.getX(), y + altarComponent.getY(), z + altarComponent.getZ());
            }
        }

        world.setBlock(x, y, z, ModBlocks.portable_altar);
        Block altarBlock = world.getBlock(x, y, z);
        configureAltar(world, x, y, z, altarBlock);
        world.markBlockForUpdate(x, y, z);
        world.markBlockForUpdate(x, y - 1, z);
    }

    private void configureAltar(World world, int x, int y, int z, Block block)
    {
        if (block instanceof BlockPortableAltar)
        {
            if (world.getBlock(x, y, z) == block)
            {
                TilePortableAltar altar = (TilePortableAltar) world.getTileEntity(x, y, z);

                altar.setTier(tier);
                altar.setCurrentBlood(lpAmount);
                altar.setConsumptionMultiplier(consumptionMultiplier);
                altar.setEfficiencyMultiplier(efficiencyMultiplier);
                altar.setSacrificeMultiplier(sacrificeEfficiencyMultiplier);
                altar.setSelfSacrificeMultiplier(selfSacrificeEfficiencyMultiplier);
                altar.setOrbMultiplier(orbCapacityMultiplier);
                altar.setDislocationMultiplier(dislocationMultiplier);
                altar.setCapacityMultiplier(capacityMultiplier);
                altar.setCapacity(capacity);
                altar.setBufferCapacity(bufferCapacity);
                altar.setAccelerationUpgrades(accelerationUpgrades);

                world.markBlockForUpdate(x, y, z);

                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z));
            }
        }
    }
}
