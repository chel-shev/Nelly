package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.CommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<CommandEntity, Long> {

    CommandEntity findByCommand(String command);
}
