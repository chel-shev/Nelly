package dev.chel_shev.nelly.inquiry.bday;

import dev.chel_shev.nelly.inquiry.InquiryHandler;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.CalendarService;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.util.TelegramBotUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.KeyboardType.BDAY;
import static dev.chel_shev.nelly.type.KeyboardType.CANCEL;

@Service
@Slf4j
@RequiredArgsConstructor
public class BdayRemoveHandler extends InquiryHandler<BdayRemoveInquiry> {

    private final BdayService service;
    private final CalendarService calendarService;
    private final BdayRemoveConfig bdayRemoveConfig;

    @Override
    public BdayRemoveInquiry executionLogic(BdayRemoveInquiry i) {
        if (service.isExist(i.getName())) {
            calendarService.removeEvent(i.getName(), i.getUser());
            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.FIRST, bdayRemoveConfig));
        } else {
            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.SECOND, bdayRemoveConfig));
        }
        i.setClosed(true);
        i.setKeyboardType(BDAY);
        return i;
    }

    @Override
    public BdayRemoveInquiry preparationLogic(BdayRemoveInquiry i, Message message) {
        if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(answerService.generateAnswer(CommandLevel.THIRD, bdayRemoveConfig));
            i.setKeyboardType(CANCEL);
        } else {
            i.setMessage(TelegramBotUtils.getArgs(message.getText()));
            i.setName(i.getArgFromMassage(0));
        }
        return i;
    }

    public BdayRemoveInquiry cancel(BdayRemoveInquiry i) {
        i.setClosed(true);
        i.setAnswerMessage("Действие отменено!");
        i.setKeyboardType(BDAY);
        save(i.getEntity());
        log.info("CANCEL Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return i;
    }
}
