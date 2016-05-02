package arc.bloodarsenal.util;

import WayofTime.bloodmagic.client.IVariantProvider;
import net.minecraft.block.properties.IProperty;

public interface IComplexVariantProvider extends IVariantProvider
{
    IProperty[] getIgnoredProperties();
}
