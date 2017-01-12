package me.leorblx.betasrv.modules;

/**
 * A server module. Handles different actions.
 * 
 * @author heyitsleo 
 * @since 1.1
 * @see me.leorblx.betasrv.modules.http.HttpModule HTTP server module
 */
public interface Module
{
    /**
     * Boot the module.
     */
    void boot();

    /**
     * Do a shutdown of the module.
     */
    void shutdown();
}
