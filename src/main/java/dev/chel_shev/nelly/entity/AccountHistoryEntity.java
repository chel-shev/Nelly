package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.type.InquiryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "account_history")
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    private Long accountBalance;
    @Enumerated(EnumType.STRING)
    private InquiryType type;
    private LocalDateTime date;

    @ManyToOne
    private AccountEntity account;
}
