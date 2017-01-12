package me.leorblx.betasrv.modules.xmpp;

import me.leorblx.betasrv.modules.xmpp.offline.XmppSrv;

public class XmppFactory
{
    private static IXmppSender instance;

    public static IXmppSender getXmppSenderInstance(String xmppServerType)
    {
        if (instance == null)
            instance = newXmppSender(xmppServerType);
        return instance;
    }

    private static IXmppSender newXmppSender(String xmppServerType)
    {
        if (xmppServerType.equalsIgnoreCase("offline"))
            return new XmppSrv();
        throw new IllegalArgumentException("Unsupported XMPP server type: " + xmppServerType);
    }
}