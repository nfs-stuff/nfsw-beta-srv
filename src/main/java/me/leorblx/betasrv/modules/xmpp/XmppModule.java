package me.leorblx.betasrv.modules.xmpp;

import me.leorblx.betasrv.config.ConfigurationManager;
import me.leorblx.betasrv.modules.Module;

public class XmppModule implements Module
{
    @Override
    public void boot()
    {
        XmppFactory.getXmppSenderInstance("offline");
//        new XmppSrv();
    }

    @Override
    public void shutdown()
    {
    }
}
