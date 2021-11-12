package dev.chel_shev.nelly.inquiry.handler;

import dev.chel_shev.nelly.inquiry.InquiryAnswer;
import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.prototype.UnknownInquiry;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@InquiryId(type = InquiryType.NONE, command = "/unknown")
public class UnknownHandler extends InquiryHandler<UnknownInquiry> {


    @Override
    public UnknownInquiry executionLogic(UnknownInquiry unknownInquiry) {
        return null;
    }

    @Override
    public UnknownInquiry preparationLogic(UnknownInquiry unknownInquiry, Message message) {
        return null;
    }
}
