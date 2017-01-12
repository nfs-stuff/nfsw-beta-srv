package me.leorblx.betasrv.utils;

public class HtmlUtils
{
    public static String symbolsToEntities(String str)
    {
        return str.replace("<", "&lt;")
                .replace(">", "&gt");
    }
}
