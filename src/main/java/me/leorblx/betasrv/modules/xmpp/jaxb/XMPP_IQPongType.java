package me.leorblx.betasrv.modules.xmpp.jaxb;

import javax.xml.bind.annotation.*;

/**
 * XML types created by Nilzao and berkay2578.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_IQPongType", propOrder = {"from", "to", "id", "type"})
@XmlRootElement(name = "iq")
public class XMPP_IQPongType
{
    @XmlAttribute(name = "from")
    private String from;
    @XmlAttribute(name = "to")
    private String to;
    @XmlAttribute(name = "id")
    private String id;
    @XmlAttribute(name = "type", required = true)
    private String type = "result";

    public XMPP_IQPongType()
    {
    }

    public XMPP_IQPongType(String id)
    {
        from = String.format("nfsw.engine.engine@%s/EA_Chat", "127.0.0.1");
        to = "127.0.0.1";
        this.id = id;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}