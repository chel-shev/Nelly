package dev.chel_shev.nelly.bot.inquiry.reminder;

import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.bot.utils.InquiryId;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.google.common.base.Strings.isNullOrEmpty;
import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;
import static dev.chel_shev.nelly.type.KeyboardType.PERIOD_LIST;

@Component
@Getter
@Setter
@Slf4j
@InquiryId(InquiryType.REMINDER_ADD)
@RequiredArgsConstructor
public class ReminderAddHandler extends InquiryHandler<ReminderAddInquiry> {

    private final ReminderAddConfig reminderAddConfig;

    @Override
    public void executionLogic(ReminderAddInquiry i) {
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
        if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.THIRD, reminderAddConfig));
            i.setKeyboardType(CANCEL);
        } else if (isNullOrEmpty(i.getName())) {
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.FOURTH, reminderAddConfig));
            i.setMessage(TelegramBotUtils.getArgs(message.getText()));
            i.setName(i.getArgFromMassage(0));
            i.setKeyboardType(PERIOD_LIST);
        } else {

        }
    }
}