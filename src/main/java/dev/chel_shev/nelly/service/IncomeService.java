package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.finance.AccountEntity;
import dev.chel_shev.nelly.entity.finance.AccountHistoryEntity;
import dev.chel_shev.nelly.entity.finance.IncomeEntity;
import dev.chel_shev.nelly.repository.user.ClientHistoryRepository;
import dev.chel_shev.nelly.repository.finance.IncomeRepository;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incR;
    private final AccountService accS;
    private final ClientHistoryRepository cliHisR;

    public void save(IncomeEntity income, AccountEntity account) {
        account.addAccountBalance(income.getAmount());
        accS.save(account);
        AccountHistoryEntity entity = new AccountHistoryEntity(null, income.getAmount(), account.getAccountBalance(), "/income", income.getDate(), account);
        cliHisR.save(entity);
        incR.save(income);
    }
}
