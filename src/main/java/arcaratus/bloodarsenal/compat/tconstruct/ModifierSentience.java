package arcaratus.bloodarsenal.compat.tconstruct;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.iface.ISentientSwordEffectProvider;
import WayofTime.bloodmagic.soul.*;
import WayofTime.bloodmagic.util.Constants;
import WayofTime.bloodmagic.util.helper.NBTHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.*;

import java.util.*;

@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
public class ModifierSentience extends ModifierTrait
{
    public static int[] soulBracket = new int[]{16, 60, 200, 400, 1000, 2000, 4000, 8000, 16000};
    public static double[] defaultDamageAdded = new double[]{1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5};
    public static double[] destructiveDamageAdded = new double[]{1.5, 2.25, 3, 3.75, 4.5, 5.25, 6, 6.75, 7.5};
    public static double[] vengefulDamageAdded = new double[]{0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4};
    public static double[] steadfastDamageAdded = new double[]{0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4};
    public static double[] defaultDigSpeedAdded = new double[]{1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5};
    public static double[] soulDrainPerSwing = new double[]{0.05, 0.1, 0.2, 0.4, 0.75, 1, 1.25, 1.5, 2};
    public static double[] soulDrop = new double[]{2, 4, 7, 10, 13, 15, 18, 20, 22};
    public static double[] staticDrop = new double[]{1, 1, 2, 3, 3, 4, 4, 5, 5};

    public static int[] absorptionTime = new int[]{200, 300, 400, 500, 600, 700, 800, 900, 1000};

    public static double maxAbsorptionHearts = 10;

    public static int[] poisonTime = new int[]{25, 50, 60, 80, 100, 120, 150, 170, 200};
    public static int[] poisonLevel = new int[]{0, 0, 0, 1, 1, 1, 1, 2, 2};

    public ModifierSentience()
    {
        super("sentience", 0x6BF6FE, 2, 1);
    }

    private int getLevel(ItemStack tool)
    {
        return ModifierNBT.readInteger(TinkerUtil.getModifierTag(tool, getModifierIdentifier())).level;
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            EnumDemonWillType type = PlayerDemonWillHandler.getLargestWillType(player);
            double will = PlayerDemonWillHandler.getTotalDemonWill(type, player);

            setCurrentType(tool, will > 0 ? type : EnumDemonWillType.DEFAULT);
            int willBracket = getWillLevel(will);

            double drain = willBracket >= 0 ? soulDrainPerSwing[willBracket] : 0;

            if (getLevel(tool) > 1)
            {
                ToolNBT toolData = TagUtil.getOriginalToolStats(tool.getTagCompound());
                float attack = toolData.attack;
                NBTTagCompound tag = TagUtil.getToolTag(tool.getTagCompound());
                attack += getExtraDamage(type, willBracket);
                tag.setFloat(Tags.ATTACK, attack);
            }

            setDrainOfActivatedSword(tool, drain);
            setStaticDropOfActivatedSword(tool, willBracket >= 0 ? staticDrop[willBracket] : 1);
            setDropOfActivatedSword(tool, willBracket >= 0 ? soulDrop[willBracket] : 0);
        }
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
    {
        if (getLevel(tool) > 1 && player instanceof EntityPlayer)
        {
            EntityPlayer attackerPlayer = (EntityPlayer) player;
            EnumDemonWillType type = getCurrentType(tool);
            double will = PlayerDemonWillHandler.getTotalDemonWill(type, attackerPlayer);
            int willBracket = getWillLevel(will);

            applyEffectToEntity(type, willBracket, target, attackerPlayer);

            ItemStack offStack = attackerPlayer.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
            if (offStack.getItem() instanceof ISentientSwordEffectProvider) {
                ISentientSwordEffectProvider provider = (ISentientSwordEffectProvider) offStack.getItem();
                if (provider.providesEffectForWill(type)) {
                    provider.applyOnHitEffect(type, tool, offStack, target, target);
                }
            }
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
    {
        if (getLevel(tool) > 1 && player instanceof EntityPlayer)
        {
            EntityPlayer actualPlayer = (EntityPlayer) player;
            double drain = getDrainOfActivatedSword(tool);
            if (drain > 0) {
                EnumDemonWillType type = getCurrentType(tool);
                double soulsRemaining = PlayerDemonWillHandler.getTotalDemonWill(type, actualPlayer);

                if (drain <= soulsRemaining) {
                    PlayerDemonWillHandler.consumeDemonWill(type, actualPlayer, drain);
                }
            }
        }
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event)
    {
        if (getLevel(tool) > 1)
        {
            float speed = ToolHelper.getActualMiningSpeed(tool);
            EnumDemonWillType type = getCurrentType(tool);
            double will = PlayerDemonWillHandler.getTotalDemonWill(type, event.getEntityPlayer());
            int willBracket = getWillLevel(will);
            speed += defaultDigSpeedAdded[willBracket];

            event.setNewSpeed(speed);
        }
    }

    private int getWillLevel(double soulsRemaining) {
        int lvl = 0;
        for (int i = 0; i < soulBracket.length; i++) {
            if (soulsRemaining >= soulBracket[i]) {
                lvl = i;
            }
        }

        return lvl;
    }

    public double getExtraDamage(EnumDemonWillType type, int willBracket) {
        if (willBracket < 0) {
            return 0;
        }

        switch (type) {
            case CORROSIVE:
            case DEFAULT:
                return defaultDamageAdded[willBracket];
            case DESTRUCTIVE:
                return destructiveDamageAdded[willBracket];
            case VENGEFUL:
                return vengefulDamageAdded[willBracket];
            case STEADFAST:
                return steadfastDamageAdded[willBracket];
        }

        return 0;
    }

    public void applyEffectToEntity(EnumDemonWillType type, int willBracket, EntityLivingBase target, EntityLivingBase attacker) {
        switch (type) {
            case CORROSIVE:
                target.addPotionEffect(new PotionEffect(MobEffects.WITHER, poisonTime[willBracket], poisonLevel[willBracket]));
                break;
            case DEFAULT:
                break;
            case DESTRUCTIVE:
                break;
            case STEADFAST:
                if (!target.isEntityAlive()) {
                    float absorption = attacker.getAbsorptionAmount();
                    attacker.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, absorptionTime[willBracket], 127));
                    attacker.setAbsorptionAmount((float) Math.min(absorption + target.getMaxHealth() * 0.05f, maxAbsorptionHearts));
                }
                break;
            case VENGEFUL:
                break;
        }
    }

