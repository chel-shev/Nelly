package dev.chel_shev.fast.entity.inquiry;

import dev.chel_shev.fast.inquiry.command.finance.InquiryFinance;
import dev.chel_shev.nelly.entity.finance.AccountEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "fast_inquiry_finance")
@NoArgsConstructor
public class FastFinanceInquiryEntity extends FastInquiryEntity {

    private Long amount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AccountEntity in;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AccountEntity out;

    public FastFinanceInquiryEntity(InquiryFinance inquiry, AccountEntity out) {
        super(inquiry);
        this.amount = inquiry.getAmount();
        this.in = inquiry.getAccount();
        this.out = out;
    }
}
