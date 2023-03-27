package dev.chel_shev.nelly.entity.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.chel_shev.nelly.entity.finance.AccountEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.ZoneOffset;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "employee")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Long chatId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private ZoneOffset zoneOffset = ZoneOffset.of("+3");

    @ManyToOne
    private RoleEntity role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AccountEntity> accountList;

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