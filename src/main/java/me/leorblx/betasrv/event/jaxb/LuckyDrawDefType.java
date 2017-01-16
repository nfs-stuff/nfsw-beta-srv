package me.leorblx.betasrv.event.jaxb;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "LuckyDrawDef")
@XmlType(name = "LuckyDrawDefType", propOrder = {"cardCount", "cardSet", "drawsAllowed"})
@XmlAccessorType(XmlAccessType.FIELD)
public class LuckyDrawDefType
{
    @XmlElement(defaultValue = "5", namespace = "http://schemas.datacontract.org/2004/07/EA.NFSWO.ENGINE.DataLayer.Serialization.LuckyDraw", name = "CardCount")
    private int cardCount;
    
    @XmlElement(namespace = "http://schemas.datacontract.org/2004/07/EA.NFSWO.ENGINE.DataLayer.Serialization.LuckyDraw", name = "CardSet")
    private String cardSet = "LD_CARD_BLUE";
    
    @XmlElement(defaultValue = "1", namespace = "http://schemas.datacontract.org/2004/07/EA.NFSWO.ENGINE.DataLayer.Serialization.LuckyDraw", name = "DrawsAllowed")
    private int drawsAllowed;

    public int getCardCount()
    {
        return cardCount;
    }

    public void setCardCount(int cardCount)
    {
        this.cardCount = cardCount;
    }

    public int getDrawsAllowed()
    {
        return drawsAllowed;
    }

    public void setDrawsAllowed(int drawsAllowed)
    {
        this.drawsAllowed = drawsAllowed;
    }
}
