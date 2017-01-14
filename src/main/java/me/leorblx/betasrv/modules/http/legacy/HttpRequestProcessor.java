package me.leorblx.betasrv.modules.http.legacy;

import me.leorblx.betasrv.modules.xmpp.IXmppSender;
import me.leorblx.betasrv.modules.xmpp.XmppFactory;
import me.leorblx.betasrv.modules.xmpp.offline.XmppSrv;
import me.leorblx.betasrv.utils.Log;
import me.leorblx.betasrv.utils.MiscUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.DefaultHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class HttpRequestProcessor extends DefaultHandler
{
    public final Log log;
    private final FileResolver fileResolver;
    private final ConfigReplacer configReplacer;

    public HttpRequestProcessor(Log log)
    {
        this.log = log;
        this.fileResolver = new FileResolver();
        this.configReplacer = new ConfigReplacer();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String modifiedTarget = target;
        String xmppTarget = modifiedTarget;

        boolean isXmpp = false;
        boolean eventXmpp = false;

        if (target.matches("(.*)/powerup/activated(.*)")) {
            isXmpp = true;
        } else if (target.equalsIgnoreCase("/nfsw/Engine.svc/Reporting/SendJoinQueue")) {
            xmppTarget = "/nfsw/Engine.svc/matchmaking/session";
            isXmpp = true;
        }

        if (target.contains("launchevent")) {
            modifiedTarget = "/nfsw/Engine.svc/matchmaking/launchevent/100/default";
            eventXmpp = true;
        }

        if (target.contains(".jpg")) {
            response.setContentType("image/jpeg");
        } else {
            response.setContentType("application/xml;charset=utf-8");
        }

        if ("/nfsw/Engine.svc/catalog/productsInCategory".equals(target)) {
            modifiedTarget = target + "_" + baseRequest.getParameter("categoryName");
        }

        if ("/nfsw/Engine.svc/DriverPersona/GetPersonaInfo".equals(target)) {
            modifiedTarget = target + "_" + baseRequest.getParameter("personaId");
        }

        if (target.matches("(.*)/powerup/activated(.*)")) {
            isXmpp = true;
        }

        if (target.matches("/nfsw/Engine.svc/User/SecureLoginPersona")) {
            HttpState.personaId = Long.valueOf(request.getParameter("personaId"));
        }

        if (target.matches("/nfsw/Engine.svc/User/SecureLogoutPersona")) {
            IXmppSender sender = XmppFactory.getXmppSenderInstance("offline");

            if (sender instanceof XmppSrv)
                XmppSrv.removeXmppClient(HttpState.personaId);
        }

        log.info(String.format("%s request to %s%s -> %s (from %s)", baseRequest.getMethod(), target, baseRequest.getQueryString() != null ? "?" + baseRequest.getQueryString() : "", modifiedTarget, MiscUtils.formatHost(request.getRemoteHost())));

        response.setStatus(200);
        response.setHeader("Connection", "close");

        byte[] content = fileResolver.resolve(modifiedTarget);

        if (content == null) {
            log.warn("Not found!");
        } else if (!target.contains(".jpg")) {
            log.debug("Sending XML data...");

            content = this.configReplacer.replaceVariables(content);

            String sContent = new String(content, StandardCharsets.UTF_8);
            if (sContent.contains("RELAYPERSONA")) {
                sContent = sContent.replace("RELAYPERSONA", "100");
            } else if (sContent.contains("{eventId}")) {
                List<String> parts = Arrays.asList(target.split("/"));
                sContent = sContent.replace("{eventId}", parts.get(parts.size() - 1));
            }

            response.setContentLength(content.length);
            response.getWriter().println(sContent);
            response.getWriter().flush();
        } else {
            log.debug("Sending JPEG data...");
            response.getOutputStream().write(content);
            response.getOutputStream().flush();
            response.getOutputStream().println();
            response.getOutputStream().flush();
        }
        
        for (RequestHandler handler : HandlerManager.getInstance().getHandlers()) {
            if (handler.applies(baseRequest))
                handler.handle(baseRequest, request, this);
        }

//        if (isXmpp) {
//            log.standOut("Sending XMPP message (" + xmppTarget + ")");
//
//            sendXmpp(xmppTarget);
//        }
//
//        if (eventXmpp) {
//            log.standOut("Sending event XMPP message");
//
//            sendXmpp("/nfsw/Engine.svc/matchmaking/session");
//        }

        baseRequest.setHandled(true);
    }

    public void sendXmppMessage(String fileName)
    {
        String pathString = String.format("www%s_xmpp.xml", fileName);
        log.standOut("XMPP file path: " + pathString);

        Path path = Paths.get(pathString);
        
        if (Files.exists(path)) {
            try {
                byte[] data = Files.readAllBytes(path);
                
                if (data != null) {
                    String msg = new String(data, StandardCharsets.UTF_8).replace("RELAYPERSONA", "100");
                    
                    XmppFactory.getXmppSenderInstance("offline").send(msg, HttpState.personaId);
                }
            } catch (IOException e) {
                log.log(e);
            }
        }
    }

    private void sendXmpp(String target)
    {
        try {
            String path = "www" + target + "_xmpp.xml";
            log.standOut("XMPP file path: " + path);
            File fxmpp = new File(path);
            byte[] encoded;
            if (fxmpp.exists()) {
                encoded = Files.readAllBytes(Paths.get(path));
                if (encoded != null) {
                    String msg = new String(encoded, StandardCharsets.UTF_8).replace("RELAYPERSONA", "100");

                    msg = setXmppSubject(msg);

                    log.info("Sending XMPP message (File: " + path + ")");
                    XmppFactory.getXmppSenderInstance("offline")
                            .send(msg, 100L);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String setXmppSubject(String msg)
    {
//        String[] splitMsg = msg.split("<body>|</body>");
//        String[] splitMsgTo = splitMsg[0].split("\\\"");
//        String msgTo = splitMsgTo[5];
//        String msgBody = splitMsg[1];
//        msgBody = msgBody.replace("&lt;", "<");
//        msgBody = msgBody.replace("&gt;", ">");
//        Long subject = SubjectCalc.calculateHash(msgTo.toCharArray(), msgBody.toCharArray());
//        msg = msg.replace("LOLnope.", subject.toString());
        return msg;
    }
}
