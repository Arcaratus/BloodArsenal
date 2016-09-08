package arc.bloodarsenal.compat.baubles;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.registry.OrbRegistry;
import WayofTime.bloodmagic.item.ItemDemonCrystal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.compat.ICompatibility;
import arc.bloodarsenal.registry.ModItems;
import arc.bloodarsenal.registry.ModRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

public class CompatBaubles implements ICompatibility
{
    public static Item SACRIFICE_AMULET;
    public static Item SELF_SACRIFICE_AMULET;
    public static Item SOUL_PENDANT;

    @Override
    public void loadCompatibility(InitializationPhase phase)
    {
        switch (phase)
        {
            case PRE_INIT:
            {
                doItems();
                break;
            }
            case INIT:
            {
                ModRecipes.addOreDictBloodOrbRecipe(new ItemStack(SACRIFICE_AMULET), "aaa", "aba", "caa", 'a', ModItems.BLOOD_BURNED_STRING, 'b', OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.ORB_APPRENTICE), 'c', ModItems.GEM_SACRIFICE);
                ModRecipes.addOreDictBloodOrbRecipe(new ItemStack(SELF_SACRIFICE_AMULET), "aaa", "aba", "caa", 'a', ModItems.BLOOD_BURNED_STRING, 'b', OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.ORB_APPRENTICE), 'c', ModItems.GEM_SELF_SACRIFICE);
                ModRecipes.addOreDictBloodOrbRecipe(new ItemStack(SOUL_PENDANT), "aaa", "aba", "aca", 'a', ModItems.BLOOD_BURNED_STRING, 'b', OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.ORB_WEAK), 'c', ModItems.GEM_TARTARIC);

                ModRecipes.addForgeRecipe(new ItemStack(SOUL_PENDANT, 1, 1), 60, 40, new ItemStack(SOUL_PENDANT), ModItems.GEM_TARTARIC, "blockRedstone", "blockLapis");
                ModRecipes.addForgeRecipe(new ItemStack(SOUL_PENDANT, 1, 2), 240, 100, new ItemStack(SOUL_PENDANT, 1, 1), ModItems.GEM_TARTARIC, "blockGold", new ItemStack(ModRecipes.getBMItem(Constants.BloodMagicItem.SLATE), 1, 2));
                ModRecipes.addForgeRecipe(new ItemStack(SOUL_PENDANT, 1, 3), 1000, 200, new ItemStack(SOUL_PENDANT, 1, 2), new ItemStack(ModRecipes.getBMItem(Constants.BloodMagicItem.SLATE), 1, 3), ModRecipes.getBMItem(Constants.BloodMagicItem.BLOOD_SHARD), ItemDemonCrystal.getStack(ItemDemonCrystal.CRYSTAL_DEFAULT));
                ModRecipes.addForgeRecipe(new ItemStack(SOUL_PENDANT, 1, 4), 4000, 1000, new ItemStack(SOUL_PENDANT, 1, 3), Items.NETHER_STAR);
                break;
            }
        }
    }

    @Override
    public String getModId()
    {
        return "Baubles";
    }

    @Override
    public boolean enableCompat()
    {
        return ConfigHandler.baublesEnabled;
    }

    @Optional.Method(modid = "Baubles")
    private static void doItems()
    {
        SACRIFICE_AMULET = ModItems.registerItem(new ItemSacrificeAmulet("sacrificeAmulet"));
        SELF_SACRIFICE_AMULET = ModItems.registerItem(new ItemSelfSacrificeAmulet("selfSacrificeAmulet"));
        SOUL_PENDANT = ModItems.registerItem(new ItemSoulPendant("soulPendant"));
    }
}
