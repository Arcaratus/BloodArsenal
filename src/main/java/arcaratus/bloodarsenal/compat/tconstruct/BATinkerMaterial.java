package arcaratus.bloodarsenal.compat.tconstruct;

import arcaratus.bloodarsenal.BloodArsenal;
import com.google.common.collect.Streams;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BATinkerMaterial
{
    private final Material material;
    private final Fluid fluid;
    private final String name;
    private final int color;

    private final String oreDictSuffix;
    private final ItemStack representativeStack;

    private List<IMaterialStats> stats = new ArrayList<>();
    private List<ITrait> allTraits = new ArrayList<>();
    private List<Pair<ITrait, String>> traits = new ArrayList<>();

    public BATinkerMaterial(String name, int color, String oreDictSuffix, ItemStack representativeStack, boolean isFluid)
    {
        this.name = name;
        this.color = color;
        this.oreDictSuffix = oreDictSuffix;
        this.representativeStack = representativeStack;
        material = new Material(name, color);

        if (isFluid)
        {
            ResourceLocation still = new ResourceLocation("bloodarsenal", name);
            fluid = new Fluid(name, still, still);
            fluid.setTemperature(1000);
        }
        else
        {
            fluid = null;
        }

        renderThings(isFluid);
        addStats(stats);
        addTraits();
    }

    @SideOnly(Side.CLIENT)
    private void renderThings(boolean isFluid)
    {
        if (isFluid)
            BloodArsenal.PROXY.registerFluidModels(fluid);

        material.setRenderInfo(createRenderInfo());
    }

    public abstract void addStats(List<IMaterialStats> stats);

    public abstract void addTraits();

    public void addTrait(ITrait trait, String dependency)
    {
        traits.add(Pair.of(trait, dependency));
    }

    public void addTrait(ITrait trait)
    {
        addTrait(trait, null);
    }

    public void addTraitToParts(ITrait trait)
    {
        allTraits.add(trait);
    }

    public List<IMaterialStats> getStats()
    {
        return stats;
    }

    public Set<ITrait> getTraits()
    {
        return Streams.concat(traits.stream().map(Pair::getLeft), allTraits.stream()).collect(Collectors.toSet());
    }

    @SideOnly(Side.CLIENT)
    public abstract MaterialRenderInfo createRenderInfo();

    private void registerTrait(ITrait trait, String name)
    {
        ITrait existingTrait = TinkerRegistry.getTrait(trait.getIdentifier());
        if (existingTrait != null)
            trait = existingTrait;

        material.addTrait(trait, name);
    }

    public void registerTraits()
    {
        for (Pair<ITrait, String> trait : traits)
            registerTrait(trait.getLeft(), trait.getRight());

        traits.stream().map(Pair::getRight).collect(Collectors.toSet()).forEach(s -> allTraits.forEach(trait -> registerTrait(trait, s)));
    }

    public Material getMaterial()
    {
        return material;
    }

    public Fluid getFluid()
    {
        return fluid;
    }

    public String getName()
    {
        return name;
    }

    public String getOreDictSuffix()
    {
        return oreDictSuffix;
    }

    public ItemStack getRepresentativeStack()
    {
        return representativeStack;
    }
}
