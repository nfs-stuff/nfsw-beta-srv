package me.leorblx.betasrv.modules.http.spring.config;

import me.leorblx.betasrv.modules.http.spring.intercept.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        super.addInterceptors(registry);
        
        registry.addInterceptor(new LogInterceptor());
    }
}