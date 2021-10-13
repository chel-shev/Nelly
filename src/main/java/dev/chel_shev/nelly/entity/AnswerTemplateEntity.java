package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answer_template")
@EqualsAndHashCode(exclude = {"id"})
public class AnswerTemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String template;
    @Enumerated(EnumType.STRING)
    private CommandLevel level;

    @ManyToOne(fetch = FetchType.EAGER)
    private CommandEntity command;
}
