package me.leorblx.betasrv.modules.config;

import me.leorblx.betasrv.config.ConfigurationManager;
import me.leorblx.betasrv.modules.Module;
import me.leorblx.betasrv.utils.Log;

public class ConfigModule implements Module
{
    @Override
    public void boot()
    {
        ConfigurationManager.getInstance();
        
        if (ConfigurationManager.getInstance().getConfiguration().isDebugEnabled())
            Log.LEVEL = Log.Level.DEBUG;
//        Configuration configuration = Configuration.getInstance();
    }

    @Override
    public void shutdown()
    {

    }
}
