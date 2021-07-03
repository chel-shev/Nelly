package dev.chel_shev.nelly.entity;

import dev.chel_shev.nelly.inquiry.command.CommandLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answer_template")
@EqualsAndHashCode(exclude = {"id"})
public class AnswerTemplateEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String template;
    @Enumerated(EnumType.STRING)
    private CommandLevel level;

    @ManyToOne
    private CommandEntity command;
}
