package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.inquiry.reminder.ReminderAddInquiry;
import dev.chel_shev.nelly.inquiry.reminder.ReminderRemoveInquiry;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "inquiry_reminder")
@NoArgsConstructor
public class ReminderEntity extends InquiryEntity {

    private PeriodType periodType;
    private LocalDateTime time;
    private String name;

    public ReminderEntity(ReminderRemoveInquiry inquiry) {
        super(inquiry);
        this.name = inquiry.getName();
    }

    public ReminderEntity(ReminderAddInquiry inquiry) {
        super(inquiry);
        this.periodType = inquiry.getPeriodType();
        this.time = inquiry.getTime();
        this.name = inquiry.getName();
    }
}