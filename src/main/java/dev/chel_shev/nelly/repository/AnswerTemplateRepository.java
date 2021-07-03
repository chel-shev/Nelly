package dev.chel_shev.nelly.repository;

import dev.chel_shev.nelly.entity.AnswerTemplateEntity;
import dev.chel_shev.nelly.entity.CommandEntity;
import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AnswerTemplateRepository extends JpaRepository<AnswerTemplateEntity, Long> {

    List<AnswerTemplateEntity> findAllByLevelAndCommand(CommandLevel level, CommandEntity entity);

    Set<AnswerTemplateEntity> findAllByCommand(CommandEntity entity);
}
