package dev.chel_shev.nelly.inquiry.utils;

import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class InquiryFactory<I extends Inquiry> {

    private final List<I> inquiries;

    public I getInquiry(InquiryType type) {
        return inquiries.stream().filter(e -> e.getType() == type).findAny().orElseThrow(() -> new TelegramBotException("Inquiry don't support!")).getInstance();
    }

    public I getInquiry(String command) {
        return getInquiry(InquiryType.getFromCommand(command));
    }
}
