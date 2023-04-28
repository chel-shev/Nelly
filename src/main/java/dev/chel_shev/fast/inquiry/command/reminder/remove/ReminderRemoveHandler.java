package dev.chel_shev.fast.inquiry.command.reminder.remove;

import dev.chel_shev.fast.inquiry.FastInquiryHandler;
import dev.chel_shev.fast.type.FastBotCommandLevel;
import dev.chel_shev.fast.type.FastKeyboardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReminderRemoveHandler extends FastInquiryHandler<ReminderRemoveInquiry> {

    private final ReminderRemoveConfig reminderRemoveConfig;

    @Override
    public void executionLogic(ReminderRemoveInquiry i, Message message) {
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
    public void preparationLogic(ReminderRemoveInquiry i, Message message) {
        if (fastUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.THIRD, reminderRemoveConfig));
            i.setKeyboardType(FastKeyboardType.REPLY);
            i.setKeyboardButtons(Arrays.asList("Отмена"));
        } else if (i.getName().isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(FastBotCommandLevel.FOURTH, reminderRemoveConfig));
            i.setMessage(fastUtils.getArgs(message.getText()));
            i.setName(i.getArgFromMassage(message.getText(), 0));
        } else {

        }
    }
}