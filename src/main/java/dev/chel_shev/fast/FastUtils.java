package dev.chel_shev.fast;


import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.service.FastCommandService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FastUtils {

    private final FastCommandService commandService;

    public String getArgs(String text) {
        String s = commandService.getCommands().stream().filter(e -> e.equals(text)).findFirst().orElse("");
        return text.replace(s, "").trim();
    }

    public static boolean validationArgs(FastInquiry i, int count, String sign) {
        int length = i.getMessage().split(" ").length;
        return switch (sign) {
            case ">" -> length > count;
            case ">=" -> length >= count;
            case "<" -> length < count;
            case "<=" -> length <= count;
            case "==" -> length == count;
            default -> throw new FastBotException("Обратись к админу :(");
        };
    }

    public static long getValueFromParam(FastInquiry i, int index) {
        try {
            return (long) (Double.parseDouble(i.getMessage().split(":")[index]
                    .replace(",", ".")
                    .replace("–", "")
                    .replace("-", "")
                    .replace(" ", "")
                    .trim()) * 100);
        } catch (NumberFormatException e) {
            throw new FastBotException(i.getUser().getChatId(), "Неверный формат!");
        }
    }

    public static String getNameFromParam(FastInquiry i, int index) {
        return i.getMessage().split(":")[index].trim();
    }

    public static boolean getDirectionFromParam(FastInquiry i, int index) {
        return !(i.getMessage().split(":")[index].contains("-") || i.getMessage().split(":")[index].contains("–"));
    }

    public static boolean isDoubleParam(FastInquiry i) {
        return i.getMessage().split(":").length == 2;
    }

    public static String getLastArgsPast(FastInquiry i, int index) {
        try {
            List<String> skip = Arrays.stream(i.getMessage().split(" ")).skip(index + 1).toList();
            return Strings.join(skip, ' ');
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FastBotException("Неверное кол-во аргументов :(");
        }
    }

}