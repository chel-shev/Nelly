package dev.chel_shev.fast.type;


import lombok.Getter;

import java.time.LocalTime;
import java.util.Arrays;

@Getter
public enum FastStudyTimeType {
    T_10_15(LocalTime.of(10, 15)),
    T_14_15(LocalTime.of(14, 15)),
    T_18_15(LocalTime.of(18, 15)),
    T_22_15(LocalTime.of(22, 15));

    private final LocalTime timeEvent;

    FastStudyTimeType(LocalTime timeEvent) {
        this.timeEvent = timeEvent;
    }

    public FastStudyTimeType getNext() {
        return Arrays.stream(values()).filter(e -> e.ordinal() == (this.ordinal() + 1) % 4).findFirst().get();
    }
}