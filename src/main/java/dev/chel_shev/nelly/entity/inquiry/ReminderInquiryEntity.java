package dev.chel_shev.nelly.entity.inquiry;

import dev.chel_shev.nelly.bot.inquiry.reminder.ReminderAddInquiry;
import dev.chel_shev.nelly.bot.inquiry.reminder.ReminderRemoveInquiry;
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
public class ReminderInquiryEntity extends InquiryEntity {

    private PeriodType periodType;
    private LocalDateTime time;
    private String name;

    public ReminderInquiryEntity(ReminderRemoveInquiry inquiry) {
        super(inquiry);
        this.name = inquiry.getName();
    }

    public ReminderInquiryEntity(ReminderAddInquiry inquiry) {
        super(inquiry);
        this.periodType = inquiry.getPeriodType();
        this.time = inquiry.getTime();
        this.name = inquiry.getName();
    }
}