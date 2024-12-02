package dev.chel_shev.mind.service;

import dev.chel_shev.mind.entity.MiRecordEntity;
import dev.chel_shev.mind.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public MiRecordEntity getRecord(Long recordId) {
        return recordRepository.findById(recordId).orElse(null);
    }

    public MiRecordEntity addRecord(MiRecordEntity record) {
        return recordRepository.save(record);
    }
}
