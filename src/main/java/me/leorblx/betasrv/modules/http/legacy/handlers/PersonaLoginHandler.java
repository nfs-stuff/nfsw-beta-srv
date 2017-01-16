package me.leorblx.betasrv.modules.http.legacy.handlers;

import me.leorblx.betasrv.modules.http.legacy.HttpRequestProcessor;
import me.leorblx.betasrv.modules.http.legacy.HttpState;
import me.leorblx.betasrv.modules.http.legacy.RequestHandler;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public class PersonaLoginHandler implements RequestHandler
{
    @Override
    public boolean applies(Request request, String target)
    {
        return request.getRequestURI().contains("SecureLoginPersona");
    }

    @Override
    public void handle(Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target)
    {
        requestProcessor.log.info("Persona login: " + request.getParameter("personaId"));

        HttpState.personaId = Long.parseLong(request.getParameter("personaId"));
    }
}
