package me.leorblx.betasrv.modules.http.legacy;

import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public interface RequestHandler
{
    boolean applies(Request request, String target);
    
    void handle(Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target);
}
