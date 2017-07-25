package arc.bloodarsenal.item.stasis;

import WayofTime.bloodmagic.api.event.BoundToolEvent;
import WayofTime.bloodmagic.api.iface.IActivatable;
import WayofTime.bloodmagic.api.iface.IBindable;
import WayofTime.bloodmagic.api.util.helper.NBTHelper;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import WayofTime.bloodmagic.client.IMeshProvider;
import WayofTime.bloodmagic.util.Utils;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.client.mesh.CustomMeshDefinitionActivatable;
import arc.bloodarsenal.modifier.*;
import arc.bloodarsenal.modifier.modifiers.ModifierShadowTool;
import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.registry.ModItems;
import com.google.common.base.Strings;
import com.google.common.collect.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;

public class ItemStasisSword extends ItemSword implements IBindable, IActivatable, IModifiableItem, IMeshProvider
{
    protected final String tooltipBase;

    public Map<ItemStack, Boolean> heldDownMap = new HashMap<>();
    public Map<ItemStack, Integer> heldDownCountMap = new HashMap<>();

    public final int CHARGE_TIME = 30;
    public int cost = 5;

    public ItemStasisSword()
    {
        super(ModItems.STASIS);

        setUnlocalizedName(BloodArsenal.MOD_ID + ".stasis.sword");
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);

        tooltipBase = "tooltip.bloodarsenal.stasis.sword.";
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if (Strings.isNullOrEmpty(getOwnerUUID(itemStack)))
        {
            setActivatedState(itemStack, false);
            return;
        }

        if (entity instanceof EntityPlayer && getActivated(itemStack) && isSelected && getBeingHeldDown(itemStack) && itemStack == ((EntityPlayer) entity).getActiveItemStack())
        {
            EntityPlayer player = (EntityPlayer) entity;
            setHeldDownCount(itemStack, Math.min(player.getItemInUseCount(), CHARGE_TIME));
        }
        else if (!isSelected)
        {
            setBeingHeldDown(itemStack, false);
        }

