package me.leorblx.betasrv.modules.http.legacy.generators;

import me.leorblx.betasrv.economy.PursuitRewardCalculator;
import me.leorblx.betasrv.economy.RewardType;
import me.leorblx.betasrv.event.FinishReason;
import me.leorblx.betasrv.event.jaxb.*;
import me.leorblx.betasrv.event.pursuit.PursuitResult;
import me.leorblx.betasrv.event.pursuit.PursuitResultImpl;
import me.leorblx.betasrv.modules.http.legacy.ContentGenerator;
import me.leorblx.betasrv.modules.http.legacy.HttpRequestProcessor;
import me.leorblx.betasrv.utils.XmlUtils;
import me.leorblx.betasrv.xml.PersonaWriter;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public class PursuitResultGenerator implements ContentGenerator
{
    private final PursuitRewardCalculator calculator = new PursuitRewardCalculator();
    private final PersonaWriter personaWriter = new PersonaWriter();
    
    @Override
    public boolean applies(Request request, String target)
    {
        return target.contains("SaveGameResult");
    }

    @Override
    public String generate(Request baseRequest, HttpServletRequest request, HttpRequestProcessor requestProcessor, String target)
    {
        FinishReason finishReason = FinishReason.fromCode(Integer.parseInt(request.getParameter("busted")));
        PursuitResult result = new PursuitResultImpl(
                Long.parseLong(request.getParameter("sessionEventId")),
                Long.parseLong(request.getParameter("personaId")),
                finishReason,
                finishReason == FinishReason.PURSUIT_EVADED,
                Integer.parseInt(request.getParameter("spikeStripsDodged")),
                Integer.parseInt(request.getParameter("copsDisabled")),
                Integer.parseInt(request.getParameter("pursuitInfractions")),
                Integer.parseInt(request.getParameter("copsDeployed")),
                Integer.parseInt(request.getParameter("copsRammed")),
                Integer.parseInt(request.getParameter("roadblocksDodged")),
                Integer.parseInt(request.getParameter("costToState")),
                Integer.parseInt(request.getParameter("heat"))
        );

        PursuitOutrunResultsType outrunResultsType = new PursuitOutrunResultsType();

        outrunResultsType.setEvaded(finishReason == FinishReason.PURSUIT_EVADED);
        outrunResultsType.setHeat(5);
        outrunResultsType.setPursuitLength(600000);

        AccoladesType accoladesType = new AccoladesType();
        OriginalRewardsType originalRewardsType = new OriginalRewardsType();

        originalRewardsType.setRep(calculator.getReward(result, RewardType.REP));
        originalRewardsType.setTokens(calculator.getReward(result, RewardType.CASH));
        
        accoladesType.setOriginalRewards(originalRewardsType);

        FinalRewardsType finalRewardsType = new FinalRewardsType();
        finalRewardsType.setRep(result.didEvade() ? calculator.getReward(result, RewardType.REP) : 0);
        finalRewardsType.setTokens(result.didEvade() ? calculator.getReward(result, RewardType.CASH) : 0);
        
        accoladesType.setFinalRewards(finalRewardsType);
//        originalRewardsType.setRep(finishReason == FinishReason.PURSUIT_EVADED ? calculator.getReward());
        
        accoladesType.setLuckyDrawDef(new LuckyDrawDefType());
        
        outrunResultsType.setAccolades(accoladesType);
        
        personaWriter.setCash(personaWriter.getCash(request.getParameter("personaId")).toString(), request.getParameter("personaId"));
        
        return XmlUtils.marshal(outrunResultsType);
    }
}
