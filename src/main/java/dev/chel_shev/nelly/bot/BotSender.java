package dev.chel_shev.nelly.bot;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.bot.event.workout.WorkoutEvent;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.utils.KeyboardFactory;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import dev.chel_shev.nelly.type.KeyboardType;
import dev.chel_shev.nelly.util.ApplicationContextUtils;
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

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotSender {

    private final KeyboardFactory keyboardFactory;

    public <I extends Inquiry> void updateMessage(I inquiry) {
        EditMessageText editMessageText = EditMessageText.builder().text(inquiry.getAnswerMessage()).chatId(String.valueOf(inquiry.getUser().getChatId())).messageId(inquiry.getAnswerMessageId()).build();
        updateMessage(editMessageText, inquiry.getKeyboardType(), inquiry.getUser());
    }

    public <I extends Inquiry> void deleteMessage(I inquiry) {
        DeleteMessage editMessageText = DeleteMessage.builder().chatId(String.valueOf(inquiry.getUser().getChatId())).messageId(inquiry.getAnswerMessageId()).build();
        updateMessage(editMessageText);
    }

    public <I extends Inquiry> Message sendMessage(I inquiry) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(inquiry.getUser().getChatId())).text(inquiry.getAnswerMessage()).build();
        return sendMessage(sendMessage, inquiry.getKeyboardType(), inquiry.getUser(), true);
    }

    public <E extends Event> void deleteMessage(E event) {
        DeleteMessage editMessageText = DeleteMessage.builder().chatId(String.valueOf(event.getUser().getChatId())).messageId(event.getAnswerMessageId()).build();
        updateMessage(editMessageText);
    }

    public Message sendMessage(UserEntity user, KeyboardType keyboardType, String text, boolean markdown) {
        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(user.getChatId())).text(text).build();
        return sendMessage(sendMessage, keyboardType, user, markdown);
    }

    private void updateMessage(DeleteMessage deleteMessage) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
        try {
            telegramBot.execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void updateMessage(EditMessageText editMessageText, KeyboardType keyboardType, UserEntity user) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
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
        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
        try {
            sendMessage.setReplyMarkup(keyboardFactory.createKeyboard(keyboardType, user));
            sendMessage.enableMarkdown(markdown);
            return telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Message sendMessage(SendPhoto sendPhoto, KeyboardType keyboardType, UserEntity user, WorkoutEventEntity workoutEvent) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
        try {
            sendPhoto.setReplyMarkup(keyboardFactory.createKeyboard(keyboardType, user, workoutEvent));
            return telegramBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <E extends Event> Message sendMessage(E e) {
        InputFile photo;
        WorkoutEntity workout = ((WorkoutEvent) e).getWorkout();
        ExerciseEntity exercise = workout.getExercises().get(((WorkoutEvent) e).getStep()).getExercise();
        if (e.getClosed()) {
            deleteMessage(e);
            return sendMessage(e.getUser(), e.getKeyboardType(), e.getAnswerMessage(), true);
        }
        String massage = "__" + exercise.getName() + "__" + " " + (!isNull(exercise.getComment()) ? ("(" + exercise.getComment() + ")" + " ") : "") + "`" + exercise.getReps() * ((WorkoutEvent) e).getLevel() + exercise.getType().getLabel() + "`";
        if (isNull(exercise.getFileId())) {
            deleteMessage(e);
            photo = new InputFile(new ByteArrayInputStream(((WorkoutEvent) e).getWorkout().getExercises().get(((WorkoutEvent) e).getStep()).getExercise().getImage()), exercise.getName());
            return sendMessage(e.getUser(), e.getKeyboardType(), photo, massage, (WorkoutEventEntity) e.getEntity());
        } else {
            photo = new InputFile(exercise.getFileId());
            return updateMessage(e.getUser(), e.getKeyboardType(), photo, massage, (WorkoutEventEntity) e.getEntity());
        }
    }

    public Message sendMessage(UserEntity user, KeyboardType keyboardType, InputFile photo, String text, WorkoutEventEntity workoutEvent) {
        SendPhoto sendMessage = SendPhoto.builder().parseMode("Markdown").chatId(String.valueOf(user.getChatId())).photo(photo).caption(text).build();
        return sendMessage(sendMessage, keyboardType, user, workoutEvent);
    }

    public Message updateMessage(UserEntity user, KeyboardType keyboardType, InputFile photo, String text, WorkoutEventEntity workoutEvent) {
        InputMediaPhoto media = InputMediaPhoto.builder().parseMode("Markdown").media(photo.getAttachName()).caption(text).build();
        EditMessageMedia editMessageMedia = EditMessageMedia.builder().chatId(String.valueOf(user.getChatId())).messageId(workoutEvent.getAnswerMessageId()).media(media).build();
        return updateMessage(editMessageMedia, keyboardType, user, workoutEvent);
    }

    private Message updateMessage(EditMessageMedia editMessageMedia, KeyboardType keyboardType, UserEntity user, WorkoutEventEntity workoutEvent) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
        try {
            editMessageMedia.setReplyMarkup((InlineKeyboardMarkup) keyboardFactory.createKeyboard(keyboardType, user, workoutEvent));
            telegramBot.execute(editMessageMedia);
            return null;
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}