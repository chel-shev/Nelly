package dev.chel_shev.nelly.bot.inquiry.reminder;

import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReminderRemoveHandler extends InquiryHandler<ReminderRemoveInquiry> {

    private final ReminderRemoveConfig reminderRemoveConfig;

    @Override
    public void executionLogic(ReminderRemoveInquiry i) {
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
        if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.THIRD, reminderRemoveConfig));
            i.setKeyboardType(CANCEL);
        } else if (i.getName().isEmpty()) {
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.FOURTH, reminderRemoveConfig));
            i.setMessage(TelegramBotUtils.getArgs(message.getText()));
            i.setName(i.getArgFromMassage(0));
        } else {

        }
    }
}