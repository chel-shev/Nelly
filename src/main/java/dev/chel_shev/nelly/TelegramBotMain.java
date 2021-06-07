package dev.chel_shev.nelly;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryHandler;
import dev.chel_shev.nelly.keyboard.KeyboardType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

import static java.util.Objects.isNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotMain extends TelegramLongPollingBot {

    @Value("${bot.api.username}")
    private String botUsername;
    @Value("${bot.api.token}")
    private String botToken;

    private final InquiryHandler inquiryHandler;

    @PostConstruct
    private void register() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (isNull(update.getMessage())) return;
        Message message = update.getMessage();
        try {
            InquiryAnswer answer = inquiryHandler.execute(message);
            sendMessage(answer.getUser(), answer.getKeyboardType(), answer.getMessage());
        } catch (TelegramBotException e) {
            log.debug(e.getMessage());
            sendMessage(message, e.getResponse().getKeyboardType(), e.getMessage());
        }
    }

    private void sendMessage(UserEntity user, KeyboardType keyboardType, String text) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(user.getChatId())).text(text).build();
        sendMessage(sendMessage, keyboardType, user);
    }

    private void sendMessage(Message message, KeyboardType keyboardType, String text) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(message.getChatId())).text(text).build();
        sendMessage(sendMessage, keyboardType, null);
    }

    private void sendMessage(SendMessage sendMessage, KeyboardType keyboardType, UserEntity user) {
        try {
//            sendMessage.setReplyMarkup(KeyboardFactory.createKeyboard(keyboardType, client));
            sendMessage.enableMarkdown(true);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}