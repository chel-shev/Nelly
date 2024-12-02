package dev.chel_shev.fast;

public class FastMarkdown {

    static public String bolt(String text) {
        if (null == text || text.isEmpty()) return "";
        String boltMark = "*";
        return boltMark + filter(text) + boltMark;
    }

    static public String italic(String text) {
        if (null == text || text.isEmpty()) return "";
        String italicMark = "_";
        return italicMark + filter(text) + italicMark;
    }

    static public String code(String text) {
        if (null == text || text.isEmpty()) return "";
        String codeMark = "`";
        return codeMark + filter(text) + codeMark;
    }

    static public String blockCode(String text) {
        if (null == text || text.isEmpty()) return "";
        String codeMark = "```";
        return codeMark + "\n" + filter(text) + codeMark;
    }

    static public String strikethrough(String text) {
        if (null == text || text.isEmpty()) return "";
        String strikeMark = "~";
        return strikeMark + filter(text) + strikeMark;
    }

    static public String underline(String text) {
        if (null == text || text.isEmpty()) return "";
        String underlineMark = "__";
        return underlineMark + filter(text) + underlineMark;
    }

    static public String spoiler(String text) {
        if (null == text || text.isEmpty()) return "";
        String underlineMark = "||";
        return underlineMark + filter(text) + underlineMark;
    }

    static public String link(String text, String link) {
        if (null == text || text.isEmpty()) return "";
        String linkMark = "[%s](%s)";
        return String.format(linkMark, filter(text), link);
    }

    public static String filter(String text) {
        String regex = "[\\\\_*\\[\\]()~`>#+\\-=|{}.!]";
        return text.replaceAll(regex, "\\\\$0");
    }
}