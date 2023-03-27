package dev.chel_shev.fast.inquiry.command.reminder.add;

import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.entity.inquiry.FastReminderInquiryEntity;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.type.FastInquiryType;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.isNull;

@Getter
@Setter
@Slf4j
@FastInquiryId(command = "/reminder_add", type = FastInquiryType.COMMAND, label = "")
public class ReminderAddInquiry extends FastInquiry {

    private PeriodType periodType;
    private LocalDateTime time;
    private String name;

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name) || isNull(periodType);
    }

    @Override
    public FastInquiryEntity getEntity() {
        return new FastReminderInquiryEntity(this);
    }
}