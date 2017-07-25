package arc.bloodarsenal.proxy;

import WayofTime.bloodmagic.client.IMeshProvider;
import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.InventoryRenderHelperV2;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.client.hud.HUDElementAugmentedHolding;
import arc.bloodarsenal.client.mesh.FluidStateMapper;
import arc.bloodarsenal.client.render.block.RenderStasisPlate;
import arc.bloodarsenal.client.render.entity.RenderSentientTool;
import arc.bloodarsenal.entity.projectile.EntitySummonedTool;
import arc.bloodarsenal.registry.ModItems;
import arc.bloodarsenal.tile.TileStasisPlate;
import arc.bloodarsenal.util.IComplexVariantProvider;
import arc.bloodarsenal.util.handler.ClientHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.apache.commons.lang3.tuple.Pair;

public class ClientProxy extends CommonProxy
{
    private InventoryRenderHelperV2 renderHelper;

    @Override
    public InventoryRenderHelperV2 getRenderHelper()
    {
        return renderHelper;
    }

    @Override
    public void preInit()
    {
        super.preInit();

        renderHelper = new InventoryRenderHelperV2(BloodArsenal.DOMAIN);

        ModItems.initSpecialRenders();

        ClientRegistry.bindTileEntitySpecialRenderer(TileStasisPlate.class, new RenderStasisPlate());

        MinecraftForge.EVENT_BUS.register(new ClientHandler());
    }

    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntitySummonedTool.class, RenderSentientTool::new);
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
    public void tryHandleBlockModel(Block block, String name)
    {
        if (block instanceof IVariantProvider)
        {
            IVariantProvider variantProvider = (IVariantProvider) block;
            for (Pair<Integer, String> variant : variantProvider.getVariants())
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), variant.getLeft(), new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, name), variant.getRight()));
        }

        if (block instanceof IComplexVariantProvider)
        {
            IComplexVariantProvider complexVariantProvider = (IComplexVariantProvider) block;
            if (complexVariantProvider.getIgnoredProperties() != null)
            {
                IStateMapper customMapper = (new StateMap.Builder()).ignore(complexVariantProvider.getIgnoredProperties()).build();
                ModelLoader.setCustomStateMapper(block, customMapper);
            }
        }
    }

    @Override
    public void tryHandleItemModel(Item item, String name)
    {
        if (item instanceof IMeshProvider)
        {
            IMeshProvider meshProvider = (IMeshProvider) item;
            ModelLoader.setCustomMeshDefinition(item, meshProvider.getMeshDefinition());
            ResourceLocation resourceLocation = meshProvider.getCustomLocation();
            if (resourceLocation == null)
                resourceLocation = new ResourceLocation(BloodArsenal.MOD_ID, "item/" + name);
            for (String variant : meshProvider.getVariants())
            {
                ModelLoader.registerItemVariants(item, new ModelResourceLocation(resourceLocation, variant));
            }

        }
        else if (item instanceof IVariantProvider)
        {
            IVariantProvider variantProvider = (IVariantProvider) item;
            for (Pair<Integer, String> variant : variantProvider.getVariants())
                ModelLoader.setCustomModelResourceLocation(item, variant.getLeft(), new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "item/" + name), variant.getRight()));
        }
    }

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
