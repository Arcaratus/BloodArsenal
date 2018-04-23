package arcaratus.bloodarsenal.proxy;

import arcaratus.bloodarsenal.client.hud.HUDElementAugmentedHolding;
import arcaratus.bloodarsenal.client.mesh.FluidStateMapper;
import arcaratus.bloodarsenal.client.render.entity.RenderSentientTool;
import arcaratus.bloodarsenal.client.render.entity.RenderWarpBlade;
import arcaratus.bloodarsenal.entity.projectile.EntitySummonedTool;
import arcaratus.bloodarsenal.entity.projectile.EntityWarpBlade;
import arcaratus.bloodarsenal.util.handler.ClientHandler;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();

//        ModItems.initSpecialRenders();

//        ClientRegistry.bindTileEntitySpecialRenderer(TileStasisPlate.class, new RenderStasisPlate());

        MinecraftForge.EVENT_BUS.register(new ClientHandler());
    }

    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntitySummonedTool.class, RenderSentientTool::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWarpBlade.class, RenderWarpBlade::new);
    }

    @Override
    public void init()
    {

    }

    @Override
    public void postInit()
    {
        new HUDElementAugmentedHolding();
    }

    @Override
    public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters)
    {
        return ModelLoaderRegistry.loadASM(location, parameters);
    }

//    @Override
//    public void tryHandleBlockModel(Block block, String name)
//    {
//        if (block instanceof IVariantProvider)
//        {
//            IVariantProvider variantProvider = (IVariantProvider) block;
//            for (Pair<Integer, String> variant : variantProvider.getVariants())
//                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), variant.getLeft(), new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, name), variant.getRight()));
//        }
//
//        if (block instanceof IComplexVariantProvider)
//        {
//            IComplexVariantProvider complexVariantProvider = (IComplexVariantProvider) block;
//            if (complexVariantProvider.getIgnoredProperties() != null)
//            {
//                IStateMapper customMapper = (new StateMap.Builder()).ignore(complexVariantProvider.getIgnoredProperties()).build();
//                ModelLoader.setCustomStateMapper(block, customMapper);
//            }
//        }
//    }
//
//    @Override
//    public void tryHandleItemModel(Item item, String name)
//    {
//        if (item instanceof IMeshProvider)
//        {
//            IMeshProvider meshProvider = (IMeshProvider) item;
//            ModelLoader.setCustomMeshDefinition(item, meshProvider.getMeshDefinition());
//            ResourceLocation resourceLocation = meshProvider.getCustomLocation();
//            if (resourceLocation == null)
//                resourceLocation = new ResourceLocation(BloodArsenal.MOD_ID, "item/" + name);
//            for (String variant : meshProvider.getVariants())
//            {
//                ModelLoader.registerItemVariants(item, new ModelResourceLocation(resourceLocation, variant));
//            }
//
//        }
//        else if (item instanceof IVariantProvider)
//        {
//            IVariantProvider variantProvider = (IVariantProvider) item;
//            for (Pair<Integer, String> variant : variantProvider.getVariants())
//                ModelLoader.setCustomModelResourceLocation(item, variant.getLeft(), new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "item/" + name), variant.getRight()));
//        }
//    }

    @Override
    public void registerFluidModels(Fluid fluid)
    {
        if (fluid == null)
            return;

        Block block = fluid.getBlock();
        if (block != null)
        {
            Item item = Item.getItemFromBlock(block);
            FluidStateMapper mapper = new FluidStateMapper(fluid);

            // item-model
            if (item != null)
            {
                ModelLoader.registerItemVariants(item);
                ModelLoader.setCustomMeshDefinition(item, mapper);
            }
            // block-model
            ModelLoader.setCustomStateMapper(block, mapper);
        }
    }
}
