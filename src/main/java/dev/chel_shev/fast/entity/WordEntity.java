package dev.chel_shev.fast.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "word")
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    private Locale locale;

    private String transcription;

    public WordEntity(String word, Locale locale, String transcription) {
        this.word = word;
        this.locale = locale;
        this.transcription = transcription;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word=" + word +
                ", locale=" + locale +
                '}';
    }
}
