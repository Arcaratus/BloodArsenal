package arcaratus.bloodarsenal.modifier.modifiers;

import WayofTime.bloodmagic.util.helper.NetworkHelper;
import arcaratus.bloodarsenal.modifier.*;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;
import java.util.Set;

public class ModifierAOD extends Modifier
{
    public ModifierAOD()
    {
        super(Constants.Modifiers.AOD, Constants.Modifiers.AOD_COUNTER.length, EnumModifierType.ABILITY, EnumAction.BOW);
    }

    @Override
    public void onRelease(ItemStack itemStack, World world, EntityPlayer player, int charge, int level)
    {
        if (world.isRemote)
            return;

        boolean silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) > 0;
        int fortuneLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack);
        int range = charge * (level + 1) / getMaxLevel();

        Item item = itemStack.getItem();
        Set<Block> effectiveOn;
        Set<Material> materialEffOn;
        String name = (String) item.getToolClasses(itemStack).toArray()[0]; // We ONLY care about the first tool class found
        BlockPos playerPos = player.getPosition();

        StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(itemStack);

        if (name.equals("sword"))
        {
            List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(playerPos.add(-range, 0, -range), playerPos.add(range, 2 * range, range)));
            double damage = (level + 1) * 2;

            for (EntityLivingBase living : entities)
            {
                if (living == player)
                    continue;

                living.attackEntityFrom(DamageSource.GENERIC, (float) (damage * ((level + 1) / getMaxLevel())));
                living.attackEntityAsMob(player);
                NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, (int) (Math.pow(charge, 3) * (level + 1) / 2.7));
                StasisModifiable.incrementModifierTracker(itemStack, this);
            }

            return;
        }

        effectiveOn = BloodArsenalUtils.getEffectiveBlocksForTool(name);
        materialEffOn = BloodArsenalUtils.getEffectiveMaterialsForTool(name);

        NonNullList<ItemStack> drops = NonNullList.create();

        boolean hasSmelting = modifiable.hasModifier(Constants.Modifiers.SMELTING);

        for (int i = -range; i <= range; i++)
        {
            for (int j = 0; j <= 2 * range; j++)
            {
                for (int k = -range; k <= range; k++)
                {
                    BlockPos blockPos = playerPos.add(i, j, k);
                    IBlockState blockState = world.getBlockState(blockPos);

                    if (blockState.getBlock().isAir(blockState, world, blockPos))
                        continue;

                    for (Material material : materialEffOn)
                        if (blockState.getMaterial() != material)
                            continue;

                    if (!effectiveOn.contains(blockState.getBlock()))
                        continue;

                    BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, blockPos, blockState, player);
                    if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Event.Result.DENY)
                        continue;

                    if (blockState.getBlock().getBlockHardness(blockState, world, blockPos) != -1)
                    {
                        float strengthVsBlock = itemStack.getDestroySpeed(blockState);

                        if (strengthVsBlock > 1.1F && world.canMineBlockBody(player, blockPos))
                        {
                            if (silkTouch && blockState.getBlock().canSilkHarvest(world, blockPos, world.getBlockState(blockPos), player))
                            {
                                drops.add(new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState)));
                            }
                            else if (hasSmelting) // Bad cross-modifier implementation
                            {
                                ItemStack blockStack = new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState));
                                ItemStack resultStack = FurnaceRecipes.instance().getSmeltingResult(blockStack);
                                if (!resultStack.isEmpty())
                                {
                                    boolean hasFortune = StasisModifiable.getModifiableFromStack(itemStack).hasModifier(Constants.Modifiers.FORTUNATE);
                                    if (level > 0 && hasFortune) // Written in a jiffy
                                    {
                                        int fortune = random.nextInt(EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack) + 2) - 1;

                                        drops.add(ItemHandlerHelper.copyStackWithSize(resultStack, resultStack.getCount() * (fortune + 1)));
                                        StasisModifiable.incrementModifierTracker(itemStack, this);
                                    }
                                    else if (level == 0 && !(resultStack.getItem() instanceof ItemBlock))
                                    {
                                        drops.add(new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState)));
                                    }
                                    else
                                    {
                                        drops.add(ItemHandlerHelper.copyStackWithSize(resultStack, resultStack.getCount()));
                                        StasisModifiable.incrementModifierTracker(itemStack, this, 1);
                                    }
                                }
                                else
                                {
                                    drops.add(new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState)));
                                }
                            }
                            else
                            {
                                NonNullList<ItemStack> itemDrops = NonNullList.create();
                                blockState.getBlock().getDrops(itemDrops, world, blockPos, world.getBlockState(blockPos), fortuneLvl);
                                drops.addAll(itemDrops);
                            }

                            world.setBlockToAir(blockPos);
                            StasisModifiable.incrementModifierTracker(itemStack, this, 1);
                        }
                    }
                }
            }
        }

        NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, (int) (Math.pow(charge, 3) * (level + 1) / 2.7));
        world.createExplosion(player, playerPos.getX(), playerPos.getY(), playerPos.getZ(), 0.1F, false);
        BloodArsenalUtils.dropStacks(drops, world, playerPos.add(0, 1, 0));
    }
}
