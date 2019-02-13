package arcaratus.bloodarsenal.compat;

import arcaratus.bloodarsenal.util.BALog;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import java.lang.annotation.*;
import java.util.Set;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CompatibilityPlugin
{
    String value();

    class Gather
    {
        public static Set<ICompatibilityPlugin> gather(ASMDataTable dataTable)
        {
            Stopwatch stopwatch = Stopwatch.createStarted();
            Set<ICompatibilityPlugin> loaders = Sets.newHashSet();
            Set<ASMDataTable.ASMData> discoveredLoaders = dataTable.getAll(CompatibilityPlugin.class.getName());

            for (ASMDataTable.ASMData data : discoveredLoaders)
            {
                try
                {
                    if (!Loader.isModLoaded((String) data.getAnnotationInfo().get("value")))
                        continue;

                    Class<?> asmClass = Class.forName(data.getClassName());
                    if (!ICompatibilityPlugin.class.isAssignableFrom(asmClass))
                    {
                        BALog.DEFAULT.error("Class at {} was annotated with @CompatibilityPlugin but is not an ICompatibilityPlugin.", data.getClassName());
                        continue;
                    }

                    BALog.DEBUG.info("Discovered a compatibility plugin at {}", data.getClassName());
                    loaders.add((ICompatibilityPlugin) asmClass.newInstance());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            BALog.DEBUG.info("Discovered {} plugin(s) in {}", loaders.size(), stopwatch.stop());
            return loaders;
        }
    }
}