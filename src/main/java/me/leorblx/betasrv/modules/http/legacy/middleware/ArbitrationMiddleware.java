package me.leorblx.betasrv.modules.http.legacy.middleware;

import me.leorblx.betasrv.modules.http.legacy.HttpRequestProcessor;
import me.leorblx.betasrv.modules.http.legacy.RequestMiddleware;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public class ArbitrationMiddleware implements RequestMiddleware
{
    @Override
    public boolean applies(Request request, String target)
    {
        return target.contains("arbitrate");
    }

    @Override
    public String modify(String content, Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target)
    {
        return content.replace("EVENT_PLACING", baseRequest.getParameter("placing"));
    }
}
