package arc.bloodarsenal.block;

import WayofTime.bloodmagic.api.altar.IBloodAltar;
import WayofTime.bloodmagic.api.iface.IBindable;
import WayofTime.bloodmagic.api.orb.IBloodOrb;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.tile.TileAltareAenigmatica;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAltareAenigmatica extends BlockContainer
{
    public BlockAltareAenigmatica(String name)
    {
        super(Material.ROCK);
        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        checkSidesForAltar(worldIn, pos);
        return super.eventReceived(state, worldIn, pos, id, param);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileAltareAenigmatica();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileAltareAenigmatica)
        {
            checkSidesForAltar(world, pos);
            if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof IBloodOrb)
                return ((TileAltareAenigmatica) tile).setLinkedOrbOwner(((IBindable) player.getHeldItemMainhand().getItem()).getOwnerName(player.getHeldItemMainhand()));
        }

        player.openGui(BloodArsenal.INSTANCE, Constants.Gui.ALTARE_AENIGMATICA_GUI, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockState)
    {
        TileAltareAenigmatica tile = (TileAltareAenigmatica) world.getTileEntity(pos);
        if (tile != null)
            tile.dropItems();

        super.breakBlock(world, pos, blockState);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileAltareAenigmatica)
            checkSidesForAltar(world, pos);
    }

    private void checkSidesForAltar(IBlockAccess world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileAltareAenigmatica)
        {
            if (world.getTileEntity(pos.down()) instanceof IBloodAltar) ((TileAltareAenigmatica) tile).setAltarPos(pos.down());
            else if (world.getTileEntity(pos.up()) instanceof IBloodAltar) ((TileAltareAenigmatica) tile).setAltarPos(pos.up());
            else if (world.getTileEntity(pos.north()) instanceof IBloodAltar) ((TileAltareAenigmatica) tile).setAltarPos(pos.north());
            else if (world.getTileEntity(pos.south()) instanceof IBloodAltar) ((TileAltareAenigmatica) tile).setAltarPos(pos.south());
            else if (world.getTileEntity(pos.west()) instanceof IBloodAltar) ((TileAltareAenigmatica) tile).setAltarPos(pos.west());
            else if (world.getTileEntity(pos.east()) instanceof IBloodAltar) ((TileAltareAenigmatica) tile).setAltarPos(pos.east());
            else ((TileAltareAenigmatica) tile).setAltarPos(BlockPos.ORIGIN);
        }
    }
}
