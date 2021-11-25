package dev.chel_shev.nelly.inquiry;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.service.UserService;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.KeyboardKeyType.CANCEL_KEY;

@Slf4j
@Component
public abstract class InquiryHandler<I extends Inquiry> {

    @Autowired
    private InquiryService<I> inquiryService;
    @Autowired
    protected AnswerService answerService;
    @Autowired
    protected UserService userService;

    /**
     * Inquiry execution logic
     *
     * @param i - Received inquiry
     * @return - Processed inquiry
     */
    protected abstract I executionLogic(I i);

    /**
     * Inquiry preparation logic
     *
     * @param i - Received inquiry
     * @param message - Bot message
     * @return - Processed inquiry
     */
    protected abstract I preparationLogic(I i, Message message);

    public I execute(I i, Message message) {
        log.info("EXECUTE ExpenseInquiry(inquiryId: {}, text: {}, type: {}, date: {}, completed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        if (message.getText().equals(CANCEL_KEY.label))
            return cancel(i);
        I inquiry = executionLogic(i);
        save(i.getEntity());
        log.info("COMPLETE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return inquiry;
    }

    public I prepare(I i, Message message) {
        if (message.getText().equals(CANCEL_KEY.label))
            return cancel(i);
        I inquiry = preparationLogic(i, message);
        save(i.getEntity());
        log.info("PREPARE Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return inquiry;
    }

    public I cancel(I i) {
        i.setClosed(true);
        i.setAnswerMessage("Действие отменено!");
        i.setKeyboardType(KeyboardType.COMMON);
        save(i.getEntity());
        log.info("CANCEL Inquiry(inquiryId: {}, text: {}, type: {}, date: {}, closed: {})", i.getId(), i.getMessage(), i.getType(), i.getDate(), i.isClosed());
        return i;
    }

    public InquiryEntity save(InquiryEntity i) {
        return inquiryService.save(i);
    }
}