package com.arc.bloodarsenal.renderer.model;

import com.arc.bloodarsenal.tileentity.TileLifeInfuser;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * LifeInfuser - Arcaratus
 * Created using Tabula 4.0.2
 */
public class ModelLifeInfuser extends ModelBase
{
    private static final ResourceLocation texture = new ResourceLocation("bloodarsenal:textures/models/LifeInfuser.png");
    private IModelCustom modelLifeInfuser;

    public ModelRenderer Base;
    public ModelRenderer Pillar1;
    public ModelRenderer Pillar2;
    public ModelRenderer Pillar3;
    public ModelRenderer Pillar4;
    public ModelRenderer Plate1;
    public ModelRenderer Plate2;

    public ModelLifeInfuser()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Pillar2 = new ModelRenderer(this, 0, 0);
        this.Pillar2.mirror = true;
        this.Pillar2.setRotationPoint(-8.0F, 10.0F, 6.0F);
        this.Pillar2.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2);
        this.Pillar3 = new ModelRenderer(this, 0, 0);
        this.Pillar3.setRotationPoint(6.0F, 10.0F, 6.0F);
        this.Pillar3.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2);
        this.Pillar4 = new ModelRenderer(this, 0, 0);
        this.Pillar4.mirror = true;
        this.Pillar4.setRotationPoint(6.0F, 10.0F, -8.0F);
        this.Pillar4.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2);
        this.Base = new ModelRenderer(this, 0, 0);
        this.Base.setRotationPoint(-8.0F, 22.0F, -8.0F);
        this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 2, 16);
        this.Plate2 = new ModelRenderer(this, 48, 0);
        this.Plate2.setRotationPoint(-2.0F, 19.0F, -2.0F);
        this.Plate2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
        this.Plate1 = new ModelRenderer(this, 0, 18);
        this.Plate1.setRotationPoint(-4.0F, 20.0F, -4.0F);
        this.Plate1.addBox(0.0F, 0.0F, 0.0F, 8, 2, 8);
        this.Pillar1 = new ModelRenderer(this, 0, 0);
        this.Pillar1.setRotationPoint(-8.0F, 10.0F, -8.0F);
        this.Pillar1.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2);
    }

    public void render()
    {
        float f = 1F / 16F;
        this.Pillar2.render(f);
        this.Pillar3.render(f);
        this.Pillar4.render(f);
        this.Base.render(f);
        this.Plate2.render(f);
        this.Plate1.render(f);
        this.Pillar1.render(f);
    }

    public void renderLifeInfuser()
    {
        modelLifeInfuser.renderAll();
    }

    public void renderLifeInfuser(TileLifeInfuser lifeInfuser, double x, double y, double z)
    {
        float scale = 0.1f;
        // Push a blank matrix onto the stack
        GL11.glPushMatrix();
        // Move the object into the correct position on the block (because the OBJ's origin is the center of the object)
        GL11.glTranslatef((float) x + 0.5f, (float) y, (float) z + 0.5f);
        // Scale our object to about half-size in all directions (the OBJ file is a little large)
        GL11.glScalef(scale, scale, scale);
        // Bind the texture, so that OpenGL properly textures our block.
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        // Render the object, using modelTutBox.renderAll();
        this.renderLifeInfuser();
        // Pop this matrix from the stack.
        GL11.glPopMatrix();
    }

}
