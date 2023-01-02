package dev.chel_shev.nelly.type;

import lombok.Getter;

public enum ExerciseType {

    TEMPORAL("с"),
    QUANTITATIVE("р"),
    METRIC("м");

    @Getter
    private final String label;

    ExerciseType(String label) {
        this.label = label;
    }
}
