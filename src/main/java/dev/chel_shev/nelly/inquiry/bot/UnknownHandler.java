package dev.chel_shev.nelly.inquiry.bot;

import dev.chel_shev.nelly.inquiry.InquiryHandler;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
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
