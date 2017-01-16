package me.leorblx.betasrv.event.jaxb;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Accolades")
@XmlType(name = "AccoladesType", propOrder = {"finalRewards", "luckyDrawDef", "originalRewards", "rewardInfo"})
@XmlAccessorType(XmlAccessType.FIELD)
public class AccoladesType
{
    @XmlElement(name = "FinalRewards")
    private FinalRewardsType finalRewards;
    
    @XmlElement(name = "RewardInfo")
    private RewardInfoType rewardInfo;
    
    @XmlElement(name = "OriginalRewards")
    private OriginalRewardsType originalRewards;
    
    @XmlElement(name = "LuckyDrawDef")
    private LuckyDrawDefType luckyDrawDef;

    public FinalRewardsType getFinalRewards()
    {
        return finalRewards;
    }

    public OriginalRewardsType getOriginalRewards()
    {
        return originalRewards;
    }

    public void setFinalRewards(FinalRewardsType finalRewards)
    {
        this.finalRewards = finalRewards;
    }

    public void setOriginalRewards(OriginalRewardsType originalRewards)
    {
        this.originalRewards = originalRewards;
    }

    public RewardInfoType getRewardInfo()
    {
        return rewardInfo;
    }

    public void setRewardInfo(RewardInfoType rewardInfo)
    {
        this.rewardInfo = rewardInfo;
    }

    public LuckyDrawDefType getLuckyDrawDef()
    {
        return luckyDrawDef;
    }

    public void setLuckyDrawDef(LuckyDrawDefType luckyDrawDef)
    {
        this.luckyDrawDef = luckyDrawDef;
    }
}
