package arcaratus.bloodarsenal.client.gui;

import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.core.RegistrarBloodArsenalItems;
import arcaratus.bloodarsenal.item.inventory.ContainerAugmentedHolding;
import arcaratus.bloodarsenal.item.inventory.InventoryAugmentedHolding;
import arcaratus.bloodarsenal.item.sigil.ItemSigilAugmentedHolding;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAugmentedHolding extends GuiContainer
{
    private final ResourceLocation texture = new ResourceLocation(BloodArsenal.MOD_ID, "textures/gui/sigil_augmented_holding.png");

    private EntityPlayer player;

    public GuiAugmentedHolding(EntityPlayer player, InventoryAugmentedHolding inventoryHolding)
    {
        super(new ContainerAugmentedHolding(player, inventoryHolding, player.getHeldItemMainhand()));
        xSize = 176;
        ySize = 123;
        this.player = player;
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
        //the parameters for drawString are: string, x, y, color
        fontRenderer.drawString(TextHelper.localize("item.bloodarsenal.sigil.augmented_holding.name"), 25, 4, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouse)
    {
        //draw your Gui here, only thing you need to change is the path
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        if (player.getHeldItemMainhand().getItem() == RegistrarBloodArsenalItems.SIGIL_AUGMENTED_HOLDING)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int ordinal = ItemSigilAugmentedHolding.getCurrentItemOrdinal(player.getHeldItemMainhand());
            drawTexturedModalRect(5 + x + ordinal * 18, y + 14, 0, 123, 22, 22);
        }
    }
}