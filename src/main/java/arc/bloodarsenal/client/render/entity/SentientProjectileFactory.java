package arc.bloodarsenal.client.render.entity;

import arc.bloodarsenal.entity.projectile.EntitySummonedSword;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SentientProjectileFactory implements IRenderFactory<EntitySummonedSword>
{
    @Override
    public Render<? super EntitySummonedSword> createRenderFor(RenderManager manager)
    {
        return new RenderSentientProjectile(manager);
    }
}
