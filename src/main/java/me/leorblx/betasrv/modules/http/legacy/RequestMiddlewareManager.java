package me.leorblx.betasrv.modules.http.legacy;

import me.leorblx.betasrv.modules.http.legacy.middleware.ArbitrationMiddleware;

import java.util.ArrayList;
import java.util.List;

public class RequestMiddlewareManager
{
    private static RequestMiddlewareManager ourInstance = new RequestMiddlewareManager();

    public static RequestMiddlewareManager getInstance()
    {
        return ourInstance;
    }

    private final List<RequestMiddleware> middlewares;
    
    private RequestMiddlewareManager()
    {
        this.middlewares = new ArrayList<>();
        
        this.middlewares.add(new ArbitrationMiddleware());
    }

    public List<RequestMiddleware> getMiddlewares()
    {
        return middlewares;
    }
}
