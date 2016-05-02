package arc.bloodarsenal.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBloodArsenal extends Potion
{
    public static final ResourceLocation potionTexture = new ResourceLocation("bloodarsenal", "textures/misc/potions.png");

    public PotionBloodArsenal(String name, boolean badEffect, int potionColor, int iconIndexX, int iconIndexY)
    {
        super(badEffect, potionColor);
        this.setPotionName(name);
        this.setIconIndex(iconIndexX, iconIndexY);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(potionTexture);
        return super.getStatusIconIndex();
    }
}