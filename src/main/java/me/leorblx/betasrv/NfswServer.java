package me.leorblx.betasrv;

import com.google.common.collect.ImmutableMap;
import me.leorblx.betasrv.modules.Module;
import me.leorblx.betasrv.modules.ModuleManager;
import me.leorblx.betasrv.utils.Log;
import org.fusesource.jansi.AnsiConsole;

public class NfswServer
{
    private static final Log LOG = Log.getLog("NFS:W Server");
    
    private static final ImmutableMap<String, String> contributors = ImmutableMap.<String, String>builder()
            .put("leorblx", "Reversing/putting stuff together/Creating this thing")
            .put("fabx24", "Log file supplier, source of info")
            .put("Nilzao", "Working on XMPP")
            .put("berkay2578", "Getting me started, providing car hashes")
            .build();
    
    /**
     * Run the program.
     */
    public static void main(String[] args)
    {
        LOG.info("Booting server...");
        
        System.setProperty("jsse.enableCBCProtection", "false");
        
        ModuleManager.getInstance().bootAll();
        AnsiConsole.systemInstall();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.info("Shutting down...");
            ModuleManager.getInstance().getModules().values().forEach(Module::shutdown);
            
            LOG.info("Thanks for using this server!");
            LOG.info("-------------- Credits --------------");
            contributors.entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .forEach(LOG::info);
            LOG.info("-------------------------------------");
            
            AnsiConsole.systemUninstall();
        }));
    }
}
