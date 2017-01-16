package me.leorblx.betasrv.event;

public interface GameEventResult
{
    Long getEventSessionId();
    
    Long getPersonaId();
    
    FinishReason getFinishReason();
}
