package com.arc.bloodarsenal.client.renderer.block;

import com.arc.bloodarsenal.client.renderer.model.ModelLifeInfuser;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTileLifeInfuser implements IItemRenderer
{
    private ModelLifeInfuser modelLifeInfuser = new ModelLifeInfuser();
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type){

        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper){

        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data){

        switch(type){
            case ENTITY: {
                render(0.0F, 0.0F, 1.0F, 1.0F);
                return;
            }
            case EQUIPPED: {
                render(0.5F, -0.5F, 2F, 1.0F);
                return;
            }
            case EQUIPPED_FIRST_PERSON: {
                render(0.7F, -0.7F, 1.9F, 1.0F);
                return;
            }
            case INVENTORY: {
                render(0.0F, 0.0F, 1.0F, 1.0F);
                return;
            }
            default:
                return;
        }
    }

    private void render(float x, float y, float z, float scale){

        GL11.glPushMatrix();
        GL11.glRotatef(-90F, 1F, 0, 0);
        // GL11.glDisable(GL11.GL_LIGHTING);

        // Scale, Translate, Rotate
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x, y, z);
        GL11.glRotatef(-90F, 1F, 0, 0);

        ResourceLocation test = new ResourceLocation("bloodarsenal:textures/models/LifeInfuser.png");
        // Bind texture
        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(test);

        // Render
        modelLifeInfuser.render();

        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}