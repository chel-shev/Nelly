package dev.chel_shev.nelly.inquiry.reminder;

import dev.chel_shev.nelly.inquiry.InquiryHandler;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;

@Component
@Getter
@Setter
@Scope("prototype")
@Slf4j
@InquiryId(type = InquiryType.REMINDER_ADD)
@RequiredArgsConstructor
public class ReminderAddHandler extends InquiryHandler<ReminderAddInquiry> {

    private final ReminderAddConfig reminderAddConfig;

    @Override
    public ReminderAddInquiry executionLogic(ReminderAddInquiry i) {
//        if (service.isExist(i.getName())) {
//            calendarService.removeEvent(i.getName(), i.getUser());
//            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.FIRST, i));
//        } else {
//            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.SECOND, i));
//        }
//        i.setClosed(true);
//        i.setKeyboardType(BDAY);
        return i;
    }

    @Override
    public ReminderAddInquiry preparationLogic(ReminderAddInquiry i, Message message) {
        if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.THIRD, reminderAddConfig));
            i.setKeyboardType(CANCEL);
        } else {
            i.setMessage(TelegramBotUtils.getArgs(message.getText()));
            i.setName(i.getArgFromMassage(0));
            i.setKeyboardType(CANCEL);
        }
        return i;
    }
}