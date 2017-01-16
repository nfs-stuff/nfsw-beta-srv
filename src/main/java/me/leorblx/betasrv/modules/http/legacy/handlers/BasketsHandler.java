package me.leorblx.betasrv.modules.http.legacy.handlers;

import me.leorblx.betasrv.modules.http.legacy.HttpRequestProcessor;
import me.leorblx.betasrv.modules.http.legacy.RequestHandler;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public class BasketsHandler implements RequestHandler
{
    @Override
    public boolean applies(Request request, String target)
    {
        return target.matches("(.*)/baskets");
    }

    @Override
    public void handle(Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target)
    {
        requestProcessor.log.standOut("Basket Request: ");
        requestProcessor.log.standOut("    > " + requestProcessor.readInputStream(request));
    }
}
