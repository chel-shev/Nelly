package dev.chel_shev.nelly.inquiry;

import dev.chel_shev.nelly.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class InquiryHandler {

    private final InquiryService inquiryService;

    public InquiryAnswer execute(Message message) {
        Inquiry inquiry = inquiryService.getInquiry(message);
        return inquiry.process();
    }
}
