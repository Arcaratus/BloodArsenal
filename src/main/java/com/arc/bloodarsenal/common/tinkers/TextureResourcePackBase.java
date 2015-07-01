package com.arc.bloodarsenal.common.tinkers;

import amerifrance.guideapi.util.LogHelper;
import com.google.common.base.Throwables;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.*;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class TextureResourcePackBase implements IResourcePack, IResourceManagerReloadListener
{
    public static List<IResourcePack> packs;
    protected static DirectColorModel rgb = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
    protected final String name;
    public HashMap<ResourceLocation, byte[]> cachedImages = new HashMap();
    protected IResourcePack delegate;
    protected List<IResourcePack> resourcePacks = null;

    public TextureResourcePackBase(String name)
    {
        this.name = name.toLowerCase();
        this.delegate = FMLClientHandler.instance().getResourcePackFor("TConstruct");
    }

    public int brightness(int col)
    {
        return brightness(rgb.getRed(col), rgb.getGreen(col), rgb.getBlue(col));
    }

    public int brightness(int r, int g, int b)
    {
        return (int)(r * 0.2126F + g * 0.7152F + b * 0.0722F);
    }

    public void register()
    {
        List<IResourcePack> packs = getResourcePacks();
        packs.add(this);
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
        LogHelper.info("Registered TConstruct Resource Pack (" + this.name + ") - " + getClass().getSimpleName());
    }

    public List<IResourcePack> getResourcePacks()
    {
        List<IResourcePack> packs1 = packs;
        if (packs1 == null) {
            packs1 = ObfuscationReflectionHelper.getPrivateValue(FMLClientHandler.class, FMLClientHandler.instance(), new String[]{"resourcePackList"});
        }
        return packs1;
    }

    public InputStream getStream(ResourceLocation location)
    {
        InputStream stream = null;

        for (IResourcePack iResourcePack : getPacks())
        {
            if (iResourcePack.resourceExists(location))
            {
                try
                {
                    stream = iResourcePack.getInputStream(location);
                }
                catch (IOException ignore) {}
            }
        }
        return stream;
    }

    public List<IResourcePack> getPacks()
    {
        if (this.resourcePacks == null)
        {
            this.resourcePacks = new ArrayList();
            this.resourcePacks.add(this.delegate);
            List<ResourcePackRepository.Entry> t = Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntriesAll();
            for (ResourcePackRepository.Entry entry : t)
            {
                IResourcePack resourcePack = entry.getResourcePack();
                if (resourcePack.getPackName().contains("tinker"))
                {
                    this.resourcePacks.add(resourcePack);
                }
            }
        }
        return this.resourcePacks;
    }

    @Override
    public InputStream getInputStream(ResourceLocation p_110590_1_) throws IOException
    {
        byte[] bytes = this.cachedImages.get(p_110590_1_);
        if (bytes == null)
        {
            ResourceLocation location = new ResourceLocation("tinker", p_110590_1_.getResourceDomain().replace(this.name, ""));
            InputStream inputStream = getStream(location);
            if (inputStream == null)
            {
                location = new ResourceLocation("tinker", p_110590_1_.getResourceDomain().replace(this.name, "iron"));
                inputStream = getStream(location);
            }
            if (inputStream == null)
            {
                location = new ResourceLocation("tinker", p_110590_1_.getResourceDomain().replace(this.name, "stone"));
                inputStream = getStream(location);
            }
            if (inputStream == null)
            {
                return this.delegate.getInputStream(p_110590_1_);
            }
            BufferedImage bufferedimage;
            try
            {
                bufferedimage = ImageIO.read(inputStream);
            }
            catch (IOException err)
            {
                throw Throwables.propagate(err);
            }
            BufferedImage image;
            try
            {
                image = modifyImage(bufferedimage);
            }
            catch (Throwable t)
            {
                t.printStackTrace();
                return this.delegate.getInputStream(location);
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", stream);
            bytes = stream.toByteArray();
            this.cachedImages.put(location, bytes);
        }
        return new ByteArrayInputStream(bytes);
    }

    public abstract BufferedImage modifyImage(BufferedImage paramBufferedImage);

    @Override
    public boolean resourceExists(ResourceLocation p_110589_1_)
    {
        if (!"tinker".equals(p_110589_1_.getResourceDomain()))
        {
            return false;
        }
        String resourcePath = p_110589_1_.getResourcePath();
        if ((!resourcePath.startsWith("textures/items/")) || (!resourcePath.endsWith(".png")))
        {
            return false;
        }
        if (this.delegate.resourceExists(p_110589_1_))
        {
            return false;
        }
        if (!resourcePath.contains(this.name))
        {
            return false;
        }
        return (this.delegate.resourceExists(new ResourceLocation("tinker", resourcePath.replace(this.name, "stone")))) || (this.delegate.resourceExists(new ResourceLocation("tinker", resourcePath.replace(this.name, "iron")))) || (this.delegate.resourceExists(new ResourceLocation("tinker", resourcePath.replace(this.name, ""))));
    }

    @Override
    public Set getResourceDomains()
    {
        return this.delegate.getResourceDomains();
    }

    @Override
    public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException
    {
        return null;
    }

    @Override
    public BufferedImage getPackImage() throws IOException
    {
        return null;
    }

    @Override
    public String getPackName()
    {
        return "BA_Delegate_Pack";
    }

    @Override
    public void onResourceManagerReload(IResourceManager p_110549_1_)
    {
        this.cachedImages.clear();
        this.resourcePacks = null;
    }
}
