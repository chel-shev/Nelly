package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.type.InquiryType;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "inquiry")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InquiryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InquiryType type;

    private String message;
    private LocalDateTime date;
    private String answerMessage;

    private boolean closed;

    @Enumerated(EnumType.STRING)
    private KeyboardType keyboardType;

    @ManyToOne
    private CommandEntity command;

    @ManyToOne
    private UserEntity user;
}
