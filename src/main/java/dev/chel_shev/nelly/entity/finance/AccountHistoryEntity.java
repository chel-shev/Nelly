package dev.chel_shev.nelly.entity.finance;

import dev.chel_shev.nelly.type.InquiryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String command;
    private LocalDateTime date;

    @ManyToOne
    private AccountEntity account;
}
