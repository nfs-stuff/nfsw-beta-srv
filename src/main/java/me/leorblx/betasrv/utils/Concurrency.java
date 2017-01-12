package me.leorblx.betasrv.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class Concurrency
{
    public static final ExecutorService threadPool = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder().setNameFormat("Worker Thread #%d").setPriority(10).setDaemon(true).build());
    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(50);
    
    public static <T> Future<T> submit(Callable<T> task)
    {
        return threadPool.submit(task);
    }
}
