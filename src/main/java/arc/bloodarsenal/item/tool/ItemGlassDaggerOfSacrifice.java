package arc.bloodarsenal.item.tool;

import WayofTime.bloodmagic.ConfigHandler;
import WayofTime.bloodmagic.api.BloodMagicAPI;
import WayofTime.bloodmagic.api.util.helper.PlayerSacrificeHelper;
import WayofTime.bloodmagic.item.ItemDaggerOfSacrifice;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemGlassDaggerOfSacrifice extends ItemDaggerOfSacrifice
{
    public ItemGlassDaggerOfSacrifice(String name)
    {
        super();

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setMaxStackSize(1);
        setFull3D();
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (target == null || attacker == null || attacker.worldObj.isRemote || (attacker instanceof EntityPlayer && !(attacker instanceof EntityPlayerMP)))
            return false;

        if (target.isChild() || target instanceof EntityPlayer)
            return false;

        if (target.isDead || target.getHealth() < 0.5F)
            return false;

        String entityName = target.getClass().getSimpleName();
        int lifeEssence = 1000;

        if (ConfigHandler.entitySacrificeValues.containsKey(entityName))
            lifeEssence = ConfigHandler.entitySacrificeValues.get(entityName) * arc.bloodarsenal.ConfigHandler.glassDaggerOfSacrificeLPMultiplier;

        if (BloodMagicAPI.getEntitySacrificeValues().containsKey(entityName))
            lifeEssence = BloodMagicAPI.getEntitySacrificeValues().get(entityName) * arc.bloodarsenal.ConfigHandler.glassDaggerOfSacrificeLPMultiplier;

        if (PlayerSacrificeHelper.findAndFillAltar(attacker.worldObj, target, lifeEssence, true))
        {
            target.worldObj.playSound(null, target.posX, target.posY, target.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (target.worldObj.rand.nextFloat() - target.worldObj.rand.nextFloat()) * 0.8F);
            target.setHealth(-1);
            target.onDeath(BloodMagicAPI.getDamageSource());
        }

        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced)
    {
        list.addAll(Arrays.asList(TextHelper.cutLongString(TextHelper.localizeEffect("tooltip.BloodArsenal.glassDaggerOfSacrifice.desc"))));
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "normal"));
        return ret;
    }
}
