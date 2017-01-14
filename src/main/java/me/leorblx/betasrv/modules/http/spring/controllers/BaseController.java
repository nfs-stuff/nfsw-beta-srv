package me.leorblx.betasrv.modules.http.spring.controllers;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseController
{
    public String resolve(String target, HttpServletResponse response)
    {
        Path first = Paths.get(String.format("www%s.xml", target));
        Path second = Paths.get(String.format("www%s", target));

        if (Files.exists(first))
            try {
                String str = new String(Files.readAllBytes(first), StandardCharsets.UTF_8);
                
                response.setContentLength(str.getBytes(StandardCharsets.UTF_8).length);
                
                return str;
            } catch (IOException e) {
                e.printStackTrace();
            }
        else if (Files.exists(second))
            try {
                String str = new String(Files.readAllBytes(second), StandardCharsets.UTF_8);

                response.setContentLength(str.getBytes(StandardCharsets.UTF_8).length);
                
                return str;
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
    }
}
