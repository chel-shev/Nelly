package dev.chel_shev.fast.scheduler;

import dev.chel_shev.fast.FastBotResource;
import dev.chel_shev.fast.FastSender;
import dev.chel_shev.fast.entity.event.FastBdayEventEntity;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.event.FastWordEventEntity;
import dev.chel_shev.fast.entity.event.FastWorkoutEventEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.bday.BdayEvent;
import dev.chel_shev.fast.event.language.FastLanguageEvent;
import dev.chel_shev.fast.event.workout.FastWorkoutEvent;
import dev.chel_shev.fast.scheduler.task.BdayTask;
import dev.chel_shev.fast.scheduler.task.WordTask;
import dev.chel_shev.fast.scheduler.task.WorkoutTask;
import dev.chel_shev.fast.service.FastBdayEventService;
import dev.chel_shev.fast.service.FastEventService;
import dev.chel_shev.fast.service.LanguageService;
import dev.chel_shev.fast.service.WorkoutEventService;
import dev.chel_shev.nelly.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.ByteArrayInputStream;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskEventCreator {

    private final FastEventService eventService;
    private final LanguageService languageService;
    private final WorkoutService workoutService;
    private final WorkoutEventService workoutEventService;
    private final FastBdayEventService bdayService;

    public void create(FastEventEntity eventEntity, FastUserSubscriptionEntity userSubscription, FastSender sender, FastBotResource resources) {
        Timer time = new Timer();
        TimerTask task = null;
        ZoneOffset zoneOffset = userSubscription.getFastUser().getZoneOffset();
        GregorianCalendar calendar = GregorianCalendar.from(eventEntity.getDateTime().atZone(zoneOffset));
        if (eventEntity instanceof FastBdayEventEntity bdayEvent) {
            BdayEvent event = (BdayEvent) eventService.getEvent("/bday");
            event.init(bdayEvent, userSubscription);
            task = new BdayTask(bdayService, sender, userSubscription.getFastUser().getChatId(), event);
        } else if (eventEntity instanceof FastWorkoutEventEntity workoutEvent) {
            FastWorkoutEvent event = (FastWorkoutEvent) eventService.getEvent("/workout");
            event.init(workoutEvent, userSubscription);
            InputFile file;
            if (isNull(workoutEvent.getWorkout().getFileId()))
                file = new InputFile(new ByteArrayInputStream(workoutEvent.getWorkout().getImage()), workoutEvent.getWorkout().getName());
            else
                file = new InputFile(workoutEvent.getWorkout().getFileId());
            task = new WorkoutTask(workoutService, workoutEventService, sender, resources, file, workoutEvent.getWorkout().getName(), event);
        } else if (eventEntity instanceof FastWordEventEntity languageEvent) {
            FastLanguageEvent event = (FastLanguageEvent) eventService.getEvent("/language");
            event.init(languageEvent, userSubscription);
            task = new WordTask(eventService, languageService, sender, event, calendar);
        }
        log.info("Task created with time = " + calendar.getTime());
        time.schedule(task, calendar.getTime());
    }
}
