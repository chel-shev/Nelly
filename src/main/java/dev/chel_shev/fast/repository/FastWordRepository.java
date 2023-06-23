package dev.chel_shev.fast.repository;

import dev.chel_shev.fast.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Locale;
import java.util.Set;


public interface FastWordRepository extends JpaRepository<WordEntity, Long> {

    Set<WordEntity> findAllByLocale(Locale locale);
}
