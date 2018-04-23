package arcaratus.bloodarsenal.item.tool;

import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import WayofTime.bloodmagic.client.IVariantProvider;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.entity.projectile.EntityWarpBlade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ItemWarpBlade extends ItemSword implements IVariantProvider
{
    public ItemWarpBlade()
    {
        super(ToolMaterial.IRON);

        setUnlocalizedName(BloodArsenal.MOD_ID + ".warpBlade");
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (PlayerHelper.isFakePlayer(player))
            return ActionResult.newResult(EnumActionResult.FAIL, stack);

        EntityWarpBlade summonedTool = new EntityWarpBlade(world, player, stack);
        player.getCooldownTracker().setCooldown(this, 20);

        world.spawnEntity(summonedTool);

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(0, "normal"));
        return ret;
    }
}
