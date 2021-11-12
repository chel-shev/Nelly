package dev.chel_shev.nelly.inquiry.prototype;

import dev.chel_shev.nelly.inquiry.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@InquiryId(type = InquiryType.KEYBOARD, command = "/keyboard")
public class KeyboardInquiry extends Inquiry {

    @Override
    public void initAnswers() {

    }
}