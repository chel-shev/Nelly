package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.InquiryEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
    InquiryEntity findTopByUserOrderByDateDesc(UserEntity user);
}

