package me.leorblx.betasrv.modules.xmpp.offline;

import me.leorblx.betasrv.utils.Concurrency;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Future;

public class XmppClient
{
    private Socket socket;

    private BufferedReader reader;
    private BufferedWriter writer;

    private boolean enabled = false;

    private int personaId;

    public XmppClient(Socket socket)
    {
        this.socket = socket;

        initBuffers();
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;

        initBuffers();
    }

    public Socket getSocket()
    {
        return socket;
    }

    private void initBuffers()
    {
        this.enabled = true;

        try {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Future<String> read()
    {
        return Concurrency.submit(() -> {
            if (!enabled)
                return null;
            if (socket.isClosed())
                return null;
            
            String msg = null;
            char[] buf = new char[8192];
            int charsRead;
            
            try {
                if ((charsRead = reader.read(buf)) != -1) {
                    msg = new String(buf).substring(0, charsRead);
                }
            } catch (Exception ignored) {
            }
            
            return msg;
        });
    }

    public void write(String msg)
    {
        try {
            if ((!enabled) || (socket.isClosed()))
                return;

            System.out.println("S->C [" + msg + "]");
            writer.write(msg);
            writer.flush();
        } catch (Exception e) {
            if (e.getCause() != null) {
                if (!e.getCause().getMessage().contains("Connection reset")) 
                    e.printStackTrace();
            } else if (!e.getMessage().contains("Connection reset"))
                e.printStackTrace();
        }
    }

    public int getPersonaId()
    {
        return personaId;
    }

    public void setPersonaId(int personaId)
    {
        this.personaId = personaId;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void close()
    {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.enabled = false;
    }
}
