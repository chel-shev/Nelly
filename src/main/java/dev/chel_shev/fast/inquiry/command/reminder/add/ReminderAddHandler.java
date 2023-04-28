package dev.chel_shev.fast.inquiry.command.reminder.add;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class ReminderAddHandler extends FastInquiryHandler<ReminderAddInquiry> {

    private final ReminderAddConfig reminderAddConfig;

    @Override
    public void executionLogic(ReminderAddInquiry i, Message message) {
//        if (service.isExist(i.getName())) {
//            calendarService.removeEvent(i.getName(), i.getUser());
//            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.FIRST, i));
//        } else {
//            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.SECOND, i));
//        }
//        i.setClosed(true);
//        i.setKeyboardType(BDAY);
    }

    @Override
    public void preparationLogic(ReminderAddInquiry i, Message message) {
        if (fastUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.THIRD, reminderAddConfig));
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtons(Arrays.asList("Отмена"));
        } else if (isNullOrEmpty(i.getName())) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FOURTH, reminderAddConfig));
            i.setMessage(fastUtils.getArgs(message.getText()));
            i.setName(i.getArgFromMassage(message.getText(), 0));
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtons(Arrays.asList("периоды"));
        } else {

        }
    }
}