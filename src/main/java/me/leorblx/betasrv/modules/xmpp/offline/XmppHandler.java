package me.leorblx.betasrv.modules.xmpp.offline;

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