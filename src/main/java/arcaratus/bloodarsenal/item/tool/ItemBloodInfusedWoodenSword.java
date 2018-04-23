package arcaratus.bloodarsenal.item.tool;

import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemBloodInfusedWoodenSword extends ItemSword implements IVariantProvider
{
    public ItemBloodInfusedWoodenSword()
    {
        super(RegistrarBloodArsenalItems.BLOOD_INFUSED_WOOD);

        setUnlocalizedName(BloodArsenal.MOD_ID + ".blood_infused_wooden.sword");
        setRegistryName("blood_infused_wooden_sword");
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (stack.getItemDamage() > 0 && world.getWorldTime() % ConfigHandler.values.bloodInfusedWoodenToolsRepairUpdate == 0 && !world.isRemote)
        {
            if (entity instanceof EntityPlayer)
            {
                NetworkHelper.getSoulNetwork((EntityPlayer) entity).syphonAndDamage((EntityPlayer) entity, ConfigHandler.values.bloodInfusedWoodenToolsRepairCost);
                stack.setItemDamage(stack.getItemDamage() - 2);
            }
        }
    }

    @Override
    public int getItemEnchantability()
    {
        return 13;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (I18n.hasKey("tooltip.bloodarsenal.blood_infused_wooden.sword.desc"))
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.blood_infused_wooden.sword.desc"));

        super.addInformation(stack, world, tooltip, flag);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 5.5, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -3, 0));
        }
        return multimap;
    }

    @Override
    public void gatherVariants(@Nonnull Int2ObjectMap<String> variants)
    {
        variants.put(0, "type=normal");
    }
}
