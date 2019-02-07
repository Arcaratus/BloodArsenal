package arcaratus.bloodarsenal.item.tool;

import WayofTime.bloodmagic.client.IVariantProvider;
import WayofTime.bloodmagic.util.helper.PlayerHelper;
import arcaratus.bloodarsenal.BloodArsenal;
import arcaratus.bloodarsenal.entity.projectile.EntityWarpBlade;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemWarpBlade extends ItemSword implements IVariantProvider
{
    public ItemWarpBlade(String name)
    {
        super(ToolMaterial.IRON);

        setTranslationKey(BloodArsenal.MOD_ID + "." + name);
        setRegistryName(name);
//        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setMaxDamage(0);
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
    public void gatherVariants(@Nonnull Int2ObjectMap<String> variants)
    {
        variants.put(0, "type=normal");
    }
}
