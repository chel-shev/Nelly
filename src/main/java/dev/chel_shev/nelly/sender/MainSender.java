package dev.chel_shev.nelly.sender;

import dev.chel_shev.nelly.TelegramBotMain;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.service.AnswerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record MainSender(TelegramBotMain telegramBot, AnswerService answerService) implements Sender {

    @Override
    public void send(UserEntity user, String massage) {

    }
}