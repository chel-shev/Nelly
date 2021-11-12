package dev.chel_shev.nelly.inquiry.handler;

import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.inquiry.prototype.UnknownUserInquiry;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@InquiryId(type = InquiryType.NONE, command = "/unknown_user")
public class UnknownUserHandler extends InquiryHandler<UnknownUserInquiry> {

    @Override
    public UnknownUserInquiry executionLogic(UnknownUserInquiry unknownUserInquiry) {
        return null;
    }

    @Override
    public UnknownUserInquiry preparationLogic(UnknownUserInquiry unknownUserInquiry, Message message) {
        return null;
    }
}