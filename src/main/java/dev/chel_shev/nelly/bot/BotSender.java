package dev.chel_shev.nelly.bot;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import dev.chel_shev.nelly.keyboard.KeyboardFactory;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class BotSender {

    public void sendMessage(Inquiry inquiry) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(inquiry.getUser().getChatId())).text(inquiry.getAnswerMessage()).build();
        sendMessage(sendMessage, inquiry.getKeyboardType(), inquiry.getUser());
    }

    public void sendMessage(UserEntity user, KeyboardType keyboardType, String text) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(user.getChatId())).text(text).build();
        sendMessage(sendMessage, keyboardType, user);
    }

    public void sendMessage(UserEntity user, KeyboardType keyboardType, InputFile photo, String caption) {
        SendPhoto sendPhoto = SendPhoto.builder().chatId(String.valueOf(user.getChatId())).photo(photo).caption(caption).build();
        sendMessage(sendPhoto, keyboardType, user);
    }

    public void sendMessage(Message message, KeyboardType keyboardType, String text) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(message.getChatId())).text(text).build();
        sendMessage(sendMessage, keyboardType, null);
    }

    private void sendMessage(SendMessage sendMessage, KeyboardType keyboardType, UserEntity user) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        TelegramBotMain telegramBot = (TelegramBotMain) appCtx.getBean("telegramBotMain");
        try {
            sendMessage.setReplyMarkup(KeyboardFactory.createKeyboard(keyboardType, user));
            sendMessage.enableMarkdown(true);
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(SendPhoto sendPhoto, KeyboardType keyboardType, UserEntity user) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        TelegramBotMain telegramBot = (TelegramBotMain) appCtx.getBean("telegramBotMain");
        try {
            sendPhoto.setReplyMarkup(KeyboardFactory.createKeyboard(keyboardType, user));
            telegramBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}