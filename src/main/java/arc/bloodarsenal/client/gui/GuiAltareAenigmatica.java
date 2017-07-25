package arc.bloodarsenal.client.gui;

import WayofTime.bloodmagic.api.altar.IBloodAltar;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.tile.TileAltareAenigmatica;
import arc.bloodarsenal.tile.TileInventory;
import arc.bloodarsenal.tile.container.ContainerAltareAenigmatica;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
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
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(TextHelper.localize("tile.bloodarsenal.altareAenigmatica.name"), 43, 6, 4210752);
        this.fontRendererObj.drawString(TextHelper.localize("container.inventory"), 7, 71, 4210752);

        if (tileAltareAenigmatica instanceof TileAltareAenigmatica)
        {
            TileInventory altar = (((TileInventory) ((TileAltareAenigmatica) tileAltareAenigmatica).getWorld().getTileEntity(((TileAltareAenigmatica) tileAltareAenigmatica).getAltarPos())));
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
        this.mc.getTextureManager().bindTexture(texture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}
