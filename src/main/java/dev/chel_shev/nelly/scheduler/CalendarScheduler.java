package dev.chel_shev.nelly.scheduler;

import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.entity.UserEntity;
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
    private final BotSender sender;

    @Scheduled(cron = DateTimeUtils.EVERY_MINUTE)
    public void schedule() {
        userRepository.findAll().forEach(this::createTasks);
    }

    private void createTasks(UserEntity userEntity) {
        log.info("CalendarScheduler is started!");
        List<CalendarEntity> calendarList = userEntity.getCalendarList();
        calendarList.forEach(e -> {
            if (isNextMinute(e.getEventDateTime(), e.getUser().getZoneOffset()))
                TaskCreator.create(e, sender);
        });
        log.info("CalendarScheduler is finished!");
    }
}