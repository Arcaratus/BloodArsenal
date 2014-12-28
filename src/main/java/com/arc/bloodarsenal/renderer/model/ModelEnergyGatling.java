package com.arc.bloodarsenal.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEnergyGatling extends ModelBase
{
    ModelRenderer Barrel1;
    ModelRenderer Barrel2;
    ModelRenderer Barrel3;
    ModelRenderer Barrel4;
    ModelRenderer Barrel5;
    ModelRenderer Barrel6;
    ModelRenderer Barrel7;
    ModelRenderer Barrel8;
    ModelRenderer Barrel9;
    ModelRenderer Barrel10;
    ModelRenderer Barrel11;
    ModelRenderer Barrel12;
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
  
    public ModelEnergyGatling()
    {
        textureWidth = 64;
        textureHeight = 32;
    
        Barrel1 = new ModelRenderer(this, 0, 0);
        Barrel1.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel1.setRotationPoint(0.5F, 8.5F, -13F);
        Barrel1.setTextureSize(64, 32);
        Barrel1.mirror = true;
        setRotation(Barrel1, 0F, 0F, 0F);

        Barrel2 = new ModelRenderer(this, 0, 0);
        Barrel2.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel2.setRotationPoint(-0.5F, 8.5F, -13F);
        Barrel2.setTextureSize(64, 32);
        Barrel2.mirror = true;
        setRotation(Barrel2, 0F, 0F, 0F);

        Barrel3 = new ModelRenderer(this, 0, 0);
        Barrel3.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel3.setRotationPoint(-1F, 9F, -13F);
        Barrel3.setTextureSize(64, 32);
        Barrel3.mirror = true;
        setRotation(Barrel3, 0F, 0F, 0F);

        Barrel4 = new ModelRenderer(this, 0, 0);
        Barrel4.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel4.setRotationPoint(1F, 9F, -13F);
        Barrel4.setTextureSize(64, 32);
        Barrel4.mirror = true;
        setRotation(Barrel4, 0F, 0F, 0F);

        Barrel5 = new ModelRenderer(this, 0, 0);
        Barrel5.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel5.setRotationPoint(-1.5F, 9.5F, -13F);
        Barrel5.setTextureSize(64, 32);
        Barrel5.mirror = true;
        setRotation(Barrel5, 0F, 0F, 0F);

        Barrel6 = new ModelRenderer(this, 0, 0);
        Barrel6.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel6.setRotationPoint(1.5F, 9.5F, -13F);
        Barrel6.setTextureSize(64, 32);
        Barrel6.mirror = true;
        setRotation(Barrel6, 0F, 0F, 0F);

        Barrel7 = new ModelRenderer(this, 0, 0);
        Barrel7.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel7.setRotationPoint(-1.5F, 10.5F, -13F);
        Barrel7.setTextureSize(64, 32);
        Barrel7.mirror = true;
        setRotation(Barrel7, 0F, 0F, 0F);

        Barrel8 = new ModelRenderer(this, 0, 0);
        Barrel8.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel8.setRotationPoint(1.5F, 10.5F, -13F);
        Barrel8.setTextureSize(64, 32);
        Barrel8.mirror = true;
        setRotation(Barrel8, 0F, 0F, 0F);

        Barrel9 = new ModelRenderer(this, 0, 0);
        Barrel9.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel9.setRotationPoint(-1F, 11F, -13F);
        Barrel9.setTextureSize(64, 32);
        Barrel9.mirror = true;
        setRotation(Barrel9, 0F, 0F, 0F);

        Barrel10 = new ModelRenderer(this, 0, 0);
        Barrel10.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel10.setRotationPoint(1F, 11F, -13F);
        Barrel10.setTextureSize(64, 32);
        Barrel10.mirror = true;
        setRotation(Barrel10, 0F, 0F, 0F);

        Barrel11 = new ModelRenderer(this, 0, 0);
        Barrel11.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel11.setRotationPoint(-0.5F, 11.5F, -13F);
        Barrel11.setTextureSize(64, 32);
        Barrel11.mirror = true;
        setRotation(Barrel11, 0F, 0F, 0F);

        Barrel12 = new ModelRenderer(this, 0, 0);
        Barrel12.addBox(0F, 0F, 0F, 1, 1, 16);
        Barrel12.setRotationPoint(0.5F, 11.5F, -13F);
        Barrel12.setTextureSize(64, 32);
        Barrel12.mirror = true;
        setRotation(Barrel12, 0F, 0F, 0F);

        Shape1 = new ModelRenderer(this, 0, 17);
        Shape1.addBox(0F, 0F, 0F, 5, 5, 5);
        Shape1.setRotationPoint(-2F, 8F, 0F);
        Shape1.setTextureSize(64, 32);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);

        Shape2 = new ModelRenderer(this, 0, 0);
        Shape2.addBox(0F, 0F, 0F, 1, 3, 1);
        Shape2.setRotationPoint(-1F, 5F, 4F);
        Shape2.setTextureSize(64, 32);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, 0F, 0F);

        Shape3 = new ModelRenderer(this, 0, 4);
        Shape3.addBox(0F, 0F, 0F, 1, 1, 1);
        Shape3.setRotationPoint(-1F, 5F, 3F);
        Shape3.setTextureSize(64, 32);
        Shape3.mirror = true;
        setRotation(Shape3, 0F, 0F, 0F);
    }
  
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Barrel1.render(f5);
        Barrel2.render(f5);
        Barrel3.render(f5);
        Barrel4.render(f5);
        Barrel5.render(f5);
        Barrel6.render(f5);
        Barrel7.render(f5);
        Barrel8.render(f5);
        Barrel9.render(f5);
        Barrel10.render(f5);
        Barrel11.render(f5);
        Barrel12.render(f5);
        Shape1.render(f5);
        Shape2.render(f5);
        Shape3.render(f5);
    }
  
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
  
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