        if (entity instanceof EntityPlayer)
        {
            if (getActivated(itemStack))
            {
                try
                {
                    StasisModifiable.invokeModMethod(itemStack, StasisModifiable.class.getDeclaredMethod("onUpdate", ItemStack.class, World.class, Entity.class, int.class), itemStack, world, entity, itemSlot);
                }
                catch (Exception e)
                {
                }

                if (world.getTotalWorldTime() % 80 == 0)
                    NetworkHelper.getSoulNetwork(getOwnerUUID(itemStack)).syphonAndDamage((EntityPlayer) entity, cost);
            }
            else
            {
                StasisModifiable modifiable = StasisModifiable.getModFromNBT(itemStack);
                if (modifiable.hasModifier(ModifierShadowTool.class))
                    modifiable.onSpecialUpdate(itemStack, world, entity);
            }
        }
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if (getActivated(itemStack))
        {
            try
            {
                StasisModifiable.invokeModMethod(itemStack, StasisModifiable.class.getDeclaredMethod("hitEntity", ItemStack.class, EntityLivingBase.class, EntityLivingBase.class), itemStack, target, attacker);
            }
            catch (Exception e)
            {
            }
        }
        else
        {
            StasisModifiable modifiable = StasisModifiable.getModFromNBT(itemStack);
            if (modifiable.hasModifier(ModifierShadowTool.class))
                ModifierTracker.getTracker(ModifierShadowTool.class).incrementCounter(modifiable, 1);
        }

        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLivingBase)
    {
        if (!world.isRemote)
        {
            if (getActivated(itemStack))
            {
                if (entityLivingBase instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entityLivingBase;

                    try
                    {
                        StasisModifiable.invokeModMethod(itemStack, StasisModifiable.class.getDeclaredMethod("onBlockDestroyed", ItemStack.class, World.class, IBlockState.class, BlockPos.class, EntityPlayer.class), itemStack, world, state, pos, player);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            else
            {
                StasisModifiable modifiable = StasisModifiable.getModFromNBT(itemStack);
                if (modifiable.hasModifier(ModifierShadowTool.class))
                    ModifierTracker.getTracker(ModifierShadowTool.class).incrementCounter(modifiable, 1);
            }
        }

        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if (player.isSneaking())
            setActivatedState(itemStack, !getActivated(itemStack));

        if (!player.isSneaking() && getActivated(itemStack))
        {
            StasisModifiable modifiable = StasisModifiable.getModFromNBT(itemStack);
            Modifier modifier = Modifier.EMPTY_MODIFIER;
            for (Entry<String, Modifier> entry : modifiable.modifierMap.entrySet())
            {
                if (entry.getValue().getType() == EnumModifierType.ABILITY)
                {
                    modifier = entry.getValue();
                    break;
                }
            }

            if (modifier != Modifier.EMPTY_MODIFIER)
            {
                if (modifier.getAction() == EnumAction.BOW)
                {
                    BoundToolEvent.Charge event = new BoundToolEvent.Charge(player, itemStack);
                    if (MinecraftForge.EVENT_BUS.post(event))
                        return ActionResult.newResult(EnumActionResult.FAIL, event.result);

                    player.setActiveHand(hand);
                    return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
                }
                else if (!world.isRemote)
                {
                    try
                    {
                        StasisModifiable.invokeModMethod(itemStack, StasisModifiable.class.getDeclaredMethod("onRightClick", ItemStack.class, World.class, EntityPlayer.class), itemStack, world, player);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }

        return super.onItemRightClick(itemStack, world, player, hand);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityLiving;
            if (!player.isSneaking() && getActivated(itemStack))
            {
                int i = getMaxItemUseDuration(itemStack) - timeLeft;
                BoundToolEvent.Release event = new BoundToolEvent.Release(player, itemStack, i);
                if (MinecraftForge.EVENT_BUS.post(event))
                    return;

                i = event.charge;

                try
                {
                    StasisModifiable.invokeModMethod(itemStack, StasisModifiable.class.getDeclaredMethod("onRelease", ItemStack.class, World.class, EntityPlayer.class, int.class), itemStack, world, player, Math.min(i, CHARGE_TIME));
                }
                catch (Exception e)
                {
                }

                setBeingHeldDown(itemStack, false);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return !ItemStack.areItemsEqual(oldStack, newStack) || slotChanged;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        subItems.add(Utils.setUnbreakable(new ItemStack(itemIn)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (!itemStack.hasTagCompound())
            return;

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
            StasisModifiable modifiable = StasisModifiable.getModFromNBT(itemStack);
            for (EnumModifierType type : EnumModifierType.values())
            {
                tooltip.add(TextHelper.localize("tooltip.bloodarsenal.modifierType." + WordUtils.swapCase(type.toString())));
                for (Entry<String, Modifier> entry : modifiable.modifierMap.entrySet())
                {
                    Modifier modifier = entry.getValue();
                    if (modifier != null && modifier.getType() == type)
                    {
                        String locName = TextHelper.localize(modifier.getUnlocalizedName());
                        String altText = TextHelper.localize(modifier.getAlternateName(itemStack));
                        tooltip.add(" -" + TextHelper.localize("tooltip.bloodarsenal.modifier.level", !Strings.isNullOrEmpty(altText) ? altText : locName, modifier.getLevel() + 1, (modifier.readyForUpgrade() ? "+" : "")));
                    }
                }
            }
        }
        else
        {
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodarsenal.holdShift"));
        }

        super.addInformation(itemStack, player, tooltip, advanced);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityLivingBase entityLiving)
    {
        return itemStack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemStack)
    {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        StasisModifiable modifiable = StasisModifiable.getModFromNBT(itemStack);
        Modifier modifier = Modifier.EMPTY_MODIFIER;
        for (Entry<String, Modifier> entry : modifiable.modifierMap.entrySet())
            if (entry.getValue().getType() == EnumModifierType.ABILITY)
                modifier = entry.getValue();

        return modifier != Modifier.EMPTY_MODIFIER ? modifier.getAction() : EnumAction.NONE;
    }

    @Override
    public Set<String> getToolClasses(ItemStack itemStack)
    {
        return ImmutableSet.of("sword");
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack)
    {
        return getActivated(itemStack) && getBeingHeldDown(itemStack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack itemStack)
    {
        return ((double) -Math.min(getHeldDownCount(itemStack), CHARGE_TIME) / CHARGE_TIME) + 1;
    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.RARE;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack itemStack)
    {
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            StasisModifiable modifiable = StasisModifiable.getModFromNBT(itemStack);
            if (getActivated(itemStack))
            {
                Multimap<String, AttributeModifier> map = HashMultimap.create();
                map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6.7, 0));
                map.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.5, 0));

                map.putAll(modifiable.getAttributeModifiers());

                return map;
            }
            else
            {
                Multimap<String, AttributeModifier> map = modifiable.getAttributeModifiers();
                boolean hasShadow = modifiable.hasModifier(ModifierShadowTool.class);

                if (hasShadow)
                {
                    Modifier modifier = modifiable.getModifier(ModifierShadowTool.class);
                    map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6.7 * (modifier.getLevel() + 1) / 5, 0));
                    map.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.5 * (modifier.getLevel() + 1) / 5, 0));
                }
                else
                {
                    map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0, 0));
                    map.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.8, 0));
                }

                return map;
            }
        }

        return super.getAttributeModifiers(equipmentSlot, itemStack);
    }

    @Nullable
    @Override
    public ResourceLocation getCustomLocation()
    {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return new CustomMeshDefinitionActivatable("ItemStasisSword");
    }

    @Override
    public List<String> getVariants()
    {
        List<String> ret = new ArrayList<>();
        ret.add("active=true");
        ret.add("active=false");
        return ret;
    }

    protected int getHeldDownCount(ItemStack itemStack)
    {
        if (!heldDownCountMap.containsKey(itemStack))
            return 0;

        return heldDownCountMap.get(itemStack);
    }

    protected void setHeldDownCount(ItemStack itemStack, int count)
    {
        heldDownCountMap.put(itemStack, count);
    }

    protected boolean getBeingHeldDown(ItemStack itemStack)
    {
        if (!heldDownMap.containsKey(itemStack))
            return false;

        return heldDownMap.get(itemStack);
    }

    protected void setBeingHeldDown(ItemStack itemStack, boolean heldDown)
    {
        heldDownMap.put(itemStack, heldDown);
    }

    // IBindable

    @Override
    public String getOwnerName(ItemStack stack)
    {
        return stack != null ? NBTHelper.checkNBT(stack).getTagCompound().getString(Constants.NBT.OWNER_NAME) : null;
    }

    @Override
    public String getOwnerUUID(ItemStack stack)
    {
        return stack != null ? NBTHelper.checkNBT(stack).getTagCompound().getString(Constants.NBT.OWNER_UUID) : null;
    }

    @Override
    public boolean onBind(EntityPlayer player, ItemStack stack)
    {
        return true;
    }

    // IActivatable

    @Override
    public boolean getActivated(ItemStack stack)
    {
        return stack != null && NBTHelper.checkNBT(stack).getTagCompound().getBoolean(Constants.NBT.ACTIVATED);
    }

    @Override
    public ItemStack setActivatedState(ItemStack stack, boolean activated)
    {
        if (stack != null)
        {
            NBTHelper.checkNBT(stack).getTagCompound().setBoolean(Constants.NBT.ACTIVATED, activated);
            return stack;
        }

        return null;
    }
}
