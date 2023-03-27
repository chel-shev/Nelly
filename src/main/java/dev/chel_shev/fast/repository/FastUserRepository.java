package dev.chel_shev.fast.repository;

import dev.chel_shev.fast.entity.user.FastUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FastUserRepository extends JpaRepository<FastUserEntity, Long> {
    Optional<FastUserEntity> findByChatId(String chatId);

    boolean existsByChatId(String chatId);

    FastUserEntity findByUserName(String username);
}
