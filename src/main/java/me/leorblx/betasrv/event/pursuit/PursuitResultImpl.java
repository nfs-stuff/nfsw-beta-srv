package me.leorblx.betasrv.event.pursuit;

import me.leorblx.betasrv.event.FinishReason;

public class PursuitResultImpl implements PursuitResult
{
    private final Long eventSessionId;
    private final Long personaId;
    private final FinishReason finishReason;
    private final boolean evaded;
    private final int dodgedSpikeStrips;
    private final int disabledCars;
    private final int infractions;
    private final int deployedCops;
    private final int rammedCops;
    private final int dodgedRoadBlocks;
    private final int costToState;
    private final float heat;

    public PursuitResultImpl(Long eventSessionId, Long personaId, FinishReason finishReason, boolean evaded, int dodgedSpikeStrips, int disabledCars, int infractions, int deployedCops, int rammedCops, int dodgedRoadBlocks, int costToState, float heat)
    {
        this.eventSessionId = eventSessionId;
        this.personaId = personaId;
        this.finishReason = finishReason;
        this.evaded = evaded;
        this.dodgedSpikeStrips = dodgedSpikeStrips;
        this.disabledCars = disabledCars;
        this.infractions = infractions;
        this.deployedCops = deployedCops;
        this.rammedCops = rammedCops;
        this.dodgedRoadBlocks = dodgedRoadBlocks;
        this.costToState = costToState;
        this.heat = heat;
    }

    @Override
    public Long getEventSessionId()
    {
        return eventSessionId;
    }

    @Override
    public Long getPersonaId()
    {
        return personaId;
    }

    @Override
    public FinishReason getFinishReason()
    {
        return finishReason;
    }

    @Override
    public boolean didEvade()
    {
        return evaded;
    }

    @Override
    public int getDodgedSpikeStrips()
    {
        return dodgedSpikeStrips;
    }

    @Override
    public int getDisabledCars()
    {
        return disabledCars;
    }

    @Override
    public int getInfractions()
    {
        return infractions;
    }

    @Override
    public int getDeployedCops()
    {
        return deployedCops;
    }

    @Override
    public int getRammedCops()
    {
        return rammedCops;
    }

    @Override
    public int getDodgedRoadBlocks()
    {
        return dodgedRoadBlocks;
    }

    @Override
    public int getCostToState()
    {
        return costToState;
    }

    @Override
    public float getHeat()
    {
        return heat;
    }
}
