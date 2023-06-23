package dev.chel_shev.fast.type;


import lombok.Getter;

import java.time.Duration;

@Getter
public enum FastStudyTimelineType {

    T_0(Duration.ofMinutes( 0)),
    T_15(Duration.ofMinutes(15)),
    T_60(Duration.ofMinutes(60)),
    T_180(Duration.ofMinutes(180)),
    T_1440(Duration.ofMinutes(1440)),
    T_4320(Duration.ofMinutes(4320)),
    T_10080(Duration.ofMinutes(10080)),
    T_20160(Duration.ofMinutes(20160)),
    T_40320(Duration.ofMinutes(40320)),
    T_80640(Duration.ofMinutes(80640));

    private final Duration min;

    FastStudyTimelineType(Duration min) {
        this.min = min;
    }
}
