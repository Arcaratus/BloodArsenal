package com.arc.bloodarsenal.renderer.item;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderBow implements IItemRenderer
{
    public static final String[] ITEM_IN_USE = new String[] { "itemInUse", "field_71074_e", "f" };
    public static final String[] ITEM_IN_USE_COUNT = new String[] { "itemInUseCount", "field_71072_f", "g" };

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        switch(type)
        {
            case ENTITY :
            {
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.5F, 0F, 0F);
                if (item.isOnItemFrame()) GL11.glTranslatef(0F, -0.3F, 0.01F);
                render(item, null, false);
                GL11.glPopMatrix();
                break;
            }

            case EQUIPPED :
            {
                render(item, data[1] instanceof EntityPlayer ? (EntityPlayer) data[1] : null, true);
                break;
            }

            case EQUIPPED_FIRST_PERSON :
            {
                render(item, data[1] instanceof EntityPlayer ? (EntityPlayer) data[1] : null, false);
                break;
            }

            default : break;
        }
    }

    public void render(ItemStack item, EntityPlayer player, boolean transform)
    {
        int dmg = item.getItemDamage();
        IIcon icon = item.getItem().getIconFromDamageForRenderPass(dmg, 0);

        if (player != null)
        {
            ItemStack using = ReflectionHelper.getPrivateValue(EntityPlayer.class, player, ITEM_IN_USE);
            int time = ReflectionHelper.getPrivateValue(EntityPlayer.class, player, ITEM_IN_USE_COUNT);
            icon = item.getItem().getIcon(item, 0, player, using, time);

            if (transform)
            {
                GL11.glTranslatef(0.2F, -0.3F, 0.1F);
                //GL11.glRotatef(20.0F, 0.0F, 1.0F, 0.0F);
                //GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            }
        }

        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        float scale = 1F / 16F;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), scale);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

        GL11.glColor4f(1F, 1F, 1F, 1F);
    }
}
