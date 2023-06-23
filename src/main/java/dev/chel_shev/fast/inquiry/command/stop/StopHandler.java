package dev.chel_shev.fast.inquiry.command.stop;

import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.inquiry.command.FastCommandInquiryHandler;
import dev.chel_shev.fast.service.FastEventService;
import dev.chel_shev.fast.service.FastUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
@Slf4j(topic = "inquiry")
@RequiredArgsConstructor
public class StopHandler extends FastCommandInquiryHandler<StopInquiry> {

    private final FastUserService userService;
    private final StopConfig stopConfig;
    private final FastEventService<? extends FastEvent> eventService;

    @Override
    public void executionLogic(StopInquiry i, Message m) {
        eventService.deleteByUser(i.getUser());
        inquiryService.deleteByUser(i.getUser());
        userService.delete(i.getUser());
        i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, stopConfig));
        i.setKeyboardType(FastKeyboardType.REPLY);
    }

    public StopInquiry execute(StopInquiry i, Message m) {
        if (m.getText().equals("Отмена"))
            return cancel(i);
        executionLogic(i, m);
        log.info("EXECUTE {}", i);
        return i;
    }
}