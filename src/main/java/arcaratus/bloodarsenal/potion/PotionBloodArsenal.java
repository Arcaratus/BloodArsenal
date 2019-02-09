package arcaratus.bloodarsenal.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBloodArsenal extends Potion
{
    public static final ResourceLocation potionTexture = new ResourceLocation("bloodarsenal", "textures/misc/potions.png");

    public PotionBloodArsenal(String name, String regName, boolean badEffect, int potionColor, int iconIndexX, int iconIndexY)
    {
        super(badEffect, potionColor);
        setPotionName(name);
        setRegistryName(regName);
        setIconIndex(iconIndexX, iconIndexY);
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect)
    {
        return true;
    }

    @Override
    public boolean shouldRender(PotionEffect effect)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(potionTexture);
        return super.getStatusIconIndex();
    }
}