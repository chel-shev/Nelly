package dev.chel_shev.nelly.entity.finance;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "transfer")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    private LocalDateTime date;

    @ManyToOne
    private AccountEntity in;

    @ManyToOne
    private AccountEntity out;

    public TransferEntity() {

    }

    public TransferEntity(AccountEntity in, AccountEntity out, long amount, LocalDateTime date) {
        this.in = in;
        this.out = out;
        this.amount = amount;
        this.date = date;
    }
}
