package dev.chel_shev.fast.scheduler.task;

import dev.chel_shev.fast.FastMarkdown;
import dev.chel_shev.fast.FastSender;
import dev.chel_shev.fast.event.bday.BdayEvent;
import dev.chel_shev.fast.service.FastEventService;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Date;
import java.util.TimerTask;

public class BdayTask extends TimerTask {

    private final FastEventService<BdayEvent> eventService;
    private final FastSender sender;
    private final String chatId;
    private final BdayEvent event;

    public BdayTask(FastEventService<BdayEvent> eventService, FastSender sender, String chatId, BdayEvent event) {
        this.eventService = eventService;
        this.sender = sender;
        this.chatId = chatId;
        this.event = event;
    }

    @Override
    public void run() {
        try {
            String textMessage = FastMarkdown.bolt(event.getName()) + " празднует свой День Рождения, поздравь, если уместно\\!";
            Message message = sender.sendMessage(chatId, textMessage, true, false);
            event.setAnswerMessageId(message.getMessageId());
            eventService.updateEvent(event.getEntity());
        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        }
    }
}