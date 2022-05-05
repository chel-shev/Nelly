package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByChatId(Long chatId);

    boolean existsByChatId(Long chatId);

    boolean existsByUserName(String name);

    Optional<UserEntity> findByUserName(String name);

    Optional<UserEntity> findByUserNameOrEmail(String userName, String email);
}
