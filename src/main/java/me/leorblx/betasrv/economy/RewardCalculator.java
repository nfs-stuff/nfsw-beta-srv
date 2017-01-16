package me.leorblx.betasrv.economy;

import me.leorblx.betasrv.event.GameEventResult;

public interface RewardCalculator<T extends GameEventResult>
{
    int getReward(T result, RewardType type);
}
