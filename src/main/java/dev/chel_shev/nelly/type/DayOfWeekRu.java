package dev.chel_shev.nelly.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum     DayOfWeekRu{

    MONDAY("пн.", "Понедельник"),
    TUESDAY("вт.", "Вторник"),
    WEDNESDAY("ср.", "Среда"),
    THURSDAY("чт.", "Четверг"),
    FRIDAY("пт.", "Пятница"),
    SATURDAY("сб.", "Суббота"),
    SUNDAY("вс.", "Воскресенье");

    @Getter
    public final String shortName;

    @Getter
    public final String fullName;

    DayOfWeekRu(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public static final Map<String, DayOfWeekRu> ACTION_SHORT_NAME = new HashMap<>();
    public static final Map<String, DayOfWeekRu> ACTION_FULL_NAME = new HashMap<>();

    static {
        for (DayOfWeekRu env : values()) {
            ACTION_SHORT_NAME.put(env.shortName, env);
            ACTION_FULL_NAME.put(env.fullName, env);
        }
    }

    public int getValue(){
        return ordinal() + 1;
    }

    public static DayOfWeekRu getShortName(String shortName) {
        return ACTION_SHORT_NAME.get(shortName);
    }

    public static DayOfWeekRu getFullName(String fullName) {
        return ACTION_FULL_NAME.get(fullName);
    }
}
