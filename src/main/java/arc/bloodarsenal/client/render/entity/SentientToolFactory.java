package arc.bloodarsenal.client.render.entity;

import arc.bloodarsenal.entity.projectile.EntitySummonedTool;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SentientToolFactory implements IRenderFactory<EntitySummonedTool>
{
    @Override
    public Render<? super EntitySummonedTool> createRenderFor(RenderManager manager)
    {
        return new RenderSentientTool(manager);
    }
}
