package dev.chel_shev.fast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FastBotException extends RuntimeException {

    private String chatId;

    public FastBotException(String s) {
        super(s);
    }

    public FastBotException(String s, String chatId) {
        super(s);
        this.chatId = chatId;
    }
}
