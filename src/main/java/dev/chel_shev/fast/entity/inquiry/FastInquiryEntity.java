package dev.chel_shev.fast.entity.inquiry;

import dev.chel_shev.fast.inquiry.FastInquiry;
import dev.chel_shev.fast.type.FastKeyboardType;
import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.user.FastUserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "fast_inquiry")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FastInquiryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Integer messageId;
    private LocalDateTime date;
    private String answerMessage;
    private Integer answerMessageId;
    private FastKeyboardType keyboardType;
    private boolean closed;

    @ManyToOne
    private FastUserEntity user;
    @ManyToOne
    private FastCommandEntity command;


    protected FastInquiryEntity(FastInquiry i){
        this.id = i.getId();
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
