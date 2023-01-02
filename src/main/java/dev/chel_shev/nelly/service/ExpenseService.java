package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.finance.AccountEntity;
import dev.chel_shev.nelly.entity.finance.AccountHistoryEntity;
import dev.chel_shev.nelly.entity.finance.ExpenseEntity;
import dev.chel_shev.nelly.entity.finance.ExpenseProductEntity;
import dev.chel_shev.nelly.repository.ClientHistoryRepository;
import dev.chel_shev.nelly.repository.ExpenseRepository;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseProductService expProS;
    private final ExpenseRepository expR;
    private final AccountService accS;
    private final ClientHistoryRepository cliHisR;

    public Iterable<ExpenseEntity> get() {
        return expR.findAllByConfirmed(false);
    }

    public ExpenseEntity getById(Long id) {
        return expR.findById(id).orElse(new ExpenseEntity());
    }

    @Transactional
    public void save(ExpenseEntity expense, AccountEntity account) {
        ExpenseProductEntity ep = expProS.getProduct(expense.getProductName());
        expense.setExpenseProduct(ep);
        expense.setAccount(account);
        expR.save(expense);
    }

    public ExpenseEntity save(ExpenseEntity expense) {
        ExpenseProductEntity ep = expProS.getProduct(expense.getProductName());
        expense.setExpenseProduct(ep);
        return expR.save(expense);
    }

    public void save(ExpenseEntity expense, AccountEntity account, long sum) {
        account.subAccountBalance(sum);
        accS.save(account);
        AccountHistoryEntity entity = new AccountHistoryEntity(null, sum, account.getAccountBalance(), InquiryType.EXPENSE, expense.getDate(), account);
        cliHisR.save(entity);
        save(expense, account);
    }

    public void saveAll(List<ExpenseEntity> expenseList, AccountEntity account, long sum) {
        account.subAccountBalance(sum);
        accS.save(account);
        AccountHistoryEntity entity = new AccountHistoryEntity(null, sum, account.getAccountBalance(), InquiryType.EXPENSE, expenseList.get(0).getDate(), account);
        cliHisR.save(entity);
        expenseList.forEach(expense -> save(expense, account));
    }

    public void updateConfirmed(List<ExpenseEntity> expenseList) {
        expenseList.forEach(expense -> {
            if (expense.isConfirmed()) save(expense);
        });
    }
}