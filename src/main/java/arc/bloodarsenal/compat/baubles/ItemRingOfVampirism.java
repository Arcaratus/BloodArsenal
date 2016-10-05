package arc.bloodarsenal.compat.baubles;

import WayofTime.bloodmagic.client.IVariantProvider;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.util.DamageSourceBleeding;
import baubles.api.BaubleType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ItemRingOfVampirism extends ItemBauble implements IVariantProvider
{
    public ItemRingOfVampirism(String name)
    {
        super(name, BaubleType.RING);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        if (CompatBaubles.RING_OF_VAMPIRISM == null || event.getEntity().getEntityWorld().isRemote)
            return;

        if (event.getSource() instanceof DamageSourceBleeding)
        {
            EntityPlayer player = event.getEntity().getEntityWorld().getClosestPlayerToEntity(event.getEntityLiving(), 5);

            if (player != null)
            {
                ItemStack baubleStack = BaubleUtils.getBaubleStackInPlayer(player, this);

                if (baubleStack != null && baubleStack.getItem() instanceof ItemRingOfVampirism)
                    player.heal(event.getAmount() * ConfigHandler.ringOfVampirismHealthSyphon);
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.EPIC;
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "type=normal"));
        return ret;
    }
}
