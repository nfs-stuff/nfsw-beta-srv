package me.leorblx.betasrv.modules.http.legacy.handlers;

import me.leorblx.betasrv.modules.http.legacy.HttpRequestProcessor;
import me.leorblx.betasrv.modules.http.legacy.RequestHandler;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public class PowerupHandler implements RequestHandler
{
    @Override
    public boolean applies(Request request, String target)
    {
        return request.getRequestURI().contains("powerup/activated");
    }

    @Override
    public void handle(Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target)
    {
        requestProcessor.log.info("Handling powerup");
        requestProcessor.sendXmppMessage(target);
    }
}
