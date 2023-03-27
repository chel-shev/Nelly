package dev.chel_shev.fast;

public class FastBotMarkdown {

    static public String bolt(String text) {
        String boltMark = "**";
        return boltMark + text + boltMark;
    }

    static public String italic(String text) {
        String italicMark = "__";
        return italicMark + text + italicMark;
    }

    static public String code(String text) {
        String codeMark = "`";
        return codeMark + text + codeMark;
    }

    static public String blockCode(String text) {
        String codeMark = "```";
        return codeMark + text + codeMark;
    }

    static public String strike(String text) {
        String strikeMark = "~~";
        return strikeMark + text + strikeMark;
    }

    static public String underline(String text) {
        String underlineMark = "--";
        return underlineMark + text + underlineMark;
    }

    static public String spoiler(String text) {
        String underlineMark = "||";
        return underlineMark + text + underlineMark;
    }

    static public String link(String text, String link) {
        String linkMark = "[%s](%s)";
        return String.format(linkMark, text, link);
    }
}
