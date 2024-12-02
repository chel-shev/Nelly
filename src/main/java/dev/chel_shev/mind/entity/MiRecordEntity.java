package dev.chel_shev.mind.entity;

import dev.chel_shev.mind.type.RecordType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "record")
public class MiRecordEntity {

    @Id
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private RecordType recordType;

    @ManyToOne
    private MiViewEntity miView;
}
