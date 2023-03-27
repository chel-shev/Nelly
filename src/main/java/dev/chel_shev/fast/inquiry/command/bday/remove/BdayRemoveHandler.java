package dev.chel_shev.fast.inquiry.command.bday.remove;

import dev.chel_shev.fast.event.FastEvent;
import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.inquiry.keyboard.bday.BdayKeyboardInquiry;
import dev.chel_shev.fast.service.FastEventService;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.service.FastBdayEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class BdayRemoveHandler extends FastInquiryHandler<BdayRemoveInquiry> {

    private final FastBdayEventService service;
    private final FastEventService<? extends FastEvent> eventService;
    private final BdayRemoveConfig bdayRemoveConfig;

    @Override
    public void executionLogic(BdayRemoveInquiry i) {
        remove(i);
    }

    @Override
    public void preparationLogic(BdayRemoveInquiry i, Message message) {
        if (fastUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.THIRD, bdayRemoveConfig));
            i.setKeyboardType(FastKeyboardType.INLINE);
            i.setKeyboardButtonList(service.getAllBdayName(i.getUser()));
        } else {
            i.setMessage(fastUtils.getArgs(message.getText()));
            i.setName(i.getArgFromMassage(message.getText(), 0));
        }
    }

    public BdayRemoveInquiry cancel(BdayRemoveInquiry i) {
        super.cancel(i);
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(Arrays.asList("Добавить", "Удалить"));
        return i;
    }

    @Override
    public void inlineExecutionLogic(BdayRemoveInquiry i, CallbackQuery callbackQuery) {
        remove(i);
    }

    @Override
    public void inlinePreparationLogic(BdayRemoveInquiry i, CallbackQuery callbackQuery) {
        i.setMessage(fastUtils.getArgs(callbackQuery.getData()));
        i.setName(callbackQuery.getData());
    }



    private void remove(BdayRemoveInquiry i) {
        if (service.isExist(i.getName())) {
            service.removeEvent(i.getName(), i.getUser());
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FIRST, bdayRemoveConfig));
        } else {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.SECOND, bdayRemoveConfig));
        }
        i.setClosed(true);
        i.setKeyboardType(FastKeyboardType.REPLY);
        i.setKeyboardButtonList(keyboardService.getButtons(BdayKeyboardInquiry.class));
    }
}
