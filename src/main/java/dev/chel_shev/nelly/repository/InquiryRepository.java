package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
    Optional<InquiryEntity> findTopByUserOrderByDateDesc(UserEntity user);
}

