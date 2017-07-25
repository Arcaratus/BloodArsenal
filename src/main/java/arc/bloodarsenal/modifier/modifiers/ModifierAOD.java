package arc.bloodarsenal.modifier.modifiers;

import WayofTime.bloodmagic.api.BlockStack;
import WayofTime.bloodmagic.api.ItemStackWrapper;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import arc.bloodarsenal.modifier.*;
import arc.bloodarsenal.registry.Constants;
import arc.bloodarsenal.util.BloodArsenalUtils;
import com.google.common.collect.HashMultiset;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;
import java.util.Set;

public class ModifierAOD extends Modifier
{
    public ModifierAOD(int level)
    {
        super(Constants.Modifiers.AOD, Constants.Modifiers.AOD_COUNTER.length, level, EnumModifierType.ABILITY, EnumAction.BOW);
    }

    @Override
    public void onRelease(ItemStack itemStack, World world, EntityPlayer player, int charge)
    {
        if (world.isRemote)
            return;

        boolean silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) > 0;
        int fortuneLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack);
        int range = charge * (getLevel() + 1) / getMaxLevel();

        Item item = itemStack.getItem();
        Set<Block> effectiveOn;
        Set<Material> materialEffOn;
        String name = (String) item.getToolClasses(itemStack).toArray()[0]; // We ONLY care about the first tool class found

        BlockPos playerPos = player.getPosition();

        if (name.equals("sword"))
        {
            List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(playerPos.add(-range, 0, -range), playerPos.add(range, 2 * range, range)));
            double damage = (getLevel() + 1) * 2;

            for (EntityLivingBase living : entities)
            {
                if (living == player)
                    continue;

                living.attackEntityFrom(DamageSource.generic, (float) (damage * ((getLevel() + 1) / getMaxLevel())));
                living.attackEntityAsMob(player);
                NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, (int) (Math.pow(charge, 3) * (getLevel() + 1) / 2.7));
                ModifierTracker.getTracker(this).incrementCounter(StasisModifiable.getStasisModifiable(itemStack), 1);
            }

            return;
        }

        effectiveOn = BloodArsenalUtils.getEffectiveBlocksForTool(name);
        materialEffOn = BloodArsenalUtils.getEffectiveMaterialsForTool(name);

        HashMultiset<ItemStackWrapper> drops = HashMultiset.create();

        StasisModifiable modifiable = StasisModifiable.getStasisModifiable(itemStack);
        boolean hasSmelting = modifiable.hasModifier(ModifierSmelting.class);

        for (int i = -range; i <= range; i++)
        {
            for (int j = 0; j <= 2 * range; j++)
            {
                for (int k = -range; k <= range; k++)
                {
                    BlockPos blockPos = playerPos.add(i, j, k);
                    BlockStack blockStack = BlockStack.getStackFromPos(world, blockPos);

                    if (blockStack.getBlock().isAir(blockStack.getState(), world, blockPos))
                        continue;

                    for (Material material : materialEffOn)
                        if (blockStack.getState().getMaterial() != material)
                            continue;

                    if (!effectiveOn.contains(blockStack.getBlock()))
                        continue;

                    BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, blockPos, blockStack.getState(), player);
                    if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Event.Result.DENY)
                        continue;

                    if (blockStack.getBlock().getBlockHardness(blockStack.getState(), world, blockPos) != -1)
                    {
                        float strengthVsBlock = itemStack.getStrVsBlock(blockStack.getState());

                        if (strengthVsBlock > 1.1F && world.canMineBlockBody(player, blockPos))
                        {
                            if (silkTouch && blockStack.getBlock().canSilkHarvest(world, blockPos, world.getBlockState(blockPos), player))
                            {
                                drops.add(new ItemStackWrapper(blockStack));
                            }
                            else if (hasSmelting)
                            {
                                IBlockState state = world.getBlockState(blockPos);
                                ItemStack blockItemStack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
                                ItemStack resultStack = FurnaceRecipes.instance().getSmeltingResult(blockItemStack);
                                if (resultStack != null)
                                    if (getLevel() > 0 || (getLevel() == 0 && resultStack.getItem() instanceof ItemBlock))
                                        drops.add(ItemStackWrapper.getHolder(resultStack));
                            }
                            else
                            {
                                List<ItemStack> itemDrops = blockStack.getBlock().getDrops(world, blockPos, world.getBlockState(blockPos), fortuneLvl);
                                for (ItemStack stacks : itemDrops)
                                    drops.add(ItemStackWrapper.getHolder(stacks));
                            }

                            world.setBlockToAir(blockPos);
                            ModifierTracker.getTracker(this).incrementCounter(StasisModifiable.getStasisModifiable(itemStack), 1);
                        }
                    }
                }
            }
        }

        NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, (int) (Math.pow(charge, 3) * (getLevel() + 1) / 2.7));
        world.createExplosion(player, playerPos.getX(), playerPos.getY(), playerPos.getZ(), 0.1F, false);
        BloodArsenalUtils.dropStacks(drops, world, playerPos.add(0, 1, 0));
    }
}
