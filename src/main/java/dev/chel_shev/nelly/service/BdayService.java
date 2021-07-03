package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.BdayEntity;
import dev.chel_shev.nelly.repository.BdayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BdayService {

    private final BdayRepository repository;

    public boolean isExist(String name, LocalDate date) {
        return repository.existsByNameAndDate(name, date);
    }

    public boolean isExist(String name) {
        return repository.existsByName(name);
    }

    public void save(BdayEntity entity) {
        repository.save(entity);
    }

    public void remove(String name) {
        repository.deleteAllByName(name);
    }
}
