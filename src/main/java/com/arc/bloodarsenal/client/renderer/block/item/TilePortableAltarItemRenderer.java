package com.arc.bloodarsenal.client.renderer.block.item;

import com.arc.bloodarsenal.client.renderer.model.ModelPortableBloodAltar;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TilePortableAltarItemRenderer implements IItemRenderer
{
    private ModelPortableBloodAltar modelBloodAltar;

    public TilePortableAltarItemRenderer()
    {
        modelBloodAltar = new ModelPortableBloodAltar();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        float scale = 0.08f;
        switch (type)
        {
            case ENTITY:
                renderBloodAltar((RenderBlocks) data[0], item, 0, 0, 0, scale);
                break;
            case EQUIPPED:
                renderBloodAltar((RenderBlocks) data[0], item, 0, 0, 0.5f, scale);
                break;
            case EQUIPPED_FIRST_PERSON:
                renderBloodAltar((RenderBlocks) data[0], item, +0.5f, 0.5f, +0.5f, scale);
                break;
            case INVENTORY:
                renderBloodAltar((RenderBlocks) data[0], item, -0.5f, -0.75f, -0.5f, scale);
                break;

            default:
                return;
        }
    }

    private void renderBloodAltar(RenderBlocks render, ItemStack item, float x, float y, float z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180f, 0f, 1f, 0f);
        ResourceLocation test = new ResourceLocation("bloodarsenal:textures/models/altar.png");
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(test);
        modelBloodAltar.renderBloodAltar();
        GL11.glPopMatrix();
    }
}
