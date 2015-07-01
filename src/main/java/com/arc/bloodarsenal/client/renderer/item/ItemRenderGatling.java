package com.arc.bloodarsenal.client.renderer.item;

import com.arc.bloodarsenal.common.BloodArsenalConfig;
import com.arc.bloodarsenal.client.renderer.model.ModelEnergyGatling;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRenderGatling implements IItemRenderer
{
    protected ModelEnergyGatling gatlingModel;

    private static final ResourceLocation resourceLocation = new ResourceLocation("bloodarsenal", "models/armor/EnergyGatling.png");
    private static final ResourceLocation resourceLocation2 = new ResourceLocation("bloodarsenal", "models/armor/EnergyGatling2.png");

    public ItemRenderGatling()
    {
        gatlingModel = new ModelEnergyGatling();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            case EQUIPPED: return true;
            default: return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

    double rx = 100.0D;
    double ry = 20.0D;
    double rz = -90.0D;
    double tx = 0.15D;
    double ty = 0.1D;
    double tz = -0.5D;

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        switch (type)
        {
            case EQUIPPED:
            {
                if (item.stackTagCompound.getBoolean("isActive"))
                {
                    GL11.glPushMatrix();

                    if (BloodArsenalConfig.isRedGood)
                    {
                        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation2);
                    }
                    else
                    {
                        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
                    }

                    GL11.glRotatef((float) rx, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef((float) ry, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef((float) rz, 0.0F, 0.0F, 1.0F);

                    GL11.glTranslatef((float) tx, (float) ty, (float) tz);

                    float scale = 1.8F;

                    GL11.glScalef(scale, scale, scale);

                    gatlingModel.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

                    GL11.glPopMatrix();
                }
            }
            default:
                break;
        }
    }
}
