package arcaratus.bloodarsenal.compat.tconstruct;

import arcaratus.bloodarsenal.core.RegistrarBloodArsenal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ModifierSerrated extends ModifierTrait
{
    public ModifierSerrated()
    {
        super("serrated", 0xDDDDDD, 3, 36);

        addAspects(ModifierAspect.weaponOnly);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
    {
        if (wasHit)
        {
            if (target.getHealth() > 0.01)
            {
                ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, getModifierIdentifier()));
                target.addPotionEffect(new PotionEffect(RegistrarBloodArsenal.BLEEDING, 20 + (random.nextInt((data.level) * 2) * 20), data.level));
            }
        }
    }
}
