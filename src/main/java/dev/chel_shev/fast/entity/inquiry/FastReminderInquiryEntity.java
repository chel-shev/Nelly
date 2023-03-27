package dev.chel_shev.fast.entity.inquiry;

import dev.chel_shev.fast.inquiry.command.reminder.add.ReminderAddInquiry;
import dev.chel_shev.fast.inquiry.command.reminder.remove.ReminderRemoveInquiry;
import dev.chel_shev.nelly.type.PeriodType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "fast_inquiry_reminder")
@NoArgsConstructor
public class FastReminderInquiryEntity extends FastInquiryEntity {

    private PeriodType periodType;
    private LocalDateTime time;
    private String name;

    public FastReminderInquiryEntity(ReminderRemoveInquiry inquiry) {
        super(inquiry);
        this.name = inquiry.getName();
    }

    public FastReminderInquiryEntity(ReminderAddInquiry inquiry) {
        super(inquiry);
        this.periodType = inquiry.getPeriodType();
        this.time = inquiry.getTime();
        this.name = inquiry.getName();
    }
}