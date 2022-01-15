package dev.chel_shev.nelly.entity.inquiry;

import dev.chel_shev.nelly.bot.inquiry.Inquiry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "inquiry_common")
@NoArgsConstructor
public class CommonInquiryEntity extends InquiryEntity {

    public CommonInquiryEntity(Inquiry inquiry) {
        super(inquiry);
    }
}
