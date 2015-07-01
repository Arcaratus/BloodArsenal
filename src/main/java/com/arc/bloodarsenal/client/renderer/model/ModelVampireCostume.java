package com.arc.bloodarsenal.client.renderer.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelVampireCostume extends ModelBiped
{
    ModelRenderer Cape;
    ModelRenderer CowlL1;
    ModelRenderer CowlR1;
    ModelRenderer CowlB;
    ModelRenderer CowlL2;
    ModelRenderer CowlR2;
    ModelRenderer GreaveR;
    ModelRenderer GreaveL;
    ModelRenderer ShoeBR;
    ModelRenderer ShoeRB;
    ModelRenderer ShoeLB;
    ModelRenderer ShoeBL;
    ModelRenderer ShoeL1;
    ModelRenderer ShoeR1;
    ModelRenderer ShoeL2;
    ModelRenderer ShoeR2;
    ModelRenderer ShoulderL1;
    ModelRenderer ShoulderR1;
    ModelRenderer ShoulderL2;
    ModelRenderer ShoulderR2;
    ModelRenderer ShoulderL3;
    ModelRenderer ShoulderR3;
    ModelRenderer ShoulderL4;
    ModelRenderer ShoulderR4;
  
    public ModelVampireCostume(float f, boolean addHelmet, boolean addChest, boolean addLeggings, boolean addBoots)
    {
        super(f, 0.0f, 64, 32);
        textureWidth = 64;
        textureHeight = 32;

        Cape = new ModelRenderer(this, 42, 0);
        Cape.addBox(0F, 0F, 0F, 10, 18, 1);
        Cape.setRotationPoint(-5F, 0F, 2F);
        Cape.setTextureSize(64, 32);
        Cape.mirror = true;
        setRotation(Cape, 0F, 0F, 0F);

        CowlL1 = new ModelRenderer(this, 0, 0);
        CowlL1.mirror = true;
        CowlL1.addBox(0F, 0F, 0F, 0, 4, 8);
        CowlL1.setRotationPoint(6F, -3F, -4F);//6F, -3F, -4F
        CowlL1.setTextureSize(64, 32);
        CowlL1.mirror = true;
        setRotation(CowlL1, 0F, 0F, 0.5235988F);

        CowlR1 = new ModelRenderer(this, 0, 0);
        CowlR1.addBox(0F, 0F, 0F, 0, 4, 8);
        CowlR1.setRotationPoint(-6F, -3F, -4F);//-6F, -3F, -4F
        CowlR1.setTextureSize(64, 32);
        CowlR1.mirror = true;
        setRotation(CowlR1, 0F, 0F, -0.5235988F);

        CowlB = new ModelRenderer(this, 16, 0);
        CowlB.addBox(0F, 0F, 0F, 8, 4, 0);
        CowlB.setRotationPoint(-4F, -3F, 6F);//-4, -3, 6
        CowlB.setTextureSize(64, 32);
        CowlB.mirror = true;
        setRotation(CowlB, -0.5235988F, 0F, 0F);

/*        CowlL2 = new ModelRenderer(this, 16, 4);
        CowlL2.mirror = true;
        CowlL2.addBox(0F, 0F, 0F, 0, 4, 3);
        CowlL2.setRotationPoint(4F, -3F, 6F);//4F, -3F, 6F
        CowlL2.setTextureSize(64, 32);
        CowlL2.mirror = true;
        setRotation(CowlL2, 0F, 2.356194F, 0.35F);

        CowlR2 = new ModelRenderer(this, 16, 4);
        CowlR2.addBox(0F, 0F, 0F, 0, 4, 3);
        CowlR2.setRotationPoint(-6F, -3F, 4F);//-6F, -3F, 4F
        CowlR2.setTextureSize(64, 32);
        CowlR2.mirror = true;
        setRotation(CowlR2, 0F, 0.7853982F, -0.3490659F);
*/
        GreaveR = new ModelRenderer(this, 0, 25);
        GreaveR.addBox(0F, 0F, 0F, 4, 6, 1);
        GreaveR.setRotationPoint(-2F, 3F, -3F);//-4F, 14F, -3F
        GreaveR.setTextureSize(64, 32);
        GreaveR.mirror = true;
        setRotation(GreaveR, 0F, 0F, 0F);

        GreaveL = new ModelRenderer(this, 0, 25);
        GreaveL.mirror = true;
        GreaveL.addBox(0F, 0F, 0F, 4, 6, 1);
        GreaveL.setRotationPoint(-2F, 3F, -3F);//0F, 14F, -3F
        GreaveL.setTextureSize(64, 32);
        GreaveL.mirror = true;
        setRotation(GreaveL, 0F, 0F, 0F);

        ShoeBR = new ModelRenderer(this, 0, 21);
        ShoeBR.addBox(0F, 0F, 0F, 4, 0, 4);
        ShoeBR.setRotationPoint(-2F, 13F, -2F);//-4F, 24F, -2F
        ShoeBR.setTextureSize(64, 32);
        ShoeBR.mirror = true;
        setRotation(ShoeBR, 0F, 0F, 0F);

        ShoeBL = new ModelRenderer(this, 0, 21);
        ShoeBL.mirror = true;
        ShoeBL.addBox(0F, 0F, 0F, 4, 0, 4);
        ShoeBL.setRotationPoint(-2F, 13F, -2F);//0F, 24F, -2F
        ShoeBL.setTextureSize(64, 32);
        ShoeBL.mirror = true;
        setRotation(ShoeBL, 0F, 0F, 0F);

        ShoeRB = new ModelRenderer(this, 2, 16);
        ShoeRB.addBox(0F, 0F, 0F, 4, 2, 0);
        ShoeRB.setRotationPoint(-2F, 11F, 2F);//-4F, 22F, 2F
        ShoeRB.setTextureSize(64, 32);
        ShoeRB.mirror = true;
        setRotation(ShoeRB, 0F, 0F, 0F);

        ShoeLB = new ModelRenderer(this, 2, 16);
        ShoeLB.mirror = true;
        ShoeLB.addBox(0F, 0F, 0F, 4, 2, 0);
        ShoeLB.setRotationPoint(-2F, 11F, 2F);//0F, 22F, 2F
        ShoeLB.setTextureSize(64, 32);
        ShoeLB.mirror = true;
        setRotation(ShoeLB, 0F, 0F, 0F);

        ShoeL1 = new ModelRenderer(this, 0, 9);
        ShoeL1.mirror = true;
        ShoeL1.addBox(0F, 0F, 0F, 0, 2, 4);
        ShoeL1.setRotationPoint(-2F, 11F, -2F);//4F, 22F, -2F
        ShoeL1.setTextureSize(64, 32);
        ShoeL1.mirror = true;
        setRotation(ShoeL1, 0F, 0F, 0F);

        ShoeR1 = new ModelRenderer(this, 0, 9);
        ShoeR1.addBox(0F, 0F, 0F, 0, 2, 4);
        ShoeR1.setRotationPoint(-2F, 11F, -2F);//-4F, 22F, -2F
        ShoeR1.setTextureSize(64, 32);
        ShoeR1.mirror = true;
        setRotation(ShoeR1, 0F, 0F, 0F);

        ShoeL2 = new ModelRenderer(this, 0, 16);
        ShoeL2.mirror = true;
        ShoeL2.addBox(0F, 0F, 0F, 4, 1, 2);
        ShoeL2.setRotationPoint(-2F, 12F, -4F);//0F, 23F, -4F
        ShoeL2.setTextureSize(64, 32);
        ShoeL2.mirror = true;
        setRotation(ShoeL2, 0F, 0F, 0F);

        ShoeR2 = new ModelRenderer(this, 0, 16);
        ShoeR2.addBox(0F, 0F, 0F, 4, 1, 2);
        ShoeR2.setRotationPoint(-2F, 12F, -4F);//-4F, 23F, -4F
        ShoeR2.setTextureSize(64, 32);
        ShoeR2.mirror = true;
        setRotation(ShoeR2, 0F, 0F, 0F);

        ShoulderL1 = new ModelRenderer(this, 42, 19);
        ShoulderL1.mirror = true;
        ShoulderL1.addBox(0F, 0F, 0F, 5, 1, 6);
        ShoulderL1.setRotationPoint(-1F, -3F, -3F);//-9F, -1F, -3F
        ShoulderL1.setTextureSize(64, 32);
        ShoulderL1.mirror = true;
        setRotation(ShoulderL1, 0F, 0F, 0F);

        ShoulderR1 = new ModelRenderer(this, 42, 19);
        ShoulderR1.addBox(0F, 0F, 0F, 5, 1, 6);
        ShoulderR1.setRotationPoint(-4F, -3F, -3F);//4F, -1F, -3F
        ShoulderR1.setTextureSize(64, 32);
        ShoulderR1.mirror = true;
        setRotation(ShoulderR1, 0F, 0F, 0F);

        ShoulderL2 = new ModelRenderer(this, 52, 26);
        ShoulderL2.mirror = true;
        ShoulderL2.addBox(0F, 0F, 0F, 5, 1, 1);
        ShoulderL2.setRotationPoint(-1F, -2F, -3F);//4F, -1F, -3F
        ShoulderL2.setTextureSize(64, 32);
        ShoulderL2.mirror = true;
        setRotation(ShoulderL2, 0F, 0F, 0F);

        ShoulderR2 = new ModelRenderer(this, 52, 26);
        ShoulderR2.addBox(0F, 0F, 0F, 5, 1, 1);
        ShoulderR2.setRotationPoint(-4F, -2F, -3F);//4F, 0F, -3F
        ShoulderR2.setTextureSize(64, 32);
        ShoulderR2.mirror = true;
        setRotation(ShoulderR2, 0F, 0F, 0F);

        ShoulderL3 = new ModelRenderer(this, 40, 26);
        ShoulderL3.mirror = true;
        ShoulderL3.addBox(0F, 0F, 0F, 1, 1, 5);
        ShoulderL3.setRotationPoint(3F, -2F, -2F);//-9F, 0F, -2F
        ShoulderL3.setTextureSize(64, 32);
        ShoulderL3.mirror = true;
        setRotation(ShoulderL3, 0F, 0F, 0F);

        ShoulderR3 = new ModelRenderer(this, 40, 26);
        ShoulderR3.addBox(0F, 0F, 0F, 1, 1, 5);
        ShoulderR3.setRotationPoint(-4F, -2F, -2F);//8F, 0F, -2F
        ShoulderR3.setTextureSize(64, 32);
        ShoulderR3.mirror = true;
        setRotation(ShoulderR3, 0F, 0F, 0F);

        ShoulderL4 = new ModelRenderer(this, 54, 28);
        ShoulderL4.mirror = true;
        ShoulderL4.addBox(0F, 0F, 0F, 4, 1, 1);
        ShoulderL4.setRotationPoint(-1F, -2F, 2F);//-8F, 0F, 2F
        ShoulderL4.setTextureSize(64, 32);
        ShoulderL4.mirror = true;
        setRotation(ShoulderL4, 0F, 0F, 0F);

        ShoulderR4 = new ModelRenderer(this, 54, 28);
        ShoulderR4.addBox(0F, 0F, 0F, 4, 1, 1);
        ShoulderR4.setRotationPoint(-3F, -2F, 2F);//4F, 0F, 2F
        ShoulderR4.setTextureSize(64, 32);
        ShoulderR4.mirror = true;
        setRotation(ShoulderR4, 0F, 0F, 0F);

        this.bipedHead.cubeList.clear();
        this.bipedHeadwear.cubeList.clear();
/*        if (addChest)
        {
            this.bipedHead.addChild(this.CowlL1);
            this.bipedHead.addChild(this.CowlL2);
            this.bipedHead.addChild(this.CowlR1);
            this.bipedHead.addChild(this.CowlR2);
        }
*/
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        if (addChest)
        {
            this.bipedBody.addChild(this.Cape);
            this.bipedLeftArm.addChild(this.ShoulderL1);
            this.bipedLeftArm.addChild(this.ShoulderL2);
            this.bipedLeftArm.addChild(this.ShoulderL3);
            this.bipedLeftArm.addChild(this.ShoulderL4);
            this.bipedRightArm.addChild(this.ShoulderR1);
            this.bipedRightArm.addChild(this.ShoulderR2);
            this.bipedRightArm.addChild(this.ShoulderR3);
            this.bipedRightArm.addChild(this.ShoulderR4);

            this.bipedBody.addChild(this.CowlB);
            this.bipedBody.addChild(this.CowlL1);
//            this.bipedBody.addChild(this.CowlL2);
            this.bipedBody.addChild(this.CowlR1);
//            this.bipedBody.addChild(this.CowlR2);
        }

        this.bipedLeftLeg.cubeList.clear();
        if (addLeggings)
        {
            this.bipedLeftLeg.addChild(this.GreaveL);
        }
        if (addBoots)
        {
            this.bipedLeftLeg.addChild(this.ShoeBL);
            this.bipedLeftLeg.addChild(this.ShoeL1);
            this.bipedLeftLeg.addChild(this.ShoeL2);
            this.bipedLeftLeg.addChild(this.ShoeLB);
        }

        this.bipedRightLeg.cubeList.clear();
        if (addLeggings)
        {
            this.bipedRightLeg.addChild(this.GreaveR);
        }
        if (addBoots)
        {
            this.bipedRightLeg.addChild(this.ShoeBR);
            this.bipedRightLeg.addChild(this.ShoeR1);
            this.bipedRightLeg.addChild(this.ShoeR2);
            this.bipedRightLeg.addChild(this.ShoeRB);
        }
    }
  
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        this.bipedHead.render(f5);
        this.bipedBody.render(f5);
        this.bipedRightArm.render(f5);
        this.bipedLeftArm.render(f5);
        this.bipedRightLeg.render(f5);
        this.bipedLeftLeg.render(f5);
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
