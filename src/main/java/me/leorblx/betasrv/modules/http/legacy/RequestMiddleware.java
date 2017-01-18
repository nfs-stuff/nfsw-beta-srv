package me.leorblx.betasrv.modules.http.legacy;

import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public interface RequestMiddleware
{
    boolean applies(Request request, String target);

    String modify(String content, Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target);
}
