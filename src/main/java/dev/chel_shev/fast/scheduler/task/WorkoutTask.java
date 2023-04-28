package dev.chel_shev.fast.scheduler.task;

import dev.chel_shev.fast.FastBotResource;
import dev.chel_shev.fast.FastSender;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.event.workout.FastWorkoutEvent;
import dev.chel_shev.fast.service.WorkoutEventService;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.nelly.service.WorkoutService;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.TimerTask;

public class WorkoutTask extends TimerTask {

    private final WorkoutService workoutService;
    private final WorkoutEventService workoutEventService;
    private final FastSender sender;
    private final FastBotResource resources;
    private final InputFile photo;
    private final String name;
    private final FastWorkoutEvent event;

    public WorkoutTask(WorkoutService workoutService, WorkoutEventService workoutEventService, FastSender sender, FastBotResource resources, InputFile photo, String name, FastEvent event) {
        this.workoutService = workoutService;
        this.workoutEventService = workoutEventService;
        this.sender = sender;
        this.resources = resources;
        this.photo = photo;
        this.name = name;
        this.event = (FastWorkoutEvent) event;
    }

    @Override
    public void run() {
        try {
            event.setKeyboardButtons(workoutEventService.getWorkoutProcess(event));
            Message message = sender.sendPhoto(event.getUserSubscription().getFastUser().getChatId(), "Сегодня по плану: " + name, photo, FastKeyboardType.INLINE, event.getKeyboardButtons());
            event.setAnswerMessageId(message.getMessageId());
            workoutEventService.save(event);
            workoutEventService.initNextEvent(event);
            workoutService.updateWorkout(event.getWorkout(), resources.getPhoto(message).getFileId());
        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        }
    }
}