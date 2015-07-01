package com.arc.bloodarsenal.client.renderer.block;

import com.arc.bloodarsenal.client.renderer.model.ModelLifeInfuser;
import com.arc.bloodarsenal.common.tileentity.TileLifeInfuser;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileLifeInfuserRenderer extends TileEntitySpecialRenderer
{
    private ModelLifeInfuser modelLifeInfuser = new ModelLifeInfuser();
    RenderItem customRenderItem;

    public TileLifeInfuserRenderer()
    {
        customRenderItem = new RenderItem()
        {
            @Override
            public boolean shouldBob()
            {
                return false;
            }
        };
        customRenderItem.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d0, double d1, double d2, float f)
    {
        if (tileEntity instanceof TileLifeInfuser)
        {
            ResourceLocation test = new ResourceLocation("bloodarsenal:textures/models/LifeInfuser.png");

            TileLifeInfuser tile = (TileLifeInfuser) tileEntity;

            GL11.glPushMatrix();
            GL11.glTranslatef((float) d0 + 0.5F, (float) d1 + 1.5F, (float) d2 + 0.5F);
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(test);
            GL11.glPushMatrix();
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glPushMatrix();

            if (tile.getStackInSlot(0) != null)
            {
                float scaleFactor = getGhostItemScaleFactor(tile.getStackInSlot(0));
                float rotationAngle = Minecraft.getMinecraft().gameSettings.fancyGraphics ? (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL) : 0;
                EntityItem ghostEntityItem = new EntityItem(tile.getWorldObj());
                ghostEntityItem.hoverStart = 0.0F;
                ghostEntityItem.setEntityItemStack(tile.getStackInSlot(0));
                float displacement = 0.3F;

                if (ghostEntityItem.getEntityItem().getItem() instanceof ItemBlock)
                {
                    GL11.glTranslatef((float) d0 + 0.5F, (float) d1 + displacement + 0.25F, (float) d2 + 0.5F);
                }
                else
                {
                    GL11.glTranslatef((float) d0 + 0.5F, (float) d1 + displacement + 0.25F, (float) d2 + 0.5F);
                }

                GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
                GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
                customRenderItem.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
            }

            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1F, 1F, 1F, 1F);
            Minecraft.getMinecraft().renderEngine.bindTexture(test);

            GL11.glTranslated(d0 + 0.5, d1 + 1.5, d2 + 0.5);
            GL11.glScalef(1F, -1F, -1F);
            modelLifeInfuser.render();
            GL11.glScalef(1F, -1F, -1F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);

            GL11.glPopMatrix();
        }
    }

    private float getGhostItemScaleFactor(ItemStack itemStack)
    {
        float scaleFactor = 5F;

        if (itemStack != null)
        {
            if (itemStack.getItem() instanceof ItemBlock)
            {
                switch (customRenderItem.getMiniBlockCount(itemStack, (byte) 1))
                {
                    case 1:
                        return 1.20F;

                    case 2:
                        return 1.20F;

                    case 3:
                        return 1.20F;

                    case 4:
                        return 1.20F;

                    case 5:
                        return 1.10F;

                    default:
                        return 1.20F;
                }
            } 
            else
            {
                switch (customRenderItem.getMiniItemCount(itemStack, (byte) 1))
                {
                    case 1:
                        return 0.95F;

                    case 2:
                        return 0.95F;

                    case 3:
                        return 0.95F;

                    case 4:
                        return 0.95F;

                    default:
                        return 0.95F;
                }
            }
        }

        return scaleFactor;
    }
}
