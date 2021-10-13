package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.AccountEntity;
import dev.chel_shev.nelly.entity.AccountHistoryEntity;
import dev.chel_shev.nelly.entity.LoanEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.repository.ClientHistoryRepository;
import dev.chel_shev.nelly.repository.LoanRepository;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loaR;
    private final AccountService accS;
    private final ClientHistoryRepository cliHisR;

    public Collection<LoanEntity> getLoanByClient(UserEntity user) {
        return loaR.findAllByAccountUser(user);
    }

    public void save(LoanEntity loan, AccountEntity account) {
        if (loan.getDirection())
            account.addAccountBalance(loan.getAmount());
        else
            account.subAccountBalance(loan.getAmount());
        accS.save(account);
        AccountHistoryEntity entity = new AccountHistoryEntity(null, loan.getAmount(), account.getAccountBalance(), InquiryType.LOAN, loan.getDateStart(), account);
        cliHisR.save(entity);
        loaR.save(loan);
    }
}
