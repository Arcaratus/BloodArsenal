package arc.bloodarsenal.compat.baubles;

import WayofTime.bloodmagic.api.registry.OrbRegistry;
import arc.bloodarsenal.compat.ICompatibility;
import arc.bloodarsenal.registry.ModItems;
import arc.bloodarsenal.registry.ModRecipes;
import net.minecraft.item.ItemStack;

public class CompatBaubles implements ICompatibility
{
    @Override
    public void loadCompatibility(InitializationPhase phase)
    {
        if (phase == InitializationPhase.PRE_INIT)
        {
            ModItems.sacrificeAmulet = ModItems.registerItem(new ItemSacrificeAmulet("sacrificeAmulet"));
            ModItems.selfSacrificeAmulet = ModItems.registerItem(new ItemSelfSacrificeAmulet("selfSacrificeAmulet"));
            ModItems.soulPendant = ModItems.registerItem(new ItemSoulPendant("soulPendant"));
        }

        if (phase == InitializationPhase.INIT)
        {
            ModRecipes.addOreDictBloodOrbRecipe(new ItemStack(ModItems.sacrificeAmulet), "aaa", "aba", "caa", 'a', ModItems.bloodBurnedString, 'b', OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbApprentice), 'c', ModItems.gemSacrifice);
            ModRecipes.addOreDictBloodOrbRecipe(new ItemStack(ModItems.selfSacrificeAmulet), "aaa", "aba", "caa", 'a', ModItems.bloodBurnedString, 'b', OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbApprentice), 'c', ModItems.gemSelfSacrifice);
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
        return true;
    }
}
