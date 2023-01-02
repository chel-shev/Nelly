package dev.chel_shev.nelly.bot.inquiry.bday;

import dev.chel_shev.nelly.bot.event.Event;
import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import dev.chel_shev.nelly.service.BdayService;
import dev.chel_shev.nelly.service.EventService;
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
    private final EventService<? extends Event> eventService;
    private final BdayRemoveConfig bdayRemoveConfig;

    @Override
    public void executionLogic(BdayRemoveInquiry i) {
        if (service.isExist(i.getName())) {
            eventService.removeEvent(i.getName(), i.getUser());
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.FIRST, bdayRemoveConfig));
        } else {
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.SECOND, bdayRemoveConfig));
        }
        i.setClosed(true);
        i.setKeyboardType(BDAY);
    }

    @Override
    public void preparationLogic(BdayRemoveInquiry i, Message message) {
        if (TelegramBotUtils.getArgs(message.getText()).isEmpty()) {
            i.setAnswerMessage(aSer.generateAnswer(CommandLevel.THIRD, bdayRemoveConfig));
            i.setKeyboardType(CANCEL);
        } else {
            i.setMessage(TelegramBotUtils.getArgs(message.getText()));
            i.setName(i.getArgFromMassage(0));
        }
    }

    public BdayRemoveInquiry cancel(BdayRemoveInquiry i) {
        super.cancel(i);
        i.setKeyboardType(BDAY);
        return i;
    }
}
