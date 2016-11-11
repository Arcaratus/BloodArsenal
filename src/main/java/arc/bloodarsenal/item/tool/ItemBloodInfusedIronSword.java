package arc.bloodarsenal.item.tool;

import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.registry.ModItems;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ItemBloodInfusedIronSword extends ItemSword implements IVariantProvider
{
    public ItemBloodInfusedIronSword()
    {
        super(ModItems.BLOOD_INFUSED_IRON);

        setUnlocalizedName(BloodArsenal.MOD_ID + ".bloodInfusedIron.sword");
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (stack.getItemDamage() > 0 && world.getWorldTime() % ConfigHandler.bloodInfusedIronToolsRepairUpdate == 0 && !world.isRemote)
        {
            if (entity instanceof EntityPlayer)
            {
                NetworkHelper.getSoulNetwork((EntityPlayer) entity).syphonAndDamage((EntityPlayer) entity, ConfigHandler.bloodInfusedIronToolsRepairCost);
                stack.setItemDamage(stack.getItemDamage() - 2);
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public int getItemEnchantability()
    {
        return 21;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (I18n.hasKey("tooltip.bloodarsenal.bloodInfusedIron.sword.desc"))
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.bloodInfusedIron.sword.desc"));

        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 8, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -3, 0));
        }
        return multimap;
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "normal"));
        return ret;
    }
}