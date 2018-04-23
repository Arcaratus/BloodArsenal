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

public class Modifier
{
    public static Modifier EMPTY_MODIFIER = null;

    public static String tooltipBase = "tooltip.bloodarsenal.modifier.";

    private String name;
    private int maxLevel;
    protected int level = 0;
    private EnumModifierType type;
    private EnumAction action;

    private boolean hasAltName = false;

    private boolean readyForUpgrade = false;

    protected static Random random = new Random();

    public Modifier(String name, int maxLevel, int level, EnumModifierType type, EnumAction action)
    {
        this.name = name;
        this.maxLevel = maxLevel;
        this.level = level;
        this.type = type;
        this.action = action;
    }

    public Modifier(String name, int maxLevel, int level, EnumModifierType type)
    {
        this(name, maxLevel, level, type, EnumAction.NONE);
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
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

    public boolean readyForUpgrade()
    {
        return readyForUpgrade;
    }

    public void setReadyForUpgrade(boolean readyForUpgrade)
    {
        this.readyForUpgrade = readyForUpgrade;
    }

    public void setAltName()
    {
        hasAltName = true;
    }

    public boolean hasAltName()
    {
        return hasAltName;
    }

    public Modifier newCopy(int level)
    {
        return new Modifier(getName(), getMaxLevel(), level, getType(), getAction());
    }

    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot)
    {

    }

    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker)
    {

    }

    public void onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityPlayer player)
    {

    }

    public void onRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {

    }

    public void onRelease(ItemStack itemStack, World world, EntityPlayer player, int charge)
    {

    }

    public Multimap<String, AttributeModifier> getAttributeModifiers()
    {
        return HashMultimap.create();
    }

    public void writeToNBT(NBTTagCompound tag)
    {

    }

    public void readFromNBT(NBTTagCompound tag)
    {

    }

    public void writeSpecialNBT(ItemStack itemStack, ItemStack extra)
    {

    }

    public void writeSpecialNBT(ItemStack itemStack)
    {
        writeSpecialNBT(itemStack, ItemStack.EMPTY);
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
