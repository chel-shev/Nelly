package dev.chel_shev.nelly.scheduler;

import dev.chel_shev.nelly.bot.BotResources;
import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.repository.EventRepository;
import dev.chel_shev.nelly.repository.UserRepository;
import dev.chel_shev.nelly.util.DateTimeUtils;
import dev.chel_shev.nelly.util.TaskCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static dev.chel_shev.nelly.util.DateTimeUtils.isNextMinute;

@Component
@RequiredArgsConstructor
@Slf4j
public class CalendarScheduler {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final BotSender sender;
    private final BotResources resources;
    private final TaskCreator taskCreator;

    @Scheduled(cron = DateTimeUtils.EVERY_MINUTE)
    public void schedule() {
        log.debug("CalendarScheduler is started!");
        userRepository.findAll().forEach(this::createTasks);
        log.debug("CalendarScheduler is finished!");
    }

    private void createTasks(UserEntity userEntity) {
        List<EventEntity> calendarList = eventRepository.findAllByUserAndClosed(userEntity, false);
        calendarList.forEach(e -> {
            if (isNextMinute(e.getEventDateTime(), e.getUser().getZoneOffset()))
                taskCreator.create(e, sender, resources);
        });
    }
}