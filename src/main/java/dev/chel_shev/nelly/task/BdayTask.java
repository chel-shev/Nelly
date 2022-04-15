package dev.chel_shev.nelly.task;

import dev.chel_shev.nelly.bot.BotSender;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.event.BdayEventEntity;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.type.KeyboardType;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.TimerTask;

public class BdayTask extends TimerTask {

    private final BdayService bdayService;
    private final BotSender sender;
    private final UserEntity user;
    private final BdayEventEntity eventEntity;

    public BdayTask(BdayService bdayService, BotSender sender, UserEntity user, BdayEventEntity eventEntity) {
        this.bdayService = bdayService;
        this.sender = sender;
        this.user = user;
        this.eventEntity = eventEntity;
    }

    @Override
    public void run() {
        try {
            Message message = sender.sendMessage(user, KeyboardType.NONE, eventEntity.getName() + " празднует свой День Рождения, поздравь, если уместно!", true);
            eventEntity.setAnswerMessageId(message.getMessageId());
            bdayService.updateEvent(eventEntity);
        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        }
    }
}