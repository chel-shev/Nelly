package dev.chel_shev.nelly.exception;

import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.keyboard.KeyboardType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TelegramBotException extends RuntimeException {

    private InquiryAnswer response;

    public TelegramBotException(String massage, KeyboardType keyboardType) {
        super(massage);
        this.response = new InquiryAnswer(null, massage, keyboardType);
    }

    public TelegramBotException(String massage) {
        this(massage, KeyboardType.NONE);
    }
}

