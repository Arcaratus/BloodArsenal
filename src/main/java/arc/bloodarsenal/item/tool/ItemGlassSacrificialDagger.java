package arc.bloodarsenal.item.tool;

import WayofTime.bloodmagic.api.event.SacrificeKnifeUsedEvent;
import WayofTime.bloodmagic.api.util.helper.PlayerHelper;
import WayofTime.bloodmagic.api.util.helper.PlayerSacrificeHelper;
import WayofTime.bloodmagic.item.ItemSacrificialDagger;
import WayofTime.bloodmagic.util.helper.TextHelper;
import arc.bloodarsenal.BloodArsenal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.registry.ModPotions;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemGlassSacrificialDagger extends ItemSacrificialDagger
{
    public ItemGlassSacrificialDagger(String name)
    {
        super();

        setUnlocalizedName(BloodArsenal.MOD_ID + "." + name);
        setCreativeTab(BloodArsenal.TAB_BLOOD_ARSENAL);
        setMaxStackSize(1);
        setFull3D();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return this.getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List<ItemStack> list)
    {
        list.add(new ItemStack(item));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced)
    {
        list.addAll(Arrays.asList(TextHelper.cutLongString(TextHelper.localizeEffect("tooltip.BloodArsenal.glassSacrificialDagger.desc"))));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        Item dummyItem = new Item();

        if (PlayerHelper.isFakePlayer(player))
            return dummyItem.onItemRightClick(stack, world, player, hand);

        if (this.canUseForSacrifice(stack))
        {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        int lpAdded = ConfigHandler.glassSacrificialDaggerLP;

        if (!player.capabilities.isCreativeMode)
        {
            SacrificeKnifeUsedEvent evt = new SacrificeKnifeUsedEvent(player, true, true, 2, lpAdded);
            if (MinecraftForge.EVENT_BUS.post(evt))
                return dummyItem.onItemRightClick(stack, world, player, hand);

            if (evt.shouldDrainHealth)
            {
                player.hurtResistantTime = 0;
                player.attackEntityFrom(BloodArsenal.getDamageSourceGlass(), 0.001F);
                player.setHealth(Math.max(player.getHealth() - ConfigHandler.glassSacrificialDaggerHealth, 0.0001f));

                if (itemRand.nextInt(4) == 3)
                    player.addPotionEffect(new PotionEffect(ModPotions.bleeding, itemRand.nextInt(8) * 20, itemRand.nextInt(2)));

                if (player.getHealth() <= 0.001f)
                {
                    player.onDeath(BloodArsenal.getDamageSourceBleeding());
                    player.setHealth(0);
                }
            }

            if (!evt.shouldFillAltar)
                return dummyItem.onItemRightClick(stack, world, player, hand);

            lpAdded = evt.lpAdded;
        }

        double posX = player.posX;
        double posY = player.posY;
        double posZ = player.posZ;
        world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

        for (int l = 0; l < 8; ++l)
            world.spawnParticle(EnumParticleTypes.REDSTONE, posX + Math.random() - Math.random(), posY + Math.random() - Math.random(), posZ + Math.random() - Math.random(), 0, 0, 0);

        if (!world.isRemote && PlayerHelper.isFakePlayer(player))
            return dummyItem.onItemRightClick(stack, world, player, hand);

        // TODO - Check if SoulFray is active
        PlayerSacrificeHelper.findAndFillAltar(world, player, lpAdded, false);

        return dummyItem.onItemRightClick(stack, world, player, hand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemMeshDefinition getMeshDefinition()
    {
        return stack -> new ModelResourceLocation(new ResourceLocation(BloodArsenal.MOD_ID, "item/ItemGlassSacrificialDagger"), canUseForSacrifice(stack) ? "type=ceremonial" : "type=normal");
    }

    @Override
    public List<String> getVariants()
    {
        List<String> variants = new ArrayList<>();
        variants.add("type=normal");
        variants.add("type=ceremonial");
        return variants;
    }
}
