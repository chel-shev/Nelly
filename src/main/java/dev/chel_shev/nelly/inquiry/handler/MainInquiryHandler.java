package dev.chel_shev.nelly.inquiry.handler;

import dev.chel_shev.nelly.bot.BotResources;
import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import dev.chel_shev.nelly.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class MainInquiryHandler<I extends Inquiry> {

    private final InquiryService<I> inquiryService;
    private final HandlerFactory<I> handlerFactory;

    public I execute(Message message) {
        I i = inquiryService.getInquiry(message);
        InquiryHandler<I> inquiryHandler = handlerFactory.getHandler(i.getClass());

        if (i.isNotReadyForExecute())
            i = inquiryHandler.prepare(i, message);



        if (!i.isNotReadyForExecute())
            return inquiryHandler.execute(i, message);
        return i;
    }
}