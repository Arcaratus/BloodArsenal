package com.arc.bloodarsenal.common.thaumcraft;

import com.arc.bloodarsenal.common.BloodArsenalConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;

public class BloodArsenalResearchItem extends ResearchItem
{
    String inter = null;

    public BloodArsenalResearchItem(String par1, String x) {
        super(par1, x);
    }

    public BloodArsenalResearchItem(String par1, String x, AspectList tags, int y, int z, int par5, ResourceLocation icon)
    {
        super(par1, x, tags, y, z, par5, icon);
    }

    public BloodArsenalResearchItem(String par1, String x, AspectList tags, int y, int z, int par5, ItemStack icon)
    {
        super(par1, x, tags, y, z, par5, icon);
    }

    public BloodArsenalResearchItem(String par1, String x, String mod, AspectList tags, int y, int z, int par5, ResourceLocation icon)
    {
        super(par1, x, tags, y, z, par5, icon);
        this.inter = mod;
    }

    public BloodArsenalResearchItem(String par1, String x, String mod, AspectList tags, int y, int z, int par5, ItemStack icon)
    {
        super(par1, x, tags, y, z, par5, icon);
        this.inter = mod;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getName() {
        return StatCollector.translateToLocal("blood_arsenal.research_name." + this.key);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getText()
    {
        return BloodArsenalConfig.researchTag ? (this.inter == null ? "[BA] " + StatCollector.translateToLocal("blood_arsenal.research_text." + this.key) : "[BA] " + this.inter + " " + StatCollector.translateToLocal("blood_arsenal.research_text." + this.key)) : StatCollector.translateToLocal("blood_arsenal.research_text." + this.key);
    }
}
