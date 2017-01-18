package me.leorblx.betasrv.modules.http.legacy.handlers;

import me.leorblx.betasrv.modules.http.legacy.HttpRequestProcessor;
import me.leorblx.betasrv.modules.http.legacy.HttpState;
import me.leorblx.betasrv.modules.http.legacy.RequestHandler;
import me.leorblx.betasrv.modules.xmpp.XmppFactory;
import me.leorblx.betasrv.utils.XmlUtils;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public class PersonaPresenceHandler implements RequestHandler
{
    @Override
    public boolean applies(Request request, String target)
    {
        return target.contains("UpdatePersonaPresence");
    }

    @Override
    public void handle(Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target)
    {
        if (!baseRequest.getParameter("presence").equals("1")) {
            requestProcessor.log.info("client -> safehouse/disconnect");
            XmppFactory.getXmppSenderInstance("Offline")
                    .send(XmlUtils.getFromFile(String.format(
                            "www/nfsw/Engine.svc/DriverPersona/UpdatePersonaPresence_%s.xml",
                            baseRequest.getParameter("presence")
                    )), HttpState.personaId);
        }
    }
}
