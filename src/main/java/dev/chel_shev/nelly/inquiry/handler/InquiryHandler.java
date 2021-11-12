package dev.chel_shev.nelly.inquiry.handler;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public abstract class InquiryHandler<I extends Inquiry> {

    @Autowired
    private InquiryService<I> inquiryService;
    @Autowired
    protected AnswerService answerService;
    @Autowired
    protected UserService userService;

    protected abstract I executionLogic(I i);

    protected abstract I preparationLogic(I i, Message message);

    public I execute(I i, Message message) {
        if (message.getText().equals("Отмена"))
            return cancel(i);
        I inquiry = executionLogic(i);
        save(i.getEntity());
        log.info("COMPLETE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return inquiry;
    }

    public I prepare(I i, Message message) {
        if (message.getText().equals("Отмена"))
            return cancel(i);
        I inquiry = preparationLogic(i, message);
        save(i.getEntity());
        log.info("PREPARE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return inquiry;
    }

    public I cancel(I i) {
        i.setClosed(true);
        save(i.getEntity());
        log.info("CANCEL Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return i;
    }

    public InquiryEntity save(InquiryEntity i) {
        return inquiryService.save(i);
    }
}