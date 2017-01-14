package me.leorblx.betasrv.modules.http.legacy;

import me.leorblx.betasrv.modules.http.legacy.handlers.LaunchEventHandler;

import java.util.ArrayList;
import java.util.List;

public class HandlerManager
{
    private static HandlerManager ourInstance = new HandlerManager();

    public static HandlerManager getInstance()
    {
        return ourInstance;
    }

    private final List<RequestHandler> handlers;
    
    private HandlerManager()
    {
        this.handlers = new ArrayList<>();
        
        this.handlers.add(new LaunchEventHandler());
    }

    public List<RequestHandler> getHandlers()
    {
        return handlers;
    }
}
