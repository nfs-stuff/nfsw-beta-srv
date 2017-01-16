package me.leorblx.betasrv.modules.http.legacy.handlers;

import me.leorblx.betasrv.modules.http.legacy.HttpRequestProcessor;
import me.leorblx.betasrv.modules.http.legacy.HttpState;
import me.leorblx.betasrv.modules.http.legacy.RequestHandler;
import me.leorblx.betasrv.modules.xmpp.IXmppSender;
import me.leorblx.betasrv.modules.xmpp.XmppFactory;
import me.leorblx.betasrv.modules.xmpp.offline.XmppServer;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public class PersonaLogoutHandler implements RequestHandler
{
    @Override
    public boolean applies(Request request, String target)
    {
        return request.getRequestURI().contains("SecureLogoutPersona");
    }

    @Override
    public void handle(Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target)
    {
        IXmppSender sender = XmppFactory.getXmppSenderInstance("offline");

        if (sender instanceof XmppServer) {
            ((XmppServer) sender).removeClient(HttpState.personaId);
            
            requestProcessor.log.info("Removed XMPP client for persona " + HttpState.personaId);
        }
    }
}
