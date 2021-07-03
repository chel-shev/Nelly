package dev.chel_shev.nelly.util;

import java.time.LocalDate;

public class DateTimeUtils {

    public static final String FIRST_SECOND_OF_THE_DAY = "1 0 0 ? * *";

    public static boolean itsToday(LocalDate date) {
        LocalDate now = LocalDate.now();
        return date.getMonth() == now.getMonth() && date.getDayOfMonth() == now.getDayOfMonth();
    }
}
