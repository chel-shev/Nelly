package dev.chel_shev.nelly.entity.inquiry;

import dev.chel_shev.nelly.entity.finance.AccountEntity;
import dev.chel_shev.nelly.bot.inquiry.finance.InquiryFinance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "inquiry_finance")
@NoArgsConstructor
public class FinanceInquiryEntity extends InquiryEntity {

    private Long amount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AccountEntity in;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AccountEntity out;

    public FinanceInquiryEntity(InquiryFinance inquiry, AccountEntity out) {
        super(inquiry);
        this.amount = inquiry.getAmount();
        this.in = inquiry.getAccount();
        this.out = out;
    }
}
