package dev.chel_shev.fast;

import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FastSender {

    private final ApplicationContext applicationContext;
    private final FastBotKeyboardFactory keyboardFactory;

    public Message sendMessage(String chatId, String textMessage, boolean markdown) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(textMessage).build();
        sendMessage.enableMarkdown(markdown);
        return sendMessage(sendMessage);
    }

    public Message sendMessage(String chatId, String textMessage, FastKeyboardType type, List<String> buttons, boolean markdown) {
        SendMessage message = SendMessage.builder().chatId(chatId).text(textMessage).build();
        message.enableMarkdown(markdown);
        message.setReplyMarkup(keyboardFactory.getKeyBoard(type, buttons));
        return sendMessage(message);
    }

    public void updateMessage(String chatId, Integer messageId, String textMessage, FastKeyboardType type, List<String> buttons, boolean markdown) {
        var builder = EditMessageText.builder();
        if (markdown) builder.parseMode("Markdown");
        EditMessageText message = builder.text(textMessage).chatId(chatId).messageId(messageId).build();
        message.setReplyMarkup((InlineKeyboardMarkup) keyboardFactory.getKeyBoard(type, buttons));
        updateMessage(message);
    }

    public Message sendPhoto(String chatId, String textMessage, InputFile photo, FastKeyboardType type, List<String> buttons) {
        SendPhoto message = SendPhoto.builder().parseMode("Markdown").chatId(chatId).photo(photo).caption(textMessage).build();
        message.setReplyMarkup(keyboardFactory.getKeyBoard(type, buttons));
        return sendMessage(message);
    }

    public void updatePhoto(String chatId, Integer messageId, String textMessage, InputFile photo, FastKeyboardType type, List<String> buttons) {
        InputMediaPhoto media = InputMediaPhoto.builder().parseMode("Markdown").media(photo.getAttachName()).caption(textMessage).build();
        EditMessageMedia message = EditMessageMedia.builder().chatId(chatId).messageId(messageId).media(media).build();
        message.setReplyMarkup((InlineKeyboardMarkup) keyboardFactory.getKeyBoard(type, buttons));
        updateMessage(message);
    }

    public void deleteMessage(String chatId, Integer messageId) {
        DeleteMessage editMessageText = DeleteMessage.builder().chatId(chatId).messageId(messageId).build();
        deleteMessage(editMessageText);
    }

    private Message sendMessage(SendMessage message) {
        FastBot telegramBot = (FastBot) applicationContext.getBean("fastBot");
        try {
//            message.setReplyMarkup(keyboardFactory.createKeyboard(keyboardType, user));
//            message.disableWebPagePreview();
            return telegramBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Message sendMessage(SendPhoto photo) {
        FastBot telegramBot = (FastBot) applicationContext.getBean("fastBot");
        try {
//            photo.setReplyMarkup(keyboardFactory.createKeyboard(keyboardType, user, workoutEvent));
            return telegramBot.execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateMessage(EditMessageText text) {
        FastBot telegramBot = (FastBot) applicationContext.getBean("fastBot");
        try {
//            text.setReplyMarkup((InlineKeyboardMarkup) keyboardFactory.createKeyboard(keyboardType, user));
            telegramBot.execute(text);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void updateMessage(EditMessageMedia media) {
        FastBot telegramBot = (FastBot) applicationContext.getBean("fastBot");
        try {
//            media.setReplyMarkup((InlineKeyboardMarkup) keyboardFactory.createKeyboard(keyboardType, user, workoutEvent));
            telegramBot.execute(media);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void deleteMessage(DeleteMessage message) {
        FastBot telegramBot = (FastBot) applicationContext.getBean("fastBot");
        try {
            telegramBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}