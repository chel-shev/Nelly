package dev.chel_shev.fast.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;


@Setter
@Getter
@Entity
@Table(name = "fast_user")
@NoArgsConstructor
public class FastUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String secondName;
    private String userName;
    private String chatId;

    public FastUserEntity(Message message) {
        this.firstName = message.getFrom().getFirstName();
        this.secondName = message.getFrom().getLastName();
        this.userName = message.getFrom().getUserName();
        this.chatId = message.getChatId().toString();
    }
}
