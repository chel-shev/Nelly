package dev.chel_shev.fast.type;

import lombok.Getter;

import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;

public enum FastPeriodType {

    ONCE ("Единожды", Period.ZERO),
    EVERY_ONE ("Ежедневно", Period.of(0, 0 , 1)),
    EVERY_TWO ("Через день", Period.of(0,0,2)),
    EVERY_THREE ("Через два", Period.of(0,0,3)),
    EVERY_FOUR ("Через три", Period.of(0,0,4)),
    EVERY_WEEK ("Еженедельно", Period.of(0,0,8)),
    EVERY_MOUTH ("Ежемесячно", Period.of(0,1,0)),
    EVERY_YEAR ("Ежегодно", Period.of(1, 0, 0));

    @Getter
    public final String label;

    @Getter
    public final TemporalAmount amount;

    FastPeriodType(String label, TemporalAmount amount) {
        this.label = label;
        this.amount = amount;
    }

    public static final Map<String, FastPeriodType> PERIOD_TYPE_MAP = new HashMap<>();

    static {
        for (FastPeriodType env : values()) {
            PERIOD_TYPE_MAP.put(env.label, env);
        }
    }

    public static FastPeriodType getByLabel(String label) {
        return PERIOD_TYPE_MAP.get(label);
    }
}