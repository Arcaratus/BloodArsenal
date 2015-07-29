package com.arc.bloodarsenal.common.rituals;

import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import WayofTime.alchemicalWizardry.common.tileEntity.TEPedestal;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectEnchant extends RitualEffect
{
    private static int[][] PEDESTAL_LOCATIONS = new int[][] {{3, 1, 0}, {-3, 1, 0}, {0, 1, 3}, {0, 1, -3}};

    public int stage = 0;
    public int stageTicks = 0;
    public int stage3EndTicks = 0;

    int lpRequired = -1;

    public ItemStack enchantItem = null;
    public List<EnchantmentData> enchants = new ArrayList();
//    public List<ItemStack> itemList = new ArrayList();

    @Override
    public void performEffect(IMasterRitualStone ritualStone)
    {
        String owner = ritualStone.getOwner();
        World worldSave = MinecraftServer.getServer().worldServers[0];
        LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);

        if (stage == 0)
        {
            advanceStage();
        }

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
        EntityPlayer player = world.getPlayerEntityByName(owner);

        if (currentEssence < getCostPerRefresh())
        {
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        }
        else
        {
            TileEntity topEntity = world.getTileEntity(x, y + 1, z);

            if (!(topEntity instanceof TEAltar))
            {
                ritualStone.setActive(false);
                sendPlayerInformation(player, 1);
                return;
            }

            if (!canActivate(world, x, y, z))
            {
                ritualStone.setActive(false);
                sendPlayerInformation(player, 2);
                return;
            }

            TEAltar tileAltar = (TEAltar) topEntity;
            ItemStack targetStack = tileAltar.getStackInSlot(0);

            if (targetStack == null)
            {
                ritualStone.setActive(false);
                sendPlayerInformation(player, 3);
                return;
            }
            else
            {
                enchantItem = targetStack;
            }

            switch (stage)
            {
                case 1:
                {
                    if (stageTicks % 20 == 0)
                    {
                        ItemStack[] pedestalStack = getItemsInPedestals(world, x, y, z, true);

                        int count = pedestalStack.length;
                        boolean addedEnchantment = false;

                        if (count > 0 && !world.isRemote)
                        {
                            for (ItemStack itemStack : pedestalStack)
                            {
                                if (itemStack != null && itemStack.getItem() == Items.enchanted_book)
                                {
                                    NBTTagList enchants = Items.enchanted_book.func_92110_g(itemStack);

                                    if (enchants != null && enchants.tagCount() > 0)
                                    {
                                        NBTTagCompound enchant = enchants.getCompoundTagAt(0);
                                        short id = enchant.getShort("id");
                                        short lvl = enchant.getShort("lvl");

                                        if (!hasEnchantAlready(id) && isEnchantmentValid(id))
                                        {
                                            this.enchants.add(new EnchantmentData(id, lvl));
                                            addedEnchantment = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            sendPlayerInformation(player, 4);
                        }

                        if (!addedEnchantment)
                        {
                            if (enchants.isEmpty())
                            {
                                stage = 0;
                                ritualStone.setActive(false);
                            }
                            else
                            {
                                advanceStage();
                                world.markBlockForUpdate(x, y, z);
                            }
                        }


                    }

                    break;
                }

                case 2:
                {
                    if (lpRequired == -1)
                    {
                        lpRequired = 0;

                        for (EnchantmentData d : enchants)
                        {
                            Enchantment ench = Enchantment.enchantmentsList[d.enchant];
                            lpRequired += (int) (500F * ((15 - Math.min(15, ench.getWeight())) * 1.05F) * ((3F + d.level * d.level) * 0.25F) * (0.9F + enchants.size() * 0.05F));
                            player.addChatComponentMessage(new ChatComponentText("Lp required: " + lpRequired));
                            SoulNetworkHandler.syphonFromNetwork(owner, lpRequired);
                        }
                    }
                    else if (SoulNetworkHandler.getCurrentEssence(owner) >= lpRequired)
                    {
                        lpRequired = 0;
                        advanceStage();
                        world.markBlockForUpdate(x, y, z);
                    }
                    else
                    {
                        sendPlayerInformation(player, 5);
                        ritualStone.setActive(false);
                    }

                    break;
                }

                case 3:
                {
                    if (stageTicks >= 100)
                    {
                        for (EnchantmentData d : enchants)
                        {
                            if (EnchantmentHelper.getEnchantmentLevel(d.enchant, enchantItem) == 0)
                            {
                                enchantItem.addEnchantment(Enchantment.enchantmentsList[d.enchant], d.level);
                            }
                        }

                        enchants.clear();
                        lpRequired = -1;
                        advanceStage();
                        world.markBlockForUpdate(x, y, z);

                        world.addWeatherEffect(new EntityLightningBolt(world, x, y + 2, z));
                        world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y + 7, z + 1));
                        world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y + 7, z + 1));
                        world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y + 7, z - 1));
                        world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y + 7, z - 1));
                    }

                    break;
                }

                case 4:
                {
                    if (stageTicks >= 20)
                    {
                        advanceStage();
                        world.markBlockForUpdate(x, y, z);
                        ritualStone.setActive(false);
                    }

                    break;
                }
            }

            if (stage != 0)
            {
                stageTicks++;
            }
        }
    }

    public void advanceStage()
    {
        stage++;

        if (stage == 4)
        {
            stage3EndTicks = stageTicks;
        }
        else if (stage == 5)
        {
            stage = 0;
            stage3EndTicks = 0;
        }

        stageTicks = 0;
    }

    @Override
    public int getCostPerRefresh()
    {
        return 0;
    }

    @Override
    public List<RitualComponent> getRitualComponentList()
    {
        ArrayList<RitualComponent> enchantRitual = new ArrayList<RitualComponent>();
        enchantRitual.add(new RitualComponent(2, 0, 0, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(3, 0, 0, RitualComponent.DUSK));
        enchantRitual.add(new RitualComponent(3, 0, -1, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(3, 0, 1, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(4, 0, 0, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(-2, 0, 0, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(-3, 0, 0, RitualComponent.DUSK));
        enchantRitual.add(new RitualComponent(-3, 0, -1, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(-3, 0, 1, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(-4, 0, 0, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(0, 0, 2, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(0, 0, 3, RitualComponent.DUSK));
        enchantRitual.add(new RitualComponent(-1, 0, 3, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(1, 0, 3, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(0, 0, 4, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(0, 0, -2, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(0, 0, -3, RitualComponent.DUSK));
        enchantRitual.add(new RitualComponent(-1, 0, -3, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(1, 0, -3, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(0, 0, -4, RitualComponent.EARTH));
        enchantRitual.add(new RitualComponent(3, -1, 3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(3, -1, -3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(-3, -1, 3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(-3, -1, -3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(3, 0, 3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(3, 0, -3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(-3, 0, 3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(-3, 0, -3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(3, 1, 3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(3, 1, -3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(-3, 1, 3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(-3, 1, -3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(3, 2, 3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(3, 2, -3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(-3, 2, 3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(-3, 2, -3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(3, 3, 3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(3, 3, -3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(-3, 3, 3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(-3, 3, -3, RitualComponent.WATER));
        enchantRitual.add(new RitualComponent(3, 4, 3, RitualComponent.FIRE));
        enchantRitual.add(new RitualComponent(3, 4, -3, RitualComponent.FIRE));
        enchantRitual.add(new RitualComponent(-3, 4, 3, RitualComponent.FIRE));
        enchantRitual.add(new RitualComponent(-3, 4, -3, RitualComponent.FIRE));
        enchantRitual.add(new RitualComponent(3, 3, 2, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(3, 3, -2, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(-3, 3, 2, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(-3, 3, -2, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(2, 3, 3, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(2, 3, -3, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(-2, 3, 3, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(-2, 3, -3, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(3, 4, 1, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(3, 4, -1, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(-3, 4, 1, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(-3, 4, -1, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(1, 4, 3, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(1, 4, -3, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(-1, 4, 3, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(-1, 4, -3, RitualComponent.AIR));
        enchantRitual.add(new RitualComponent(2, 5, 2, RitualComponent.FIRE));
        enchantRitual.add(new RitualComponent(2, 5, -2, RitualComponent.FIRE));
        enchantRitual.add(new RitualComponent(-2, 5, 2, RitualComponent.FIRE));
        enchantRitual.add(new RitualComponent(-2, 5, -2, RitualComponent.FIRE));
        enchantRitual.add(new RitualComponent(1, 6, 1, RitualComponent.DUSK));
        enchantRitual.add(new RitualComponent(1, 6, -1, RitualComponent.DUSK));
        enchantRitual.add(new RitualComponent(-1, 6, 1, RitualComponent.DUSK));
        enchantRitual.add(new RitualComponent(-1, 6, -1, RitualComponent.DUSK));

        return enchantRitual;
    }

    public ItemStack[] getItemsInPedestals(World world, int x, int y, int z, boolean yes)
    {
        ItemStack[] items = new ItemStack[4];

        if (yes)
        {
            int i = 0;

            for (int[] pedestals : PEDESTAL_LOCATIONS)
            {
                if (i < 4)
                {
                    TileEntity tileEntity = world.getTileEntity(pedestals[0] + x, pedestals[1] + y, pedestals[2] + z);

                    if (tileEntity instanceof TEPedestal)
                    {
                        items[i] = ((TEPedestal) tileEntity).getStackInSlot(0);
                        i++;
                    }
                }
            }
        }
        else
        {
            int i = 0;

            for (int[] pedestals : PEDESTAL_LOCATIONS)
            {
                if (i < 4)
                {
                    TileEntity tileEntity = world.getTileEntity(pedestals[0] + x, pedestals[1] + y, pedestals[2] + z);

                    if (tileEntity instanceof TEPedestal)
                    {
                        items[i] = ((TEPedestal) tileEntity).getStackInSlot(0);
                        i++;
                    }
                }
            }
        }

        return items;
    }

    private static boolean canActivate(World world, int x, int y, int z)
    {
        for (int[] pedestals : PEDESTAL_LOCATIONS)
        {
            if (world.getBlock(pedestals[0] + x, pedestals[1] + y, pedestals[2] + z) != ModBlocks.blockPedestal)
            {
                return false;
            }
        }

        return true;
    }

    private boolean hasEnchantAlready(int enchant)
    {
        for (EnchantmentData data : enchants)
        {
            if (data.enchant == enchant)
            {
                return true;
            }
        }

        return false;
    }

    public boolean isEnchantmentValid(short id)
    {
        Enchantment ench = Enchantment.enchantmentsList[id];

        if (!ench.canApply(enchantItem) || !ench.type.canEnchantItem(enchantItem.getItem()))
        {
            return false;
        }

        for (EnchantmentData data : enchants)
        {
            Enchantment otherEnch = Enchantment.enchantmentsList[data.enchant];

            if (!otherEnch.canApplyTogether(ench) || !ench.canApplyTogether(otherEnch))
            {
                return false;
            }
        }

        return true;
    }

    private static void sendPlayerInformation(EntityPlayer player, int number)
    {
        switch (number)
        {
            case 1:
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.enchant.noAltar")));
                break;
            case 2:
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.enchant.noPedestals")));
                break;
            case 3:
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.enchant.noEnchantItem")));
                break;
            case 4:
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.enchant.noEnchantments")));
                break;
            case 5:
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.enchant.notEnoughLP")));
                break;
            case 6:
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("message.enchant.enchantIsNotValid")));
                break;
        }
    }

    private static class EnchantmentData
    {
        public int enchant, level;

        public EnchantmentData(int enchant, int level)
        {
            this.enchant = enchant;
            this.level = level;
        }
    }
}
