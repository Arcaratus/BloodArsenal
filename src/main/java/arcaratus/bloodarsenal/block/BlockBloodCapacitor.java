package arcaratus.bloodarsenal.block;

import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.item.block.ItemBlockBloodCapacitor;
import arcaratus.bloodarsenal.tile.TileBloodCapacitor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockBloodCapacitor extends Block implements IVariantProvider, IBABlock
{
    public BlockBloodCapacitor(String name)
    {
        super(Material.IRON);

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(5F);
        setResistance(2.0F);
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileBloodCapacitor)
        {
            TileBloodCapacitor capacitor = (TileBloodCapacitor) tile;

            if (!world.isRemote)
                player.sendStatusMessage(new TextComponentString(TextHelper.localizeEffect("tooltip.bloodarsenal.energy", capacitor.getEnergyStored())), true);
        }

//        player.openGui(BloodArsenal.INSTANCE, Constants.Gui.ALTARE_AENIGMATICA_GUI, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World worldIn, IBlockState blockState)
    {
        return new TileBloodCapacitor();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase living, ItemStack stack)
    {
        TileBloodCapacitor capacitor = (TileBloodCapacitor) world.getTileEntity(pos);
        if (stack.hasTagCompound())
        {
            NBTTagCompound tag = stack.getTagCompound();
            capacitor.amountRecv = tag.getInteger("Recv");
            capacitor.amountSend = tag.getInteger("Send");
            capacitor.setEnergyStored(tag.getInteger("Stored"));
        }

        world.markAndNotifyBlock(pos, world.getChunk(pos), state, state, 3);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
            dropBlockAsItem(worldIn, pos, state, 0);
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tile, ItemStack stack)
    {
        super.harvestBlock(world, player, pos, state, tile, stack);
        world.setBlockToAir(pos);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState blockState, int fortune)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileBloodCapacitor)
        {
            TileBloodCapacitor capacitor = (TileBloodCapacitor) tile;
            ItemStack drop = new ItemStack(this);
            NBTTagCompound energyTag = new NBTTagCompound();

            energyTag.setInteger("Recv", capacitor.amountRecv);
            energyTag.setInteger("Send", capacitor.amountSend);
            energyTag.setInteger("Stored", capacitor.getEnergyStored());
            drop.setTagCompound(energyTag);

            drops.add(drop);
        }
    }

    @Override
    public ItemBlock getItem()
    {
        return new ItemBlockBloodCapacitor(this);
    }
}
