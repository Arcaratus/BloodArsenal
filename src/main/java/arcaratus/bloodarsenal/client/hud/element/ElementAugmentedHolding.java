package arcaratus.bloodarsenal.client.hud.element;

import WayofTime.bloodmagic.client.Sprite;
import WayofTime.bloodmagic.client.hud.element.HUDElement;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.item.sigil.ItemSigilAugmentedHolding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ElementAugmentedHolding extends HUDElement
{
    private static final Sprite HOLDING_BAR = new Sprite(new ResourceLocation(BloodArsenal.MOD_ID, "textures/gui/widgets.png"), 0, 0, 180, 22);
    private static final Sprite SELECTED_OVERLAY = new Sprite(new ResourceLocation(BloodArsenal.MOD_ID, "textures/gui/widgets.png"), 0, 22, 24, 24);

    public ElementAugmentedHolding()
    {
        super(HOLDING_BAR.getTextureWidth(), HOLDING_BAR.getTextureHeight());
    }

    @Override
    public void draw(ScaledResolution scaledResolution, float partialTicks, int drawX, int drawY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        HOLDING_BAR.draw(drawX - 10, drawY);

        Minecraft minecraft = Minecraft.getMinecraft();
        ItemStack sigilHolding = minecraft.player.getHeldItemMainhand();
        if (!(sigilHolding.getItem() == RegistrarBloodArsenalItems.SIGIL_AUGMENTED_HOLDING))
            sigilHolding = minecraft.player.getHeldItemOffhand();
        // Check offhand for Sigil of Holding
        if (sigilHolding.getItem() != RegistrarBloodArsenalItems.SIGIL_AUGMENTED_HOLDING)
            return;

        int currentSlot = ItemSigilAugmentedHolding.getCurrentItemOrdinal(sigilHolding);
        SELECTED_OVERLAY.draw(drawX - 11  + (currentSlot * 20 - (currentSlot > 4 ? 1 : 0)), drawY - 1);

        RenderHelper.enableGUIStandardItemLighting();
        List<ItemStack> holdingInv = ItemSigilAugmentedHolding.getInternalInventory(sigilHolding);
        int xOffset = 0;
        for (ItemStack sigil : holdingInv)
        {
            renderHotbarItem(drawX - 7 + xOffset - (xOffset == 80 ? 1 : xOffset > 80 ? 2 : 0), drawY + 3, partialTicks, minecraft.player, sigil);
            xOffset += 20;
        }

        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public boolean shouldRender(Minecraft minecraft)
    {
        ItemStack sigilHolding = minecraft.player.getHeldItemMainhand();
        // Check mainhand for Sigil of Holding
        if (!(sigilHolding.getItem() == RegistrarBloodArsenalItems.SIGIL_AUGMENTED_HOLDING))
            sigilHolding = minecraft.player.getHeldItemOffhand();
        // Check offhand for Sigil of Holding
        if (!(sigilHolding.getItem() == RegistrarBloodArsenalItems.SIGIL_AUGMENTED_HOLDING))
            return false;

        return true;
    }

    protected void renderHotbarItem(int x, int y, float partialTicks, EntityPlayer player, ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            float animation = (float) stack.getAnimationsToGo() - partialTicks;

            if (animation > 0.0F)
            {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + animation / 5.0F;
                GlStateManager.translate((float) (x + 8), (float) (y + 12), 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((float) (-(x + 8)), (float) (-(y + 12)), 0.0F);
            }

            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(player, stack, x, y);

            if (animation > 0.0F)
                GlStateManager.popMatrix();

            Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRenderer, stack, x, y);
        }
    }
}
