package me.leorblx.betasrv.modules.http.legacy;

import me.leorblx.betasrv.config.ConfigurationManager;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigReplacer
{
    private final Pattern pattern = Pattern.compile("\\{config:([A-Za-z0-9_]+)\\}");
    
    public byte[] replaceVariables(byte[] bytes)
    {
        String str = new String(bytes, StandardCharsets.UTF_8);

        Matcher matcher = pattern.matcher(str);
        final Map<String, Object> properties = ConfigurationManager.getInstance().getConfiguration().getAsMap();
        
        while (matcher.find()) {
            String var = matcher.group(1);
            
            if (properties.containsKey(var)) {
                str = str.replace("{config:" + var + "}", properties.get(var).toString());
            }
        }
        
        return str.getBytes();
    }
}
