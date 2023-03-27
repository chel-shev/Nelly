package dev.chel_shev.fast.repository;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.type.FastInquiryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FastBotCommandRepository extends JpaRepository<FastCommandEntity, Long> {

    Optional<FastCommandEntity> findByName(String name);
    Optional<FastCommandEntity> findByLabel(String label);
    Optional<FastCommandEntity> findByLabelOrName(String label, String name);

    boolean existsByName(String name);
    boolean existsByLabel(String label);

    boolean existsByLabelAndTypeInOrNameAndTypeIn(String label, Collection<FastInquiryType> type_a, String name, Collection<FastInquiryType> type_b);

    List<FastCommandEntity> findAllByTypeIn(Collection<FastInquiryType> types);
}
