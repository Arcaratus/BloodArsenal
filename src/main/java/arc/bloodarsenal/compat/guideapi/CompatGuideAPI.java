package arc.bloodarsenal.compat.guideapi;

import WayofTime.bloodmagic.api.BloodMagicAPI;
import WayofTime.bloodmagic.api.registry.OrbRegistry;
import amerifrance.guideapi.api.GuideAPI;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.compat.ICompatibility;
import arc.bloodarsenal.registry.ModRecipes;
import net.minecraft.init.Items;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CompatGuideAPI implements ICompatibility
{
    @Override
    public void loadCompatibility(InitializationPhase phase)
    {
        switch (phase)
        {
            case PRE_INIT:
            {
                GuideBloodArsenal.initBook();
                GameRegistry.register(GuideBloodArsenal.guideBook);
                ModRecipes.addShapelessBloodOrbRecipe(GuideAPI.getStackFromBook(GuideBloodArsenal.guideBook), Items.BOOK, Items.FLINT_AND_STEEL, OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbWeak), UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BloodMagicAPI.getLifeEssence()));
                if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
                    GuideAPI.setModel(GuideBloodArsenal.guideBook);

                break;
            }
            case INIT:
            {
                break;
            }
            case POST_INIT:
            {
                GuideBloodArsenal.initCategories();
                break;
            }
        }
    }

    @Override
    public String getModId()
    {
        return "guideapi";
    }

    @Override
    public boolean enableCompat()
    {
        return ConfigHandler.guideAPIEnabled;
    }
}
