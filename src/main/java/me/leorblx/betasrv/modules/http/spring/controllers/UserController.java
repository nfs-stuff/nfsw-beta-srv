package me.leorblx.betasrv.modules.http.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/User")
public class UserController extends BaseController
{
    @RequestMapping(path = {"GetPermanentSession"}, produces = {"application/xml;charset=utf-8"})
    @ResponseBody
    public String getPermanentSession(HttpServletResponse response)
    {
        response.setHeader("Connection", "close");
        
        return resolve("/nfsw/Engine.svc/User/GetPermanentSession", response);
    }
}
