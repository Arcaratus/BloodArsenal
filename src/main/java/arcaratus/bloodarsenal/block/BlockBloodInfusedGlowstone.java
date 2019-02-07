package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.altar.ComponentType;
import WayofTime.bloodmagic.altar.IAltarComponent;
import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.item.types.EnumBaseTypes;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBloodInfusedGlowstone extends Block implements IAltarComponent, IVariantProvider, IBABlock
{
    public BlockBloodInfusedGlowstone(String name)
    {
        super(Material.GLASS);

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(0.75F);
        setResistance(1.0F);
        setSoundType(SoundType.GLASS);
        setLightLevel(1.0F);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(fortune + 1), 1, 4);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 2 + random.nextInt(3);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return EnumBaseTypes.BLOOD_INFUSED_GLOWSTONE_DUST.getStack().getItem();
    }

    @Override
    public ComponentType getType(World world, IBlockState state, BlockPos pos)
    {
        return ComponentType.GLOWSTONE;
    }

    @Override
    public ItemBlock getItem()
    {
        return new ItemBlock(this);
    }
}
