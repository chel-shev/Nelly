package dev.chel_shev.nelly.entity.event.finance;

import dev.chel_shev.nelly.entity.inquiry.FinanceInquiryEntity;
import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.exception.TelegramBotException;
import dev.chel_shev.nelly.type.AccountType;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.Data;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "in")
    private List<FinanceInquiryEntity> accountInList;

    @OneToMany(mappedBy = "out")
    private List<FinanceInquiryEntity> accountOutList;

    @OneToMany(mappedBy = "account")
    private List<AccountHistoryEntity> userHistoryList;

    public void addAccountBalance(long difference) {
        if (!isNull(volume) && accountBalance + difference > volume)
            throw new TelegramBotException(user, "Сумма пополнения слишком большая!", KeyboardType.CANCEL);
        this.accountBalance = accountBalance + difference;
    }

    public void subAccountBalance(long difference) {
        if (accountBalance - difference < 0)
            throw new TelegramBotException(user, "Недостаточно средств на счете!", KeyboardType.CANCEL);
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
