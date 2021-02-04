package arcaratus.bloodarsenal.common.item.sigil;

import arcaratus.bloodarsenal.common.ConfigHandler;
import arcaratus.bloodarsenal.common.core.handler.EquipmentHandler;
import arcaratus.bloodarsenal.common.item.ModItems;
import arcaratus.bloodarsenal.common.util.helper.PlayerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class DivinitySigilItem extends SigilBaseItem.Toggleable
{
    public DivinitySigilItem(Properties properties)
    {
        super(properties, ConfigHandler.COMMON.divinitySigilCost.get());
    }

    @Override
    public void onSigilUpdate(ItemStack stack, World world, PlayerEntity player, int itemSlot, boolean isSelected)
    {
        if (PlayerHelper.isFakePlayer(player))
            return;

        player.fallDistance = 0;
        player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 2, 4, true, false));
        player.setHealth(player.getMaxHealth());
    }

    public static void onPlayerAttacked(LivingAttackEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            if (event.isCancelable())
            {
                ItemStack divinitySigil = EquipmentHandler.findOrEmpty(ModItems.DIVINITY_SIGIL.get(), player);
                if (!divinitySigil.isEmpty())
                {
                    DivinitySigilItem divinitySigilItem = (DivinitySigilItem) divinitySigil.getItem();
                    if (divinitySigilItem.getActivated(divinitySigil))
                    {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
