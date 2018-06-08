package arcaratus.bloodarsenal.item.baubles;

import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.ConfigHandler;
import baubles.api.BaubleType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
public class ItemVampireRing extends ItemBauble implements IVariantProvider
{
    public ItemVampireRing(String name)
    {
        super(name, BaubleType.RING);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        if (event.getEntity().getEntityWorld().isRemote)
            return;

        if (event.getSource().getImmediateSource() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getImmediateSource();

            if (player != null)
            {
                ItemStack baubleStack = BaubleUtils.getBaubleStackInPlayer(player, this);

                if (baubleStack.getItem() instanceof ItemVampireRing)
                    player.heal(event.getAmount() * (float) ConfigHandler.values.vampireRingMultiplier);
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.EPIC;
    }

    @Override
    public void gatherVariants(@Nonnull Int2ObjectMap<String> variants)
    {
        variants.put(0, "type=normal");
    }
}
