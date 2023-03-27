package dev.chel_shev.fast;

import dev.chel_shev.nelly.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotSender {

    private final ExerciseService exerciseService;

//    public <E extends Event> void deleteMessage(E event) {
//        DeleteMessage editMessageText = DeleteMessage.builder().chatId(String.valueOf(event.getUserSubscription().getUser().getChatId())).messageId(event.getAnswerMessageId()).build();
//        updateMessage(editMessageText);
//    }
//
//    public Message sendMessage(UserEntity user, KeyboardType keyboardType, String text, boolean markdown) {
//        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(user.getChatId())).text(text).build();
//        return sendMessage(sendMessage, keyboardType, user, markdown);
//    }
//
//    private void updateMessage(DeleteMessage deleteMessage) {
//        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
//        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
//        try {
//            telegramBot.execute(deleteMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void updateMessage(EditMessageText editMessageText, KeyboardType keyboardType, UserEntity user) {
//        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
//        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
//        try {
//            editMessageText.setReplyMarkup((InlineKeyboardMarkup) keyboardFactory.createKeyboard(keyboardType, user));
//            editMessageText.enableMarkdown(true);
//            telegramBot.execute(editMessageText);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Message sendMessage(SendMessage sendMessage, KeyboardType keyboardType, UserEntity user, boolean markdown) {
//        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
//        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
//        try {
//            sendMessage.setReplyMarkup(keyboardFactory.createKeyboard(keyboardType, user));
//            sendMessage.enableMarkdown(markdown);
//            sendMessage.disableWebPagePreview();
//            List<MessageEntity> entities = new ArrayList<>();
//            MessageEntity entity = MessageEntity.builder().customEmojiId("5820934185170242766").text("▶️").length(2).offset(0).type("custom_emoji").build();
//            entities.add(entity);
//            sendMessage.setEntities(entities);
//            return telegramBot.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private Message sendMessage(SendPhoto sendPhoto, KeyboardType keyboardType, UserEntity user, FastWorkoutEventEntity workoutEvent) {
//        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
//        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
//        try {
//            sendPhoto.setReplyMarkup(keyboardFactory.createKeyboard(keyboardType, user, workoutEvent));
//            return telegramBot.execute(sendPhoto);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public <E extends Event> Message sendMessage(E e) {
//        if (e.getClosed()) {
//            deleteMessage(e);
//            return sendMessage(e.getUserSubscription().getUser(), e.getKeyboardType(), e.getAnswerMessage(), true);
//        }
//        InputFile photo;
//        WorkoutEntity workout = ((FastWorkoutEvent) e).getWorkout();
//        int step = ((FastWorkoutEvent) e).getStep();
//        int amountExercises = workout.getCountExercise();
//        List<WorkoutExerciseEntity> exerciseList = exerciseService.getExerciseList(workout.getId());
//        ExerciseEntity exercise = exerciseList.get(step).getExercise();
//        String massage = Markdown.code((step + 1) + " / " + amountExercises) + " | " +
//                Markdown.code(exercise.getName()) +
//                Markdown.code((!isNull(exercise.getComment()) ? ("(" + exercise.getComment() + ")") : "")) + " | " +
//                Markdown.code(exercise.getReps() * ((FastWorkoutEvent) e).getLevel() + exercise.getType().getLabel());
//        if (isNull(exercise.getFileId())) {
//            deleteMessage(e);
//            photo = new InputFile(new ByteArrayInputStream(exercise.getImage()), exercise.getName());
//            return sendMessage(e.getUserSubscription().getUser(), e.getKeyboardType(), photo, massage, (FastWorkoutEventEntity) e.getEntity());
//        } else {
//            photo = new InputFile(exercise.getFileId());
//            return updateMessage(e.getUserSubscription().getUser(), e.getKeyboardType(), photo, massage, (FastWorkoutEventEntity) e.getEntity());
//        }
//    }
//
//    public Message sendMessage(UserEntity user, KeyboardType keyboardType, InputFile photo, String text, FastWorkoutEventEntity workoutEvent) {
//        SendPhoto sendMessage = SendPhoto.builder().parseMode("Markdown").chatId(String.valueOf(user.getChatId())).photo(photo).caption(text).build();
//        return sendMessage(sendMessage, keyboardType, user, workoutEvent);
//    }
//
//    public Message updateMessage(UserEntity user, KeyboardType keyboardType, InputFile photo, String text, FastWorkoutEventEntity workoutEvent) {
//        InputMediaPhoto media = InputMediaPhoto.builder().parseMode("Markdown").media(photo.getAttachName()).caption(text).build();
//        EditMessageMedia editMessageMedia = EditMessageMedia.builder().chatId(String.valueOf(user.getChatId())).messageId(workoutEvent.getAnswerMessageId()).media(media).build();
//        return updateMessage(editMessageMedia, keyboardType, user, workoutEvent);
//    }
//
//    private Message updateMessage(EditMessageMedia editMessageMedia, KeyboardType keyboardType, UserEntity user, FastWorkoutEventEntity workoutEvent) {
//        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
//        NellyNotBot<? extends Inquiry, ? extends Event> telegramBot = (NellyNotBot<? extends Inquiry, ? extends Event>) appCtx.getBean("nellyNotBot");
//        try {
//            editMessageMedia.setReplyMarkup((InlineKeyboardMarkup) keyboardFactory.createKeyboard(keyboardType, user, workoutEvent));
//            telegramBot.execute(editMessageMedia);
//            return null;
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}