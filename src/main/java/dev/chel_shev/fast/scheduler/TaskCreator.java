package dev.chel_shev.fast.scheduler;

import dev.chel_shev.fast.FastBotResource;
import dev.chel_shev.fast.FastSender;
import dev.chel_shev.fast.entity.event.FastBdayEventEntity;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.event.FastEventFactory;
import dev.chel_shev.fast.event.bday.BdayEvent;
import dev.chel_shev.fast.event.workout.FastWorkoutEvent;
import dev.chel_shev.fast.scheduler.task.BdayTask;
import dev.chel_shev.fast.scheduler.task.WorkoutTask;
import dev.chel_shev.fast.service.FastBdayEventService;
import dev.chel_shev.fast.service.WorkoutEventService;
import dev.chel_shev.nelly.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.ByteArrayInputStream;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Timer;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskCreator {

    private final WorkoutService workoutService;
    private final WorkoutEventService workoutEventService;
    private final FastBdayEventService bdayService;
    private final FastEventFactory<? extends FastEvent> eventFactory;

    public void create(FastEventEntity eventEntity, FastUserSubscriptionEntity userSubscription, FastSender sender, FastBotResource resources) {
        Timer time = new Timer();
        Date date = Date.from(eventEntity.getDateTime().atZone(ZoneOffset.of("+3")).toInstant()); /// TODO: 06.03.2023
        if (eventEntity instanceof FastBdayEventEntity bdayEvent) {
            BdayEvent event = (BdayEvent) eventFactory.getEvent("/bday");
            event.init(bdayEvent, userSubscription);
            BdayTask bdayTask = new BdayTask(bdayService, sender, userSubscription.getFastUser().getChatId(), event);
            log.info("Task created with time = " + date);
            time.schedule(bdayTask, date);
        } else if (eventEntity instanceof FastWorkoutEventEntity workoutEvent) {
            FastWorkoutEvent event = (FastWorkoutEvent) eventFactory.getEvent("/workout");
            event.init(workoutEvent, userSubscription);
            InputFile file;
            if (isNull(workoutEvent.getWorkout().getFileId()))
                file = new InputFile(new ByteArrayInputStream(workoutEvent.getWorkout().getImage()), workoutEvent.getWorkout().getName());
            else
                file = new InputFile(workoutEvent.getWorkout().getFileId());
            WorkoutTask bdayTask = new WorkoutTask(workoutService, workoutEventService, sender, resources, file, workoutEvent.getWorkout().getName(), event);
            log.info("Task created with time = " + date);
            time.schedule(bdayTask, date);
        }
    }
}
