package arc.bloodarsenal.item.tool;

import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.translation.I18n;
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
    protected final String tooltipBase;
    private final int enchantibility;

    private final int repairUpdate;
    private final int repairCost;

    public ItemBloodInfusedTool(String type, ToolMaterial toolMaterial, String name, float damage, Set<Block> effectiveBlocks, int enchantibility, int repairUpdate, int repairCost)
    {
        super(damage, -2.8F, toolMaterial, effectiveBlocks);

        setUnlocalizedName(BloodArsenal.MOD_ID + ".bloodInfused" + type + "." + name);
        setCreativeTab(BloodArsenal.tabBloodArsenal);

        this.tooltipBase = "tooltip.BloodArsenal.bloodInfused" + type + "." + name + ".";
        this.enchantibility = enchantibility;

        this.repairUpdate = repairUpdate;
        this.repairCost = repairCost;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (stack.getItemDamage() > 0 && world.getWorldTime() % repairUpdate == 0 && !world.isRemote)
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

                System.out.println("COST: " + cost);
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
        if (I18n.canTranslate(tooltipBase + "desc"))
            tooltip.add(TextHelper.localizeEffect(tooltipBase + "desc"));

        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        ret.add(new ImmutablePair<Integer, String>(0, "normal"));
        return ret;
    }
}
