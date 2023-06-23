package dev.chel_shev.fast;

import org.springframework.data.jpa.repository.query.JSqlParserUtils;

public class FastMarkdown {

    static public String bolt(String text) {
        String boltMark = "*";
        return boltMark + filter(text) + boltMark;
    }

    static public String italic(String text) {
        String italicMark = "_";
        return italicMark + filter(text) + italicMark;
    }

    static public String code(String text) {
        String codeMark = "`";
        return codeMark + filter(text) + codeMark;
    }

    static public String blockCode(String text) {
        String codeMark = "```";
        return codeMark + "\n" + filter(text) + codeMark;
    }

    static public String strikethrough(String text) {
        String strikeMark = "~";
        return strikeMark + filter(text) + strikeMark;
    }

    static public String underline(String text) {
        String underlineMark = "__";
        return underlineMark + filter(text) + underlineMark;
    }

    static public String spoiler(String text) {
        String underlineMark = "||";
        return underlineMark + filter(text) + underlineMark;
    }

    static public String link(String text, String link) {
        String linkMark = "[%s](%s)";
        return String.format(linkMark, filter(text), link);
    }

    public static String filter(String text) {
        String regex = "[\\\\_*\\[\\]()~`>#+\\-=|{}.!]";
        return text.replaceAll(regex, "\\\\$0");
    }

    public static void main(String[] args) {
        String as = "_*[]()~`>#+-=|{},.!";

        System.out.println(filter("ssfsdf:)"));
    }
}
