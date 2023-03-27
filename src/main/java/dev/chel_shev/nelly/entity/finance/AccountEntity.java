package dev.chel_shev.nelly.entity.finance;

import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.exception.NellyException;
import dev.chel_shev.nelly.type.AccountType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import static java.util.Objects.isNull;

@Data
@Entity(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long accountBalance;
    private Long volume;
    private boolean main;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "account")
    private List<ExpenseEntity> expenseList;

    @OneToMany(mappedBy = "account")
    private List<IncomeEntity> incomeList;

    @OneToMany(mappedBy = "account")
    private List<LoanEntity> loanList;

    @OneToMany(mappedBy = "in")
    private List<TransferEntity> inTransferList;

    @OneToMany(mappedBy = "out")
    private List<TransferEntity> outTransferList;

    @OneToMany(mappedBy = "account")
    private List<AccountHistoryEntity> userHistoryList;

    public void addAccountBalance(long difference) {
        if (!isNull(volume) && accountBalance + difference > volume)
            throw new NellyException("Сумма пополнения слишком большая!");
        this.accountBalance = accountBalance + difference;
    }

    public void subAccountBalance(long difference) {
        if (accountBalance - difference < 0)
            throw new NellyException("Недостаточно средств на счете!");
        this.accountBalance = accountBalance - difference;
    }

    public String getInfoString() {
        String amount = switch (this.getAccountType()) {
            case CASH, DEBIT -> String.valueOf((long) (this.getAccountBalance() / 100d));
            case CREDIT -> String.valueOf((long) (this.getAccountBalance() / 100d - this.getVolume() / 100d));
        };
        return getAccountType().getIcon() + " " + getName() + " (" + amount + ")";
    }
}
