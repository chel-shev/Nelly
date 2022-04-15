package dev.chel_shev.nelly.type;

import lombok.Getter;

public enum TimeoutType {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    @Getter
    public final int label;

    TimeoutType(int label) {
        this.label = label;
    }
}
