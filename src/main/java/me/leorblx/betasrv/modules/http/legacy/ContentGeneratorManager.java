package me.leorblx.betasrv.modules.http.legacy;

import me.leorblx.betasrv.modules.http.legacy.generators.PursuitResultGenerator;
import me.leorblx.betasrv.modules.http.legacy.handlers.LaunchEventHandler;
import me.leorblx.betasrv.modules.http.legacy.handlers.PowerupHandler;

import java.util.ArrayList;
import java.util.List;

public class ContentGeneratorManager
{
    private static ContentGeneratorManager ourInstance = new ContentGeneratorManager();

    public static ContentGeneratorManager getInstance()
    {
        return ourInstance;
    }

    private final List<ContentGenerator> generators;
    
    private ContentGeneratorManager()
    {
        this.generators = new ArrayList<>();
        
        this.generators.add(new PursuitResultGenerator());
    }

    public List<ContentGenerator> getGenerators()
    {
        return generators;
    }
}
