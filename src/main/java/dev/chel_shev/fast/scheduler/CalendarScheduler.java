package dev.chel_shev.fast.scheduler;

import dev.chel_shev.fast.FastBotResource;
import dev.chel_shev.fast.FastSender;
import dev.chel_shev.fast.entity.event.FastEventEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.repository.FastEventRepository;
import dev.chel_shev.fast.repository.FastUserRepository;
import dev.chel_shev.nelly.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.List;

import static dev.chel_shev.nelly.util.DateTimeUtils.isNextMinute;

@Component
@RequiredArgsConstructor
@Slf4j
public class CalendarScheduler {

    private final FastUserRepository userRepository;
    private final FastEventRepository eventRepository;
    private final FastSender sender;
    private final FastBotResource resources;
    private final TaskEventCreator taskEventCreator;

    @Scheduled(cron = DateTimeUtils.EVERY_MINUTE)
    public void schedule() {
        log.debug("CalendarScheduler is started!");
        userRepository.findAll().forEach(this::createEventTasks);
        log.debug("CalendarScheduler is finished!");
    }

    private void createEventTasks(FastUserEntity user) {
        List<FastEventEntity> calendarList = eventRepository.findAllByUser_FastUserAndClosed(user, false);
        calendarList.forEach(event -> {
            if (isNextMinute(event.getDateTime(), ZoneOffset.of("+3")))
                taskEventCreator.create(event, event.getUser(), sender, resources);
        });
    }
}