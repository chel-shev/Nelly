package dev.chel_shev.fast.repository;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import dev.chel_shev.fast.entity.inquiry.FastInquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FastBotInquiryRepository extends JpaRepository<FastInquiryEntity, Long> {
    Optional<FastInquiryEntity> findTopByUser_ChatIdOrderByDateDesc(String chatId);
    List<FastInquiryEntity> findAllByUser(FastUserEntity userEntity);
    Optional<FastInquiryEntity> findByUser_ChatIdAndAnswerMessageId(String chatId, Integer answerMessageId);
}

