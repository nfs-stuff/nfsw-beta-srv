package me.leorblx.betasrv.modules.xmpp.offline_old;

import java.util.concurrent.Future;

public class XmppHandler
{
    private XmppTalk xmppTalk;

    public XmppHandler(XmppTalk xmppTalk)
    {
        this.xmppTalk = xmppTalk;
    }

    public Future<String> read()
    {
        return xmppTalk.read();
    }
}