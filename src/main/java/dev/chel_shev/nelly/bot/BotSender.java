package dev.chel_shev.nelly.bot;

import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.inquiry.KeyboardFactory;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.ApplicationContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotSender {

    private final KeyboardFactory keyboardFactory;

    public void updateMessage(Inquiry inquiry) {
        EditMessageText editMessageText = EditMessageText.builder().text(inquiry.getAnswerMessage()).chatId(String.valueOf(inquiry.getUser().getChatId())).messageId(inquiry.getAnswerMessageId()).build();
        updateMessage(editMessageText, inquiry.getKeyboardType(), inquiry.getUser());
    }

    public Message sendMessage(Inquiry inquiry) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(inquiry.getUser().getChatId())).text(inquiry.getAnswerMessage()).build();
        return sendMessage(sendMessage, inquiry.getKeyboardType(), inquiry.getUser(), true);
    }

    public Message sendMessage(UserEntity user, KeyboardType keyboardType, String text, boolean markdown) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(user.getChatId())).text(text).build();
        return sendMessage(sendMessage, keyboardType, user, markdown);
    }

    public Message sendMessage(UserEntity user, KeyboardType keyboardType, InputFile photo, String text) {
        SendPhoto sendPhoto = SendPhoto.builder().chatId(String.valueOf(user.getChatId())).photo(photo).caption(text).build();
        return sendMessage(sendPhoto, keyboardType, user);
    }

    public Message sendMessage(Message message, KeyboardType keyboardType, String text) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(message.getChatId())).text(text).build();
        return sendMessage(sendMessage, keyboardType, null, true);
    }

    private void updateMessage(EditMessageText editMessageText, KeyboardType keyboardType, UserEntity user) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        NellyNotBot<? extends Inquiry> telegramBot = (NellyNotBot<? extends Inquiry>) appCtx.getBean("nellyNotBot");
        try {
            editMessageText.setReplyMarkup((InlineKeyboardMarkup) keyboardFactory.createKeyboard(keyboardType, user));
            editMessageText.enableMarkdown(true);
            telegramBot.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private Message sendMessage(SendMessage sendMessage, KeyboardType keyboardType, UserEntity user, boolean markdown) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        NellyNotBot<? extends Inquiry> telegramBot = (NellyNotBot<? extends Inquiry>) appCtx.getBean("nellyNotBot");
        try {
            sendMessage.setReplyMarkup(keyboardFactory.createKeyboard(keyboardType, user));
            sendMessage.enableMarkdown(markdown);
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
            sendPhoto.setReplyMarkup(keyboardFactory.createKeyboard(keyboardType, user));
            return telegramBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}