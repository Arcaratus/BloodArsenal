package com.arc.bloodarsenal.common.tinkers;

public abstract class TextureLiquidLava extends TextureDerived
{
    public TextureLiquidLava(String p_i1282_1_, String baseIcon)
    {
        super(p_i1282_1_, baseIcon, TextureDerived.TextureMapType.BLOCK);
    }
/*
    public BufferedImage processImage(BufferedImage image, AnimationMetadataSection animationmetadatasection)
    {
        int w = image.getWidth();
        int h = image.getHeight();

        int[] pixels = new int[h * w];
        image.getRGB(0, 0, w, h, pixels, 0, w);

        double mean = 0.0D;
        for (int i = 0; i < pixels.length; i++)
        {
            pixels[i] = getLuminosity(pixels[i]);
            mean += pixels[i];
        }
        mean /= pixels.length;

        BufferedImage bedrockImage = TextureResourcePackBloodInfusedIron.getBedrockImage();
        for (int i = 0; i < pixels.length; i++)
        {
            int x = i % w;
            int y = (i - x) / w % w;
            int sn = (i - x) / w / w;

            int dx = x * bedrockImage.getWidth() / w;
            int dy = y * bedrockImage.getHeight() / w;

            int col = bedrockImage.getRGB(dx, dy);

            double f = pixels[i] / mean;

            int r = clamp(this.rgb.getRed(col) * f);
            int g = clamp(this.rgb.getGreen(col) * f);
            int b = clamp(this.rgb.getBlue(col) * f);

            pixels[i] = (0xFF000000 | r << 16 | g << 8 | b);
        }
        image.setRGB(0, 0, w, h, pixels, 0, w);

        return image;
    }

    private int clamp(double v)
    {
        return MathHelper.func_76125_a((int) v, 0, 255);
    }
    */
}
