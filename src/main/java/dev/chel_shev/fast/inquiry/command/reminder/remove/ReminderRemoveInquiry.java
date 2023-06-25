package dev.chel_shev.fast.inquiry.command.reminder.remove;

import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import dev.chel_shev.fast.entity.inquiry.FastReminderInquiryEntity;
import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.inquiry.FastInquiryId;
import dev.chel_shev.fast.type.FastInquiryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
@Setter
@Slf4j
@FastInquiryId(command = "/reminder_remove", type = FastInquiryType.COMMAND, label = "")
public class ReminderRemoveInquiry extends FastInquiry {

    private String name;

    @Override
    public boolean isNotReadyForExecute() {
        return isNullOrEmpty(name);
    }

    @Override
    public FastInquiryEntity getEntity() {
        return new FastReminderInquiryEntity(this);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", name=" + name +
                '}';
    }
}