package dev.chel_shev.nelly.inquiry.bot;

import dev.chel_shev.nelly.inquiry.InquiryHandler;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Scope("prototype")
@RequiredArgsConstructor
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