package me.leorblx.betasrv.modules.xmpp.offline;

import java.util.concurrent.Future;

public class XmppInputHandler
{
    private XmppClient xmppClient;

    public XmppInputHandler(XmppClient xmppClient)
    {
        this.xmppClient = xmppClient;
    }

    public Future<String> read()
    {
        return xmppClient.read();
    }
}
