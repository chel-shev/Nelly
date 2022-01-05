package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.inquiry.bday.BdayAddInquiry;
import dev.chel_shev.nelly.inquiry.bday.BdayRemoveInquiry;
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

    public BdayInquiryEntity(BdayAddInquiry inquiry) {
        super(inquiry);
        this.bdayDate = inquiry.getBdayDate();
        this.name = inquiry.getName();
    }

    public BdayInquiryEntity(BdayRemoveInquiry inquiry) {
        super(inquiry);
        this.name = inquiry.getName();
    }
}