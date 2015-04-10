package com.arc.bloodarsenal;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * LifeInfuser - Arcaratus
 * Created using Tabula 4.1.1
 */
public class LifeInfuser extends ModelBase {
    public ModelRenderer Base;
    public ModelRenderer Pillar1;
    public ModelRenderer Pillar2;
    public ModelRenderer Pillar3;
    public ModelRenderer Pillar4;
    public ModelRenderer Plate1;
    public ModelRenderer Plate2;

    public LifeInfuser() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Pillar4 = new ModelRenderer(this, 0, 0);
        this.Pillar4.setRotationPoint(6.0F, 10.0F, -8.0F);
        this.Pillar4.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.Pillar3 = new ModelRenderer(this, 0, 0);
        this.Pillar3.setRotationPoint(6.0F, 10.0F, 6.0F);
        this.Pillar3.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.Plate2 = new ModelRenderer(this, 0, 0);
        this.Plate2.setRotationPoint(-2.0F, 19.0F, -2.0F);
        this.Plate2.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, 0.0F);
        this.Plate1 = new ModelRenderer(this, 0, 0);
        this.Plate1.setRotationPoint(-4.0F, 20.0F, -4.0F);
        this.Plate1.addBox(0.0F, 0.0F, 0.0F, 8, 2, 8, 0.0F);
        this.Base = new ModelRenderer(this, 0, 0);
        this.Base.setRotationPoint(-8.0F, 22.0F, -8.0F);
        this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 2, 16, 0.0F);
        this.Pillar1 = new ModelRenderer(this, 0, 0);
        this.Pillar1.setRotationPoint(-8.0F, 10.0F, -8.0F);
        this.Pillar1.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.Pillar2 = new ModelRenderer(this, 0, 0);
        this.Pillar2.setRotationPoint(-8.0F, 10.0F, 6.0F);
        this.Pillar2.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Pillar4.render(f5);
        this.Pillar3.render(f5);
        this.Plate2.render(f5);
        this.Plate1.render(f5);
        this.Base.render(f5);
        this.Pillar1.render(f5);
        this.Pillar2.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
