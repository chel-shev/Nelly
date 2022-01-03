package dev.chel_shev.nelly.inquiry.bot;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@InquiryId(type = InquiryType.NONE)
public class UnknownUserInquiry extends Inquiry {

    @Override
    public Inquiry getInstance() {
        return new UnknownUserInquiry();
    }
}