package arcaratus.bloodarsenal.common.block;

import arcaratus.bloodarsenal.common.BloodArsenal;
import arcaratus.bloodarsenal.common.item.ModItems;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BloodArsenal.MOD_ID);

    public static final RegistryObject<Block> BLANK_SLATE = registerBlock("blank_slate", new BloodArsenalBlock(Properties.from(Blocks.STONE).hardnessAndResistance(2, 5)));
    public static final RegistryObject<Block> REINFORCED_SLATE = registerBlock("reinforced_slate", new BloodArsenalBlock(Properties.from(Blocks.STONE)));
    public static final RegistryObject<Block> IMBUED_SLATE = registerBlock("imbued_slate", new BloodArsenalBlock(Properties.from(Blocks.STONE)));
    public static final RegistryObject<Block> DEMONIC_SLATE = registerBlock("demonic_slate", new BloodArsenalBlock(Properties.from(Blocks.STONE)));
    public static final RegistryObject<Block> ETHEREAL_SLATE = registerBlock("ethereal_slate", new BloodArsenalBlock(Properties.from(Blocks.STONE)));

    public static RegistryObject<Block> registerBlock(String name, Block block)
    {
        RegistryObject<Block> reg = BLOCKS.register(name, () -> block);
        ModItems.ITEMS.register(name, () -> new BlockItem(reg.get(), ModItems.defaultBuilder()));
        return reg;
    }
}
