package arcaratus.bloodarsenal.core;

import WayofTime.bloodmagic.client.IMeshProvider;
import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.block.IBABlock;
import arcaratus.bloodarsenal.item.*;
import arcaratus.bloodarsenal.item.baubles.*;
import arcaratus.bloodarsenal.item.sigil.*;
import arcaratus.bloodarsenal.item.stasis.*;
import arcaratus.bloodarsenal.item.tool.*;
import arcaratus.bloodarsenal.util.IComplexVariantProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = BloodArsenal.MOD_ID)
@ObjectHolder(BloodArsenal.MOD_ID)
@SuppressWarnings("unchecked")
public class RegistrarBloodArsenalItems
{
    public static final Item BLOOD_DIAMOND = Items.AIR;

    public static final Item BASE_ITEM = Items.AIR;

    public static final Item BLOOD_BURNED_STRING = Items.AIR;
    public static final Item BLOOD_ORANGE = Items.AIR;

    public static final Item BLOOD_INFUSED_WOODEN_PICKAXE = Items.AIR;
    public static final Item BLOOD_INFUSED_WOODEN_AXE = Items.AIR;
    public static final Item BLOOD_INFUSED_WOODEN_SHOVEL = Items.AIR;
    public static final Item BLOOD_INFUSED_WOODEN_SICKLE = Items.AIR;
    public static final Item BLOOD_INFUSED_WOODEN_SWORD = Items.AIR;
    public static final Item BLOOD_INFUSED_IRON_PICKAXE = Items.AIR;
    public static final Item BLOOD_INFUSED_IRON_AXE = Items.AIR;
    public static final Item BLOOD_INFUSED_IRON_SHOVEL = Items.AIR;
    public static final Item BLOOD_INFUSED_IRON_SICKLE = Items.AIR;
    public static final Item BLOOD_INFUSED_IRON_SWORD = Items.AIR;

    public static final Item GLASS_SACRIFICIAL_DAGGER = Items.AIR;
    public static final Item GLASS_DAGGER_OF_SACRIFICE = Items.AIR;
//    public static final Item STYGIAN_DAGGER = Items.AIR;

    public static final Item GEM = Items.AIR;

    public static final Item SIGIL_SWIMMING = Items.AIR;
    public static final Item SIGIL_ENDER = Items.AIR;
    public static final Item SIGIL_AUGMENTED_HOLDING = Items.AIR;
    public static final Item SIGIL_LIGHTNING = Items.AIR;
    public static final Item SIGIL_DIVINITY = Items.AIR;
    public static final Item SIGIL_SENTIENCE = Items.AIR;

    public static final Item BOUND_STICK = Items.AIR;
    public static final Item BOUND_SICKLE = Items.AIR;
    public static final Item BOUND_IGNITER = Items.AIR;
    public static final Item BOUND_SHEARS = Items.AIR;

    public static final Item STASIS_PICKAXE = Items.AIR;
    public static final Item STASIS_AXE = Items.AIR;
    public static final Item STASIS_SHOVEL = Items.AIR;
    public static final Item STASIS_SWORD = Items.AIR;

    public static final Item MODIFIER_TOME = Items.AIR;

    public static final Item WARP_BLADE = Items.AIR;

    public static final Item SACRIFICE_AMULET = Items.AIR;
    public static final Item SELF_SACRIFICE_AMULET = Items.AIR;
    public static final Item VAMPIRE_RING = Items.AIR;
    public static final Item SOUL_PENDANT = Items.AIR;

    public static Item.ToolMaterial BLOOD_INFUSED_WOOD = EnumHelper.addToolMaterial("bloodinfusedwood", 1, 186, 5.5F, 1.0F, 13);
    public static Item.ToolMaterial BLOOD_INFUSED_IRON = EnumHelper.addToolMaterial("bloodinfusediron", 3, 954, 7.25F, 2.7F, 21);
    public static Item.ToolMaterial STASIS = EnumHelper.addToolMaterial("stasis", 4, 0, 9F, 4.2F, 0);

    public static List<Item> items;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        items = Lists.newArrayList();

        RegistrarBloodArsenalBlocks.blocks.stream().filter(block -> block instanceof IBABlock && ((IBABlock) block).getItem() != null).forEach(block ->
        {
            IBABlock baBlock = (IBABlock) block;
            items.add(baBlock.getItem().setRegistryName(block.getRegistryName()));
        });

