package dev.chel_shev.nelly.bot;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.KeyboardFactory;
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

    public Message sendMessage(Inquiry inquiry) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(inquiry.getUser().getChatId())).text(inquiry.getAnswerMessage()).build();
        return sendMessage(sendMessage, inquiry.getKeyboardType(), inquiry.getUser());
    }

    public Message sendMessage(UserEntity user, KeyboardType keyboardType, String text) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(user.getChatId())).text(text).build();
        return sendMessage(sendMessage, keyboardType, user);
    }

    public Message sendMessage(UserEntity user, KeyboardType keyboardType, InputFile photo, String text) {
        SendPhoto sendPhoto = SendPhoto.builder().chatId(String.valueOf(user.getChatId())).photo(photo).caption(text).build();
        return sendMessage(sendPhoto, keyboardType, user);
    }

    public Message sendMessage(Message message, KeyboardType keyboardType, String text) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(message.getChatId())).text(text).build();
        return sendMessage(sendMessage, keyboardType, null);
    }

    private Message sendMessage(SendMessage sendMessage, KeyboardType keyboardType, UserEntity user) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        NellyNotBot<? extends Inquiry> telegramBot = (NellyNotBot<? extends Inquiry>) appCtx.getBean("nellyNotBot");
        try {
            sendMessage.setReplyMarkup(KeyboardFactory.createKeyboard(keyboardType, user));
            sendMessage.enableMarkdown(true);
            return telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Message sendMessage(SendPhoto sendPhoto, KeyboardType keyboardType, UserEntity user) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        NellyNotBot<? extends Inquiry> telegramBot = (NellyNotBot<? extends Inquiry>) appCtx.getBean("nellyNotBot");
        try {
            sendPhoto.setReplyMarkup(KeyboardFactory.createKeyboard(keyboardType, user));
            return telegramBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}