package com.arc.bloodarsenal.common.tinkers;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.io.IOException;

public abstract class TextureDerived extends TextureAtlasSprite
{
    private final String baseIcon;
    private final String basePath;
    private final TextureMapType type;

    public static enum TextureMapType
    {
        BLOCK(0, "textures/blocks"),  ITEM(1, "textures/items");

        public final int textureMapType;
        public final String basePath;

        private TextureMapType(int textureMapType, String basePath)
        {
            this.textureMapType = textureMapType;
            this.basePath = basePath;
        }
    }

    public TextureDerived(String p_i1282_1_, String baseIcon, TextureMapType type)
    {
        super(p_i1282_1_);
        this.baseIcon = baseIcon;
        this.type = type;
        this.basePath = type.basePath;
    }

    public static DirectColorModel rgbBase = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
    protected DirectColorModel rgb = rgbBase;

    public int getLuminosity(int col)
    {
        return getLuminosity(this.rgb.getRed(col), this.rgb.getGreen(col), this.rgb.getBlue(col));
    }

    public int getLuminosity(int r, int g, int b)
    {
        return (int)(r * 0.2126F + g * 0.7152F + b * 0.0722F);
    }

    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location)
    {
        return true;
    }

    public abstract BufferedImage processImage(BufferedImage paramBufferedImage, AnimationMetadataSection paramAnimationMetadataSection);

    public boolean load(IResourceManager manager, ResourceLocation oldlocation)
    {
        ResourceLocation location = new ResourceLocation(this.baseIcon);
        location = completeResourceLocation(location);
        try
        {
            int mipmapLevels = Minecraft.getMinecraft().gameSettings.mipmapLevels;
            int anisotropicFiltering = Minecraft.getMinecraft().gameSettings.anisotropicFiltering;
            IResource iresource = manager.getResource(location);
            BufferedImage[] abufferedimage = new BufferedImage[1 + mipmapLevels];
            abufferedimage[0] = ImageIO.read(iresource.getInputStream());
            AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)iresource.getMetadata("animation");
            abufferedimage[0] = processImage(abufferedimage[0], animationmetadatasection);
            loadSprite(abufferedimage, animationmetadatasection, anisotropicFiltering > 1.0F);
        }
        catch (RuntimeException runtimeexception)
        {
            FMLClientHandler.instance().trackBrokenTexture(location, runtimeexception.getMessage());
            return true;
        }
        catch (IOException ioexception1)
        {
            FMLClientHandler.instance().trackMissingTexture(location);
            return true;
        }
        return false;
    }

    private ResourceLocation completeResourceLocation(ResourceLocation p_147634_1_)
    {
        return new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/%s%s", new Object[] { this.basePath, p_147634_1_.getResourcePath(), ".png" }));
    }
}