        items.addAll(Lists.newArrayList(
                new ItemBloodDiamond("blood_diamond"),
                new ItemBloodArsenalBase("base_item"),
//                new ItemBlockSpecialBloodArsenal("blood_burned_string", RegistrarBloodArsenalBlocks.BLOOD_BURNED_STRING),
                new ItemBloodOrange("blood_orange"),
                new ItemBloodInfusedWoodenPickaxe(),
                new ItemBloodInfusedWoodenAxe(),
                new ItemBloodInfusedWoodenShovel(),
                new ItemBloodInfusedWoodenSickle(),
                new ItemBloodInfusedWoodenSword(),
                new ItemBloodInfusedIronPickaxe(),
                new ItemBloodInfusedIronAxe(),
                new ItemBloodInfusedIronShovel(),
                new ItemBloodInfusedIronSickle(),
                new ItemBloodInfusedIronSword(),
                new ItemGlassSacrificialDagger("glass_sacrificial_dagger"),
                new ItemGlassDaggerOfSacrifice("glass_dagger_of_sacrifice"),
                new ItemGem("gem"),
                new ItemSigilSwimming(),
                new ItemSigilEnder(),
                new ItemSigilAugmentedHolding(),
                new ItemSigilLightning(),
                new ItemSigilDivinity(),
                new ItemSigilSentience(),
                new ItemBoundStick("bound_stick"),
                new ItemBoundSickle(),
                new ItemBoundIgniter("bound_igniter"),
                new ItemBoundShears("bound_shears"),
                new ItemStasisAxe(),
                new ItemStasisPickaxe(),
                new ItemStasisShovel(),
                new ItemStasisSword(),
                new ItemModifierTome("modifier_tome"),
                new ItemWarpBlade("warp_blade"),
                new ItemSacrificeAmulet("sacrifice_amulet"),
                new ItemSelfSacrificeAmulet("self_sacrifice_amulet"),
                new ItemVampireRing("vampire_ring"),
                new ItemSoulPendant("soul_pendant")
        ));

        event.getRegistry().registerAll(items.toArray(new Item[0]));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event)
    {
        items.stream().filter(i -> i instanceof IVariantProvider).forEach(i ->
        {
            Int2ObjectMap<String> variants = new Int2ObjectOpenHashMap<>();
            ((IVariantProvider) i).gatherVariants(variants);
            for (Int2ObjectMap.Entry<String> variant : variants.int2ObjectEntrySet())
                ModelLoader.setCustomModelResourceLocation(i, variant.getIntKey(), new ModelResourceLocation(i.getRegistryName(), variant.getValue()));
        });

        items.stream().filter(i -> i instanceof IMeshProvider).forEach(i ->
        {
            IMeshProvider mesh = (IMeshProvider) i;
            ResourceLocation loc = mesh.getCustomLocation();
            if (loc == null)
                loc = i.getRegistryName();

            Set<String> variants = Sets.newHashSet();
            mesh.gatherVariants(variants::add);
            for (String variant : variants)
                ModelLoader.registerItemVariants(i, new ModelResourceLocation(loc, variant));

            ModelLoader.setCustomMeshDefinition(i, mesh.getMeshDefinition());
        });

        RegistrarBloodArsenalBlocks.blocks.stream().filter(b -> b instanceof IVariantProvider).forEach(b ->
        {
            Int2ObjectMap<String> variants = new Int2ObjectOpenHashMap<>();
            ((IVariantProvider) b).gatherVariants(variants);
            for (Int2ObjectMap.Entry<String> variant : variants.int2ObjectEntrySet())
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), variant.getIntKey(), new ModelResourceLocation(b.getRegistryName(), variant.getValue()));
        });

        RegistrarBloodArsenalBlocks.blocks.stream().filter(b -> b instanceof IComplexVariantProvider).forEach(b ->
        {
            IComplexVariantProvider complexVariantProvider = (IComplexVariantProvider) b;
            if (complexVariantProvider.getIgnoredProperties() != null)
            {
                IStateMapper customMapper = (new StateMap.Builder()).ignore(complexVariantProvider.getIgnoredProperties()).build();
                ModelLoader.setCustomStateMapper(b, customMapper);
            }
        });
    }
}
