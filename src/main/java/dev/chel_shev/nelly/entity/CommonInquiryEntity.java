package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.inquiry.prototype.Inquiry;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "inquiry_common")
@NoArgsConstructor
public class CommonInquiryEntity extends InquiryEntity{

    public CommonInquiryEntity(Inquiry inquiry) {
        setType(inquiry.getType());
        setMessage(inquiry.getMessage());
        setClosed(inquiry.isClosed());
        setDate(inquiry.getDate());
        setCommand(inquiry.getCommand());
        setAnswerMessage(inquiry.getAnswerMessage());
        setKeyboardType(inquiry.getKeyboardType());
        setUser(inquiry.getUser());
    }
}
