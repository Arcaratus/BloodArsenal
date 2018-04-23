package arcaratus.bloodarsenal.block;

public class BlockBloodBurnedString //extends BlockTripWire implements IComplexVariantProvider, IBABlock
{
//    public BlockBloodBurnedString(String name)
//    {
//        super();
//
//        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
//        setRegistryName(name);
//    }
//
//    @Override
//    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
//    {
//        return AABB;
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public BlockRenderLayer getBlockLayer()
//    {
//        return BlockRenderLayer.TRANSLUCENT;
//    }
//
//    @Override
//    @Nullable
//    public Item getItemDropped(IBlockState state, Random rand, int fortune)
//    {
//        return ModItems.BLOOD_BURNED_STRING;
//    }
//
//    @Override
//    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
//    {
//        return new ItemStack(RegistrarBloodArsenalItems.BLOOD_BURNED_STRING);
//    }
//
//    @Override
//    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
//    {
//        return state.withProperty(NORTH, isConnectedTo(worldIn, pos, EnumFacing.NORTH)).withProperty(EAST, isConnectedTo(worldIn, pos, EnumFacing.EAST)).withProperty(SOUTH, isConnectedTo(worldIn, pos, EnumFacing.SOUTH)).withProperty(WEST, isConnectedTo(worldIn, pos, EnumFacing.WEST));
//    }
//
//    @Override
//    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
//    {
//        worldIn.setBlockState(pos, state, 3);
//        this.notifyHook(worldIn, pos);
//    }
//
//    private void notifyHook(World worldIn, BlockPos pos)
//    {
//        for (EnumFacing enumfacing : new EnumFacing[]{EnumFacing.SOUTH, EnumFacing.WEST})
//        {
//            for (int i = 1; i < 42; ++i)
//            {
//                BlockPos blockpos = pos.offset(enumfacing, i);
//                IBlockState iblockstate = worldIn.getBlockState(blockpos);
//
//                if (iblockstate.getBlock() != ModBlocks.BLOOD_BURNED_STRING)
//                {
//                    break;
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
//    {
//        if (!worldIn.isRemote)
//        {
//            if (!state.getValue(POWERED))
//            {
//                this.updateState(worldIn, pos);
//            }
//
//            Random rand = new Random();
//            if (rand.nextBoolean() && rand.nextInt(1000) == 4)
//                entityIn.setFire(3);
//        }
//    }
//
//    @Override
//    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
//    {
//        if (!worldIn.isRemote)
//        {
//            if (worldIn.getBlockState(pos).getValue(POWERED))
//            {
//                this.updateState(worldIn, pos);
//            }
//        }
//    }
//
//    private void updateState(World worldIn, BlockPos pos)
//    {
//        IBlockState iblockstate = worldIn.getBlockState(pos);
//        boolean flag = iblockstate.getValue(POWERED);
//        boolean flag1 = false;
//        List<? extends Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(null, iblockstate.getBoundingBox(worldIn, pos).offset(pos));
//
//        if (!list.isEmpty())
//        {
//            for (Entity entity : list)
//            {
//                if (!entity.doesEntityNotTriggerPressurePlate())
//                {
//                    flag1 = true;
//                    break;
//                }
//            }
//        }
//
//        if (flag1 != flag)
//        {
//            iblockstate = iblockstate.withProperty(POWERED, flag1);
//            worldIn.setBlockState(pos, iblockstate, 3);
//            this.notifyHook(worldIn, pos);
//        }
//
//        if (flag1)
//        {
//            worldIn.scheduleUpdate(new BlockPos(pos), this, this.tickRate(worldIn));
//        }
//    }
//
//    private static boolean isConnectedTo(IBlockAccess worldIn, BlockPos pos, EnumFacing direction)
//    {
//        return worldIn.getBlockState(pos.offset(direction)).getBlock() == ModBlocks.BLOOD_BURNED_STRING;
//    }
//
//    @Override
//    public EnumBlockRenderType getRenderType(IBlockState state)
//    {
//        return EnumBlockRenderType.MODEL;
//    }
//
//    @Override
//    public IProperty[] getIgnoredProperties()
//    {
//        return new IProperty[]{DISARMED, POWERED};
//    }
//
//    @Override
//    public ItemBlock getItem()
//    {
//        return new ItemBlock(this);
//    }
}
