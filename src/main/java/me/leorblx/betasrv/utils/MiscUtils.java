package me.leorblx.betasrv.utils;

public class MiscUtils
{
    public static String formatHost(String host)
    {
        if (host.equals("0:0:0:0:0:0:0:1"))
            return "127.0.0.1";
        return host;
    }
}
