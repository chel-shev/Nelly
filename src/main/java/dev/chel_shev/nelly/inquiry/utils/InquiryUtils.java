package dev.chel_shev.nelly.inquiry.utils;

import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.type.KeyboardType;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InquiryUtils {

    public static boolean validationArgs(Inquiry i, int count, String sign) {
        int length = i.getMessage().split(" ").length;
        return switch (sign) {
            case ">" -> length > count;
            case ">=" -> length >= count;
            case "<" -> length < count;
            case "<=" -> length <= count;
            case "==" -> length == count;
            default -> throw new TelegramBotException("Обратись к админу :(");
        };
    }

    public static long getValueFromParam(Inquiry i, int index) {
        try {
            return (long) (Double.parseDouble(i.getMessage().split(":")[index]
                    .replace(",", ".")
                    .replace("–", "")
                    .replace("-", "")
                    .replace(" ", "")
                    .trim()) * 100);
        } catch (NumberFormatException e) {
            throw new TelegramBotException(i.getUser(), "Неверный формат!", KeyboardType.CANCEL);
        }
    }

    public static String getNameFromParam(Inquiry i, int index) {
        return i.getMessage().split(":")[index].trim();
    }

    public static boolean getDirectionFromParam(Inquiry i, int index) {
        return !(i.getMessage().split(":")[index].contains("-") || i.getMessage().split(":")[index].contains("–"));
    }

    public static boolean isDoubleParam(Inquiry i) {
        return i.getMessage().split(":").length == 2;
    }

    public static String getLastArgsPast(Inquiry i, int index) {
        try {
            List<String> skip = Arrays.stream(i.getMessage().split(" ")).skip(index + 1).collect(Collectors.toList());
            return Strings.join(skip, ' ');
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new TelegramBotException("Неверное кол-во аргументов :(");
        }
    }
}
