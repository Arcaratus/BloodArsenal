package arc.bloodarsenal.compat.guideapi;

import WayofTime.bloodmagic.api.BloodMagicAPI;
import WayofTime.bloodmagic.api.registry.OrbRegistry;
import amerifrance.guideapi.api.GuideAPI;
import arc.bloodarsenal.ConfigHandler;
import arc.bloodarsenal.compat.ICompatibility;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CompatGuideAPI implements ICompatibility
{
    private static IRecipe guideRecipe = null;
    private static boolean worldFlag;

    @Override
    public void loadCompatibility(InitializationPhase phase)
    {
        switch (phase)
        {
            case PRE_INIT:
            {
                GuideBloodArsenal.initBook();
                GameRegistry.register(GuideBloodArsenal.guideBook);
                if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
                    GuideAPI.setModel(GuideBloodArsenal.guideBook);

                break;
            }
            case INIT:
            {
                guideRecipe = new ShapelessOreRecipe(GuideAPI.getStackFromBook(GuideBloodArsenal.guideBook), Items.BOOK, Items.FLINT_AND_STEEL, OrbRegistry.getOrbStack(WayofTime.bloodmagic.registry.ModItems.orbWeak), UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, BloodMagicAPI.getLifeEssence()));
                break;
            }
            case POST_INIT:
            {
                if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
                    GuideBloodArsenal.initCategories();

                break;
            }
            case MAPPING:
            {
                if (!worldFlag)
                {
                    GameRegistry.addRecipe(guideRecipe);
                    worldFlag = true;
                }
                else
                {
                    CraftingManager.getInstance().getRecipeList().remove(guideRecipe);
                    worldFlag = false;
                }

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
