package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.inquiry.finance.InquiryFinance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "inquiry_finance")
@NoArgsConstructor
public class FinanceInquiryEntity extends InquiryEntity {

    private Long amount;

    @ManyToOne
    private AccountEntity in;

    @ManyToOne
    private AccountEntity out;

    public FinanceInquiryEntity(InquiryFinance inquiry, AccountEntity out) {
        super(inquiry);
        this.amount = inquiry.getAmount();
        this.in = inquiry.getAccount();
        this.out = out;
    }
}
