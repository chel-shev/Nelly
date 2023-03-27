package dev.chel_shev.nelly.entity.finance;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "loan")
@NoArgsConstructor
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long amount;
    private LocalDateTime dateStart;
    private LocalDateTime dateFinish;
    private Boolean direction;

    @ManyToOne
    private AccountEntity account;

    public LoanEntity(String name, long amount, LocalDateTime dateStart, LocalDateTime dateFinish, boolean direction, AccountEntity account) {
        this.name = name;
        this.amount = amount;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.direction = direction;
        this.account = account;
    }

    public LoanEntity(String name, long amount) {
        this.name = name;
        this.amount = amount;
    }
}
