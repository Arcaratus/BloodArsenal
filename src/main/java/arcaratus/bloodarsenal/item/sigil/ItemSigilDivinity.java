package arcaratus.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.util.helper.PlayerHelper;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemSigilDivinity extends ItemSigilBaseToggleable
{
    public ItemSigilDivinity()
    {
        super("divinity", ConfigHandler.values.sigilDivinityCost);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onSigilUpdate(ItemStack stack, World world, EntityPlayer player, int itemSlot, boolean isSelected)
    {
        if (PlayerHelper.isFakePlayer(player))
            return;

        player.fallDistance = 0;
        player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2, 4, true, false));
        player.setHealth(player.getMaxHealth());
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.EPIC;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDamaged(LivingAttackEvent event)
    {
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();

            if (BloodArsenalUtils.isSigilInInvAndActive(player, this) && event.isCancelable())
                event.setCanceled(true);
        }
    }
}
