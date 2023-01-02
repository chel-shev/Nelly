package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.finance.AccountHistoryEntity;
import dev.chel_shev.nelly.entity.finance.TransferEntity;
import dev.chel_shev.nelly.repository.ClientHistoryRepository;
import dev.chel_shev.nelly.repository.TransferRepository;
import dev.chel_shev.nelly.type.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository traR;
    private final AccountService accS;
    private final ClientHistoryRepository cliHisR;

    @Transactional
    public TransferEntity save(TransferEntity transfer) {
        transfer.getIn().addAccountBalance(transfer.getAmount());
        accS.save(transfer.getIn());
        AccountHistoryEntity entityIn = new AccountHistoryEntity(null, transfer.getAmount(), transfer.getIn().getAccountBalance(), InquiryType.TRANSFER, transfer.getDate(), transfer.getIn());
        cliHisR.save(entityIn);
        transfer.getOut().subAccountBalance(transfer.getAmount());
        accS.save(transfer.getOut());
        AccountHistoryEntity entityOut = new AccountHistoryEntity(null, transfer.getAmount(), transfer.getOut().getAccountBalance(), InquiryType.TRANSFER, transfer.getDate(), transfer.getOut());
        cliHisR.save(entityOut);
        return traR.save(transfer);
    }
}
