package arcaratus.bloodarsenal.common.core;

import arcaratus.bloodarsenal.common.BloodArsenal;
import arcaratus.bloodarsenal.common.item.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class BloodArsenalCreativeTab extends ItemGroup
{
    public static final BloodArsenalCreativeTab INSTANCE = new BloodArsenalCreativeTab();

    public BloodArsenalCreativeTab()
    {
        super(BloodArsenal.MOD_ID);
    }

    @Nonnull
    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ModItems.INFUSED_BLOOD_DIAMOND.get());
    }
}
