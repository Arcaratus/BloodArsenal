package com.arc.bloodarsenal.common.tinkers;

import net.minecraft.util.MathHelper;

import java.awt.image.BufferedImage;

public class TextureResourcePackBloodInfusedIron extends TextureResourcePackBase
{
    static int[][] offsets = new int[][]{{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

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
        boolean[][] base = new boolean[w][h];
        int mean = 0;
        int div = 0;

        for(int n = 0; n < w; ++n)
        {
            for(int baseSilhouette = 0; baseSilhouette < h; ++baseSilhouette)
            {
                int interior1 = image.getRGB(n, baseSilhouette);
                pixels[n][baseSilhouette] = this.brightness(interior1);
                boolean baseCorners = interior1 != 0 && rgb.getAlpha(interior1) > 64;
                if(baseCorners)
                {
                    base[n][baseSilhouette] = true;
                    mean += pixels[n][baseSilhouette];
                    ++div;
                }
            }
        }

        mean = mean / div * 2 / 4;
        byte var26;
        if(w >= 256)
        {
            var26 = 5;
        }
        else if(w >= 128)
        {
            var26 = 4;
        }
        else if(w >= 64)
        {
            var26 = 3;
        }
        else if(w >= 32)
        {
            var26 = 2;
        }
        else
        {
            var26 = 1;
        }

        boolean[][] var27 = this.contract(base, var26);
        boolean[][] var28 = this.contract(var27, var26);
        boolean[][] interior2 = this.contract(var28, 2 * var26);
        boolean[][] interior3 = this.contract(interior2, var26);
        boolean[][] interior4 = this.contract(interior3, var26);
        boolean[][] interiorCorners = this.multI(this.mult(this.expand(this.getCorners(interior2), var26), interior2), interior3);
        boolean[][] interiorCornersShift = this.orwise(this.orwise(this.shift(interiorCorners, -1, 0), this.shift(interiorCorners, 0, -1)), this.shift(interiorCorners, -1, -1));
        byte trans = 0;
        int red = 16752029;
        int pink_highlight = 16752029;
        int iron = 16752029;
        int darkiron = 13592423;
        int[][] outpixels = new int[w][w];

        for(int x = 0; x < w; ++x)
        {
            for(int y = 0; y < h; ++y)
            {
                if(!var27[x][y])
                {
                    if(base[x][y])
                    {
                        outpixels[x][y] = this.multPixel(darkiron, pixels[x][y] / 2);
                    }
                    else
                    {
                        outpixels[x][y] = trans;
                    }
                }
                else if(interior2[x][y] && !interior3[x][y])
                {
                    if(interiorCorners[x][y])
                    {
                        if(interiorCornersShift[x][y])
                        {
                            outpixels[x][y] = this.multPixel(red, Math.max(pixels[x][y], mean));
                        }
                        else
                        {
                            outpixels[x][y] = this.multPixel(pink_highlight, Math.max(pixels[x][y], mean) + 5);
                        }
                    }
                    else
                    {
                        outpixels[x][y] = this.multPixel(red, pixels[x][y]);
                    }
                }
                else
                {
                    outpixels[x][y] = this.multPixel(iron, pixels[x][y]);
                }

                image.setRGB(x, y, outpixels[x][y]);
            }
        }

        return image;
    }

    private boolean[][] orwise(boolean[][] a, boolean[][] b)
    {
        for(int i = 0; i < a.length; ++i)
        {
            for(int j = 0; j < a[i].length; ++j)
            {
                a[i][j] |= b[i][j];
            }
        }

        return a;
    }

    private boolean[][] mult(boolean[][] a, boolean[][] b)
    {
        for(int i = 0; i < a.length; ++i)
        {
            for(int j = 0; j < a[i].length; ++j)
            {
                a[i][j] &= b[i][j];
            }
        }

        return a;
    }

    private boolean[][] multI(boolean[][] a, boolean[][] b)
    {
        for(int i = 0; i < a.length; ++i)
        {
            for(int j = 0; j < a[i].length; ++j)
            {
                a[i][j] &= !b[i][j];
            }
        }

        return a;
    }

    private boolean[][] expand(boolean[][] base, int n)
    {
        boolean[][] output = this.expand(base);

        for(int i = 0; i < n - 1; ++i)
        {
            output = this.expand(output);
        }

        return output;
    }

    private boolean[][] contract(boolean[][] base, int n)
    {
        boolean[][] output = this.contract(base);

        for(int i = 0; i < n - 1; ++i)
        {
            output = this.contract(output);
        }

        return output;
    }

    public int multPixel(int col, int b)
    {
        return -16777216 | this.clamp(rgb.getRed(col) * b / 255) << 16 | this.clamp(rgb.getGreen(col) * b / 255) << 8 | this.clamp(rgb.getBlue(col) * b / 255);
    }

    private int clamp(int i) {
        return MathHelper.clamp_int(i, 0, 255);
    }

    public boolean get(boolean[][] img, int x, int y)
    {
        return x >= 0 && y >= 0 && x < img.length && y < img[x].length && img[x][y];
    }

    public boolean[][] shift(boolean[][] img, int dx, int dy)
    {
        int w = img.length;
        boolean[][] img2 = new boolean[w][w];

        for(int x = Math.max(-dx, 0); x < Math.min(w, w + dx); ++x)
        {
            System.arraycopy(img[x + dx], Math.max(-dy, 0) + dy, img2[x], Math.max(-dy, 0), Math.min(w, w + dy) - Math.max(-dy, 0));
        }

        return img2;
    }

    public boolean[][] getCorners(boolean[][] img)
    {
        int w = img.length;
        boolean[][] img2 = new boolean[w][w];

        for(int x = 0; x < w; ++x)
        {
            for(int y = 0; y < w; ++y)
            {
                if(img[x][y]) {
                    int an = -1;
                    int n = 0;
                    int[][] arr$ = offsets;
                    int len$ = arr$.length;

                    for(int i$ = 0; i$ < len$; ++i$)
                    {
                        int[] offset = arr$[i$];
                        if(this.get(img, x + offset[0], y + offset[1]))
                        {
                            if(an == -1)
                            {
                                an = n;
                            }

                            n = 0;
                        }
                        else
                        {
                            ++n;
                            if(n == 5)
                            {
                                break;
                            }
                        }
                    }

                    if(an != -1)
                    {
                        n += an;
                    }

                    if(n >= 5)
                    {
                        img2[x][y] = true;
                    }
                }
            }
        }

        return img2;
    }

    public boolean[][] contract(boolean[][] img)
    {
        int w = img.length;
        boolean[][] img2 = new boolean[w][w];

        int x;
        for(x = 0; x < w; ++x)
        {
            System.arraycopy(img[x], 0, img2[x], 0, w);
        }

        for(x = 0; x < w; ++x)
        {
            for(int y = 0; y < w; ++y)
            {
                if(img[x][y])
                {
                    if(x == 0 || y == 0 || x == w - 1 || y == w - 1) {
                        img2[x][y] = false;
                    }
                }
                else
                {
                    if(x > 0)
                    {
                        img2[x - 1][y] = false;
                    }

                    if(y > 0)
                    {
                        img2[x][y - 1] = false;
                    }

                    if(x < w - 1)
                    {
                        img2[x + 1][y] = false;
                    }

                    if(y < w - 1)
                    {
                        img2[x][y + 1] = false;
                    }
                }
            }
        }

        return img2;
    }

    public boolean[][] expand(boolean[][] img)
    {
        int w = img.length;
        boolean[][] img2 = new boolean[w][w];

        int x;
        for(x = 0; x < w; ++x)
        {
            System.arraycopy(img[x], 0, img2[x], 0, w);
        }

        for(x = 0; x < w; ++x)
        {
            for(int y = 0; y < w; ++y)
            {
                if(img[x][y]) {
                    int[][] arr$ = offsets;
                    int len$ = arr$.length;

                    for(int i$ = 0; i$ < len$; ++i$)
                    {
                        int[] offset = arr$[i$];
                        int dx = x + offset[0];
                        int dy = y + offset[1];
                        if(dx >= 0 && dy >= 0 && dx < w && dy < w)
                        {
                            img2[dx][dy] = true;
                        }
                    }
                }
            }
        }

        return img2;
    }
}
