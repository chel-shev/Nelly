package dev.chel_shev.fast.repository;

import dev.chel_shev.fast.entity.WordTranslateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FastWordTranslateRepository extends JpaRepository<WordTranslateEntity, Long> {

    List<WordTranslateEntity> findAllByWord_Id(Long id);
}
