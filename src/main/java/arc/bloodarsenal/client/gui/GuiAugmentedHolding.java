package arc.bloodarsenal.client.gui;

import WayofTime.bloodmagic.item.sigil.ItemSigilHolding;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.item.inventory.ContainerAugmentedHolding;
import arc.bloodarsenal.item.inventory.InventoryAugmentedHolding;
import arc.bloodarsenal.registry.ModItems;
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
        super(new ContainerAugmentedHolding(player, inventoryHolding));
        xSize = 176;
        ySize = 121;
        this.player = player;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        //the parameters for drawString are: string, x, y, color
        fontRendererObj.drawString(TextHelper.localize("item.bloodarsenal.sigil.augmentedHolding.name"), 28, 4, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouse)
    {
        //draw your Gui here, only thing you need to change is the path
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        if (player.getHeldItemMainhand().getItem() == ModItems.SIGIL_AUGMENTED_HOLDING)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int ordinal = ItemSigilHolding.getCurrentItemOrdinal(player.getHeldItemMainhand());
            this.drawTexturedModalRect(5 + x + 18 * ordinal - (int) (0.5 * ordinal), y + 14, 0, 123, 22, 22);
        }
    }
}