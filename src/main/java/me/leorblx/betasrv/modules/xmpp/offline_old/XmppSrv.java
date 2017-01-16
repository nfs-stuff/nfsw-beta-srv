package me.leorblx.betasrv.modules.xmpp.offline_old;

import me.leorblx.betasrv.config.ConfigurationManager;
import me.leorblx.betasrv.modules.xmpp.IXmppSender;
import me.leorblx.betasrv.utils.XmlUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

public class XmppSrv implements IXmppSender
{
    private static final Random random = new Random();
    private static final List<String> messages = Arrays.asList(
            "Made by leorblx!",
            "Now with powerups!",
            "You're using leorblx's BetaSrv 1.2!",
            "Now with more hardcoded data!",
            "Reverse-engineering is fun!",
            "XMPP is cool"
    );

    private static ConcurrentHashMap<Long, XmppTalk> xmppClients = new ConcurrentHashMap<Long, XmppTalk>();

    static void addXmppClient(long personaId, XmppTalk xmppClient)
    {
        xmppClients.put(personaId, xmppClient);
    }

    private static void sendMsg(long personaId, String msg)
    {
        if (xmppClients.containsKey(personaId)) {
            XmppTalk xTalk = xmppClients.get(personaId);
            if (xTalk != null) {
                xTalk.write(msg);
            } else {
                System.err.println("xmppClient with the personaId " + personaId + " is attached to a null XmppTalk instance!");
            }
        } else {
            System.err.println("xmppClients doesn't contain personaId " + personaId);
        }
    }

    public static void removeXmppClient(long personaId)
    {
        try {
            xmppClients.get(personaId).getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        xmppClients.remove(personaId);
    }

    public XmppSrv()
    {
        System.setProperty("jsse.enableCBCProtection", "false");
        XmppSrvRun xmppSrvRun = new XmppSrvRun();
        xmppSrvRun.start();
    }

    private static class XmppSrvRun extends Thread
    {
        public void run()
        {
            try {
                System.out.println("Xmpp server is running.");
                System.out.println("");
                try (ServerSocket listener = new ServerSocket(ConfigurationManager.getInstance().getConfiguration().getXmppPort())) {
                    while (true) {
                        new Capitalizer(listener.accept()).start();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class Capitalizer extends Thread
    {
        private Socket socket;
        private XmppTalk xmppTalk;

        Capitalizer(Socket socket)
        {
            this.socket = socket;
            xmppTalk = new XmppTalk(this.socket);
            System.out.println("New connection at " + socket);
        }

        public void run()
        {
            ScheduledFuture future = null;

            try {
                new XmppHandShake(xmppTalk);

                xmppTalk.write(XmppChat.getJoinChannelMessage(100L, 13));

//                xmppTalk.write(XmppChat.getSystemMessage(100L, "Hey there! Welcome to the Beta Server!"));
//
//                future = Concurrency.scheduler.scheduleAtFixedRate(
//                        () -> {
//                            xmppTalk.write(
//                                    XmppChat.getUserMessage(100L, 100L, "Server", messages.get(random.nextInt(messages.size())))
//                            );
//                        },
//                        20,
//                        20,
//                        TimeUnit.SECONDS);

                XmppHandler xmppHandler = new XmppHandler(xmppTalk);
                while (true) {
                    String input;

                    try {
                        input = xmppHandler.read().get();

                        if (input == null || input.contains("</stream:stream>")) {
                            break;
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Couldn't close a socket, what's going on?");
                }
                XmppSrv.removeXmppClient(xmppTalk.getPersonaId());
                System.out.println("Connection with client closed");

//                if (future != null)
//                    future.cancel(true);
            }
        }

    }

    public static XmppTalk get(Long personaId)
    {
        return xmppClients.get(personaId);
    }

    @Override
    public void send(String msg, Long to)
    {
//        XMPP_MessageType messageType = new XMPP_MessageType();
//        messageType.setFrom("nfsw.engine.engine@127.0.0.1/EA_Chat");
//        messageType.setToPersonaId(to);
//        messageType.setBody(msg);

        sendMsg(to, msg);

//        messageType.setSubject(Router.calculateHash(messageType.getTo().toCharArray(), msg.toCharArray()));
//        String packet = MarshalXML.marshal(messageType);
//        sendMsg(to, packet);
    }

    @Override
    public void send(Object object, Long to)
    {
        String responseXmlStr = XmlUtils.marshal(object);
        this.send(responseXmlStr, to);
    }

    @Override
    public void createUpdatePersona(Long id, String password)
    {
    }
}