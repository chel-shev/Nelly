package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByChatId(Long chatId);
    boolean existsByChatId(Long chatId);
}
