package me.leorblx.betasrv.config;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Code from Smbarbour's RavenBot, with modifications.
 * https://github.com/MCUpdater/RavenBot/blob/master/src/main/java/org/mcupdater/ravenbot/Settings.java
 */
public class Configuration
{
    private String xmppIp;
    private int xmppPort;

    @SerializedName("debug")
    private boolean debugEnabled;

    private int httpPort;

    public int getXmppPort()
    {
        return xmppPort;
    }

    public void setXmppPort(int xmppPort)
    {
        this.xmppPort = xmppPort;
    }

    public void setXmppIp(String xmppIp)
    {
        this.xmppIp = xmppIp;
    }

    public String getXmppIp()
    {
        return xmppIp;
    }

    public boolean isDebugEnabled()
    {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled)
    {
        this.debugEnabled = debugEnabled;
    }

    public int getHttpPort()
    {
        return httpPort;
    }

    public void setHttpPort(int httpPort)
    {
        this.httpPort = httpPort;
    }

    public Map<String, Object> getAsMap()
    {
        Map<String, Object> map = new HashMap<>();
        
        // generic XMPP
        map.put("xmppIp", getXmppIp());
        map.put("xmppPort", getXmppPort());
        
//        map.put("openFireRestPort", getOpenFireRestPort());
//        map.put("openFireToken", getOpenFireToken());
        
        // etc
        map.put("debug", isDebugEnabled());
        map.put("httpPort", getHttpPort());
        
        return map;
    }
}
