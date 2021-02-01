package arcaratus.bloodarsenal.common.item.tool;

import arcaratus.bloodarsenal.common.ConfigHandler;
import arcaratus.bloodarsenal.common.util.DamageSourceGlass;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.util.FakePlayer;
import wayoftime.bloodmagic.util.helper.PlayerSacrificeHelper;

public class GlassDaggerOfSacrificeItem extends Item
{
    public GlassDaggerOfSacrificeItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker instanceof FakePlayer)
        {
            return false;
        }
        else if (!attacker.getEntityWorld().isRemote && (!(attacker instanceof PlayerEntity) || attacker instanceof ServerPlayerEntity))
        {
            if (!target.isNonBoss())
            {
                return false;
            }
            else if (target instanceof PlayerEntity)
            {
                return false;
            }
            else if (target.isChild() && !(target instanceof IMob))
            {
                return false;
            }
            else if (target.isAlive() && !(target.getHealth() < 0.5F))
            {
                int lifeEssenceRatio = 25;
                int lifeEssence = (int) ((float) lifeEssenceRatio * target.getHealth());
                if (target.isChild())
                {
                    lifeEssence = (int) ((float) lifeEssence * 0.5F);
                }

                lifeEssence = (int) (ConfigHandler.COMMON.glassDaggerOfSacrificeMultiplier.get() * (double) lifeEssence);

                if (PlayerSacrificeHelper.findAndFillAltar(attacker.getEntityWorld(), target, lifeEssence, true))
                {
                    target.getEntityWorld().playSound(null, target.getPosX(), target.getPosY(), target.getPosZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (target.getEntityWorld().rand.nextFloat() - target.getEntityWorld().rand.nextFloat()) * 0.8F);
                    target.setHealth(-1.0F);
                    target.onDeath(DamageSourceGlass.INSTANCE);
                }

                return false;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}