package dev.chel_shev.nelly.bot.inquiry.bot;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.service.EventService;
import dev.chel_shev.nelly.service.UserService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;

@Component
@Slf4j
@RequiredArgsConstructor
public class StopHandler extends InquiryHandler<StopInquiry> {

    private final UserService userService;
    private final StopConfig stopConfig;
    private final EventService<? extends Event> eventService;

    @Override
    public void executionLogic(StopInquiry i) {
        eventService.deleteByUser(i.getUser());
        userService.delete(i.getUser());
        i.setAnswerMessage(aSer.generateAnswer(CommandLevel.FIRST, stopConfig));
        i.setKeyboardType(KeyboardType.NONE);
    }

    public StopInquiry execute(StopInquiry i, Message message) {
        if (message.getText().equals(CANCEL.label))
            return cancel(i);
        executionLogic(i);
        log.info("EXECUTE {}", i);
        return i;
    }
}