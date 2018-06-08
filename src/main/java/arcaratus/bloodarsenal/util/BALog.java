package arcaratus.bloodarsenal.util;

import WayofTime.bloodmagic.ConfigHandler;
import arcaratus.bloodarsenal.BloodArsenal;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum BALog
{
    DEFAULT(BloodArsenal.MOD_ID) {
        @Override
        boolean enabled() {
            return true;
        }
    },
    DEBUG() {
        @Override
        boolean enabled() {
            return ConfigHandler.general.enableDebugLogging;
        }
    },
    API() {
        @Override
        boolean enabled() {
            return ConfigHandler.general.enableAPILogging;
        }
    },
    API_VERBOSE() {
        @Override
        boolean enabled() {
            return ConfigHandler.general.enableVerboseAPILogging;
        }
    },
    ;

    private final Logger logger;

    BALog(String logName) {
        logger = LogManager.getLogger(logName);
    }

    BALog() {
        logger = LogManager.getLogger(BloodArsenal.MOD_ID + "|" + WordUtils.capitalizeFully(name().replace("_", " ")));
    }

    abstract boolean enabled();

    public void info(String input, Object... args) {
        if (enabled())
            logger.info(input, args);
    }

    public void error(String input, Object... args) {
        if (enabled())
            logger.error(input, args);
    }

    public void warn(String input, Object... args) {
        if (enabled())
            logger.warn(input, args);
    }
}
