package dev.chel_shev.nelly.inquiry.reminder;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.ReminderEntity;
import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.utils.InquiryId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Strings.isNullOrEmpty;
import static dev.chel_shev.nelly.type.InquiryType.REMINDER_ADD;

@Getter
@Setter
@Slf4j
@InquiryId(REMINDER_ADD)
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
}