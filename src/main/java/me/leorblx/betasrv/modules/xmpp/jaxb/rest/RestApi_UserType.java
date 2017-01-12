package me.leorblx.betasrv.modules.xmpp.jaxb.rest;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {"username", "password", "name", "email"})
@XmlRootElement(name = "user")
public class RestApi_UserType
{
    @XmlElement(required = true)
    private String username;
    
    @XmlElement(required = true)
    private String password;
    
    private String name;
    
    private String email;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
