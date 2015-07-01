package com.arc.bloodarsenal.client.renderer.projectile;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderEntityBloodBall extends Render
{
    private Item field_94151_a;
    private int field_94150_f;

    public RenderEntityBloodBall(Item item, int par2)
    {
        this.field_94151_a = item;
        this.field_94150_f = par2;
    }

    public RenderEntityBloodBall(Item item)
    {
        this(item, 0);
    }

    public void doRender(Entity entity, double so, double me, double thing, float dont, float know)
    {
        IIcon iicon = this.field_94151_a.getIconFromDamage(this.field_94150_f);

        if (iicon != null)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)so, (float)me, (float)thing);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            this.bindEntityTexture(entity);
            Tessellator tessellator = Tessellator.instance;

            if (iicon == ItemPotion.func_94589_d("bottle_splash"))
            {
                int i = PotionHelper.func_77915_a(((EntityPotion) entity).getPotionDamage(), false);
                float f2 = (float)(i >> 16 & 255) / 255.0F;
                float f3 = (float)(i >> 8 & 255) / 255.0F;
                float f4 = (float)(i & 255) / 255.0F;
                GL11.glColor3f(f2, f3, f4);
                GL11.glPushMatrix();
                this.func_77026_a(tessellator, ItemPotion.func_94589_d("overlay"));
                GL11.glPopMatrix();
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }

            this.func_77026_a(tessellator, iicon);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    private void func_77026_a(Tessellator par1Tessellator, IIcon par2IIcon)
    {
        float f = par2IIcon.getMinU();
        float f1 = par2IIcon.getMaxU();
        float f2 = par2IIcon.getMinV();
        float f3 = par2IIcon.getMaxV();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
        par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
        par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
        par1Tessellator.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
        par1Tessellator.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
        par1Tessellator.draw();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("bloodarsenal", "textures/items/blood_ball.png");
    }
}
