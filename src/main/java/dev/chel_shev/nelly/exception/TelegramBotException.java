package dev.chel_shev.nelly.exception;

import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TelegramBotException extends RuntimeException {

    private UserEntity user;
    private String message;
    private KeyboardType keyboardType;

    public TelegramBotException(String message, KeyboardType keyboardType) {
        this(null, message, keyboardType);
    }

    public TelegramBotException(UserEntity user, String message, KeyboardType keyboardType) {
        super(message);
        this.user = user;
        this.message = message;
        this.keyboardType = keyboardType;
    }

    public TelegramBotException(String message) {
        this(message, KeyboardType.CANCEL);
    }

    public TelegramBotException(UserEntity user, String message) {
        this(user, message, KeyboardType.NONE);
    }
}

