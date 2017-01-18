package me.leorblx.betasrv.modules.xmpp.offline;

import me.leorblx.betasrv.config.ConfigurationManager;
import me.leorblx.betasrv.modules.xmpp.IXmppSender;
import me.leorblx.betasrv.utils.Log;
import me.leorblx.betasrv.utils.XmlUtils;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class XmppServer implements IXmppSender
{
    private final Log LOG = Log.getLog("XmppServer");
    private final ConcurrentHashMap<Long, XmppClient> clients = new ConcurrentHashMap<>();

    /**
     * Register an XMPP client.
     *
     * @param personaId The persona ID of the client.
     * @param client    The client itself.
     */
    public void addClient(long personaId, XmppClient client)
    {
        clients.put(personaId, client);
    }

    /**
     * Remove an XMPP client.
     *
     * @param personaId The persona ID of the client.
     */
    public void removeClient(long personaId)
    {
        if (clients.containsKey(personaId)) {
            clients.get(personaId).close();
            clients.remove(personaId);
        }
    }

    /**
     * Start the server.
     */
    public XmppServer()
    {
        new XmppRun().start();
    }

    @Override
    public void send(String msg, Long to)
    {
        if (clients.containsKey(to)) {
            XmppClient client = clients.get(to);

            if (client != null) {
                client.write(msg);
            } else {
                LOG.warn("XmppClient for persona " + to + " is null!");
            }
        } else {
            LOG.warn("No XmppClient for persona " + to);
        }
    }

    @Override
    public void send(Object object, Long to)
    {
        this.send(XmlUtils.marshal(object), to);
    }

    @Override
    public void createUpdatePersona(Long id, String password)
    {

    }

    private final class XmppRun extends Thread
    {
        @Override
        public void run()
        {
            try {
                LOG.info("The offline-XMPP server is running.");
                System.out.println(' ');
                try (ServerSocket socket = new ServerSocket(5222)) {
                    while (!socket.isClosed()) {
                        new Capitalizer(socket.accept()).start();
                    }
                }
            } catch (Exception e) {
                LOG.log(e);
            }
        }
    }

    private final class Capitalizer extends Thread
    {
        private final Socket socket;
        private final XmppClient client;

        Capitalizer(Socket socket)
        {
            this.socket = socket;
            this.client = new XmppClient(this.socket);

            LOG.info("New connection: " + socket);
        }

        @Override
        public void run()
        {
            try {
                new XmppHandShake(client, XmppServer.this);

                XmppInputHandler handler = new XmppInputHandler(client);

                while (true) {
                    String input;

                    try {
                        input = handler.read().get();

                        if (input == null || input.contains("</stream:stream>")) {
                            removeClient(client.getPersonaId());
                            break;
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                removeClient(client.getPersonaId());

                LOG.info("Client disconnected from server. PersonaId: " + client.getPersonaId());
            }
        }
    }
}
