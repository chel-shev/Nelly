package dev.chel_shev.nelly.inquiry.handler.start;

import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.handler.InquiryHandler;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.inquiry.prototype.start.StartInquiry;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.service.UserService;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@InquiryId(type = InquiryType.START, command = "/start")
public class StartHandler extends InquiryHandler<StartInquiry> {

    private final UserService userService;

    @Override
    public StartInquiry executionLogic(StartInquiry inquiry) {
        if (userService.isExist(inquiry.getUser().getChatId())) {
            inquiry.setAnswerMessage(answerService.generateAnswer(CommandLevel.SECOND, inquiry));
        } else {
            userService.save(inquiry.getUser());
            inquiry.setAnswerMessage(answerService.generateAnswer(CommandLevel.FIRST, inquiry));
        }
        inquiry.setKeyboardType(KeyboardType.COMMON);
        inquiry.setClosed(true);
        return inquiry;
    }

    @Override
    public StartInquiry preparationLogic(StartInquiry startInquiry, Message message) {
        return null;
    }
}