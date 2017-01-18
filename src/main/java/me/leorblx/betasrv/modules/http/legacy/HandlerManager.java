package me.leorblx.betasrv.modules.http.legacy;

import me.leorblx.betasrv.modules.http.legacy.handlers.*;

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
        this.handlers.add(new ArbitrationHandler());
        this.handlers.add(new PowerupHandler());
        this.handlers.add(new PersonaLoginHandler());
//        this.handlers.add(new PersonaLogoutHandler());
        this.handlers.add(new PersonaPresenceHandler());
        this.handlers.add(new BasketsHandler());
    }

    public List<RequestHandler> getHandlers()
    {
        return handlers;
    }
}
