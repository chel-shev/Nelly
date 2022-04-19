package dev.chel_shev.nelly.util;

import dev.chel_shev.nelly.bot.BotResources;
import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.entity.event.BdayEventEntity;
import dev.chel_shev.nelly.entity.event.WorkoutEventEntity;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.WorkoutService;
import dev.chel_shev.nelly.task.BdayTask;
import dev.chel_shev.nelly.task.WorkoutTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Timer;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskCreator {

    private final WorkoutService workoutService;
    private final BdayService bdayService;

    public void create(CalendarEntity entity, BotSender sender, BotResources resources) {
        Timer time = new Timer();
        Date date = Date.from(entity.getEventZonedDateTime().toInstant());
        if (entity.getEvent() instanceof BdayEventEntity bdayEvent) {
            BdayTask bdayTask = new BdayTask(bdayService, sender, entity.getUser(), bdayEvent);
            log.info("Task created with time = " + date);
            time.schedule(bdayTask, date);
        } else if (entity.getEvent() instanceof WorkoutEventEntity workoutEvent) {
            InputFile file;
            if (isNull(workoutEvent.getWorkout().getFileId()))
                file = new InputFile(new ByteArrayInputStream(workoutEvent.getWorkout().getImage()), workoutEvent.getWorkout().getName());
            else
                file = new InputFile(workoutEvent.getWorkout().getFileId());
            WorkoutTask bdayTask = new WorkoutTask(workoutService, sender, resources, entity.getUser(), file, workoutEvent.getWorkout().getName(), entity.getEvent());
            log.info("Task created with time = " + date);
            time.schedule(bdayTask, date);
        }
    }
}
