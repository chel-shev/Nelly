package dev.chel_shev.nelly.inquiry.handler.stop;

import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.handler.InquiryHandler;
import dev.chel_shev.nelly.type.CommandLevel;
import dev.chel_shev.nelly.inquiry.prototype.stop.StopInquiry;
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
@InquiryId(type = InquiryType.STOP, command = "/stop")
public class StopHandler extends InquiryHandler<StopInquiry> {

    private final UserService userService;

    @Override
    public StopInquiry executionLogic(StopInquiry inquiry) {
        userService.delete(inquiry.getUser());
        inquiry.setAnswerMessage(answerService.generateAnswer(CommandLevel.FIRST, inquiry));
        inquiry.setKeyboardType(KeyboardType.NONE);
        return inquiry;
    }

    @Override
    public StopInquiry preparationLogic(StopInquiry stopInquiry, Message message) {
        return null;
    }
}