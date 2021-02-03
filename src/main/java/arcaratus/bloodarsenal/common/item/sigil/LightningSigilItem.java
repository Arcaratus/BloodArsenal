package arcaratus.bloodarsenal.common.item.sigil;

import arcaratus.bloodarsenal.common.ConfigHandler;
import arcaratus.bloodarsenal.common.core.Constants;
import arcaratus.bloodarsenal.common.util.Utils;
import arcaratus.bloodarsenal.common.util.helper.NBTHelper;
import arcaratus.bloodarsenal.common.util.helper.PlayerHelper;
import arcaratus.bloodarsenal.common.util.helper.TextHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wayoftime.bloodmagic.common.item.sigil.ISigil;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import java.util.List;

public class LightningSigilItem extends SigilBaseItem
{
    public LightningSigilItem(Properties properties)
    {
        super(properties, ConfigHandler.COMMON.lightningSigilCost.get());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof ISigil.Holding)
            stack = ((Holding) stack.getItem()).getHeldItem(stack, player);
        if (PlayerHelper.isFakePlayer(player) || getBinding(stack) == null)
            return ActionResult.resultFail(stack);

        if (!world.isRemote)
        {
            int level = NBTHelper.getInt(stack, Constants.NBT.LEVEL, 0);

            if (player.isSneaking())
            {
                if (level >= ConfigHandler.COMMON.lightningSigilMaxLevel.get())
                {
                    NBTHelper.setInt(stack, Constants.NBT.LEVEL, 0);
                }
                else
                {
                    NBTHelper.setInt(stack, Constants.NBT.LEVEL, level + 1);
                }

                player.sendStatusMessage(new TranslationTextComponent(TextHelper.localizeEffect("chat.bloodarsenal.set_level", TextHelper.toRoman(NBTHelper.getInt(stack, Constants.NBT.LEVEL, 0) + 1))), true);
                return ActionResult.resultPass(stack);
            }
            else if (!isUnusable(stack))
            {
                RayTraceResult rayTrace = PlayerHelper.rayTrace(world, player, RayTraceContext.FluidMode.NONE);

                if (rayTrace != null)
                {
                    double x = rayTrace.getHitVec().getX();
                    double y = rayTrace.getHitVec().getY();
                    double z = rayTrace.getHitVec().getZ();

                    if (rayTrace.getType() == RayTraceResult.Type.BLOCK)
                    {
                        BlockRayTraceResult blockRayTrace = (BlockRayTraceResult) rayTrace;
                        switch (blockRayTrace.getFace())
                        {
                            case DOWN:
                                y -= 2.0;
                                break;
                            case NORTH:
                                z -= 0.5;
                                break;
                            case SOUTH:
                                z += 0.5;
                                break;
                            case WEST:
                                x -= 0.5;
                                break;
                            case EAST:
                                x += 0.5;
                                break;
                            default:
                                break;
                        }
                    }

                    int cost;

                    switch (level)
                    {
                        case 0:
                            cost = getLpUsed();
                            break;
                        case 1:
                            cost = 4 * getLpUsed();
                            break;
                        case 2:
                            cost = 7 * getLpUsed();
                            break;
                        case 3:
                            cost = 13 * getLpUsed();
                            break;
                        case 4:
                            cost = 29 * getLpUsed();
                            break;
                        case 5:
                            cost = 85 * getLpUsed();
                            break;
                        default:
                            cost = 0;
                            break;
                    }

                    if (NetworkHelper.canSyphonFromContainer(stack, cost) || player.isCreative())
                    {
                        switch (level)
                        {
                            case 0:
                                Utils.lightningBolt(world, x, y, z, false);
                                break;
                            case 1:
                                Utils.lightningBolt(world, x, y, z, false);
                                Utils.lightningBolt(world, x + 1, y, z, false);
                                Utils.lightningBolt(world, x, y, z + 1, false);
                                Utils.lightningBolt(world, x + 1, y, z + 1, false);
                                break;
                            case 2:
                                Utils.lightningBolt(world, x, y, z, false);
                                Utils.lightningBolt(world, x + 2, y, z, false);
                                Utils.lightningBolt(world, x - 2, y, z, false);
                                Utils.lightningBolt(world, x + 1, y, z + 2, false);
                                Utils.lightningBolt(world, x - 1, y, z + 2, false);
                                Utils.lightningBolt(world, x + 1, y, z - 2, false);
                                Utils.lightningBolt(world, x - 1, y, z - 2, false);
                                break;
                            case 3:
                                Utils.lightningBolt(world, x, y, z, false);
                                Utils.lightningBolt(world, x + 3, y, z, false);
                                Utils.lightningBolt(world, x - 3, y, z, false);
                                Utils.lightningBolt(world, x + 2, y, z + 2, false);
                                Utils.lightningBolt(world, x + 2, y, z - 2, false);
                                Utils.lightningBolt(world, x - 2, y, z + 2, false);
                                Utils.lightningBolt(world, x - 2, y, z - 2, false);
                                Utils.lightningBolt(world, x, y, z + 3, false);
                                Utils.lightningBolt(world, x, y, z - 3, false);
                                Utils.lightningBolt(world, x + 1, y, z + 1, false);
                                Utils.lightningBolt(world, x + 1, y, z - 1, false);
                                Utils.lightningBolt(world, x - 1, y, z + 1, false);
                                Utils.lightningBolt(world, x - 1, y, z - 1, false);
                                break;
                            case 4:
                                Utils.lightningBolt(world, x, y, z, false);
                                Utils.lightningBolt(world, x + 3, y, z, false);
                                Utils.lightningBolt(world, x - 3, y, z, false);
                                Utils.lightningBolt(world, x + 2, y, z + 2, false);
                                Utils.lightningBolt(world, x + 2, y, z - 2, false);
                                Utils.lightningBolt(world, x - 2, y, z + 2, false);
                                Utils.lightningBolt(world, x - 2, y, z - 2, false);
                                Utils.lightningBolt(world, x, y, z + 3, false);
                                Utils.lightningBolt(world, x, y, z - 3, false);
                                Utils.lightningBolt(world, x + 1, y, z + 1, false);
                                Utils.lightningBolt(world, x + 1, y, z - 1, false);
                                Utils.lightningBolt(world, x - 1, y, z + 1, false);
                                Utils.lightningBolt(world, x - 1, y, z - 1, false);
                                Utils.lightningBolt(world, x + 6, y, z, false);
                                Utils.lightningBolt(world, x + 4, y, z + 4, false);
                                Utils.lightningBolt(world, x, y, z + 6, false);
                                Utils.lightningBolt(world, x + 4, y, z - 4, false);
                                Utils.lightningBolt(world, x - 6, y, z, false);
                                Utils.lightningBolt(world, x - 4, y, z - 4, false);
                                Utils.lightningBolt(world, x, y, z - 6, false);
                                Utils.lightningBolt(world, x - 4, y, z + 4, false);
                                Utils.lightningBolt(world, x - 5, y, z - 2, false);
                                Utils.lightningBolt(world, x - 5, y, z + 2, false);
                                Utils.lightningBolt(world, x + 5, y, z - 2, false);
                                Utils.lightningBolt(world, x + 5, y, z + 2, false);
                                Utils.lightningBolt(world, x - 2, y, z - 5, false);
                                Utils.lightningBolt(world, x - 2, y, z + 5, false);
                                Utils.lightningBolt(world, x + 2, y, z - 5, false);
                                Utils.lightningBolt(world, x + 2, y, z + 5, false);
                                break;
                            case 5:
                                Utils.lightningBolt(world, x, y, z, false);
                                Utils.lightningBolt(world, x + 3, y, z, false);
                                Utils.lightningBolt(world, x - 3, y, z, false);
                                Utils.lightningBolt(world, x + 2, y, z + 2, false);
                                Utils.lightningBolt(world, x + 2, y, z - 2, false);
                                Utils.lightningBolt(world, x - 2, y, z + 2, false);
                                Utils.lightningBolt(world, x - 2, y, z - 2, false);
                                Utils.lightningBolt(world, x, y, z + 3, false);
                                Utils.lightningBolt(world, x, y, z - 3, false);
                                Utils.lightningBolt(world, x + 1, y, z + 1, false);
                                Utils.lightningBolt(world, x + 1, y, z - 1, false);
                                Utils.lightningBolt(world, x - 1, y, z + 1, false);
                                Utils.lightningBolt(world, x - 1, y, z - 1, false);
                                Utils.lightningBolt(world, x - 4, y, z - 1, false);
                                Utils.lightningBolt(world, x - 4, y, z + 1, false);
                                Utils.lightningBolt(world, x + 4, y, z - 1, false);
                                Utils.lightningBolt(world, x + 4, y, z + 1, false);
                                Utils.lightningBolt(world, x - 1, y, z - 4, false);
                                Utils.lightningBolt(world, x - 1, y, z + 4, false);
                                Utils.lightningBolt(world, x + 1, y, z - 4, false);
                                Utils.lightningBolt(world, x + 1, y, z + 4, false);
                                Utils.lightningBolt(world, x + 3, y, z + 3, false);
                                Utils.lightningBolt(world, x + 3, y, z - 3, false);
                                Utils.lightningBolt(world, x - 3, y, z + 3, false);
                                Utils.lightningBolt(world, x - 3, y, z - 3, false);
                                Utils.lightningBolt(world, x + 6, y, z, false);
                                Utils.lightningBolt(world, x + 4, y, z + 4, false);
                                Utils.lightningBolt(world, x, y, z + 6, false);
                                Utils.lightningBolt(world, x + 4, y, z - 4, false);
                                Utils.lightningBolt(world, x - 6, y, z, false);
                                Utils.lightningBolt(world, x - 4, y, z - 4, false);
                                Utils.lightningBolt(world, x, y, z - 6, false);
                                Utils.lightningBolt(world, x - 4, y, z + 4, false);
                                Utils.lightningBolt(world, x - 5, y, z - 2, false);
                                Utils.lightningBolt(world, x - 5, y, z + 2, false);
                                Utils.lightningBolt(world, x + 5, y, z - 2, false);
                                Utils.lightningBolt(world, x + 5, y, z + 2, false);
                                Utils.lightningBolt(world, x - 2, y, z - 5, false);
                                Utils.lightningBolt(world, x - 2, y, z + 5, false);
                                Utils.lightningBolt(world, x + 2, y, z - 5, false);
                                Utils.lightningBolt(world, x + 2, y, z + 5, false);
                                Utils.lightningBolt(world, x + 9, y, z, false);
                                Utils.lightningBolt(world, x - 9, y, z, false);
                                Utils.lightningBolt(world, x, y, z + 9, false);
                                Utils.lightningBolt(world, x, y, z - 9, false);
                                Utils.lightningBolt(world, x + 7, y, z + 1, false);
                                Utils.lightningBolt(world, x + 7, y, z - 1, false);
                                Utils.lightningBolt(world, x - 7, y, z + 1, false);
                                Utils.lightningBolt(world, x - 7, y, z - 1, false);
                                Utils.lightningBolt(world, x + 1, y, z + 7, false);
                                Utils.lightningBolt(world, x + 1, y, z - 7, false);
                                Utils.lightningBolt(world, x - 1, y, z + 7, false);
                                Utils.lightningBolt(world, x - 1, y, z - 7, false);
                                Utils.lightningBolt(world, x + 6, y, z + 3, false);
                                Utils.lightningBolt(world, x + 6, y, z - 3, false);
                                Utils.lightningBolt(world, x - 6, y, z + 3, false);
                                Utils.lightningBolt(world, x - 6, y, z - 3, false);
                                Utils.lightningBolt(world, x + 3, y, z + 6, false);
                                Utils.lightningBolt(world, x + 3, y, z - 6, false);
                                Utils.lightningBolt(world, x - 3, y, z + 6, false);
                                Utils.lightningBolt(world, x - 3, y, z - 6, false);
                                Utils.lightningBolt(world, x + 5, y, z + 5, false);
                                Utils.lightningBolt(world, x + 5, y, z - 5, false);
                                Utils.lightningBolt(world, x - 5, y, z + 5, false);
                                Utils.lightningBolt(world, x - 5, y, z - 5, false);
                                Utils.lightningBolt(world, x + 6, y, z + 6, false);
                                Utils.lightningBolt(world, x + 6, y, z - 6, false);
                                Utils.lightningBolt(world, x - 6, y, z + 6, false);
                                Utils.lightningBolt(world, x - 6, y, z - 6, false);
                                Utils.lightningBolt(world, x + 8, y, z + 2, false);
                                Utils.lightningBolt(world, x + 8, y, z - 2, false);
                                Utils.lightningBolt(world, x - 8, y, z + 2, false);
                                Utils.lightningBolt(world, x - 8, y, z - 2, false);
                                Utils.lightningBolt(world, x + 2, y, z + 8, false);
                                Utils.lightningBolt(world, x + 2, y, z - 8, false);
                                Utils.lightningBolt(world, x - 2, y, z + 8, false);
                                Utils.lightningBolt(world, x - 2, y, z - 8, false);
                                Utils.lightningBolt(world, x + 7, y, z + 4, false);
                                Utils.lightningBolt(world, x + 7, y, z - 4, false);
                                Utils.lightningBolt(world, x - 7, y, z + 4, false);
                                Utils.lightningBolt(world, x - 7, y, z - 4, false);
                                Utils.lightningBolt(world, x + 4, y, z + 7, false);
                                Utils.lightningBolt(world, x + 4, y, z - 7, false);
                                Utils.lightningBolt(world, x - 4, y, z + 7, false);
                                Utils.lightningBolt(world, x - 4, y, z - 7, false);
                                break;
                        }

                        NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, SoulTicket.item(stack, world, player, cost));
                        player.getCooldownTracker().setCooldown(this, ConfigHandler.COMMON.lightningSigilCooldown.get());
                        return ActionResult.resultSuccess(stack);
                    }
                    else
                    {
                        player.sendStatusMessage(new TranslationTextComponent(TextHelper.localize("chat.bloodarsenal.too_weak")), true);
                        return ActionResult.resultFail(stack);
                    }
                }
            }
        }

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(TextHelper.localize("tooltip.bloodarsenal.level", TextHelper.toRoman(NBTHelper.getInt(stack, Constants.NBT.LEVEL, 0) + 1))).mergeStyle(TextFormatting.GRAY));
    }
}
