package arcaratus.bloodarsenal.modifier;

import arcaratus.bloodarsenal.BloodArsenal;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Singleton class that is immutable except for certain NBT-special implementations
 * Mutable values are in the respective ModifierTracker class
 */
public class Modifier
{
    public static final Modifier EMPTY_MODIFIER = new Modifier("", 0, EnumModifierType.HEAD);

    public static String tooltipBase = "tooltip.bloodarsenal.modifier.";

    private String name;
    private int maxLevel;
    private EnumModifierType type;
    private EnumAction action;

    private boolean hasAltName = false;

    protected static Random random = new Random();

    public Modifier(String name, int maxLevel, EnumModifierType type, EnumAction action)
    {
        this.name = name;
        this.maxLevel = maxLevel - 1; // -1 because of the first entry being 0
        this.type = type;
        this.action = action;
    }

    public Modifier(String name, int maxLevel, EnumModifierType type)
    {
        this(name, maxLevel, type, EnumAction.NONE);
    }

    public EnumModifierType getType()
    {
        return type;
    }

    public EnumAction getAction()
    {
        return action;
    }

    public String getUniqueIdentifier()
    {
        return BloodArsenal.MOD_ID + ".modifier." + name;
    }

    public String getName()
    {
        return name;
    }

    public String getUnlocalizedName()
    {
        return tooltipBase + name;
    }

    public String getAlternateName(ItemStack itemStack)
    {
        return "";
    }

    public int getMaxLevel()
    {
        return maxLevel;
    }

    public void setAltName()
    {
        hasAltName = true;
    }

    public boolean hasAltName()
    {
        return hasAltName;
    }

    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, int level)
    {

    }

    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker, int level)
    {

    }

    public void onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityPlayer player, int level)
    {

    }

    public void onRightClick(ItemStack itemStack, World world, EntityPlayer player, int level)
    {

    }

    public void onRelease(ItemStack itemStack, World world, EntityPlayer player, int charge, int level)
    {

    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(int level)
    {
        return HashMultimap.create();
    }

    public void writeToNBT(NBTTagCompound tag)
    {

    }

    public void readFromNBT(NBTTagCompound tag)
    {

    }

    public void writeSpecialNBT(ItemStack itemStack, ItemStack extra, int level)
    {

    }

    public void writeSpecialNBT(ItemStack itemStack, int level)
    {
        writeSpecialNBT(itemStack, ItemStack.EMPTY, level);
    }

    /**
     * To be used with tag.merge()
     *
     * @returns The NBT portion that the modifier writes, nothing else
     */
    public NBTTagCompound getSpecialNBT(ItemStack itemStack)
    {
        return null;
    }

    public void removeSpecialNBT(ItemStack itemStack)
    {

    }
}
