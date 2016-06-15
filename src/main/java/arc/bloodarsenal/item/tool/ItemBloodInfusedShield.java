package arc.bloodarsenal.item.tool;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBloodInfusedShield extends ItemShield// implements IMeshProvider
{
    public ItemBloodInfusedShield(String name)
    {
        super();

        setMaxStackSize(1);
        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        addPropertyOverride(new ResourceLocation("bloodarsenal:blocking"), (stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab()
    {
        return BloodArsenal.TAB_BLOOD_ARSENAL;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == Item.getItemFromBlock(ModBlocks.bloodInfusedWoodenLog) || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public ItemMeshDefinition getMeshDefinition()
//    {
//        return stack -> new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "ItemBloodInfusedShield"), "normal");
//    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return TextHelper.localize("item.BloodArsenal.bloodInfusedShield.name");
    }

//    @Override
//    public List<String> getVariants()
//    {
//        List<String> variants = new ArrayList<>();
//        variants.add("type=normal");
//        return variants;
//    }
//
//    @Override
//    public ResourceLocation getCustomLocation()
//    {
//        return null;
//    }
}
