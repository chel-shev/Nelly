package dev.chel_shev.nelly.inquiry;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.service.AnswerService;
import dev.chel_shev.nelly.service.InquiryService;
import dev.chel_shev.nelly.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.chel_shev.nelly.type.KeyboardKeyType.CANCEL_KEY;
import static dev.chel_shev.nelly.type.KeyboardType.COMMON;

@Slf4j
@Component
public abstract class InquiryHandler<I extends Inquiry> {

    protected InquiryService<I> inquiryService;
    protected AnswerService aSer;
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
     * @param i       - Received inquiry
     * @param message - Bot message
     * @return - Processed inquiry
     */
    protected abstract I preparationLogic(I i, Message message);

    public I execute(I i, Message message) {
        if (message.getText().equals(CANCEL_KEY.label))
            return cancel(i);
        executionLogic(i);
        i.setId(save(i.getEntity()).getId());
        log.info("EXECUTE {}", i);
        return i;
    }

    public I prepare(I i, Message message) {
        if (message.getText().equals(CANCEL_KEY.label))
            return cancel(i);
        preparationLogic(i, message);
        i.setId(save(i.getEntity()).getId());
        log.info("PREPARE {}", i);
        return i;
    }

    public I cancel(I i) {
        i.setClosed(true);
        i.setAnswerMessage("Действие отменено!");
        i.setId(save(i.getEntity()).getId());
        i.setKeyboardType(COMMON);
        log.info("CANCEL {}", i);
        return i;
    }

    public InquiryEntity save(InquiryEntity i) {
        return inquiryService.save(i);
    }

    @Autowired
    public final void setInquiryService(@Qualifier("inquiryService") InquiryService<I> inquiryService) {
        this.inquiryService = inquiryService;
    }

    @Autowired
    public final void setAnswerService(@Qualifier("answerService") AnswerService aSer) {
        this.aSer = aSer;
    }

    @Autowired
    public final void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void updateInquiry(I i, Message reply) {
        if (null == i) return;
        i.setAnswerMessage(reply.getText());
        i.setAnswerMessageId(reply.getMessageId());
        save(i.getEntity());
        log.info("UPDATE {}", i);
    }
}