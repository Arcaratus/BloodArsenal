package com.arc.bloodarsenal.common.tinkers;

import net.minecraft.util.MathHelper;

import java.awt.image.BufferedImage;

public class TextureResourcePackBloodInfusedIron extends TextureResourcePackBase
{
    public TextureResourcePackBloodInfusedIron(String name)
    {
        super(name);
    }

    public BufferedImage modifyImage(BufferedImage image)
    {
        int w = image.getWidth();
        int h = image.getHeight();

        image.getType();

        int[][] pixels = new int[w][h];
        int[][] base = new int[w][h];

        int mean = 0;
        int div = 0;
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                int c = image.getRGB(x, y);
                pixels[x][y] = brightness(c);
                boolean nottrans = (c != 0) && (rgb.getAlpha(c) > 64);
                if (nottrans)
                {
                    base[x][y] = 1;
                    mean += pixels[x][y];
                    div++;
                }
            }
        }
        mean = mean / div * 2 / 4;
        int n;
        if (w >= 256)
        {
            n = 5;
        }
        else
        {
            if (w >= 128)
            {
                n = 4;
            }
            else
            {
                if (w >= 64)
                {
                    n = 3;
                }
                else
                {
                    if (w >= 32)
                    {
                        n = 2;
                    } else
                    {
                        n = 1;
                    }
                }
            }
        }
        int[][] baseSilhouette = contract(base, n);

        int[][] interior1 = contract(baseSilhouette, n);

        int[][] baseCorners = multI(mult(expand(getCorners(baseSilhouette), n), baseSilhouette), interior1);
        int[][] baseCornersShift = orwise(orwise(shift(baseCorners, 0, -1), shift(baseCorners, -1, 0)), shift(baseCorners, -1, -1));

        int[][] interior2 = contract(interior1, 2 * n);

        int[][] interior3 = contract(interior2, n);
        int[][] interior4 = contract(interior3, n);

        int[][] interiorCorners = multI(mult(expand(getCorners(interior2), n), interior2), interior3);
        int[][] interiorCornersShift = orwise(orwise(shift(interiorCorners, -1, 0), shift(interiorCorners, 0, -1)), shift(interiorCorners, -1, -1));

        int trans = 0;
        int gold = -398001;
        int gold_highlight = -117;
        int wood = -6455217;
        int darkwood = -10071758;

        int[][] outpixels = new int[w][w];
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                if (baseSilhouette[x][y] == 0)
                {
                    if (base[x][y] != 0)
                    {
                        outpixels[x][y] = multPixel(darkwood, pixels[x][y] / 2);
                    }
                    else
                    {
                        outpixels[x][y] = trans;
                    }
                }
                else if (interior1[x][y] == 0)
                {
                    if (baseCorners[x][y] != 0)
                    {
                        if (baseCornersShift[x][y] != 0)
                        {
                            outpixels[x][y] = multPixel(gold, Math.max(pixels[x][y], mean));
                        }
                        else
                        {
                            outpixels[x][y] = multPixel(gold_highlight, Math.max(pixels[x][y], mean) + 5);
                        }
                    }
                    else
                    {
                        outpixels[x][y] = multPixel(darkwood, pixels[x][y]);
                    }
                }
                else if ((interior2[x][y] == 0) || (interior3[x][y] != 0))
                {
                    if ((interior3[x][y] != 0) && (interior4[x][y] == 0))
                    {
                        outpixels[x][y] = multPixel(wood, pixels[x][y] * 3 / 4);
                    }
                    else
                    {
                        outpixels[x][y] = multPixel(wood, pixels[x][y]);
                    }
                }
                else if (interiorCorners[x][y] != 0)
                {
                    if (interiorCornersShift[x][y] != 0)
                    {
                        outpixels[x][y] = multPixel(gold, Math.max(pixels[x][y], mean));
                    }
                    else
                    {
                        outpixels[x][y] = multPixel(gold_highlight, Math.max(pixels[x][y], mean) + 5);
                    }
                }
                else
                {
                    outpixels[x][y] = multPixel(darkwood, pixels[x][y]);
                }
                image.setRGB(x, y, outpixels[x][y]);
            }
        }
        return image;
    }

    private int[][] orwise(int[][] a, int[][] b)
    {
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[i].length; j++)
            {
                a[i][j] |= b[i][j];
            }
        }
        return a;
    }

    private int[][] mult(int[][] a, int[][] b)
    {
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[i].length; j++)
            {
                a[i][j] &= b[i][j];
            }
        }
        return a;
    }

    private int[][] multI(int[][] a, int[][] b)
    {
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[i].length; j++)
            {
                a[i][j] &= (b[i][j] == 0 ? 1 : 0);
            }
        }
        return a;
    }

    private int[][] expand(int[][] base, int n)
    {
        int[][] output = expand(base);
        for (int i = 0; i < n - 1; i++)
        {
            output = expand(output);
        }
        return output;
    }

    private int[][] contract(int[][] base, int n)
    {
        int[][] output = contract(base);
        for (int i = 0; i < n - 1; i++)
        {
            output = contract(output);
        }
        return output;
    }

    public int multPixel(int col, int b)
    {
        return 0xFF000000 | clamp(rgb.getRed(col) * b / 255) << 16 | clamp(rgb.getGreen(col) * b / 255) << 8 | clamp(rgb.getBlue(col) * b / 255);
    }

    private int clamp(int i)
    {
        return MathHelper.clamp_int(i, 0, 255);
    }

    public boolean get(int[][] img, int x, int y)
    {
        return (x >= 0) && (y >= 0) && (x < img.length) && (y < img[x].length) && (img[x][y] != 0);
    }

    static int[][] offsets = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } };

    public int[][] shift(int[][] img, int dx, int dy)
    {
        int w = img.length;
        int[][] img2 = new int[w][w];
        for (int x = Math.max(-dx, 0); x < Math.min(w, w + dx); x++)
        {
            System.arraycopy(img[(x + dx)], Math.max(-dy, 0) + dy, img2[x], Math.max(-dy, 0), Math.min(w, w + dy) - Math.max(-dy, 0));
        }
        return img2;
    }

    public int[][] getCorners(int[][] img)
    {
        int w = img.length;
        int[][] img2 = new int[w][w];
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < w; y++)
            {
                if (img[x][y] != 0)
                {
                    int an = -1;
                    int n = 0;
                    for (int[] offset : offsets)
                    {
                        if (get(img, x + offset[0], y + offset[1]))
                        {
                            if (an == -1)
                            {
                                an = n;
                            }
                            n = 0;
                        }
                        else
                        {
                            n++;
                            if (n == 5)
                            {
                                break;
                            }
                        }
                    }
                    if (an != -1)
                    {
                        n += an;
                    }
                    if (n >= 5)
                    {
                        img2[x][y] = 1;
                    }
                }
            }
        }
        return img2;
    }

    public int[][] contract(int[][] img)
    {
        int w = img.length;
        int[][] img2 = new int[w][w];
        for (int x = 0; x < w; x++)
        {
            System.arraycopy(img[x], 0, img2[x], 0, w);
        }
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < w; y++)
            {
                if (img[x][y] != 0)
                {
                    if ((x == 0) || (y == 0) || (x == w - 1) || (y == w - 1))
                    {
                        img2[x][y] = 0;
                    }
                }
                else
                {
                    if (x > 0)
                    {
                        img2[(x - 1)][y] = 0;
                    }
                    if (y > 0)
                    {
                        img2[x][(y - 1)] = 0;
                    }
                    if (x < w - 1)
                    {
                        img2[(x + 1)][y] = 0;
                    }
                    if (y < w - 1)
                    {
                        img2[x][(y + 1)] = 0;
                    }
                }
            }
        }
        return img2;
    }

    public int[][] expand(int[][] img)
    {
        int w = img.length;
        int[][] img2 = new int[w][w];
        for (int x = 0; x < w; x++)
        {
            System.arraycopy(img[x], 0, img2[x], 0, w);
        }
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < w; y++)
            {
                if (img[x][y] != 0)
                {
                    for (int[] offset : offsets)
                    {
                        int dx = x + offset[0];
                        int dy = y + offset[1];
                        if ((dx >= 0) && (dy >= 0) && (dx < w) && (dy < w))
                        {
                            img2[dx][dy] = 1;
                        }
                    }
                }
            }
        }
        return img2;
    }
}
