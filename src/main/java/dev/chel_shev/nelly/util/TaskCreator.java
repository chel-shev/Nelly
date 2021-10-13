package dev.chel_shev.nelly.util;

import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.entity.BdayEntity;
import dev.chel_shev.nelly.entity.CalendarEntity;
import dev.chel_shev.nelly.task.BdayTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Timer;

@Slf4j
public class TaskCreator {

    public static void create(CalendarEntity entity, BotSender sender) {

        if (entity.getEvent() instanceof BdayEntity) {
            Timer time = new Timer();
            Date date = Date.from(entity.getEventZonedDateTime().toInstant());
            BdayTask bdayTask = new BdayTask(sender, entity.getUser(), ((BdayEntity) entity.getEvent()).getName());
            log.info("Task created with time = " + date);
            time.schedule(bdayTask, date);
        }
    }
}
