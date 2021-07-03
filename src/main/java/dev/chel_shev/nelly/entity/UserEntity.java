package dev.chel_shev.nelly.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String userName;

    private Long chatId;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<InquiryEntity> inquiryList;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval = true)
    private List<BdayEntity> bdayList;

    public UserEntity(Long id, String firstName, String lastName, String userName, Long chatId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.chatId = chatId;
    }

    public UserEntity(Message message) {
        this.firstName = message.getFrom().getFirstName();
        this.lastName = message.getFrom().getLastName();
        this.userName = message.getFrom().getUserName();
        this.chatId = message.getChatId();
    }
}