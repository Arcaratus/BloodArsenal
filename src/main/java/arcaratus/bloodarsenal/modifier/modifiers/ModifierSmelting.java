package arcaratus.bloodarsenal.modifier.modifiers;

import arcaratus.bloodarsenal.modifier.EnumModifierType;
import arcaratus.bloodarsenal.modifier.Modifier;
import arcaratus.bloodarsenal.modifier.StasisModifiable;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.items.ItemHandlerHelper;

public class ModifierSmelting extends Modifier
{
    public ModifierSmelting()
    {
        super(Constants.Modifiers.SMELTING, Constants.Modifiers.SMELTING_COUNTER.length, EnumModifierType.CORE);
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityPlayer player, int level)
    {
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, player);
        if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Event.Result.DENY)
            return;

        ItemStack blockStack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
        ItemStack resultStack = FurnaceRecipes.instance().getSmeltingResult(blockStack);
        world.setBlockToAir(pos);// Needed to prevent duplicates
        if (!resultStack.isEmpty())
        {
            StasisModifiable modifiable = StasisModifiable.getModifiableFromStack(itemStack);
            boolean hasFortune = modifiable.hasModifier(Constants.Modifiers.FORTUNATE);
            if (level > 0 && hasFortune) // Written in a jiffy
            {
                int i = random.nextInt(EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack) + 2) - 1;

                NonNullList<ItemStack> drops = NonNullList.create();
                drops.add(ItemHandlerHelper.copyStackWithSize(resultStack, resultStack.getCount() * (i + 1)));
                BloodArsenalUtils.dropStacks(drops, world, pos);
                modifiable.incrementModifierTracker(itemStack, this);
            }
            else if (level == 0 && !(resultStack.getItem() instanceof ItemBlock))
            {
                state.getBlock().dropBlockAsItem(world, pos, state, 0);
            }
            else
            {
                Block.spawnAsEntity(world, pos, resultStack);
                modifiable.incrementModifierTracker(itemStack, this);
            }
        }
        else
        {
            state.getBlock().dropBlockAsItem(world, pos, state, 0);
        }
    }
}
