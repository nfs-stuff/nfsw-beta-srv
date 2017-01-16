package me.leorblx.betasrv.modules.http.legacy;

import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public interface ContentGenerator
{
    boolean applies(Request request, String target);

    String generate(Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target);
}
