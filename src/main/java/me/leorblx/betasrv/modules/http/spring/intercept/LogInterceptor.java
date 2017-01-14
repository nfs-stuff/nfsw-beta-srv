package me.leorblx.betasrv.modules.http.spring.intercept;

import me.leorblx.betasrv.modules.http.HttpModule;
import me.leorblx.betasrv.utils.MiscUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LogInterceptor extends HandlerInterceptorAdapter
{
    private final Map<String, HttpServletRequest> requests = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        requests.put(requestKey(request), request);
        
        HttpModule.LOG.info(String.format(
                "%s request to %s%s (from %s)",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString() != null ? "?" + request.getQueryString() : "",
                MiscUtils.formatHost(request.getRemoteHost())
        ));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        super.postHandle(request, response, handler, modelAndView);

        HttpServletRequest match = requests.get(requestKey(request));
        
        if (match == null) return;
        
        HttpModule.LOG.info(String.format("Request (%s) finished with status code %s", requestKey(request), response.getStatus()));
    }

    private String requestKey(HttpServletRequest request)
    {
        return String.format("%s:%s%s", request.getMethod(), request.getRequestURI(), request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }
}
