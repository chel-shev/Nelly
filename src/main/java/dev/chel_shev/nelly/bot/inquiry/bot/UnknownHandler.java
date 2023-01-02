package dev.chel_shev.nelly.bot.inquiry.bot;

import dev.chel_shev.nelly.bot.inquiry.InquiryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class UnknownHandler extends InquiryHandler<UnknownInquiry> {
}