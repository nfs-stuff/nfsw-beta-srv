package me.leorblx.betasrv.economy;

import me.leorblx.betasrv.event.pursuit.PursuitResult;

public class PursuitRewardCalculator implements RewardCalculator<PursuitResult>
{
    @Override
    public int getReward(PursuitResult result, RewardType type)
    {
        if (type == RewardType.REP) {
            int rep = 0;

            rep += result.getDisabledCars() * 1.5;
            rep += result.getDodgedSpikeStrips() * 1.05;
            rep += result.getInfractions() * 1.15;
            rep += result.getDeployedCops() * 5;
            rep += result.getDodgedRoadBlocks() * 3.5;
            rep += result.getHeat() * 1.25;
            rep += result.getDeployedCops() * 1.7;
            
            rep *= 2.5;

            return rep;
        } else if (type == RewardType.CASH) {
            int cash = 0;

            cash += result.getDisabledCars() * 2.5;
            cash += result.getDodgedSpikeStrips() * 3.5;
            cash += result.getInfractions() * 3.5;
            cash += result.getDeployedCops() * 5.5;
            cash += result.getDodgedRoadBlocks() * 4;
            cash += result.getHeat() * 2;
            cash += result.getDeployedCops() * 1.75;
            
            cash *= 2.55;

            return cash;
        }

        return 0;
    }
}
