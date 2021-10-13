package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.inquiry.Inquiry;
import dev.chel_shev.nelly.inquiry.finance.InquiryFinance;
import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "inquiry")
@NoArgsConstructor
public class InquiryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InquiryType type;

    private String message;
    private boolean closed;
    private Long amount;
    private LocalDateTime date;

    @ManyToOne
    private CommandEntity command;

    @ManyToOne
    private AccountEntity in;

    @ManyToOne
    private AccountEntity out;

    @ManyToOne
    private UserEntity user;

    public InquiryEntity(Inquiry inquiry) {
        this.type = inquiry.getType();
        this.message = inquiry.getMessage();
        this.closed = inquiry.isClosed();
        this.date = inquiry.getDate();
        this.command = inquiry.getCommand();
        this.user = inquiry.getUser();
    }

    public InquiryEntity(InquiryFinance inquiry, AccountEntity in, AccountEntity out) {
        this.id = inquiry.getId();
        this.type = inquiry.getType();
        this.message = inquiry.getMessage();
        this.closed = inquiry.isClosed();
        this.date = inquiry.getDate();
        this.command = inquiry.getCommand();
        this.user = inquiry.getUser();
        this.amount = inquiry.getAmount();
        this.in = in;
        this.out = out;
    }
}
