package dev.chel_shev.nelly.inquiry.reminder;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.ReminderEntity;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@Getter
@Setter
@Scope("prototype")
@Slf4j
@InquiryId(type = InquiryType.REMINDER_ADD)
public class ReminderRemoveInquiry extends Inquiry {

    private String name;

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name);
    }

    @Override
    public InquiryEntity getEntity() {
        return new ReminderEntity(this);
    }

    @Override
    public ReminderRemoveInquiry getInstance() {
        return new ReminderRemoveInquiry();
    }
}