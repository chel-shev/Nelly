package dev.chel_shev.nelly.util;

import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import dev.chel_shev.nelly.exception.NellyException;
import dev.chel_shev.nelly.type.DayOfWeekRu;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class DateTimeUtils {

    public static final String EVERY_HOUR = "0 0 * * * *";
    public static final String EVERY_MINUTE = "0 * * * * *";
    public static final DateTimeFormatter[] DATE_FORMATTER = new DateTimeFormatter[]{
            new DateTimeFormatterBuilder().appendPattern("dd.MM.yyyy")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter(),
            new DateTimeFormatterBuilder().appendPattern("dd.MM")
                    .parseDefaulting(ChronoField.YEAR, 1900)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter(),
    };
    public static final LocalTime[] bDayTimesToCheck = new LocalTime[]{
            LocalTime.of(10, 0),
            LocalTime.of(0, 0),
    };

    public static boolean isTodayWithTimes(LocalDateTime dateTime, ZoneOffset offset, LocalTime[] timeToCheck) {
        return Arrays.stream(timeToCheck).anyMatch(time -> isNow(LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), time.getHour(), time.getMinute()), offset));
    }

    public static boolean isNow(LocalDateTime dateTime, ZoneOffset offset) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(offset.getId()));
        return dateTime.getMonth() == now.getMonth() && dateTime.getDayOfMonth() == now.getDayOfMonth() && dateTime.getHour() == now.getHour();
    }

    public static boolean isToday(LocalDateTime dateTime, ZoneOffset offset) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(offset.getId()));
        return dateTime.getMonth() == now.getMonth() && dateTime.getDayOfMonth() == now.getDayOfMonth();
    }

    public static LocalDateTime tryToParse(String date) {
        for (DateTimeFormatter formatter : DATE_FORMATTER) {
            try {
                return LocalDateTime.parse(date, formatter);
            } catch (DateTimeParseException ignore) {
            }
        }
        throw new NellyException("Проверь дату, мне кажется ты ошибся");
    }

    public static LocalDateTime getTimeFromTimeout(String date, WorkoutEntity workout, ZoneOffset offset) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DayOfWeekRu dayOfWeekRu = DayOfWeekRu.getShortName(date);
            if (workout.getBasicTime().isBefore(LocalTime.now(offset)))
                now = now.plusDays(1);
            while (now.getDayOfWeek().getValue() != dayOfWeekRu.getValue())
                now = now.plusDays(1);
            return now.with(workout.getBasicTime());
        } catch (Exception e) {
            throw new NellyException("Проверь дату, мне кажется ты ошибся");
        }
    }

    public static List<String> getPeriodList(){
        return Arrays.stream(PeriodType.values()).map(PeriodType::getLabel).toList();
    }

    public static List<String> getDayOfWeek(){
        return Arrays.stream(DayOfWeekRu.values()).map(DayOfWeekRu::getShortName).toList();
    }

    public static boolean isNextHour(LocalDateTime dateTime, ZoneOffset offset) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(offset.getId()));
        return dateTime.getMonth() == now.getMonth() && dateTime.getDayOfMonth() == now.getDayOfMonth() && dateTime.getHour() == now.getHour();
    }

    public static boolean isNextMinute(LocalDateTime dateTime, ZoneOffset offset) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(offset.getId()));
        return dateTime.getMonth() == now.getMonth() && dateTime.getDayOfMonth() == now.getDayOfMonth() && dateTime.getHour() == now.getHour() && dateTime.getMinute() == now.getMinute();
    }
}
