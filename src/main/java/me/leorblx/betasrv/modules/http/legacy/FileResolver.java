package me.leorblx.betasrv.modules.http.legacy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Resolves XML files from HTTP request targets.
 */
public class FileResolver
{
    public byte[] resolve(String target) throws IOException
    {
        Path first = Paths.get(String.format("www%s.xml", target));
        Path second = Paths.get(String.format("www%s", target));
        
        if (Files.exists(first))
            return Files.readAllBytes(first);
        else if (Files.exists(second))
            return Files.readAllBytes(second);
        
        return null;
    }
}
