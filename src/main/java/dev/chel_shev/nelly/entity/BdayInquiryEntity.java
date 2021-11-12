package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.inquiry.prototype.bday.BdayInquiry;
import dev.chel_shev.nelly.inquiry.prototype.bday.BdayRemoveInquiry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "inquiry_bday")
@NoArgsConstructor
public class BdayInquiryEntity extends InquiryEntity {

    private LocalDateTime bdayDate;
    private String name;

    public BdayInquiryEntity(BdayInquiry inquiry) {
        setId(inquiry.getId());
        setType(inquiry.getType());
        setMessage(inquiry.getMessage());
        setClosed(inquiry.isClosed());
        setDate(inquiry.getDate());
        setCommand(inquiry.getCommand());
        setUser(inquiry.getUser());
        setBdayDate(inquiry.getBdayDate());
        setName(inquiry.getName());
        setKeyboardType(inquiry.getKeyboardType());
    }

    public BdayInquiryEntity(BdayRemoveInquiry inquiry) {
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
}