package arc.bloodarsenal.client.render.entity;

import arc.bloodarsenal.entity.projectile.EntityWarpBlade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWarpBlade extends Render<EntityWarpBlade>
{
    private final RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

    public RenderWarpBlade(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public void doRender(EntityWarpBlade entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.enableBlend();
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(1F, 1F, 1F);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderItem.renderItem(entity.getStack(), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWarpBlade entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
