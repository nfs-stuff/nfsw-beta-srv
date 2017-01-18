package me.leorblx.betasrv.event.pursuit;

import me.leorblx.betasrv.event.GameEventResult;

public interface PursuitResult extends GameEventResult
{
    boolean didEvade();
    
    int getDodgedSpikeStrips();
    
    int getDisabledCars();
    
    int getInfractions();
    
    int getDeployedCops();
    
    int getRammedCops();
    
    int getDodgedRoadBlocks();
    
    int getCostToState();
    
    float getHeat();
}