    public static List<ItemStack> getRandomDemonWillDrop(EntityLivingBase killedEntity, EntityLivingBase attackingEntity, ItemStack stack, int looting) {
        List<ItemStack> soulList = new ArrayList<>();

        if (killedEntity.getEntityWorld().getDifficulty() != EnumDifficulty.PEACEFUL && !(killedEntity instanceof IMob)) {
            return soulList;
        }

        double willModifier = killedEntity instanceof EntitySlime ? 0.67 : 1;

        IDemonWill soul = ((IDemonWill) RegistrarBloodMagicItems.MONSTER_SOUL);

        EnumDemonWillType type = getCurrentType(stack);

        for (int i = 0; i <= looting; i++) {
            if (i == 0 || attackingEntity.getEntityWorld().rand.nextDouble() < 0.4) {
                ItemStack soulStack = soul.createWill(type.ordinal(), willModifier * (getDropOfActivatedSword(stack) * attackingEntity.getEntityWorld().rand.nextDouble() + getStaticDropOfActivatedSword(stack)) * killedEntity.getMaxHealth() / 20d);
                soulList.add(soulStack);
            }
        }

        return soulList;
    }

    public static EnumDemonWillType getCurrentType(ItemStack stack) {
        NBTHelper.checkNBT(stack);

        NBTTagCompound tag = stack.getTagCompound();

        if (!tag.hasKey(Constants.NBT.WILL_TYPE)) {
            return EnumDemonWillType.DEFAULT;
        }

        return EnumDemonWillType.valueOf(tag.getString(Constants.NBT.WILL_TYPE).toUpperCase(Locale.ENGLISH));
    }

    public void setCurrentType(ItemStack stack, EnumDemonWillType type) {
        NBTHelper.checkNBT(stack);

        NBTTagCompound tag = stack.getTagCompound();

        tag.setString(Constants.NBT.WILL_TYPE, type.toString());
    }

    public static double getDrainOfActivatedSword(ItemStack stack) {
        NBTHelper.checkNBT(stack);

        NBTTagCompound tag = stack.getTagCompound();
        return tag.getDouble(Constants.NBT.SOUL_SWORD_ACTIVE_DRAIN);
    }

    public static void setDrainOfActivatedSword(ItemStack stack, double drain) {
        NBTHelper.checkNBT(stack);

        NBTTagCompound tag = stack.getTagCompound();

        tag.setDouble(Constants.NBT.SOUL_SWORD_ACTIVE_DRAIN, drain);
    }

    public static double getStaticDropOfActivatedSword(ItemStack stack) {
        NBTHelper.checkNBT(stack);

        NBTTagCompound tag = stack.getTagCompound();
        return tag.getDouble(Constants.NBT.SOUL_SWORD_STATIC_DROP);
    }

    public static void setStaticDropOfActivatedSword(ItemStack stack, double drop) {
        NBTHelper.checkNBT(stack);

        NBTTagCompound tag = stack.getTagCompound();

        tag.setDouble(Constants.NBT.SOUL_SWORD_STATIC_DROP, drop);
    }

    public static double getDropOfActivatedSword(ItemStack stack) {
        NBTHelper.checkNBT(stack);

        NBTTagCompound tag = stack.getTagCompound();
        return tag.getDouble(Constants.NBT.SOUL_SWORD_DROP);
    }

    public static void setDropOfActivatedSword(ItemStack stack, double drop) {
        NBTHelper.checkNBT(stack);

        NBTTagCompound tag = stack.getTagCompound();

        tag.setDouble(Constants.NBT.SOUL_SWORD_DROP, drop);
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event)
    {
        EntityLivingBase attackedEntity = event.getEntityLiving();
        DamageSource source = event.getSource();
        Entity entity = source.getTrueSource();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            ItemStack heldStack = player.getHeldItemMainhand();

            if (TinkerUtil.getModifiers(heldStack).contains(TConstructPlugin.MODIFIER_SENTIENCE) && !player.getEntityWorld().isRemote) {
                List<ItemStack> droppedSouls = getRandomDemonWillDrop(attackedEntity, player, heldStack, event.getLootingLevel());
                if (!droppedSouls.isEmpty()) {
                    ItemStack remainder;
                    for (ItemStack willStack : droppedSouls) {
                        remainder = PlayerDemonWillHandler.addDemonWill(player, willStack);

                        if (!remainder.isEmpty()) {
                            EnumDemonWillType pickupType = ((IDemonWill) remainder.getItem()).getType(remainder);
                            if (((IDemonWill) remainder.getItem()).getWill(pickupType, remainder) >= 0.0001) {
                                event.getDrops().add(new EntityItem(attackedEntity.getEntityWorld(), attackedEntity.posX, attackedEntity.posY, attackedEntity.posZ, remainder));
                            }
                        }
                    }
                    player.inventoryContainer.detectAndSendChanges();
                }
            }
        }
    }
}
