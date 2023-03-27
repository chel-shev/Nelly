package dev.chel_shev.fast.entity.inquiry;

import dev.chel_shev.fast.inquiry.command.bday.add.BdayAddInquiry;
import dev.chel_shev.fast.inquiry.command.bday.remove.BdayRemoveInquiry;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "fast_inquiry_bday")
@NoArgsConstructor
public class FastBdayInquiryEntity extends FastInquiryEntity {

    private LocalDateTime bdayDate;
    private String name;

    public FastBdayInquiryEntity(BdayAddInquiry inquiry) {
        super(inquiry);
        this.bdayDate = inquiry.getBdayDate();
        this.name = inquiry.getName();
    }

    public FastBdayInquiryEntity(BdayRemoveInquiry inquiry) {
        super(inquiry);
        this.name = inquiry.getName();
    }
}