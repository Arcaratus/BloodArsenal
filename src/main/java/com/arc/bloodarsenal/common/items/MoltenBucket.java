package com.arc.bloodarsenal.common.items;

import com.arc.bloodarsenal.common.BloodArsenal;
import com.google.common.collect.HashBiMap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoltenBucket extends ItemBucket
{
    private IIcon _filling = null;
    private Block _fluidBlock = null;
    private int _capacity = 1000;
    private HashBiMap<Integer, String> fluidNames = HashBiMap.create();
    private HashMap<Integer, String> textures = new HashMap();
    private HashMap<Integer, IIcon> icons = new HashMap();
    protected static final String pathToTextures = BloodArsenal.MODID + ":" + "bucket/";


    public MoltenBucket(Block fluidBlock)
    {
        super(fluidBlock);
        this._fluidBlock = fluidBlock;
        setUnlocalizedName("extratic.bucket");
        setCreativeTab(BloodArsenal.BA_TAB  );
        setContainerItem(Items.bucket);
        setTextureName("minecraft:bucket_empty");
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int damage)
    {
        return 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(getIconString());


        this._filling = register.registerIcon(pathToTextures + "bucket_fill_fblood_infused_iron");

        for (Map.Entry<Integer, String> entry : this.textures.entrySet())
        {
            IIcon filling = register.registerIcon(pathToTextures + entry.getValue());

            this.icons.put(entry.getKey(), filling);
        }
    }

    @Override
    public IIcon getIcon(ItemStack itemStack, int pass)
    {
        int index = itemStack.getItemDamage();

        if ((pass == 0) || (index <= 0))
        {

            return this.itemIcon;
        }

        IIcon icon = this.icons.get(Integer.valueOf(index));

        if (icon != null)
        {
            return icon;
        }

        return this.itemIcon;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if ((itemStack == null) || (itemStack.getItem() != this))
        {
            return itemStack;
        }

        int index = itemStack.getItemDamage();
        String fluidName = this.fluidNames.get(Integer.valueOf(index));

        if (fluidName == null)
        {
            return itemStack;
        }

        Block block = BucketHandler.getBlock(itemStack);

        boolean isAir = block == null;

        MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, isAir);

        if (movingobjectposition == null)
        {
            return itemStack;
        }

        if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            int xCoord = movingobjectposition.blockX;
            int yCoord = movingobjectposition.blockY;
            int zCoord = movingobjectposition.blockZ;

            if (!world.canMineBlock(player, xCoord, yCoord, zCoord))
            {
                return itemStack;
            }

            if (block == Blocks.air)
            {
                return new ItemStack(getContainerItem());
            }

            if (movingobjectposition.sideHit == 0)
            {
                yCoord--;
            }

            if (movingobjectposition.sideHit == 1)
            {
                yCoord++;
            }

            if (movingobjectposition.sideHit == 2)
            {
                zCoord--;
            }

            if (movingobjectposition.sideHit == 3)
            {
                zCoord++;
            }

            if (movingobjectposition.sideHit == 4)
            {
                xCoord--;
            }

            if (movingobjectposition.sideHit == 5)
            {
                xCoord++;
            }

            if (!player.canPlayerEdit(xCoord, yCoord, zCoord, movingobjectposition.sideHit, itemStack))
            {
                return itemStack;
            }
            Material material = world.getBlock(xCoord, yCoord, zCoord).getMaterial();

            if (world.isAirBlock(xCoord, yCoord, zCoord) && !material.isSolid())
            {
                if (!world.isRemote && !material.isSolid() && !material.isLiquid())
                {
                    world.func_147480_a(xCoord, yCoord, zCoord, true);
                }

                world.setBlock(xCoord, yCoord, zCoord, block, 0, 3);

                if (!player.capabilities.isCreativeMode)
                {
                    return new ItemStack(getContainerItem());
                }
            }
        }
        return itemStack;
    }

    @Override
    public boolean tryPlaceContainedLiquid(World world, int xCoord, int yCoord, int zCoord)
    {
        return false;
    }

    public void setCapcity(int capacity)
    {
        this._capacity = capacity;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        if (itemStack == null)
        {
            return getUnlocalizedName();
        }

        int index = itemStack.getItemDamage();
        String fluidName = this.fluidNames.get(Integer.valueOf(index));

        if (fluidName == null)
        {
            return getUnlocalizedName();
        }

        return "item.ba.bucket.filled." + fluidName;
    }

    public void addTextureMapping(int id, String fluidName, String texture)
    {
        this.fluidNames.put(id, fluidName);
        this.textures.put(id, texture);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List itemStackList)
    {
        if (!this.fluidNames.isEmpty())
        {
            for (Map.Entry<Integer, String> subItem : this.fluidNames.entrySet())
            {
                int entryId = subItem.getKey().intValue();
                ItemStack bucketStack = new ItemStack(this, 1, entryId);
                itemStackList.add(bucketStack);
            }
        }
        else
        {
            itemStackList.add(new ItemStack(this, 1, 0));
        }
    }
}
