package arcaratus.bloodarsenal.item.tool;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.DamageSourceBloodMagic;
import WayofTime.bloodmagic.util.helper.*;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.ConfigHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ItemGlassDaggerOfSacrifice extends Item implements IVariantProvider
{
    public ItemGlassDaggerOfSacrifice(String name)
    {
        super();

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setMaxStackSize(1);
        setFull3D();
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (target == null || attacker == null || attacker.getEntityWorld().isRemote || attacker instanceof FakePlayer || (attacker instanceof EntityPlayer && !(attacker instanceof EntityPlayerMP)))
            return false;

        if (!target.isNonBoss())
            return false;

        if (target instanceof EntityPlayer)
            return false;

        if (target.isChild() && !(target instanceof IMob))
            return false;

        if (target.isDead || target.getHealth() < 0.5F)
            return false;

        EntityEntry entityEntry = EntityRegistry.getEntry(target.getClass());
        if (entityEntry == null)
            return false;
        int lifeEssenceRatio = BloodMagicAPI.INSTANCE.getValueManager().getSacrificial().getOrDefault(entityEntry.getRegistryName(), 25);

        if (lifeEssenceRatio <= 0)
            return false;

        int lifeEssence = (int) (lifeEssenceRatio * target.getHealth());
        if (target instanceof EntityAnimal)
            lifeEssence = (int) (lifeEssence * (1 + PurificationHelper.getCurrentPurity((EntityAnimal) target)));

        if (target.isChild())
            lifeEssence *= 0.5F;

        lifeEssence *= ConfigHandler.values.glassDaggerOfSacrificeLPMultiplier;

        if (PlayerSacrificeHelper.findAndFillAltar(attacker.getEntityWorld(), target, lifeEssence, true))
        {
            target.getEntityWorld().playSound(null, target.posX, target.posY, target.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (target.getEntityWorld().rand.nextFloat() - target.getEntityWorld().rand.nextFloat()) * 0.8F);
            target.setHealth(-1);
            target.onDeath(DamageSourceBloodMagic.INSTANCE);
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.addAll(Arrays.asList(TextHelper.cutLongString(TextHelper.localizeEffect("tooltip.bloodarsenal.glass_dagger_of_sacrifice.desc"))));
    }

    @Override
    public void gatherVariants(@Nonnull Int2ObjectMap<String> variants)
    {
        variants.put(0, "type=normal");
    }
}
