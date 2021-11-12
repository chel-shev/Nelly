package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.CommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandRepository extends JpaRepository<CommandEntity, Long> {

    Optional<CommandEntity> findByCommand(String command);
}
