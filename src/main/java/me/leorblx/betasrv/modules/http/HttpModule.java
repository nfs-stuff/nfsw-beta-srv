package me.leorblx.betasrv.modules.http;

import com.google.common.base.Preconditions;
import me.leorblx.betasrv.config.ConfigurationManager;
import me.leorblx.betasrv.modules.Module;
import me.leorblx.betasrv.utils.Log;
import me.leorblx.betasrv.utils.NoLogging;
import org.eclipse.jetty.server.Server;

public class HttpModule implements Module
{
    private static final Log LOG = Log.getLog("HTTP");

    private Server server;

    @Override
    public void boot()
    {
        org.eclipse.jetty.util.log.Log.setLog(new NoLogging());

        try {
            LOG.info("Starting web server...");

            server = new Server(ConfigurationManager.getInstance().getConfiguration().getHttpPort());
            server.setHandler(new HttpRequestProcessor(LOG));
            server.setStopAtShutdown(true);
            server.start();

            LOG.info("Web server started on port " + ConfigurationManager.getInstance().getConfiguration().getHttpPort());
        } catch (Throwable e) {
            LOG.log(e);
        }
    }

    @Override
    public void shutdown()
    {
        Preconditions.checkNotNull(server, "No server created");

        try {
            LOG.info("Shutting down HTTP server...");
            server.stop();
        } catch (Throwable e) {
            LOG.log(e);
        }
    }
}
