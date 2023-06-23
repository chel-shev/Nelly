package dev.chel_shev.fast.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(value = WordTranslateEntity.WordTranslateId.class)
@Table(name = "word_translate")
public class WordTranslateEntity {

    @Id
    @ManyToOne
    WordEntity word;

    @Id
    @ManyToOne
    WordEntity translate;


    @Data
    public static class WordTranslateId implements Serializable {
        private long word;
        private long translate;
    }
}
