package dev.chel_shev.nelly.task;

import dev.chel_shev.nelly.bot.BotResources;
import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.service.WorkoutService;
import dev.chel_shev.nelly.type.KeyboardType;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.TimerTask;

public class WorkoutTask extends TimerTask {

    private final WorkoutService workoutService;
    private final BotSender sender;
    private final BotResources resources;
    private final UserEntity user;
    private final InputFile photo;
    private final String name;
    private final WorkoutEventEntity workoutEvent;

    public WorkoutTask(WorkoutService workoutService, BotSender sender, BotResources resources, UserEntity user, InputFile photo, String name, EventEntity event) {
        this.workoutService = workoutService;
        this.sender = sender;
        this.resources = resources;
        this.user = user;
        this.photo = photo;
        this.name = name;
        this.workoutEvent = (WorkoutEventEntity) event;
    }

    @Override
    public void run() {
        try {
            Message message = sender.sendMessage(user, KeyboardType.WORKOUT_PROCESS, photo, "Сегодня по плану: " + name, workoutEvent);
            workoutEvent.setAnswerMessageId(message.getMessageId());
            workoutService.save(workoutEvent);
            workoutService.initNextEvent(workoutEvent, user);
            workoutService.updateExercise(workoutEvent, resources.getPhoto(message).getFileId());
        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        }
    }
}