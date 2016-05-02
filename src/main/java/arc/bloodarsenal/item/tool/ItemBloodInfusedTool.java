package arc.bloodarsenal.item.tool;

import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.registry.ModItems;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemBloodInfusedTool extends ItemTool implements IVariantProvider
{
    protected static final Set<Block> AXE_EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER);
    protected static final Set<Block> PICKAXE_EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB);
    protected static final Set<Block> SHOVEL_EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND);

    protected final String tooltipBase;
    private final int enchantibility;

    private final int repairUpdate;
    private final int repairCost;

    public ItemBloodInfusedTool(String type, ToolMaterial toolMaterial, String name, float damage, Set<Block> effectiveBlocks, int enchantibility, int repairUpdate, int repairCost)
    {
        super(damage, -2.8F, toolMaterial, effectiveBlocks);

        setUnlocalizedName(BloodArsenal.MOD_ID + ".bloodInfused" + type + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);

        this.tooltipBase = "tooltip.BloodArsenal.bloodInfused" + type + "." + name + ".";
        this.enchantibility = enchantibility;

        this.repairUpdate = repairUpdate;
        this.repairCost = repairCost;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (!world.isRemote && stack.getItemDamage() > 0 && world.getWorldTime() % repairUpdate == 0 && itemRand.nextBoolean())
        {
            if (entity instanceof EntityPlayer)
            {
                int cost = repairCost;

                if (stack.isItemEnchanted())
                {
                    NBTTagList enchants = stack.getEnchantmentTagList();

                    if (enchants != null && enchants.tagCount() > 0)
                    {
                        NBTTagCompound enchant;

                        for (int i = 0; i <= enchants.tagCount(); i++)
                        {
                            enchant = enchants.getCompoundTagAt(i);
                            short lvl = enchant.getShort("lvl");

                            cost *= ((repairCost / 10) * lvl);
                        }
                    }
                }

                NetworkHelper.getSoulNetwork((EntityPlayer) entity).syphonAndDamage((EntityPlayer) entity, cost);
                stack.setItemDamage(stack.getItemDamage() - 2);
            }
        }
    }

    @Override
    public int getItemEnchantability()
    {
        return enchantibility;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (I18n.hasKey(tooltipBase + "desc"))
            tooltip.add(TextHelper.localizeEffect(tooltipBase + "desc"));

        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "normal"));
        return ret;
    }

    public static class Wooden extends ItemBloodInfusedTool
    {
        public Wooden(String name, float damage, Set<Block> effectiveBlocks)
        {
            super(Constants.Item.WOODEN, ModItems.bloodInfusedWoodMaterial, name, damage, effectiveBlocks, 18, ConfigHandler.bloodInfusedWoodenToolsRepairUpdate, ConfigHandler.bloodInfusedWoodenToolsRepairCost);
        }
    }

    public static class Iron extends ItemBloodInfusedTool
    {
        public Iron(String name, float damage, Set<Block> effectiveBlocks)
        {
            super(Constants.Item.IRON, ModItems.bloodInfusedIronMaterial, name, damage, effectiveBlocks, 18, ConfigHandler.bloodInfusedWoodenToolsRepairUpdate, ConfigHandler.bloodInfusedWoodenToolsRepairCost);
        }

        @Override
        public EnumRarity getRarity(ItemStack stack)
        {
            return EnumRarity.UNCOMMON;
        }
    }
}
