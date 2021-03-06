package me.leorblx.betasrv.event;

import java.util.Arrays;

public enum FinishReason
{
    FINISHED(6),
    
    PURSUIT_EVADED(258),
    PURSUIT_BUSTED(138);

    int code;

    FinishReason(int code)
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

    public static FinishReason fromCode(int code)
    {
        return Arrays.stream(values())
                .filter(c -> c.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unknown finishReason: " + code));
    }
}
