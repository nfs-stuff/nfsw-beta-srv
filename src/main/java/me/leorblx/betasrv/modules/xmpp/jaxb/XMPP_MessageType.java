package me.leorblx.betasrv.modules.xmpp.jaxb;

import me.leorblx.betasrv.utils.XmlUtils;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageType", propOrder = {"body", "subject"})
@XmlRootElement(name = "message")
public class XMPP_MessageType
{
    @XmlElement(required = true)
    protected String body;
    protected int subject = 1;
    @XmlAttribute(name = "from")
    protected String from = "nfsw.engine.engine7.victoryworker.0@127.0.0.1/EA_Chat";
    @XmlAttribute(name = "id")
    protected String id = "JN_1234567";
    @XmlAttribute(name = "to")
    protected String to;

    public String getBody()
    {
        return body;
    }

    public void setBody(Object object)
    {
        String responseXmlStr = XmlUtils.marshal(object);
        this.setBody(responseXmlStr);
    }

    public void setBody(String value)
    {
        this.body = value;
    }

    public int getSubject()
    {
        return subject;
    }

    public void setSubject(int value)
    {
        this.subject = value;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String value)
    {
        this.from = value;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String value)
    {
        this.id = value;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String value)
    {
        this.to = value;
    }

    public void setToPersonaId(Long personaId)
    {
        this.to = "nfsw." + personaId.toString() + "@127.0.0.1";
    }
}
