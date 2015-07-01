package com.arc.bloodarsenal.client.renderer.entity;

import com.arc.bloodarsenal.common.entity.mob.EntityBloodHound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderEntityBloodHound extends RenderLiving
{
    private static final ResourceLocation wolfTextures = new ResourceLocation("textures/entity/wolf/wolf.png");
    private static final ResourceLocation tamedWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
    private static final ResourceLocation anrgyWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

    public RenderEntityBloodHound(ModelBase p_i1269_1_, ModelBase p_i1269_2_, float p_i1269_3_)
    {
        super(p_i1269_1_, p_i1269_3_);
        this.setRenderPassModel(p_i1269_2_);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityBloodHound p_77044_1_, float p_77044_2_)
    {
        return p_77044_1_.getTailRotation();
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityBloodHound p_77032_1_, int p_77032_2_, float p_77032_3_)
    {
        if (p_77032_2_ == 0 && p_77032_1_.getWolfShaking())
        {
            float f1 = p_77032_1_.getBrightness(p_77032_3_) * p_77032_1_.getShadingWhileShaking(p_77032_3_);
            this.bindTexture(wolfTextures);
            GL11.glColor3f(f1, f1, f1);
            return 1;
        }
        else
        {
            return -1;
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityBloodHound p_110775_1_)
    {
        return p_110775_1_.isTamed() ? tamedWolfTextures : (p_110775_1_.isAngry() ? anrgyWolfTextures : wolfTextures);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
    {
        return this.shouldRenderPass((EntityBloodHound)p_77032_1_, p_77032_2_, p_77032_3_);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLivingBase p_77044_1_, float p_77044_2_)
    {
        return this.handleRotationFloat((EntityBloodHound)p_77044_1_, p_77044_2_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityBloodHound)p_110775_1_);
    }
}
