/**
 *  Taken from SpitefulFox's ForbiddenMagic
 *  https://github.com/SpitefulFox/ForbiddenMagic
 */

package com.arc.bloodarsenal.common.thaumcraft;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;

import java.util.List;

public class BloodyWandCap extends WandCap
{
    public BloodyWandCap(String tag, float discount, ItemStack item, int craftCost, ResourceLocation skin) {
        super(tag, discount, item, craftCost);
        this.setTexture(skin);
    }

    public BloodyWandCap(String tag, float discount, List<Aspect> specialAspects, float discountSpecial, ItemStack item, int craftCost, ResourceLocation skin) {
        super(tag, discount, specialAspects, discountSpecial, item, craftCost);
        this.setTexture(skin);
    }
}
