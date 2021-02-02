package arcaratus.bloodarsenal.common.block;

import arcaratus.bloodarsenal.common.BloodArsenal;
import arcaratus.bloodarsenal.common.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Direction;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BloodArsenal.MOD_ID);

    private static final AbstractBlock.IExtendedPositionPredicate<EntityType<?>> NO_SPAWN = (state, world, pos, et) -> false;
    private static final AbstractBlock.IPositionPredicate NO_SUFFOCATION = (state, world, pos) -> false;

    public static final Properties PROP_BLOOD_INFUSED_WOOD = Properties.from(Blocks.OAK_PLANKS).hardnessAndResistance(3, 6);
    public static final Properties PROP_BLOOD_INFUSED_IRON = Properties.from(Blocks.IRON_BLOCK).hardnessAndResistance(7, 10).setRequiresTool();
    public static final Properties PROP_BLOOD_STAINED_GLASS = Properties.from(Blocks.GLASS).hardnessAndResistance(1, 3).setLightLevel(s -> 15).setBlocksVision(NO_SUFFOCATION).setSuffocates(NO_SUFFOCATION).setAllowsSpawn(NO_SPAWN);
    public static final Properties PROP_SLATE = Properties.from(Blocks.STONE).hardnessAndResistance(2, 5).setRequiresTool();

    public static final RegistryObject<Block> BLOOD_INFUSED_PLANKS = registerBlock("blood_infused_planks", new BloodArsenalBlock(PROP_BLOOD_INFUSED_WOOD));
    public static final RegistryObject<Block> BLOOD_INFUSED_LOG = registerBlock("blood_infused_log", new RotatedPillarBlock(Properties.create(Material.WOOD, state -> state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.CRIMSON_HYPHAE : MaterialColor.RED_TERRACOTTA).hardnessAndResistance(3, 6).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> STRIPPED_BLOOD_INFUSED_LOG = registerBlock("stripped_blood_infused_log", new RotatedPillarBlock(Properties.create(Material.WOOD, state -> state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.CRIMSON_HYPHAE : MaterialColor.ADOBE).hardnessAndResistance(3, 6).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> BLOOD_INFUSED_IRON_BLOCK = registerBlock("blood_infused_iron_block", new BloodArsenalBlock(PROP_BLOOD_INFUSED_IRON));
    public static final RegistryObject<Block> BLOOD_INFUSED_GLOWSTONE = registerBlock("blood_infused_glowstone", new BloodArsenalBlock(Properties.create(Material.GLASS, MaterialColor.RED).hardnessAndResistance(0.75F, 1).sound(SoundType.GLASS).setLightLevel(s -> 15)));
    public static final RegistryObject<Block> BLOOD_STAINED_GLASS = registerBlock("blood_stained_glass", new BloodStainedGlassBlock(PROP_BLOOD_STAINED_GLASS));
    public static final RegistryObject<Block> BLOOD_STAINED_GLASS_PANE = registerBlock("blood_stained_glass_pane", new BloodStainedGlassPaneBlock(PROP_BLOOD_STAINED_GLASS));
    public static final RegistryObject<Block> BLOOD_INFUSED_WOOD_STAIRS = registerBlock("blood_infused_wood_stairs", new StairsBlock(() -> BLOOD_INFUSED_PLANKS.get().getDefaultState(), PROP_BLOOD_INFUSED_WOOD));
    public static final RegistryObject<Block> BLOOD_INFUSED_WOOD_SLAB = registerBlock("blood_infused_wood_slab", new SlabBlock(PROP_BLOOD_INFUSED_WOOD));
    public static final RegistryObject<Block> BLOOD_INFUSED_WOOD_FENCE = registerBlock("blood_infused_wood_fence", new FenceBlock(PROP_BLOOD_INFUSED_WOOD));
    public static final RegistryObject<Block> BLOOD_INFUSED_WOOD_FENCE_GATE = registerBlock("blood_infused_wood_fence_gate", new FenceGateBlock(PROP_BLOOD_INFUSED_WOOD));
    public static final RegistryObject<Block> BLANK_SLATE = registerBlock("blank_slate", new BloodArsenalBlock(PROP_SLATE));
    public static final RegistryObject<Block> REINFORCED_SLATE = registerBlock("reinforced_slate", new BloodArsenalBlock(PROP_SLATE));
    public static final RegistryObject<Block> IMBUED_SLATE = registerBlock("imbued_slate", new BloodArsenalBlock(PROP_SLATE));
    public static final RegistryObject<Block> DEMONIC_SLATE = registerBlock("demonic_slate", new BloodArsenalBlock(PROP_SLATE));
    public static final RegistryObject<Block> ETHEREAL_SLATE = registerBlock("ethereal_slate", new BloodArsenalBlock(PROP_SLATE));

    public static RegistryObject<Block> registerBlock(String name, Block block)
    {
        RegistryObject<Block> reg = BLOCKS.register(name, () -> block);
        ModItems.ITEMS.register(name, () -> new BlockItem(reg.get(), ModItems.defaultBuilder()));
        return reg;
    }
}
