package me.leorblx.betasrv.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fusesource.jansi.Ansi;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Log class from DV8FromTheWorld's JDA library, with modifications to make stuff a bit nicer.
 * Removes file logs, removes the Javadocs, and adds new log levels (and colors!!!!!!)
 * <p>
 * <p>
 *
 * @author DV8FromTheWorld, modified by leorblx
 *         Original: https://github.com/DV8FromTheWorld/JDA/blob/master/src/main/java/net/dv8tion/jda/core/utils/SimpleLog.java
 *         (Apache 2.0 license, not entirely sure if I need to include it)
 */
public class Log
{
    /**
     * The global LOG-level that is used as standard if not overwritten
     */
    public static Level LEVEL = Level.INFO;

    private static final String FORMAT = "[%time%] [%level%] [%name%]: %text%";
    private static final SimpleDateFormat DFORMAT = new SimpleDateFormat("HH:mm:ss");

    private static final Map<String, Log> LOGS = new HashMap<>();
    private static final Set<LogListener> listeners = new HashSet<>();

    public static Log getLog(String name)
    {
        synchronized (LOGS) {
            if (!LOGS.containsKey(name.toLowerCase())) {
                LOGS.put(name.toLowerCase(), new Log(name));
            }
        }
        return LOGS.get(name.toLowerCase());
    }

    public static void addListener(LogListener listener)
    {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public static void removeListener(LogListener listener)
    {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public final String name;
    private Level level = null;

    private Log(String name)
    {
        this.name = name;
    }

    public void setLevel(Level lev)
    {
        this.level = lev;
    }

    public Level getLevel()
    {
        return level;
    }

    public void log(Level level, Object msg)
    {
        synchronized (listeners) {
            for (LogListener listener : listeners) {
                listener.onLog(this, level, msg);
            }
        }

        String format = FORMAT;
        format = format.replace("%time%", DFORMAT.format(new Date())).replace("%level%", level.getTag()).replace("%name%", name).replace("%text%", String.valueOf(msg));
        if (level != Level.OFF && level.getPriority() >= ((this.level == null) ? Log.LEVEL.getPriority() : this.level.getPriority())) {
            print(format, level);
        }
    }

    public void log(Throwable ex)
    {
        synchronized (listeners) {
            for (LogListener listener : listeners) {
                listener.onError(this, ex);
            }
        }

        log(Level.FATAL, "Encountered an exception:");
        log(Level.FATAL, ExceptionUtils.getStackTrace(ex));
    }

    public void trace(Object msg)
    {
        log(Level.TRACE, msg);
    }

    public void debug(Object msg)
    {
        log(Level.DEBUG, msg);
    }

    public void info(Object msg)
    {
        log(Level.INFO, msg);
    }

    public void success(Object msg)
    {
        log(Level.SUCCESS, msg);
    }

    public void warn(Object msg)
    {
        log(Level.WARNING, msg);
    }

    public void standOut(Object msg)
    {
        log(Level.STANDOUT, msg);
    }

    public void fatal(Object msg)
    {
        log(Level.FATAL, msg);
    }

    private void print(String msg, Level level)
    {
        Ansi ansi = ansi();
        if (level.getColor() != null)
            ansi = ansi.fg(level.getColor());
        ansi = ansi.a(msg).reset();

        System.out.println(ansi);
    }

    public interface LogListener
    {
        void onLog(Log log, Level logLevel, Object message);

        void onError(Log log, Throwable err);
    }

    public enum Level
    {
        ALL("Finest", 0, false),
        TRACE("Trace", 1, false),
        DEBUG("Debug", 2, false, Ansi.Color.CYAN),
        INFO("Info", 3, false, Ansi.Color.BLUE),
        WARNING("Warning", 4, true, Ansi.Color.YELLOW),
        FATAL("Fatal", 5, true, Ansi.Color.RED),
        OFF("NO-LOGGING", 8, true),
        SUCCESS("Success", 6, false, Ansi.Color.GREEN),
        STANDOUT("Standout", 7, false, Ansi.Color.RED);

        private String tag;
        private int priority;
        private boolean isError;
        private Ansi.Color color;

        Level(String tag, int priority, boolean isError)
        {
            this(tag, priority, isError, null);
        }

        Level(String tag, int priority, boolean isError, Ansi.Color color)
        {
            this.tag = tag;
            this.priority = priority;
            this.isError = isError;
            this.color = color;
        }

        public String getTag()
        {
            return tag;
        }

        public int getPriority()
        {
            return priority;
        }

        public boolean isError()
        {
            return isError;
        }

        public Ansi.Color getColor()
        {
            return color;
        }
    }
}