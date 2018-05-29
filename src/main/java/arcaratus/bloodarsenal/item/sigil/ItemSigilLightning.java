package arcaratus.bloodarsenal.item.sigil;

import WayofTime.bloodmagic.util.ChatUtil;
import WayofTime.bloodmagic.util.helper.*;
import arcaratus.bloodarsenal.ConfigHandler;
import arcaratus.bloodarsenal.registry.Constants;
import arcaratus.bloodarsenal.util.BloodArsenalUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSigilLightning extends ItemSigilBase
{
    private int level;

    public ItemSigilLightning()
    {
        super("lightning");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
            return ActionResult.newResult(EnumActionResult.FAIL, stack);

        if (!world.isRemote)
        {
            if (player.isSneaking())
            {
                if (stack.getTagCompound().getInteger(Constants.NBT.LEVEL) >= 5)
                    stack.getTagCompound().setInteger(Constants.NBT.LEVEL, 0);
                else
                    stack.getTagCompound().setInteger(Constants.NBT.LEVEL, stack.getTagCompound().getInteger(Constants.NBT.LEVEL) + 1);

                level = stack.getTagCompound().getInteger(Constants.NBT.LEVEL);
                ChatUtil.sendNoSpam(player, TextHelper.localizeEffect("chat.bloodarsenal.setLevel", NumeralHelper.toRoman(stack.getTagCompound().getInteger(Constants.NBT.LEVEL) + 1)));
                return super.onItemRightClick(world, player, hand);
            }
            else if (!isUnusable(stack))
            {
                RayTraceResult rayTrace = BloodArsenalUtils.rayTrace(world, player, false);

                if (rayTrace != null && rayTrace.typeOfHit == RayTraceResult.Type.BLOCK)
                {
                    double x = rayTrace.hitVec.x;
                    double y = rayTrace.hitVec.y;
                    double z = rayTrace.hitVec.z;

                    switch (rayTrace.sideHit)
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

                    int cost;
                    double multiplier = ConfigHandler.values.sigilLightningMultiplier;

                    switch (getLevel())
                    {
                        case 0:
                            cost = (int) multiplier;
                            break;
                        case 1:
                            cost = (int) (4 * multiplier);
                            break;
                        case 2:
                            cost = (int) (7 * multiplier);
                            break;
                        case 3:
                            cost = (int) (13 * multiplier);
                            break;
                        case 4:
                            cost = (int) (29 * multiplier);
                            break;
                        case 5:
                            cost = (int) (85 * multiplier);
                            break;
                        default:
                            cost = 0;
                            break;
                    }

                    if (NetworkHelper.canSyphonFromContainer(stack, cost) || player.capabilities.isCreativeMode)
                    {
                        switch (getLevel())
                        {
                            case 0:
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
                                break;
                            case 1:
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z + 1, false));
                                break;
                            case 2:
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z - 2, false));
                                break;
                            case 3:
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 3, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 3, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z + 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z - 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z - 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z - 1, false));
                                break;
                            case 4:
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 3, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 3, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z + 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z - 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z - 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z - 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 6, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y, z + 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z + 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y, z - 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 6, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y, z - 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z - 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y, z + 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 5, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 5, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 5, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 5, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z - 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z + 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z - 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z + 5, false));
                                break;
                            case 5:
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 3, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 3, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z + 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z - 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z - 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z - 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y, z - 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y, z - 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z - 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z + 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z - 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z + 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 3, y, z + 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 3, y, z - 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 3, y, z + 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 3, y, z - 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 6, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y, z + 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z + 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y, z - 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 6, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y, z - 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z - 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y, z + 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 5, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 5, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 5, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 5, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z - 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z + 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z - 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z + 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 9, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 9, y, z, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z + 9, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z - 9, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 7, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 7, y, z - 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 7, y, z + 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 7, y, z - 1, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z + 7, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 1, y, z - 7, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z + 7, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 1, y, z - 7, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 6, y, z + 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 6, y, z - 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 6, y, z + 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 6, y, z - 3, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 3, y, z + 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 3, y, z - 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 3, y, z + 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 3, y, z - 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 5, y, z + 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 5, y, z - 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 5, y, z + 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 5, y, z - 5, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 6, y, z + 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 6, y, z - 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 6, y, z + 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 6, y, z - 6, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 8, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 8, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 8, y, z + 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 8, y, z - 2, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z + 8, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 2, y, z - 8, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z + 8, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 2, y, z - 8, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 7, y, z + 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 7, y, z - 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 7, y, z + 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 7, y, z - 4, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y, z + 7, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x + 4, y, z - 7, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y, z + 7, false));
                                world.addWeatherEffect(new EntityLightningBolt(world, x - 4, y, z - 7, false));
                                break;
                        }

                        NetworkHelper.getSoulNetwork(player).syphonAndDamage(player, cost);
                    }
                    else
                        ChatUtil.sendChat(player, TextHelper.localize("chat.bloodarsenal.tooWeak"));
                }
            }
        }

        return super.onItemRightClick(world, player, hand);
    }

    public int getLevel()
    {
        return level;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(TextHelper.localize("tooltip.bloodarsenal.level", NumeralHelper.toRoman(getLevel() + 1)));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }
}
