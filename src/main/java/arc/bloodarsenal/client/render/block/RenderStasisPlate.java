package arc.bloodarsenal.client.render.block;

import arc.bloodarsenal.tile.TileStasisPlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RenderStasisPlate extends TileEntitySpecialRenderer<TileStasisPlate>
{
    public static Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void renderTileEntityAt(TileStasisPlate stasisPlate, double x, double y, double z, float partialTicks, int destroyStage)
    {
        ItemStack heldStack = stasisPlate.getStackInSlot(0);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        renderItem(stasisPlate.getWorld(), heldStack, stasisPlate);
        GlStateManager.popMatrix();
    }

    private void renderItem(World world, ItemStack stack, TileStasisPlate stasisPlate)
    {
        RenderItem itemRenderer = mc.getRenderItem();
        if (!stack.isEmpty())
        {
            GlStateManager.translate(0.5, 0.6125, 0.5);
            EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, stack);
            entityitem.getEntityItem().setCount(stack.getCount());
            entityitem.hoverStart = 0.0F;
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();

            if (!stasisPlate.getStasis())
            {
                float rotation = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

                GlStateManager.rotate(rotation, 0.0F, 1.0F, 0);
            }

            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            itemRenderer.renderItem(entityitem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}
