package arcaratus.bloodarsenal.item.tool;

import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.event.BoundToolEvent;
import WayofTime.bloodmagic.iface.IActivatable;
import WayofTime.bloodmagic.iface.IBindable;
import WayofTime.bloodmagic.util.Utils;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import com.google.common.collect.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

/**
 * Shameless copying from Blood Magic repo just to change a few lines ¯\_(ツ)_/¯
 * Wait a minute, I wrote half of this...
 */
public class ItemBoundTool extends ItemTool implements IBindable, IActivatable
{
    public final int chargeTime = 30;
    protected final String tooltipBase;
    private final String name;
    public Map<ItemStack, Boolean> heldDownMap = new HashMap<>();
    public Map<ItemStack, Integer> heldDownCountMap = new HashMap<>();

    public ItemBoundTool(String name, float damage, Set<Block> effectiveBlocks)
    {
        super(damage, 1, RegistrarBloodMagicItems.BOUND_TOOL_MATERIAL, effectiveBlocks);
        setUnlocalizedName(BloodArsenal.MOD_ID + ".bound." + name);
        setRegistryName("bound_" + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHarvestLevel(name, 4);

        this.name = name;
        this.tooltipBase = "tooltip.bloodarsenal.bound." + name + ".";
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return getActivated(stack) ? toolMaterial.getEfficiency() : 1.0F;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
        if (!isInCreativeTab(tab))
            return;

        subItems.add(Utils.setUnbreakable(new ItemStack(this)));
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        Binding binding = getBinding(stack);
        if (binding == null)
        {
            setActivatedState(stack, false);
            return;
        }

        if (entity instanceof EntityPlayer && getActivated(stack) && isSelected && getBeingHeldDown(stack) && stack == ((EntityPlayer) entity).getActiveItemStack())
        {
            EntityPlayer player = (EntityPlayer) entity;
            setHeldDownCount(stack, Math.min(player.getItemInUseCount(), chargeTime));
        }
        else if (!isSelected)
        {
            setBeingHeldDown(stack, false);
        }

        if (entity instanceof EntityPlayer && getActivated(stack) && world.getTotalWorldTime() % 80 == 0)
            NetworkHelper.getSoulNetwork(binding).syphonAndDamage((EntityPlayer) entity, 20);
    }

    protected int getHeldDownCount(ItemStack stack)
    {
        if (!heldDownCountMap.containsKey(stack))
            return 0;

        return heldDownCountMap.get(stack);
    }

    protected void setHeldDownCount(ItemStack stack, int count)
    {
        heldDownCountMap.put(stack, count);
    }

    protected boolean getBeingHeldDown(ItemStack stack)
    {
        if (!heldDownMap.containsKey(stack))
            return false;

        return heldDownMap.get(stack);
    }

    protected void setBeingHeldDown(ItemStack stack, boolean heldDown)
    {
        heldDownMap.put(stack, heldDown);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking())
            setActivatedState(stack, !getActivated(stack));

        if (!player.isSneaking() && getActivated(stack))
        {
            BoundToolEvent.Charge event = new BoundToolEvent.Charge(player, stack);
            if (MinecraftForge.EVENT_BUS.post(event))
                return new ActionResult<>(EnumActionResult.FAIL, event.result);

            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityLiving;
            if (!player.isSneaking() && getActivated(stack))
            {
                int i = getMaxItemUseDuration(stack) - timeLeft;
                BoundToolEvent.Release event = new BoundToolEvent.Release(player, stack, i);
                if (MinecraftForge.EVENT_BUS.post(event))
                    return;

                i = event.charge;

                onBoundRelease(stack, worldIn, player, Math.min(i, chargeTime));
                setBeingHeldDown(stack, false);
            }
        }
    }

    protected void onBoundRelease(ItemStack stack, World world, EntityPlayer player, int charge)
    {

    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
    {
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public int getItemEnchantability()
    {
        return 50;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (TextHelper.canTranslate(tooltipBase + "desc"))
            tooltip.add(TextHelper.localizeEffect(tooltipBase + "desc"));

        tooltip.add(TextHelper.localize("tooltip.bloodmagic." + (getActivated(stack) ? "activated" : "deactivated")));

        if (!stack.hasTagCompound())
            return;

        Binding binding = getBinding(stack);
        if (binding != null)
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.currentOwner", binding.getOwnerName()));

        super.addInformation(stack, world, tooltip, flag);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return ImmutableSet.of(name);
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        return ArrayListMultimap.create(); // No-op
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return getActivated(stack) && getBeingHeldDown(stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return ((double) -Math.min(getHeldDownCount(stack), chargeTime) / chargeTime) + 1;
    }

    public String getTooltipBase()
    {
        return tooltipBase;
    }

    public String getName()
    {
        return name;
    }

    public Map<ItemStack, Boolean> getHeldDownMap()
    {
        return heldDownMap;
    }

    public Map<ItemStack, Integer> getHeldDownCountMap()
    {
        return heldDownCountMap;
    }

    public int getChargeTime()
    {
        return chargeTime;
    }
}