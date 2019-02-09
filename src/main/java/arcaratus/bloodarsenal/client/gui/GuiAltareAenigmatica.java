package arcaratus.bloodarsenal.client.gui;

import WayofTime.bloodmagic.altar.IBloodAltar;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.tile.TileAltareAenigmatica;
import arcaratus.bloodarsenal.tile.container.ContainerAltareAenigmatica;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

@SideOnly(Side.CLIENT)
public class GuiAltareAenigmatica extends GuiContainer
{
    private final ResourceLocation texture = new ResourceLocation(BloodArsenal.MOD_ID, "textures/gui/altare_aenigmatica.png");

    private IInventory tileAltareAenigmatica;

    public GuiAltareAenigmatica(InventoryPlayer inventoryPlayer, IInventory tileAltareAenigmatica)
    {
        super(new ContainerAltareAenigmatica(inventoryPlayer, tileAltareAenigmatica));
        this.tileAltareAenigmatica = tileAltareAenigmatica;
        xSize = 176;
        ySize = 163;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        fontRenderer.drawString(TextHelper.localize("tile.bloodarsenal.altare_aenigmatica.name"), 43, 6, 4210752);
        fontRenderer.drawString(TextHelper.localize("container.inventory"), 7, 71, 4210752);
        if (tileAltareAenigmatica instanceof TileAltareAenigmatica)
        {
            TileEntity altar = ((TileAltareAenigmatica) tileAltareAenigmatica).getWorld().getTileEntity(((TileAltareAenigmatica) tileAltareAenigmatica).getAltarPos());
            if (altar instanceof IBloodAltar)
            {
                GlStateManager.pushMatrix();

                GlStateManager.enableRescaleNormal();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
                mc.getRenderItem().renderItemAndEffectIntoGUI(altar.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP).getStackInSlot(0), 80, 26);
                net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
                GlStateManager.disableRescaleNormal();

                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(texture);
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;
        drawTexturedModalRect(i, j, 0, 0, xSize, ySize);
    }
}
