package dev.chel_shev.fast.entity.inquiry;

import dev.chel_shev.fast.inquiry.FastInquiry;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "fast_inquiry_common")
@NoArgsConstructor
public class FastCommonInquiryEntity extends FastInquiryEntity {

    public FastCommonInquiryEntity(FastInquiry inquiry) {
        super(inquiry);
    }
}
