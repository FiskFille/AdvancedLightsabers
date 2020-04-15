package fiskfille.utils;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

import fiskfille.utils.helper.FiskServerUtils;
import net.minecraft.util.StringUtils;

public class Log
{
    private static Map<String, Logger> loggers = Maps.newHashMap();

    private static Logger fetchLogger(String key)
    {
        Logger logger = loggers.get(key);

        if (logger == null)
        {
            loggers.put(key, logger = LogManager.getLogger(key));
        }

        return logger;
    }

    private static Logger getLogger(String prefix)
    {
        String name = FiskServerUtils.getActiveModName();

        if (!StringUtils.isNullOrEmpty(prefix))
        {
            name = String.format("%s/%s", name, prefix);
        }

        return fetchLogger(name);
    }

    public static boolean warn2(String prefix, String message, Object... args)
    {
        return log(Level.WARN, prefix, message, args);
    }

    public static boolean error2(String prefix, String message, Object... args)
    {
        return log(Level.ERROR, prefix, message, args);
    }

    public static boolean info2(String prefix, String message, Object... args)
    {
        return log(Level.INFO, prefix, message, args);
    }

    public static boolean debug2(String prefix, String message, Object... args)
    {
        return log(Level.DEBUG, prefix, message, args);
    }

    public static boolean warn(String message, Object... args)
    {
        return warn2("", message, args);
    }

    public static boolean error(String message, Object... args)
    {
        return error2("", message, args);
    }

    public static boolean info(String message, Object... args)
    {
        return info2("", message, args);
    }

    public static boolean debug(String message, Object... args)
    {
        return debug2("", message, args);
    }

    private static boolean log(Level level, String prefix, String message, Object... args)
    {
        Logger logger = getLogger(prefix);

        if (args.length > 0)
        {
            message = String.format(message, args);
        }

        logger.log(level, message);

        return logger.isEnabled(level);
    }
}
