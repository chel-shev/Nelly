package dev.chel_shev.nelly.exception;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TelegramBotException extends RuntimeException {

    private InquiryAnswer response;

    public TelegramBotException(String message, KeyboardType keyboardType) {
        super(message);
        this.response = new InquiryAnswer(null, message, keyboardType);
    }

    public TelegramBotException(UserEntity user, String message, KeyboardType keyboardType) {
        super(message);
        this.response = new InquiryAnswer(user, message, keyboardType);
    }

    public TelegramBotException(String message) {
        this(message, KeyboardType.NONE);
    }
}

