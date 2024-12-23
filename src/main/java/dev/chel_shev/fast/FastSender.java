package dev.chel_shev.fast;

import dev.chel_shev.fast.scheduler.task.DeleteTask;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
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

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;

@Slf4j
@Component
@RequiredArgsConstructor
public class FastSender {

    private final ApplicationContext applicationContext;
    private final FastBotKeyboardFactory keyboardFactory;

    public Message sendMessage(String chatId, String textMessage, boolean markdown, boolean mute) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        if (markdown) builder.parseMode(ParseMode.MARKDOWNV2);
        SendMessage sendMessage = builder.disableNotification(mute).chatId(chatId).text(textMessage).build();
        return sendMessage(sendMessage);
    }

    public Message sendMessage(String chatId, String textMessage, FastKeyboardType type, List<String> buttons, boolean markdown, boolean mute) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        if (markdown) builder.parseMode(ParseMode.MARKDOWNV2);
        SendMessage message = builder.disableNotification(mute).chatId(chatId).text(textMessage).build();
        message.setReplyMarkup(keyboardFactory.getKeyBoard(type, buttons));
        return sendMessage(message);
    }

    public void updateMessage(String chatId, Integer messageId, String textMessage, FastKeyboardType type, List<String> buttons, boolean markdown) {
        var builder = EditMessageText.builder();
        if (markdown) builder.parseMode(ParseMode.MARKDOWNV2);
        EditMessageText message = builder.text(textMessage).chatId(chatId).messageId(messageId).build();
        message.setReplyMarkup((InlineKeyboardMarkup) keyboardFactory.getKeyBoard(type, buttons));
        updateMessage(message);
    }

    public Message sendPhoto(String chatId, String textMessage, InputFile photo, FastKeyboardType type, List<String> buttons, boolean markdown) {
        SendPhoto.SendPhotoBuilder builder = SendPhoto.builder();
        if (markdown) builder.parseMode(ParseMode.MARKDOWNV2);
        SendPhoto message = builder.parseMode(ParseMode.MARKDOWNV2).chatId(chatId).photo(photo).caption(textMessage).build();
        message.setReplyMarkup(keyboardFactory.getKeyBoard(type, buttons));
        return sendMessage(message);
    }

    public void updatePhoto(String chatId, Integer messageId, String textMessage, InputFile photo, FastKeyboardType type, List<String> buttons, boolean markdown) {
        InputMediaPhoto.InputMediaPhotoBuilder builder = InputMediaPhoto.builder();
        if (markdown) builder.parseMode(ParseMode.MARKDOWNV2);
        InputMediaPhoto media = builder.media(photo.getAttachName()).caption(textMessage).build();
        EditMessageMedia message = EditMessageMedia.builder().chatId(chatId).messageId(messageId).media(media).build();
        message.setReplyMarkup((InlineKeyboardMarkup) keyboardFactory.getKeyBoard(type, buttons));
        updateMessage(message);
    }

    public void deleteMessage(String chatId, Integer messageId) {
        DeleteMessage editMessageText = DeleteMessage.builder().chatId(chatId).messageId(messageId).build();
        deleteMessage(editMessageText);
    }

    public void deleteMessage(String chatId, Integer messageId, Integer duration) {
        Timer time = new Timer();
        GregorianCalendar calendar = GregorianCalendar.from(ZonedDateTime.now().plusSeconds(duration));
        DeleteTask task = new DeleteTask(this, messageId, chatId);
        time.schedule(task, calendar.getTime());
    }

    private Message sendMessage(SendMessage message) {
        FastBot telegramBot = (FastBot) applicationContext.getBean("fastBot");
        try {
            message.disableWebPagePreview();
            return telegramBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Message sendMessage(SendPhoto photo) {
        FastBot telegramBot = (FastBot) applicationContext.getBean("fastBot");
        try {
            return telegramBot.execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateMessage(EditMessageText text) {
        FastBot telegramBot = (FastBot) applicationContext.getBean("fastBot");
        try {
            telegramBot.execute(text);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void updateMessage(EditMessageMedia media) {
        FastBot telegramBot = (FastBot) applicationContext.getBean("fastBot");
        try {
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