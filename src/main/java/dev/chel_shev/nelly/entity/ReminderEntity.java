package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.inquiry.reminder.ReminderAddInquiry;
import dev.chel_shev.nelly.inquiry.reminder.ReminderRemoveInquiry;
import dev.chel_shev.nelly.type.PeriodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "inquiry_reminder")
@NoArgsConstructor
public class ReminderEntity extends InquiryEntity {

    private PeriodType periodType;
    private String description;
    private String name;

    public ReminderEntity(ReminderRemoveInquiry inquiry) {
        setId(inquiry.getId());
        setType(inquiry.getType());
        setMessage(inquiry.getMessage());
        setClosed(inquiry.isClosed());
        setDate(inquiry.getDate());
        setCommand(inquiry.getCommand());
        setUser(inquiry.getUser());
        setName(inquiry.getName());
        setKeyboardType(inquiry.getKeyboardType());
    }

    public ReminderEntity(ReminderAddInquiry inquiry) {
        setId(inquiry.getId());
        setType(inquiry.getType());
        setMessage(inquiry.getMessage());
        setClosed(inquiry.isClosed());
        setDate(inquiry.getDate());
        setCommand(inquiry.getCommand());
        setUser(inquiry.getUser());
        setPeriodType(inquiry.getPeriodType());
        setDescription(inquiry.getDescription());
        setName(inquiry.getName());
        setKeyboardType(inquiry.getKeyboardType());
    }
}