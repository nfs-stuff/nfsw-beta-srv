package me.leorblx.betasrv.modules.http.legacy.handlers;

import me.leorblx.betasrv.modules.http.legacy.HttpRequestProcessor;
import me.leorblx.betasrv.modules.http.legacy.RequestHandler;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public class ArbitrationHandler implements RequestHandler
{
    @Override
    public boolean applies(Request request, String target)
    {
        return target.contains("event/arbitrate");
    }

    @Override
    public void handle(Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target)
    {
        requestProcessor.log.info("Arbitration handler");
        
        requestProcessor.sendXmppMessage("/nfsw/Engine.svc/event/eventstats");
    }
}
