package dev.chel_shev.nelly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "command")
@EqualsAndHashCode(exclude = {"templateList", "inquiryList"})
public class CommandEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String command;

    @OneToMany(mappedBy = "command", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerTemplateEntity> templateList;

    @OneToMany(mappedBy = "command", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InquiryEntity> inquiryList;
}
