package dev.chel_shev.nelly.entity.finance;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "income")
@NoArgsConstructor
public class IncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long amount;
    private LocalDateTime date;

    @ManyToOne
    private IncomeCategoryEntity incomeCategory;

    @ManyToOne
    private AccountEntity account;

    public IncomeEntity(String name, Long amount, LocalDateTime date, IncomeCategoryEntity incomeCategory, AccountEntity account) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.incomeCategory = incomeCategory;
        this.account = account;
    }
}