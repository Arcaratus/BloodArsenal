package arc.bloodarsenal.block;

import WayofTime.bloodmagic.api.altar.EnumAltarComponent;
import WayofTime.bloodmagic.api.altar.IAltarComponent;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class BlockBloodInfusedGlowstone extends BlockBloodArsenalBase implements IAltarComponent
{
    public BlockBloodInfusedGlowstone(String name)
    {
        super(name, Material.glass);

        setHardness(0.75F);
        setResistance(1.0F);
        setSoundType(SoundType.GLASS);
        setLightLevel(1.0F);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return MathHelper.clamp_int(this.quantityDropped(random) + random.nextInt(fortune + 1), 1, 4);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 2 + random.nextInt(3);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.glowstone_dust;
    }

    @Override
    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.redColor;
    }

    @Override
    public EnumAltarComponent getType(int meta)
    {
        return EnumAltarComponent.GLOWSTONE;
    }
}
