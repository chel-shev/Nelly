package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum PeriodType {

    ONCE ("Единожды"),
    EVERY_ONE ("Ежедневно"),
    EVERY_TWO ("Через день"),
    EVERY_THREE ("Через два"),
    EVERY_FOUR ("Через три"),
    EVERY_WEEK ("Еженедельно"),
    EVERY_MOUTH ("Ежемесячно"),
    EVERY_YEAR ("Ежегодно");

    @Getter
    public final String label;

    PeriodType(String label) {
        this.label = label;
    }

    public static final Map<String, PeriodType> PERIOD_TYPE_MAP = new HashMap<>();

    static {
        for (PeriodType env : values()) {
            PERIOD_TYPE_MAP.put(env.label, env);
        }
    }

    public static PeriodType getByLabel(String label) {
        return PERIOD_TYPE_MAP.get(label);
    }
}