package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.inquiry.prototype.finance.InquiryFinance;
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
        setId(inquiry.getId());
        setType(inquiry.getType());
        setMessage(inquiry.getMessage());
        setClosed(inquiry.isClosed());
        setDate(inquiry.getDate());
        setCommand(inquiry.getCommand());
        setUser(inquiry.getUser());
        this.amount = inquiry.getAmount();
        this.in = inquiry.getAccount();
        this.out = out;
    }
}
