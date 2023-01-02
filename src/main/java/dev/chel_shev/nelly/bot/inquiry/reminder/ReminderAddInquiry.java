package dev.chel_shev.nelly.bot.inquiry.reminder;

import dev.chel_shev.nelly.entity.inquiry.InquiryEntity;
import dev.chel_shev.nelly.entity.inquiry.ReminderInquiryEntity;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import dev.chel_shev.nelly.bot.utils.InquiryId;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.google.common.base.Strings.isNullOrEmpty;
import static dev.chel_shev.nelly.type.InquiryType.REMINDER_ADD;
import static java.util.Objects.isNull;

@Getter
@Setter
@Slf4j
@InquiryId(REMINDER_ADD)
public class ReminderAddInquiry extends Inquiry {

    private PeriodType periodType;
    private LocalDateTime time;
    private String name;

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name) || isNull(periodType);
    }

    @Override
    public InquiryEntity getEntity() {
        return new ReminderInquiryEntity(this);
    }
}