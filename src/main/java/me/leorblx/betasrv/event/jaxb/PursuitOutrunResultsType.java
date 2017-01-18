package me.leorblx.betasrv.event.jaxb;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "PursuitOutrunResults")
@XmlType(name = "PursuitOutrunResultsType")
@XmlAccessorType(XmlAccessType.FIELD)
public class PursuitOutrunResultsType
{
    @XmlElement(name = "Accolades")
    private AccoladesType accolades;
    
    @XmlElement
    private boolean evaded;
    
    @XmlElement
    private float heat;
    
    @XmlElement
    private int pursuitLength;

    public AccoladesType getAccolades()
    {
        return accolades;
    }

    public void setAccolades(AccoladesType accolades)
    {
        this.accolades = accolades;
    }

    public float getHeat()
    {
        return heat;
    }

    public void setHeat(float heat)
    {
        this.heat = heat;
    }

    public boolean isEvaded()
    {
        return evaded;
    }

    public void setEvaded(boolean evaded)
    {
        this.evaded = evaded;
    }

    public int getPursuitLength()
    {
        return pursuitLength;
    }

    public void setPursuitLength(int pursuitLength)
    {
        this.pursuitLength = pursuitLength;
    }
}
