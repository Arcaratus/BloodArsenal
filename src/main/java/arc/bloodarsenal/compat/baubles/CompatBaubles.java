package arc.bloodarsenal.compat.baubles;

import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.api.registry.OrbRegistry;
import WayofTime.bloodmagic.item.ItemDemonCrystal;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.compat.ICompatibility;
import arc.bloodarsenal.registry.ModItems;
import arc.bloodarsenal.registry.ModRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

public class CompatBaubles implements ICompatibility
{
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
                ModRecipes.addOreDictBloodOrbRecipe(new ItemStack(ModItems.sacrificeAmulet), "aaa", "aba", "caa", 'a', ModItems.bloodBurnedString, 'b', OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbApprentice), 'c', ModItems.gemSacrifice);
                ModRecipes.addOreDictBloodOrbRecipe(new ItemStack(ModItems.selfSacrificeAmulet), "aaa", "aba", "caa", 'a', ModItems.bloodBurnedString, 'b', OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbApprentice), 'c', ModItems.gemSelfSacrifice);
                ModRecipes.addOreDictBloodOrbRecipe(new ItemStack(ModItems.soulPendant), "aaa", "aba", "aca", 'a', ModItems.bloodBurnedString, 'b', OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbWeak), 'c', ModItems.gemTartaric);

                ModRecipes.addForgeRecipe(new ItemStack(ModItems.soulPendant, 1, 1), 60, 40, new ItemStack(ModItems.soulPendant), ModItems.gemTartaric, "blockRedstone", "blockLapis");
                ModRecipes.addForgeRecipe(new ItemStack(ModItems.soulPendant, 1, 2), 240, 100, new ItemStack(ModItems.soulPendant, 1, 1), ModItems.gemTartaric, "blockGold", new ItemStack(ModRecipes.getBMItem(Constants.BloodMagicItem.SLATE), 1, 2));
                ModRecipes.addForgeRecipe(new ItemStack(ModItems.soulPendant, 1, 3), 1000, 200, new ItemStack(ModItems.soulPendant, 1, 2), new ItemStack(ModRecipes.getBMItem(Constants.BloodMagicItem.SLATE), 1, 3), ModRecipes.getBMItem(Constants.BloodMagicItem.BLOOD_SHARD), ItemDemonCrystal.getStack(ItemDemonCrystal.CRYSTAL_DEFAULT));
                ModRecipes.addForgeRecipe(new ItemStack(ModItems.soulPendant, 1, 4), 4000, 1000, new ItemStack(ModItems.soulPendant, 1, 3), Items.NETHER_STAR);
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
    private void doItems()
    {
        ModItems.sacrificeAmulet = ModItems.registerItem(new ItemSacrificeAmulet("sacrificeAmulet"));
        ModItems.selfSacrificeAmulet = ModItems.registerItem(new ItemSelfSacrificeAmulet("selfSacrificeAmulet"));
        ModItems.soulPendant = ModItems.registerItem(new ItemSoulPendant("soulPendant"));
    }
}
