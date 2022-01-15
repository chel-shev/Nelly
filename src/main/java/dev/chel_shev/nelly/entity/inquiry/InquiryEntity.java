package dev.chel_shev.nelly.entity.inquiry;

import dev.chel_shev.nelly.entity.CommandEntity;
import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.bot.inquiry.Inquiry;
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
    private Integer messageId;
    private LocalDateTime date;
    private String answerMessage;
    private Integer answerMessageId;

    private boolean closed;

    @Enumerated(EnumType.STRING)
    private KeyboardType keyboardType;

    @ManyToOne
    private CommandEntity command;

    @ManyToOne
    private UserEntity user;

    protected InquiryEntity(Inquiry i){
        this.id = i.getId();
        this.type = i.getType();
        this.message = i.getMessage();
        this.messageId = i.getMessageId();
        this.date = i.getDate();
        this.answerMessage = i.getAnswerMessage();
        this.answerMessageId = i.getAnswerMessageId();
        this.closed = i.isClosed();
        this.keyboardType = i.getKeyboardType();
        this.command = i.getCommand();
        this.user = i.getUser();
    }
}
