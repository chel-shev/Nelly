package dev.chel_shev.nelly.util;

import dev.chel_shev.nelly.exception.TelegramBotException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

public class DateTimeUtils {

    public static final String FIRST_SECOND_OF_THE_DAY = "1 0 0 ? * *";
    public static final String[] DATE_FORMAT = new String[]{"dd.MM.yyyy", "dd.MM"};

    public static boolean itsToday(LocalDate date) {
        LocalDate now = LocalDate.now();
        return date.getMonth() == now.getMonth() && date.getDayOfMonth() == now.getDayOfMonth();
    }

    public static LocalDate tryToParse(String date) {
        for (String format : DATE_FORMAT) {
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern(format)
                        .parseDefaulting(ChronoField.YEAR, 1900)
                        .toFormatter();
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException ignore) {
            }
        }
        throw new TelegramBotException("Проверь дату, мне кажется ты ошибся");
    }
}
