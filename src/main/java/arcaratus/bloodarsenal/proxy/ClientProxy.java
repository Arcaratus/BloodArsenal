package arcaratus.bloodarsenal.proxy;

import arcaratus.bloodarsenal.client.hud.HUDElementAugmentedHolding;
import arcaratus.bloodarsenal.client.mesh.FluidStateMapper;
import arcaratus.bloodarsenal.client.render.block.RenderStasisPlate;
import arcaratus.bloodarsenal.client.render.entity.RenderSentientTool;
import arcaratus.bloodarsenal.client.render.entity.RenderWarpBlade;
import arcaratus.bloodarsenal.entity.projectile.EntitySummonedTool;
import arcaratus.bloodarsenal.entity.projectile.EntityWarpBlade;
import arcaratus.bloodarsenal.tile.TileStasisPlate;
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
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();

        ClientRegistry.bindTileEntitySpecialRenderer(TileStasisPlate.class, new RenderStasisPlate());

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
