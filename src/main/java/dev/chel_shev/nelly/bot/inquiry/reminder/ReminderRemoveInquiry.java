package dev.chel_shev.nelly.bot.inquiry.reminder;

import dev.chel_shev.nelly.entity.inquiry.InquiryEntity;
import dev.chel_shev.nelly.entity.inquiry.ReminderInquiryEntity;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.utils.InquiryId;
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
        return new ReminderInquiryEntity(this);
    }
}