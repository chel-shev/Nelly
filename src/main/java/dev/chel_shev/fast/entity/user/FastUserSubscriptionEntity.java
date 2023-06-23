package dev.chel_shev.fast.entity.user;

import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.type.SubscriptionStatusType;
import dev.chel_shev.fast.type.SubscriptionType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "fast_user_subscription")
@IdClass(value = FastUserSubscriptionEntity.UserSubscriptionEntityId.class)
@AllArgsConstructor
@NoArgsConstructor
public class FastUserSubscriptionEntity {

    @Id
    @ManyToOne
    private FastUserEntity fastUser;

    @Id
    @ManyToOne
    private FastCommandEntity command;

    @ManyToOne
    private FastCommandEntity parentCommand;

    @Enumerated(EnumType.STRING)
    private SubscriptionType type;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatusType status;

    private String name;

    @Data
    public static class UserSubscriptionEntityId implements Serializable {
        private long fastUser;
        private long command;
    }
}
