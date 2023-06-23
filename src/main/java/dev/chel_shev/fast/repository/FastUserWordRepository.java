package dev.chel_shev.fast.repository;

import dev.chel_shev.fast.entity.UserWordEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FastUserWordRepository extends JpaRepository<UserWordEntity, Long> {

    Set<UserWordEntity> findAllByUser(FastUserEntity user);
}
